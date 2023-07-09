package net.klnetwork.playerrolechecker.api.data;

import net.klnetwork.playerrolechecker.api.data.common.JoinHandler;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Consumer;

import java.util.ArrayList;
import java.util.List;

public class JoinManager {
    private final Plugin plugin;
    private List<JoinHandler> handlers = new ArrayList<>();
    private final Listener listener = new Listener() {};

    public JoinManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void register(JoinHandler handler) {
        handlers.add(handler);
    }

    @Deprecated
    public void destroy() {
        HandlerList.unregisterAll(this.listener);
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
        this.init(EventPriority.LOWEST);
    }

    public void init(EventPriority priority) {
        this.registerEvent(priority, AsyncPlayerPreLoginEvent.class, event -> {
            if (!handlers.isEmpty()) {
                handlers.forEach(h -> {
                    try {
                        h.onPreLoginEvent(event);
                    } catch (Exception ex) {
                        h.onErrorCaught(event, ex);
                    }
                });
            }
        });
        this.registerEvent(priority, PlayerJoinEvent.class, event -> {
            if (!handlers.isEmpty()) {
                handlers.forEach(h -> h.onLoginEvent(event));
            }
        });
    }

    public <T extends Event> void registerEvent(EventPriority priority, Class<T> clazz, Consumer<T> con) {
        Bukkit.getPluginManager().registerEvent(clazz, this.listener, priority, (listener1, event) -> con.accept((T) event),  plugin);
    }
}