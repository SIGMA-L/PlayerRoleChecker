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
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent extends JoinHandler {
    @Override
    public void onPreLoginEvent(AsyncPlayerPreLoginEvent event) {
        if (!PlayerRoleCheckerConnector.INSTANCE.getConfigManager().isJoinMode()
                && !callEvent(new CheckSkippedEvent(SkippedReasonEnum.SKIP_MODE)).isCancelled()) {
            return;
        }

        /* todo: recode localSQL */
        if (LocalSQL.getInstance().isCreated()
                && LocalSQL.getInstance().hasUUID(event.getUniqueId())
                && !callEvent(new CheckSkippedEvent(SkippedReasonEnum.BYPASS)).isCancelled()) {
            return;
        }

        PlayerData data = PlayerDataSQL.getInstance().getDiscordId(event.getUniqueId());

        if (!callEvent(new CheckStartEvent(data)).isCancelled() && isJoin(data)) {
            disallow(event, PlayerRoleCheckerConnector.INSTANCE.getConfig().getStringList("Minecraft.kickMessage"));
        }
    }

    public boolean isJoin(PlayerData data) {
        if (data == null) {
            return callEvent(new CheckResultEvent(null, CheckResultEnum.NOT_REGISTERED, false)).getResult();
        } else {
            Guild guild = PlayerRoleCheckerConnector.INSTANCE.getJDA().getGuildById(PlayerRoleCheckerConnector.INSTANCE.getConfig().getLong("GuildID"));

            if (guild == null) {
                return callEvent(new CheckResultEvent(data, CheckResultEnum.GUILD_IS_INVALID, false)).getResult();
            } else {
                if (CommonUtils.hasRole(guild.retrieveMemberById(data.getDiscordId()).complete().getRoles(), PlayerRoleCheckerConnector.INSTANCE.getConfigManager().getRoleList())) {
                    return callEvent(new CheckResultEvent(data, CheckResultEnum.SUCCESS, true)).getResult();
                } else {
                    return callEvent(new CheckResultEvent(data, CheckResultEnum.UNKNOWN, false)).getResult();
                }
            }
        }
    }


    @Override
    public void onLoginEvent(PlayerJoinEvent event) {
        //todo: write code?
    }

    @Override
    public void onErrorCaught(AsyncPlayerPreLoginEvent event, Exception ex) {
        if (!callEvent(new CheckResultEvent(null, CheckResultEnum.ERROR, false).getResult())) {
            disallow(event, PlayerRoleCheckerConnector.INSTANCE.getConfig().getStringList("Minecraft.errorCaught"));

            if (PlayerRoleCheckerConnector.INSTANCE.getConfigManager().isDebug()) {
                /* print error */
                ex.printStackTrace();
            }
        }
    }
}