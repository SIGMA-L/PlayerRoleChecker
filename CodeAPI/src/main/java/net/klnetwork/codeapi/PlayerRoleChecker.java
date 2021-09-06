package net.klnetwork.codeapi;

import net.klnetwork.codeapi.API.StartAPI;
import net.klnetwork.codeapi.Events.JoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerRoleChecker extends JavaPlugin {
    public static Plugin plugin;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        plugin = this;
        SQL.init();
        StartAPI.startServer();
        getServer().getPluginManager().registerEvents(new JoinEvent(),this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
