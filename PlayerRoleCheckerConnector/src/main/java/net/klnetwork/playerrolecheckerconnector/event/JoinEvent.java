package net.klnetwork.playerrolecheckerconnector.event;

import net.klnetwork.playerrolechecker.api.data.JoinHandler;
import net.klnetwork.playerrolecheckerconnector.PlayerRoleCheckerConnector;
import net.klnetwork.playerrolecheckerconnector.table.LocalSQL;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class JoinEvent extends JoinHandler {
    @Override
    public void onPreLoginEvent(AsyncPlayerPreLoginEvent event) {
        if (!PlayerRoleCheckerConnector.INSTANCE.getConfigManager().isJoinMode()) {
            return;
        }

        if (LocalSQL.getInstance().isCreated() && LocalSQL.getInstance().getUUID(event.getUniqueId()) != null){
            return;
        }
    }
}
