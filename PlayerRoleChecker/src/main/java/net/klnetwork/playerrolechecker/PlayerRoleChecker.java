package net.klnetwork.playerrolechecker;

import net.klnetwork.playerrolechecker.event.JoinEvent;
import net.klnetwork.playerrolechecker.jda.JDA;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerRoleChecker extends JavaPlugin {

    public static PlayerRoleChecker INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;

        saveDefaultConfig();
        SQL.init();
        JDA.init();
        getServer().getPluginManager().registerEvents(new JoinEvent(), this);

    }

    @Override
    public void onDisable() {
        if (JDA.INSTANCE != null) JDA.INSTANCE.shutdown();
        // Plugin shutdown logic
    }
}