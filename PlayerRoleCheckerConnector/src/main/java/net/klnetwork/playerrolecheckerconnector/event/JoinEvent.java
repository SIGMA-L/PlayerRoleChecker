package net.klnetwork.playerrolecheckerconnector.event;

import net.klnetwork.playerrolechecker.api.data.common.JoinHandler;
import net.klnetwork.playerrolecheckerconnector.PlayerRoleCheckerConnector;
import net.klnetwork.playerrolecheckerconnector.table.LocalSQL;
import net.klnetwork.playerrolecheckerconnector.util.OtherUtil;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class JoinEvent extends JoinHandler {
    @Override
    public void onPreLoginEvent(AsyncPlayerPreLoginEvent event) {
        if (!PlayerRoleCheckerConnector.INSTANCE.getConfigManager().isJoinMode()) {
            return;
        }

        /* todo: recode localSQL */
        if (LocalSQL.getInstance().isCreated() && LocalSQL.getInstance().hasUUID(event.getUniqueId())) {
            return;
        }

        /* todo: remove OtherUtil */
        final boolean hasRole = OtherUtil.hasRole(event.getUniqueId());

        /* todo: check is working */
        if (!hasRole) {
            disallow(event, String.join("\n", PlayerRoleCheckerConnector.INSTANCE.getConfig().getStringList("Minecraft.kickMessage")));
        }
    }

    @Override
    public void onErrorCaught(AsyncPlayerPreLoginEvent event, Exception ex) {
        disallow(event, String.join("\n", PlayerRoleCheckerConnector.INSTANCE.getConfig().getStringList("Minecraft.errorCaught")));

        if (PlayerRoleCheckerConnector.INSTANCE.getConfigManager().isDebug()) {
            /* print error */
            ex.printStackTrace();
        }
    }
}