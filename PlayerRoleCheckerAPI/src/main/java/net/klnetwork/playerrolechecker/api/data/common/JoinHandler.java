package net.klnetwork.playerrolechecker.api.data.common;

import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public abstract class JoinHandler implements Listener {
    public void onPreLoginEvent(AsyncPlayerPreLoginEvent event) {}

    public void onLoginEvent(PlayerJoinEvent event) {}

    public void onErrorCaught(AsyncPlayerPreLoginEvent event, Exception ex) {}

    public void disallow(AsyncPlayerPreLoginEvent event, String message) {
        event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, ChatColor.translateAlternateColorCodes('&', message));
    }
}