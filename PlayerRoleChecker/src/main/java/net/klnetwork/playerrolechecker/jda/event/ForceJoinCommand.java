package net.klnetwork.playerrolechecker.jda.event;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.klnetwork.playerrolechecker.api.ForceJoinEvent;
import net.klnetwork.playerrolechecker.util.DiscordUtil;
import net.klnetwork.playerrolechecker.util.OtherUtil;
import net.klnetwork.playerrolechecker.util.SQLUtil;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ForceJoinCommand extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (!event.getAuthor().isBot() && event.isFromType(ChannelType.TEXT) && DiscordUtil.limitChecker(event.getTextChannel().getId())) {
            if (event.getMember() != null && event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                String[] args = event.getMessage().getContentRaw().split("\\s+");
                if (args.length == 3 && args[0].equals("!forcejoin")) {

                    try {
                        UUID uuid = OtherUtil.getUUID(args[1]);

                        SQLUtil.asyncDiscordId(uuid, discordId -> {
                            if (discordId != null) {
                                event.getMessage().replyEmbeds(DiscordUtil.embedBuilder("ForceJoinCommand.already-registered", event.getMessage().getTimeCreated(), String.valueOf(uuid), discordId).build()).queue();
                            } else {

                                ForceJoinEvent forceJoinEvent = new ForceJoinEvent(args[2], uuid, event.getMessage());
                                Bukkit.getPluginManager().callEvent(forceJoinEvent);

                                if (!forceJoinEvent.isCancelled()) {

                                    UUID resultUUID = forceJoinEvent.getUUID();
                                    String resultDiscordID = forceJoinEvent.getMemberId();

                                    SQLUtil.putSQL(resultUUID, resultDiscordID);

                                    event.getMessage().replyEmbeds(DiscordUtil.embedBuilder("ForceJoinCommand.success-register", event.getMessage().getTimeCreated(), String.valueOf(resultUUID), resultDiscordID).build()).queue();

                                    Member member = event.getGuild().retrieveMemberById(forceJoinEvent.getMemberId()).complete();

                                    if (member != null) {
                                        DiscordUtil.addRole(event.getGuild(), member);
                                    }
                                }
                            }
                        });

                    } catch (Exception ex) {
                        event.getMessage().replyEmbeds(DiscordUtil.embedBuilder("ForceJoinCommand.invalid-name", event.getMessage().getTimeCreated(), null, null).build()).queue();
                    }
                }
            }
        }
    }
}
