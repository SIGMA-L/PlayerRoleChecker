package net.klnetwork.playerrolechecker;

import net.klnetwork.playerrolechecker.Events.JoinEvent;
import net.klnetwork.playerrolechecker.JDA.JDA;
import net.klnetwork.playerrolechecker.SQL;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import static net.klnetwork.playerrolechecker.JDA.JDA.jda;

public final class PlayerRoleChecker extends JavaPlugin {
    public static Plugin plugin;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        plugin = this;
        SQL.init();
        JDA.init();
        getServer().getPluginManager().registerEvents(new JoinEvent(),this);

    }

    @Override
    public void onDisable() {
        if(jda != null) jda.shutdown();
        // Plugin shutdown logic
    }
}
