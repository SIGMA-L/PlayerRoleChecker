package net.klnetwork.playerrolechecker.api.data.common;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public abstract class JoinHandler implements Listener {
    @EventHandler
    public void onPreLoginEvent(AsyncPlayerPreLoginEvent event) {}

    @EventHandler
    public void onLoginEvent(PlayerJoinEvent event) {}
}