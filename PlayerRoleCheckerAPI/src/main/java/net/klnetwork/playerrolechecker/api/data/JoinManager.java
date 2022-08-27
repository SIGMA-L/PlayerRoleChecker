package net.klnetwork.playerrolechecker.api.data;

import net.klnetwork.playerrolechecker.api.data.common.JoinHandler;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class JoinManager implements Listener {

    private final Plugin plugin;

    public JoinManager(Plugin plugin) {
        this.plugin = plugin;
    }

    private List<JoinHandler> handlers = new ArrayList<>();

    @EventHandler
    public void onAsyncPlayerJoinEvent(AsyncPlayerPreLoginEvent event) {
        if (!handlers.isEmpty()) {
            handlers.forEach(h -> {
                try {
                    h.onPreLoginEvent(event);
                } catch (Exception ex) {
                    h.onErrorCaught(event, ex);
                }
            });
        }
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        if (!handlers.isEmpty()) {
            handlers.forEach(h -> h.onLoginEvent(event));
        }
    }

    public void register(JoinHandler handler) {
        handlers.add(handler);
    }

    @Deprecated
    public void destroy() {
        HandlerList.unregisterAll(this);
    }

    public List<JoinHandler> getHandlers() {
        return handlers;
    }

    public void setHandlers(List<JoinHandler> handlers) {
        this.handlers = handlers;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public void init() {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
}