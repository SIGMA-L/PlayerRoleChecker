package net.klnetwork.playerrolechecker.playerrolechecker;

import net.klnetwork.playerrolechecker.playerrolechecker.Commands.SQLDebug;
import net.klnetwork.playerrolechecker.playerrolechecker.Events.JoinEvent;
import net.klnetwork.playerrolechecker.playerrolechecker.JDA.JDA;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import static net.klnetwork.playerrolechecker.playerrolechecker.JDA.JDA.jda;

public final class PlayerRoleChecker extends JavaPlugin {
    public static Plugin plugin;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        plugin = this;
        SQL.init();
        JDA.init();
        if (getConfig().getBoolean("UseSQLDebug")) {
            getCommand("sqldebug").setExecutor(new SQLDebug());
        }
        getServer().getPluginManager().registerEvents(new JoinEvent(),this);

    }

    @Override
    public void onDisable() {
        jda.shutdown();
        // Plugin shutdown logic
    }
}
