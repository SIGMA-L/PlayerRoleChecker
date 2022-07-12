package net.klnetwork.playerrolechecker.jda.event;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.klnetwork.playerrolechecker.api.event.JoinEvent;
import net.klnetwork.playerrolechecker.table.PlayerData;
import net.klnetwork.playerrolechecker.util.DiscordUtil;
import net.klnetwork.playerrolechecker.table.LocalSQL;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;


public class JoinCommand extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (!event.getAuthor().isBot() && event.isFromType(ChannelType.TEXT) && DiscordUtil.channelChecker(event.getTextChannel().getId())) {
            String[] args = event.getMessage().getContentRaw().split("\\s+");
            if (args.length == 1) {

                try {
                    final int code = Integer.parseInt(args[0]);

                    String uuid = LocalSQL.getInstance().getUUID(code);

                    if (uuid == null) {
                        event.getMessage().replyEmbeds(DiscordUtil.embedBuilder("JoinCommand.invalid-number", event.getMessage().getTimeCreated(), null, null).build()).queue();
                    } else {
                        PlayerData.getInstance().asyncDiscordId(uuid, already -> {
                            if (already != null) {
                                event.getMessage().replyEmbeds(DiscordUtil.embedBuilder("JoinCommand.already-registered", event.getMessage().getTimeCreated(), uuid, already).build()).queue();

                                //Security Issues
                                LocalSQL.getInstance().remove(uuid, code);
                            } else {

                                JoinEvent joinEvent = new JoinEvent(UUID.fromString(uuid), code, event.getMessage());
                                Bukkit.getPluginManager().callEvent(joinEvent);

                                if (!joinEvent.isCancelled()) {
                                    Member resultMember = joinEvent.getMember();
                                    UUID resultUUID = joinEvent.getUUID();

                                    LocalSQL.getInstance().remove(resultUUID, joinEvent.getCode());
                                    PlayerData.getInstance().put(resultUUID, resultMember.getId());

                                    event.getMessage().replyEmbeds(DiscordUtil.embedBuilder("JoinCommand.success-register", event.getMessage().getTimeCreated(), String.valueOf(resultUUID), resultMember.getId()).build()).queue();
                                    DiscordUtil.sendMessageToChannel(DiscordUtil.embedBuilder("JoinCommand.sendmessage", event.getMessage().getTimeCreated(), String.valueOf(resultUUID), resultMember.getId()));

                                    DiscordUtil.addRole(event.getGuild(), event.getMember());
                                }
                            }
                        });
                    }

                } catch (NumberFormatException ex) {
                    event.getMessage().replyEmbeds(DiscordUtil.embedBuilder("JoinCommand.not-number", event.getMessage().getTimeCreated(), null, null).build()).queue();
                }

            } else {
                event.getMessage().replyEmbeds(DiscordUtil.embedBuilder("JoinCommand.length-big", event.getMessage().getTimeCreated(), null, null).build()).queue();
            }
        }
    }
}