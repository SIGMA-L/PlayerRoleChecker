package net.klnetwork.playerrolechecker.jda.command;

import net.klnetwork.playerrolechecker.api.discord.data.CommandData;
import net.klnetwork.playerrolechecker.api.discord.data.CommandMessage;
import net.klnetwork.playerrolechecker.api.enums.JoinEventType;
import net.klnetwork.playerrolechecker.api.event.JoinEvent;
import net.klnetwork.playerrolechecker.table.LocalSQL;
import net.klnetwork.playerrolechecker.table.PlayerData;
import net.klnetwork.playerrolechecker.util.DiscordUtil;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

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
        final int code = Integer.parseInt(event.getCommandName());

        String uuid = LocalSQL.getInstance().getUUID(code);

        if (uuid == null) {
            if (!callEvent(new JoinEvent(null, code, event.getMessage(), JoinEventType.UNKNOWN_NUMBER)).isCancelled()) {
                event.getMessage().replyEmbeds(DiscordUtil.embedBuilder("JoinCommand.invalid-number", event.getMessage().getTimeCreated(), null, null).build()).queue();
            }
        } else {
            String discordId = PlayerData.getInstance().getDiscordId(uuid);

            if (discordId != null) {
                if (!callEvent(new JoinEvent(UUID.fromString(uuid), code, event.getMessage(), JoinEventType.ALREADY_REGISTERED)).isCancelled()) {
                    event.getMessage().replyEmbeds(DiscordUtil.embedBuilder("JoinCommand.already-registered", event.getMessage().getTimeCreated(), uuid, discordId).build()).queue();

                    //セキュリティー上の問題
                    LocalSQL.getInstance().remove(uuid, code);
                }
            } else {
                JoinEvent call = callEvent(new JoinEvent(UUID.fromString(uuid), code, event.getMessage(), JoinEventType.SUCCESS));

                if (!call.isCancelled()) {
                    LocalSQL.getInstance().remove(call.getUUID(), call.getCode());
                    PlayerData.getInstance().put(call.getUUID(), call.getMember().getId());

                    event.getMessage().replyEmbeds(DiscordUtil.embedBuilder("JoinCommand.success-register", event.getMessage().getTimeCreated(), call.getUUID(), call.getMember().getId()).build()).queue();

                    //todo: recode
                    DiscordUtil.sendMessageToChannel(DiscordUtil.embedBuilder("JoinCommand.sendmessage", event.getMessage().getTimeCreated(), call.getUUID(), call.getMember().getId()));

                    DiscordUtil.addRole(event.getGuild(), event.getMember());
                }
            }
        }
    }

    @Override
    public void onErrorCaught(CommandData event, Exception exception) {
        if (exception instanceof NumberFormatException) {
            if (!callEvent(new JoinEvent(null, Integer.MIN_VALUE, event.getMessage(), JoinEventType.NOT_NUMBER)).isCancelled()) {
                event.getMessage().replyEmbeds(DiscordUtil.embedBuilder("JoinCommand.not-number", event.getMessage().getTimeCreated(), null, null).build()).queue();
            }
        } else if (!callEvent(new JoinEvent(null, Integer.MIN_VALUE, event.getMessage(), JoinEventType.UNKNOWN)).isCancelled()) {
            //todo: add message
        }
    }
}
