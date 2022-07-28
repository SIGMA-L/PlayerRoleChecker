package net.klnetwork.playerrolechecker.api.discord.data;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;

public abstract class CommandMessage {

    private final Plugin plugin;

    public CommandMessage(Plugin plugin) {
        this.plugin = plugin;
    }

    public abstract String getCommandName();

    /**
     * @return Config Path
     */
    public abstract String getPath();

    public abstract boolean isWork(CommandData data);

    public abstract void onMessageReceiveEvent(CommandData event) throws Exception;

    public abstract void onErrorCaught(CommandData event, Exception exception);

    public <T> T callEvent(T event) {
        if (event instanceof Event) {
            Bukkit.getPluginManager().callEvent((Event) event);
        }

        return event;
    }

    public Plugin getPlugin() {
        return plugin;
    }
}