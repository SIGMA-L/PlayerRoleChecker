package net.klnetwork.playerrolecheckerconnector.playerrolecheckerconnector;

import net.klnetwork.playerrolecheckerconnector.playerrolecheckerconnector.Util.SQLUtil;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerRoleCheckerConnector extends JavaPlugin {


    public static Plugin plugin;


    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        SQL.init();
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
