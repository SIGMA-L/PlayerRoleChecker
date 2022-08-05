package net.klnetwork.playerrolecheckerconnector.event;

import net.klnetwork.playerrolechecker.api.data.JoinHandler;
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
        if (LocalSQL.getInstance().isCreated() && LocalSQL.getInstance().getUUID(event.getUniqueId()) != null){
            return;
        }

        try {
            OtherUtil.hasRole(event.getUniqueId());
        } catch (Exception ex) {
            
        }
    }
}
