package net.klnetwork.playerrolechecker.jda.command;

import net.dv8tion.jda.api.entities.Member;
import net.klnetwork.playerrolechecker.api.discord.data.CommandData;
import net.klnetwork.playerrolechecker.api.discord.data.CommandMessage;
import net.klnetwork.playerrolechecker.api.enums.ForceJoinEventType;
import net.klnetwork.playerrolechecker.api.event.ForceJoinEvent;
import net.klnetwork.playerrolechecker.api.utils.CommonUtils;
import net.klnetwork.playerrolechecker.table.PlayerData;
import net.klnetwork.playerrolechecker.util.DiscordUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import java.util.UUID;

public class ForceJoinCommand implements CommandMessage {
    @Override
    public String getCommandName() {
        //todo: customizable!
        return "!forcejoin";
    }

    @Override
    public void onMessageReceiveEvent(CommandData event) throws Exception {
        if (event.length() > 2) {
            UUID uuid = CommonUtils.getUUID(event.getArgs().get(0));

            PlayerData.getInstance().asyncDiscordId(uuid, discordId -> {

                if (discordId != null) {
                    if (!callEvent(new ForceJoinEvent(event.getArgs().get(1), uuid, event.getMessage(), ForceJoinEventType.ALREADY_REGISTERED)).isCancelled()) {
                        event.getMessage().replyEmbeds(DiscordUtil.embedBuilder("ForceJoinCommand.already-registered", event.getMessage().getTimeCreated(), uuid, discordId).build()).queue();
                    }
                } else {
                    ForceJoinEvent call = callEvent(new ForceJoinEvent(event.getArgs().get(1), uuid, event.getMessage(), ForceJoinEventType.SUCCESS));

                    if (!call.isCancelled()) {
                        PlayerData.getInstance().put(call.getUUID(), call.getMemberId());

                        Member member = event.getGuild().retrieveMemberById(call.getMemberId()).complete();

                        if (member != null) {
                            DiscordUtil.addRole(event.getGuild(), member);
                        }
                    }
                }
            });
        } else if (!callEvent(new ForceJoinEvent(null, null, event.getMessage(), ForceJoinEventType.ARG_INVALID)).isCancelled()) {
            //todo: add message
        }
    }

    public <T> T callEvent(T event) {
        if (event instanceof Event) {
            Bukkit.getPluginManager().callEvent((Event) event);
        }

        return event;
    }

    @Override
    public void onErrorCaught(CommandData event, Exception exception) {
        if (!callEvent(new ForceJoinEvent(null, null, event.getMessage(), ForceJoinEventType.UNKNOWN)).isCancelled()) {
            event.getMessage().replyEmbeds(DiscordUtil.embedBuilder("ForceJoinCommand.invalid-name", event.getMessage().getTimeCreated(), null, null).build()).queue();
        }
    }
}