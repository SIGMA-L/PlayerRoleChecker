package net.klnetwork.playerrolechecker.api.data;

import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public abstract class JoinHandler {
    public void onPreLoginEvent(AsyncPlayerPreLoginEvent event) {};

    public void onLoginEvent(PlayerLoginEvent event) {};
}