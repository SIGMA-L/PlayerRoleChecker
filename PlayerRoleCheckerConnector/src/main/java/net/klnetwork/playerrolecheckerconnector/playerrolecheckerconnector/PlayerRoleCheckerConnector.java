package net.klnetwork.playerrolecheckerconnector.playerrolecheckerconnector;

import net.klnetwork.playerrolecheckerconnector.playerrolecheckerconnector.JDA.JDA;
import net.klnetwork.playerrolecheckerconnector.playerrolecheckerconnector.Util.SQLUtil;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class PlayerRoleCheckerConnector extends JavaPlugin {


    public static Plugin plugin;
    public static List<String> list;

    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        SQL.init();
        JDA.init();
        list.addAll(plugin.getConfig().getStringList("Discord.RoleID"));
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
