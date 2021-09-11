package net.klnetwork.playerrolechecker.JDA.Events;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.klnetwork.playerrolechecker.Util.DiscordUtil;
import net.klnetwork.playerrolechecker.Util.OtherUtil;
import net.klnetwork.playerrolechecker.Util.SQLUtil;
import org.jetbrains.annotations.NotNull;

public class ForceJoinCommand extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (!event.getAuthor().isBot() && event.isFromType(ChannelType.TEXT) && DiscordUtil.ChannelChecker(event.getTextChannel().getId())) {
            if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                String[] args = event.getMessage().getContentRaw().split("\\s+");
                if (args.length == 3 && args[0].equals("!forcejoin")) {
                    String uuid = null;
                    try {
                        uuid = OtherUtil.getUUID(args[1]).toString();
                    } catch (Exception exception) {
                        event.getMessage().replyEmbeds(DiscordUtil.embedBuilder("ForceVerify.invalid-name", event.getMessage().getTimeCreated(), null, null).build()).queue();
                    }
                    String[] result = SQLUtil.getDiscordFromSQL(uuid);
                    if (result != null) {
                        event.getMessage().replyEmbeds(DiscordUtil.embedBuilder("ForceVerify.already-registered", event.getMessage().getTimeCreated(), result[0], result[1]).build()).queue();
                        return;
                    }
                    SQLUtil.putSQL(uuid,args[2]);
                    event.getMessage().replyEmbeds(DiscordUtil.embedBuilder("ForceVerify.success-register", event.getMessage().getTimeCreated(), uuid, args[2]).build()).queue();

                    DiscordUtil.AddRole(event.getGuild(), event.getMember());
                }
            }
        }
    }
}
