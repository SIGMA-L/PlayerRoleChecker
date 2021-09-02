package net.klnetwork.playerrolecheckerconnector.playerrolecheckerconnector;

import net.klnetwork.playerrolecheckerconnector.playerrolecheckerconnector.Command.AddBypass;
import net.klnetwork.playerrolecheckerconnector.playerrolecheckerconnector.Command.JoinMode;
import net.klnetwork.playerrolecheckerconnector.playerrolecheckerconnector.Command.RemoveBypass;
import net.klnetwork.playerrolecheckerconnector.playerrolecheckerconnector.Command.SQLDebug;
import net.klnetwork.playerrolecheckerconnector.playerrolecheckerconnector.Events.JoinEvent;
import net.klnetwork.playerrolecheckerconnector.playerrolecheckerconnector.JDA.JDA;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

import static net.klnetwork.playerrolecheckerconnector.playerrolecheckerconnector.JDA.JDA.jda;

public final class PlayerRoleCheckerConnector extends JavaPlugin {


    public static Plugin plugin;
    public static List<String> list = new ArrayList<>();

    @Override
    public void onEnable() {

        plugin = this;
        saveDefaultConfig();
        SQL.init();
        JDA.init();
        list.addAll(plugin.getConfig().getStringList("Discord.RoleID"));
        Bukkit.getPluginManager().registerEvents(new JoinEvent(),this);
        getCommand("joinmode").setExecutor(new JoinMode());
        getCommand("addbypass").setExecutor(new AddBypass());
        getCommand("removebypass").setExecutor(new RemoveBypass());
        if (getConfig().getBoolean("UseSQLDebug")) {
            getCommand("sqldebug").setExecutor(new SQLDebug());
        }
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        if(jda != null) jda.shutdown();
        // Plugin shutdown logic
    }
}
