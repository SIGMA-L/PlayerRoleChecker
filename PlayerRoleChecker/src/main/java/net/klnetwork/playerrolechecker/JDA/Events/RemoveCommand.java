package net.klnetwork.playerrolechecker.JDA.Events;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.klnetwork.playerrolechecker.Util.DiscordUtil;
import net.klnetwork.playerrolechecker.Util.OtherUtil;
import net.klnetwork.playerrolechecker.Util.SQLUtil;
import org.jetbrains.annotations.NotNull;

public class RemoveCommand extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (!event.getAuthor().isBot() && event.isFromType(ChannelType.TEXT)) {
            if (event.getMember() != null && event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                String[] args = event.getMessage().getContentRaw().split("\\s+");
                if (args.length == 2 && args[0].equals("!remove")) {
                    String uuid = null;
                    try {
                        uuid = OtherUtil.getUUID(args[1]).toString();
                    } catch (Exception exception) {
                        event.getMessage().replyEmbeds(DiscordUtil.embedBuilder("RemoveCommand.invalid-name", event.getMessage().getTimeCreated(), null, null).build()).queue();
                    }
                    String finalUUID = uuid;
                    SQLUtil.getDiscordFromSQL(uuid, result -> {
                        if (result == null) {
                            event.getMessage().replyEmbeds(DiscordUtil.embedBuilder("RemoveCommand.not-registered", event.getMessage().getTimeCreated(), null, null).build()).queue();
                            return;
                        }
                        SQLUtil.removeSQL(result[0], result[1]);
                        DiscordUtil.RemoveRole(event.getGuild(), event.getMember());

                        event.getMessage().replyEmbeds(DiscordUtil.embedBuilder("RemoveCommand.success-remove", event.getMessage().getTimeCreated(), finalUUID, result[1]).build()).queue();
                    });
                }
            }
        }
    }
}
