package net.klnetwork.playerrolecheckerconnector.event;

import net.dv8tion.jda.api.entities.Guild;
import net.klnetwork.playerrolechecker.api.data.common.JoinHandler;
import net.klnetwork.playerrolechecker.api.data.common.PlayerData;
import net.klnetwork.playerrolechecker.api.enums.CheckResultEnum;
import net.klnetwork.playerrolechecker.api.enums.SkippedReasonEnum;
import net.klnetwork.playerrolechecker.api.event.checker.CheckResultEvent;
import net.klnetwork.playerrolechecker.api.event.checker.CheckSkippedEvent;
import net.klnetwork.playerrolechecker.api.event.checker.CheckStartEvent;
import net.klnetwork.playerrolechecker.api.utils.CommonUtils;
import net.klnetwork.playerrolecheckerconnector.PlayerRoleCheckerConnector;
import net.klnetwork.playerrolecheckerconnector.table.LocalSQL;
import net.klnetwork.playerrolecheckerconnector.table.PlayerDataSQL;
import org.bukkit.Bukkit;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent extends JoinHandler {
    @Override
    public void onPreLoginEvent(AsyncPlayerPreLoginEvent event) {
        if (PlayerRoleCheckerConnector.INSTANCE.getConfigManager().isWhitelistSkipped()
                && Bukkit.getWhitelistedPlayers().stream().anyMatch(player -> event.getUniqueId().equals(player.getUniqueId()))
                && !callEvent(new CheckSkippedEvent(event, SkippedReasonEnum.WHITELIST).isCancelled())) {
            return;
        }

        if (!PlayerRoleCheckerConnector.INSTANCE.getConfigManager().isJoinMode()
                && !callEvent(new CheckSkippedEvent(event, SkippedReasonEnum.SKIP_MODE)).isCancelled()) {
            return;
        }

        /* todo: recode localSQL */
        if (LocalSQL.getInstance().isCreated()
                && LocalSQL.getInstance().hasUUID(event.getUniqueId())
                && !callEvent(new CheckSkippedEvent(event, SkippedReasonEnum.BYPASS)).isCancelled()) {
            return;
        }

        PlayerData data = PlayerDataSQL.getInstance().getDiscordId(event.getUniqueId(), CommonUtils.isFloodgateUser(event.getUniqueId()));

        if (!callEvent(new CheckStartEvent(event, data)).isCancelled() && isJoin(event, data)) {
            disallow(event, PlayerRoleCheckerConnector.INSTANCE.getConfig().getStringList("Minecraft.kickMessage"));
        }
    }

    public boolean isJoin(AsyncPlayerPreLoginEvent event, PlayerData data) {
        if (data == null) {
            return callEvent(new CheckResultEvent(event, null, CheckResultEnum.NOT_REGISTERED)).getResult();
        } else {
            Guild guild = PlayerRoleCheckerConnector.INSTANCE.getJDA().getGuildById(PlayerRoleCheckerConnector.INSTANCE.getConfig().getLong("GuildID"));

            if (guild == null) {
                //new method
                if (CommonUtils.hasRole(PlayerRoleCheckerConnector.INSTANCE.getJDA().getRoleById(PlayerRoleCheckerConnector.INSTANCE.getConfigManager().getRoleList().get(0))
                        .getGuild().retrieveMemberById(data.getDiscordId())
                        .complete().getRoles(), PlayerRoleCheckerConnector.INSTANCE.getConfigManager().getRoleList())) {
                    return callEvent(new CheckResultEvent(event, data, CheckResultEnum.SUCCESS)).getResult();
                }

                //deprecated! don't use!
                /*for (Guild g : PlayerRoleCheckerConnector.INSTANCE.getJDA().getGuilds()) {
                    if (CommonUtils.hasRole(g.retrieveMemberById(data.getDiscordId()).complete().getRoles(), PlayerRoleCheckerConnector.INSTANCE.getConfigManager().getRoleList())) {
                        return callEvent(new CheckResultEvent(data, CheckResultEnum.SUCCESS)).getResult();
                    }
                }*/

                return callEvent(new CheckResultEvent(event, data, CheckResultEnum.GUILD_IS_INVALID)).getResult();
            } else {
                if (CommonUtils.hasRole(guild.retrieveMemberById(data.getDiscordId()).complete().getRoles(), PlayerRoleCheckerConnector.INSTANCE.getConfigManager().getRoleList())) {
                    return callEvent(new CheckResultEvent(event, guild, data, CheckResultEnum.SUCCESS)).getResult();
                } else {
                    return callEvent(new CheckResultEvent(event, guild, data, CheckResultEnum.UNKNOWN)).getResult();
                }
            }
        }
    }


    @Override
    public void onLoginEvent(PlayerJoinEvent event) {
        //WARNING: NOT ASYNC
        PlayerRoleCheckerConnector.INSTANCE.getConfigManager().getJoinCommand().forEach(command -> {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command
                    .replaceAll("%name%", event.getPlayer().getName())
                    .replaceAll("%uuid%", event.getPlayer().getUniqueId().toString()));
        });
    }

    @Override
    public void onErrorCaught(AsyncPlayerPreLoginEvent event, Exception ex) {
        if (!callEvent(new CheckResultEvent(event, null, CheckResultEnum.ERROR).getResult())) {
            disallow(event, PlayerRoleCheckerConnector.INSTANCE.getConfig().getStringList("Minecraft.errorCaught"));

            if (PlayerRoleCheckerConnector.INSTANCE.getConfigManager().isDebug()) {
                /* print error */
                ex.printStackTrace();
            }
        }
    }
}