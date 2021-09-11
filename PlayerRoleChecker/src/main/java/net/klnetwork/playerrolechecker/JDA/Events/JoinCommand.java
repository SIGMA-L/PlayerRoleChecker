package net.klnetwork.playerrolechecker.JDA.Events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.klnetwork.playerrolechecker.Util.DiscordUtil;
import net.klnetwork.playerrolechecker.Util.SQLUtil;
import net.klnetwork.playerrolechecker.Util.SQLiteUtil;
import org.jetbrains.annotations.NotNull;


public class JoinCommand extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (!event.getAuthor().isBot() && event.isFromType(ChannelType.TEXT) && DiscordUtil.ChannelChecker(event.getTextChannel().getId())) {
            String[] args = event.getMessage().getContentRaw().split("\\s+");
            if (args.length == 1) {
                int integer;
                try {
                    integer = Integer.parseInt(args[0]);
                } catch (Exception e) {
                    event.getMessage().replyEmbeds(DiscordUtil.embedBuilder("RemoveCommand.invalid-name", event.getMessage().getTimeCreated(), null, null).build()).queue();
                    return;
                }
                String[] result = SQLiteUtil.getUUIDFromSQLite(Integer.toString(integer));
                if (result == null) {
                    event.getMessage().replyEmbeds(DiscordUtil.embedBuilder("VerifyCommand.invalid-number", event.getMessage().getTimeCreated(), null, null).build()).queue();
                    return;
                }
                String[] alreadyUUID = SQLUtil.getDiscordFromSQL(result[0]);
                if (alreadyUUID != null) {
                    event.getMessage().replyEmbeds(DiscordUtil.embedBuilder("VerifyCommand.already-registered", event.getMessage().getTimeCreated(), alreadyUUID[0], alreadyUUID[1]).build()).queue();
                    return;
                }
                SQLiteUtil.removeSQLite(result[0], result[1]);
                SQLUtil.putSQL(result[0], event.getAuthor().getId());

                String discordID = event.getAuthor().getId();

                EmbedBuilder embedBuilder = DiscordUtil.embedBuilder("VerifyCommand.success-register", event.getMessage().getTimeCreated(), result[0], discordID);
                event.getMessage().replyEmbeds(embedBuilder.build()).queue();

                DiscordUtil.sendMessageToChannel(DiscordUtil.embedBuilder("VerifyCommand.sendmessage", event.getMessage().getTimeCreated(), result[0], discordID));
                DiscordUtil.AddRole(event.getGuild(), event.getMember());
            } else {
                event.getMessage().replyEmbeds(DiscordUtil.embedBuilder("VerifyCommand.length-big", event.getMessage().getTimeCreated(), null, null).build()).queue();
            }
        }
    }
}