package net.klnetwork.playerrolechecker.jda.command;

import net.klnetwork.playerrolechecker.api.data.common.PlayerData;
import net.klnetwork.playerrolechecker.api.data.common.TemporaryData;
import net.klnetwork.playerrolechecker.api.discord.data.CommandData;
import net.klnetwork.playerrolechecker.api.discord.data.CommandMessage;
import net.klnetwork.playerrolechecker.api.enums.JoinEventType;
import net.klnetwork.playerrolechecker.api.event.connector.JoinEvent;
import net.klnetwork.playerrolechecker.api.utils.CommonUtils;
import net.klnetwork.playerrolechecker.table.LocalSQL;
import net.klnetwork.playerrolechecker.table.PlayerDataSQL;
import net.klnetwork.playerrolechecker.util.DiscordUtil;
import org.bukkit.plugin.Plugin;

public class JoinCommand extends CommandMessage {
    public JoinCommand(Plugin plugin) {
        super(plugin);
    }

    @Override
    public String getCommandName() {
        return null;
    }

    @Override
    public String getPath() {
        return "JoinCommand";
    }

    @Override
    public boolean isWork(CommandData data) {
        return true;
    }

    @Override
    public void onMessageReceiveEvent(CommandData event) throws Exception {
        String commandName = selectCommandName();

        final int code = Integer.parseInt(commandName == null || commandName.isEmpty() ? event.getCommandName() : event.getArgs().get(0));
        TemporaryData temp = LocalSQL.getInstance().getUUID(code);

        if (temp == null) {
            if (!callEvent(new JoinEvent(null, code, false, event.getMessage(),  JoinEventType.UNKNOWN_NUMBER)).isCancelled()) {
                event.getMessage().replyEmbeds(DiscordUtil.embedBuilder("JoinCommand.invalid-number", event.getMessage().getTimeCreated(), null, null).build()).queue();
            }
        } else {
            PlayerData data = PlayerDataSQL.getInstance().getDiscordId(temp.getUUID());

            if (data != null) {
                if (!callEvent(new JoinEvent(data.getUUID(), temp.getCode(), CommonUtils.isFloodgateUser(data.getUUID()), event.getMessage(), JoinEventType.ALREADY_REGISTERED)).isCancelled()) {
                    event.getMessage().replyEmbeds(DiscordUtil.embedBuilder("JoinCommand.already-registered", event.getMessage().getTimeCreated(), temp, data.getDiscordId()).build()).queue();

                    //セキュリティー上の問題
                    LocalSQL.getInstance().remove(temp.getUUID(), temp.getCode());
                }
            } else {
                JoinEvent call = callEvent(new JoinEvent(temp.getUUID(), temp.getCode(), temp.isBedrock(), event.getMessage(), JoinEventType.SUCCESS));

                if (!call.isCancelled()) {
                    LocalSQL.getInstance().remove(call.getUUID(), call.getCode());
                    PlayerDataSQL.getInstance().put(call.getUUID(), call.getMember().getId(), call.isBedrock());

                    event.getMessage().replyEmbeds(DiscordUtil.embedBuilder("JoinCommand.success-register", event.getMessage().getTimeCreated(), call.getUUID(), call.getMember().getId()).build()).queue();

                    DiscordUtil.sendMessageToChannel(DiscordUtil.embedBuilder("JoinCommand.sendmessage", event.getMessage().getTimeCreated(), call.getUUID(), call.getMember().getId()));

                    DiscordUtil.addRole(event.getGuild(), event.getMember());
                }
            }
        }
    }

    @Override
    public void onErrorCaught(CommandData event, Exception exception) {
        if (exception instanceof NumberFormatException) {
            if (!callEvent(new JoinEvent(null, Integer.MIN_VALUE, false, event.getMessage(), JoinEventType.NOT_NUMBER)).isCancelled()) {
                event.getMessage().replyEmbeds(DiscordUtil.embedBuilder("JoinCommand.not-number", event.getMessage().getTimeCreated(), null, null).build()).queue();
            }
        } else if (!callEvent(new JoinEvent(null, Integer.MIN_VALUE, false, event.getMessage(), JoinEventType.UNKNOWN)).isCancelled()) {
            //todo: add message
        }
    }
}
