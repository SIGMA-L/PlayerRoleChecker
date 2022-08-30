package net.klnetwork.playerrolechecker.jda.command;

import net.klnetwork.playerrolechecker.api.data.common.PlayerData;
import net.klnetwork.playerrolechecker.api.discord.data.CommandData;
import net.klnetwork.playerrolechecker.api.discord.data.CommandMessage;
import net.klnetwork.playerrolechecker.api.enums.RemoveEventType;
import net.klnetwork.playerrolechecker.api.event.connector.RemoveEvent;
import net.klnetwork.playerrolechecker.api.utils.CommonUtils;
import net.klnetwork.playerrolechecker.table.PlayerDataSQL;
import net.klnetwork.playerrolechecker.util.DiscordUtil;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class RemoveCommand extends CommandMessage {
    public RemoveCommand(Plugin plugin) {
        super(plugin);
    }

    @Override
    public String getCommandName() {
        return "!remove";
    }

    @Override
    public String getPath() {
        return "RemoveCommand";
    }

    @Override
    public boolean isWork(CommandData data) {
        return true;
    }

    @Override
    public void onMessageReceiveEvent(CommandData event) throws Exception {
        if (event.length() > 0) {
            UUID uuid = CommonUtils.getUUID(event.getArgs().get(0));

            PlayerData data = PlayerDataSQL.getInstance().getDiscordId(uuid);

            if (data == null) {
                if (!callEvent(new RemoveEvent(null, null, event.getMessage(), RemoveEventType.NOT_REGISTERED)).isCancelled()) {
                    event.getMessage().replyEmbeds(DiscordUtil.embedBuilder("RemoveCommand.not-registered", event.getMessage().getTimeCreated(), null, null).build()).queue();
                }
            } else {
                RemoveEvent call = callEvent(new RemoveEvent(event.getGuild().retrieveMemberById(data.getDiscordId()).complete(), uuid, event.getMessage(), RemoveEventType.SUCCESS));

                if (!call.isCancelled()) {
                    event.getMessage().replyEmbeds(DiscordUtil.embedBuilder("RemoveCommand.success-remove", event.getMessage().getTimeCreated(), call.getUUID(), call.getMember().getId()).build()).queue();

                    PlayerDataSQL.getInstance().remove(call.getUUID(), call.getMember().getId());

                    if (call.getMember() != null) {
                        DiscordUtil.removeRole(call.getMember());
                    }
                }
            }
        } else if (!callEvent(new RemoveEvent(null, null, event.getMessage(), RemoveEventType.ARG_INVALID)).isCancelled()) {

        }
    }

    @Override
    public void onErrorCaught(CommandData event, Exception exception) {
        if (!callEvent(new RemoveEvent(null, null, event.getMessage(), RemoveEventType.UNKNOWN)).isCancelled()) {
            event.getMessage().replyEmbeds(DiscordUtil.embedBuilder("RemoveCommand.invalid-name", event.getMessage().getTimeCreated(), null, null).build()).queue();
        }
    }
}
