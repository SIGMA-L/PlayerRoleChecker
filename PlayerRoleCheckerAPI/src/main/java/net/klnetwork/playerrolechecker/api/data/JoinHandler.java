package net.klnetwork.playerrolechecker.api.data;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public abstract class JoinHandler implements Listener {
    @EventHandler
    public void onPreLoginEvent(AsyncPlayerPreLoginEvent event) {}

    @EventHandler
    public void onLoginEvent(PlayerLoginEvent event) {}
}