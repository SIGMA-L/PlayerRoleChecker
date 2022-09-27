package net.klnetwork.playerrolechecker.jda.command;

import net.dv8tion.jda.internal.utils.tuple.Pair;
import net.klnetwork.playerrolechecker.PlayerRoleChecker;
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
            if (!callEvent(new JoinEvent(null, code, false, event.getMessage(), JoinEventType.UNKNOWN_NUMBER)).isCancelled()) {
                event.getMessage().replyEmbeds(DiscordUtil.embedBuilder("JoinCommand.invalid-number", event.getMessage().getTimeCreated(), null, null).build()).queue();
            }
        } else {
            Pair<Integer, PlayerData> pair = PlayerRoleChecker.INSTANCE.getConfigManager().canRegisterUnlimitedAccount()
                    ? PlayerDataSQL.getInstance().getSize(event.getMember().getId()) : null;

            if (pair != null && pair.getLeft() > PlayerRoleChecker.INSTANCE.getConfigManager().getAccountPerDiscord()) {
                JoinEvent call = callEvent(new JoinEvent(pair.getRight().getUUID(), pair.getRight().getDiscordId(), temp.getCode(), pair.getRight().isBedrock(), event.getMessage(), JoinEventType.MAX_ACCOUNT_REGISTER));

                if (!call.isCancelled()) {
                    event.reply(DiscordUtil.createEmbedMessage("JoinCommand.max-account", call.getUUID(), call.getMemberId(), call.isBedrock()),
                            event.getSkin(call.getUUID(), call.isBedrock() && PlayerRoleChecker.INSTANCE.getConfig().getBoolean("JoinCommand.requestBedrockSkin")));

                    //セキュリティー上の問題
                    LocalSQL.getInstance().remove(temp.getUUID(), temp.getCode());
                }
            } else {
                PlayerData data = PlayerDataSQL.getInstance().getDiscordId(temp.getUUID());

                if (data != null) {
                    JoinEvent call = callEvent(new JoinEvent(data.getUUID(), data.getDiscordId(), temp.getCode(), CommonUtils.isFloodgateUser(data.getUUID()), event.getMessage(), JoinEventType.ALREADY_REGISTERED));

                    if (!call.isCancelled()) {
                        event.reply(DiscordUtil.createEmbedMessage("JoinCommand.already-registered", call.getUUID(), call.getMemberId(), call.isBedrock()),
                                event.getSkin(call.getUUID(), call.isBedrock() && PlayerRoleChecker.INSTANCE.getConfig().getBoolean("JoinCommand.requestBedrockSkin")));

                        //セキュリティー上の問題
                        LocalSQL.getInstance().remove(temp.getUUID(), temp.getCode());
                    }
                } else {
                    JoinEvent call = callEvent(new JoinEvent(temp.getUUID(), temp.getCode(), temp.isBedrock(), event.getMessage(), JoinEventType.SUCCESS));

                    if (!call.isCancelled()) {
                        LocalSQL.getInstance().remove(call.getUUID(), call.getCode());
                        PlayerDataSQL.getInstance().put(call.getUUID(), call.getMember().getId(), call.isBedrock());

                        //too big length>>>
                        event.reply(DiscordUtil.createEmbedMessage("JoinCommand.success-register", call.getUUID(), call.getMember().getId(), call.isBedrock()),
                                event.getSkin(call.getUUID(), call.isBedrock() && PlayerRoleChecker.INSTANCE.getConfig().getBoolean("JoinCommand.requestBedrockSkin")));

                        DiscordUtil.sendMessage(DiscordUtil.createEmbedMessage("JoinCommand.sendmessage", call.getUUID(), call.getMember().getId(), call.isBedrock()),
                                event.getSkin(call.getUUID(), call.isBedrock() && PlayerRoleChecker.INSTANCE.getConfig().getBoolean("JoinCommand.requestBedrockSkin")));

                        DiscordUtil.addRole(event.getMember());
                    }
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