package net.klnetwork.playerrolechecker.api.data;

import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public abstract class JoinHandler {
    public abstract void onPreLoginEvent(AsyncPlayerPreLoginEvent event);

    public abstract void onLoginEvent(PlayerLoginEvent event);
}