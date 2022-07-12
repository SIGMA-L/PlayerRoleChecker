package net.klnetwork.playerrolechecker.jda.event;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.klnetwork.playerrolechecker.api.event.RemoveEvent;
import net.klnetwork.playerrolechecker.table.PlayerData;
import net.klnetwork.playerrolechecker.util.DiscordUtil;
import net.klnetwork.playerrolechecker.util.OtherUtil;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class RemoveCommand extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (!event.getAuthor().isBot() && event.isFromType(ChannelType.TEXT) && DiscordUtil.limitChecker(event.getTextChannel().getId())) {
            if (event.getMember() != null && event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                String[] args = event.getMessage().getContentRaw().split("\\s+");
                if (args.length == 2 && args[0].equals("!remove")) {

                    try {
                        UUID uuid = OtherUtil.getUUID(args[1]);

                        PlayerData.getInstance().asyncDiscordId(uuid, result -> {
                            if (result == null) {
                                event.getMessage().replyEmbeds(DiscordUtil.embedBuilder("RemoveCommand.not-registered", event.getMessage().getTimeCreated(), null, null).build()).queue();
                            } else {
                                Member member = event.getGuild().retrieveMemberById(result).complete();

                                RemoveEvent removeEvent = new RemoveEvent(member, uuid, event.getMessage());
                                Bukkit.getPluginManager().callEvent(removeEvent);

                                if (!removeEvent.isCancelled()) {

                                    UUID resultUUID = removeEvent.getUUID();
                                    Member resultMember = removeEvent.getMember();

                                    event.getMessage().replyEmbeds(DiscordUtil.embedBuilder("JoinCommand.success-register", event.getMessage().getTimeCreated(), String.valueOf(resultUUID), resultMember.getId()).build()).queue();

                                    PlayerData.getInstance().remove(resultUUID, resultMember.getId());

                                    if (member != null) DiscordUtil.removeRole(event.getGuild(), resultMember);
                                }
                            }
                        });
                    } catch (Exception ex) {
                        event.getMessage().replyEmbeds(DiscordUtil.embedBuilder("RemoveCommand.invalid-name", event.getMessage().getTimeCreated(), null, null).build()).queue();
                    }
                }
            }
        }
    }
}