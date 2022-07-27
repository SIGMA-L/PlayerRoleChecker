package net.klnetwork.playerrolechecker.jda.command;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.klnetwork.playerrolechecker.api.discord.data.CommandData;
import net.klnetwork.playerrolechecker.api.discord.data.CommandMessage;
import net.klnetwork.playerrolechecker.api.enums.RemoveEventType;
import net.klnetwork.playerrolechecker.api.event.RemoveEvent;
import net.klnetwork.playerrolechecker.api.utils.CommonUtils;
import net.klnetwork.playerrolechecker.table.PlayerData;
import net.klnetwork.playerrolechecker.util.DiscordUtil;

import java.util.UUID;

public class RemoveCommand extends CommandMessage {
    @Override
    public String getCommandName() {
        return "!remove";
    }

    @Override
    public Permission[] requirePermission() {
        return new Permission[] {Permission.ADMINISTRATOR};
    }

    @Override
    public boolean isWork(CommandData data) {
        return false;
    }

    @Override
    public void onMessageReceiveEvent(CommandData event) throws Exception {
        if (event.length() > 0) {
            UUID uuid = CommonUtils.getUUID(event.getArgs().get(0));

            PlayerData.getInstance().asyncDiscordId(uuid, result -> {
                if (result == null) {
                    if (!callEvent(new RemoveEvent(null, null, event.getMessage(), RemoveEventType.NOT_REGISTERED)).isCancelled()) {
                        event.getMessage().replyEmbeds(DiscordUtil.embedBuilder("RemoveCommand.not-registered", event.getMessage().getTimeCreated(), null, null).build()).queue();
                    }
                } else {
                    Member member = event.getGuild().retrieveMemberById(result).complete();

                    RemoveEvent removeEvent = callEvent(new RemoveEvent(member, uuid, event.getMessage(), RemoveEventType.SUCCESS));

                    if (!removeEvent.isCancelled()) {

                        UUID resultUUID = removeEvent.getUUID();
                        Member resultMember = removeEvent.getMember();

                        event.getMessage().replyEmbeds(DiscordUtil.embedBuilder("JoinCommand.success-register", event.getMessage().getTimeCreated(), String.valueOf(resultUUID), resultMember.getId()).build()).queue();

                        PlayerData.getInstance().remove(resultUUID, resultMember.getId());

                        if (member != null) DiscordUtil.removeRole(event.getGuild(), resultMember);
                    }
                }
            });
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
