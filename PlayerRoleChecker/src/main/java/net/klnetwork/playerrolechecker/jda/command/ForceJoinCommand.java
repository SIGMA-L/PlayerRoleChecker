package net.klnetwork.playerrolechecker.jda.command;

import net.dv8tion.jda.api.entities.Member;
import net.klnetwork.playerrolechecker.api.data.common.PlayerData;
import net.klnetwork.playerrolechecker.api.discord.data.CommandData;
import net.klnetwork.playerrolechecker.api.discord.data.CommandMessage;
import net.klnetwork.playerrolechecker.api.enums.ForceJoinEventType;
import net.klnetwork.playerrolechecker.api.event.connector.ForceJoinEvent;
import net.klnetwork.playerrolechecker.api.utils.CommonUtils;
import net.klnetwork.playerrolechecker.table.PlayerDataSQL;
import net.klnetwork.playerrolechecker.util.DiscordUtil;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class ForceJoinCommand extends CommandMessage {
    public ForceJoinCommand(Plugin plugin) {
        super(plugin);
    }

    @Override
    public String getCommandName() {
        //todo: customizable!
        return "!forcejoin";
    }

    @Override
    public String getPath() {
        return "ForceJoinCommand";
    }

    @Override
    public boolean isWork(CommandData data) {
        return true;
    }

    @Override
    public void onMessageReceiveEvent(CommandData event) throws Exception {
        if (event.length() > 1) {
            UUID uuid = CommonUtils.getUUID(event.getArgs().get(0));

            PlayerData data = PlayerDataSQL.getInstance().getDiscordId(uuid);

            if (data != null) {
                if (!callEvent(new ForceJoinEvent(event.getArgs().get(1), uuid, event.getMessage(), ForceJoinEventType.ALREADY_REGISTERED)).isCancelled()) {
                    event.getMessage().replyEmbeds(DiscordUtil.embedBuilder("ForceJoinCommand.already-registered", event.getMessage().getTimeCreated(), uuid, data.getDiscordId()).build()).queue();
                }
            } else {
                ForceJoinEvent call = callEvent(new ForceJoinEvent(event.getArgs().get(1), uuid, event.getMessage(), ForceJoinEventType.SUCCESS));

                if (!call.isCancelled()) {
                    event.getMessage().replyEmbeds(DiscordUtil.embedBuilder("ForceJoinCommand.success-register", event.getMessage().getTimeCreated(), call.getUUID(), call.getMemberId()).build()).queue();

                    PlayerDataSQL.getInstance().put(call.getUUID(), call.getMemberId(), false);

                    Member member = call.getGuild().retrieveMemberById(call.getMemberId()).complete();

                    if (member != null) {
                        //todo: recode!
                        DiscordUtil.addRole(call.getGuild(), member);
                    }
                }
            }
        } else if (!callEvent(new ForceJoinEvent(null, null, event.getMessage(), ForceJoinEventType.ARG_INVALID)).isCancelled()) {
            //todo: add message
        }
    }

    @Override
    public void onErrorCaught(CommandData event, Exception exception) {
        if (!callEvent(new ForceJoinEvent(null, null, event.getMessage(), ForceJoinEventType.UNKNOWN)).isCancelled()) {
            event.getMessage().replyEmbeds(DiscordUtil.embedBuilder("ForceJoinCommand.invalid-name", event.getMessage().getTimeCreated(), null, null).build()).queue();
        }
    }
}