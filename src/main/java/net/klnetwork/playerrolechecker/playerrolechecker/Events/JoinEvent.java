package net.klnetwork.playerrolechecker.playerrolechecker.Events;

import net.klnetwork.playerrolechecker.playerrolechecker.API.CodeUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.UUID;

public class JoinEvent implements Listener {
    @EventHandler
    public void CodeIssue(AsyncPlayerPreLoginEvent e) {
        UUID uuid = e.getUniqueId();
    }
}
