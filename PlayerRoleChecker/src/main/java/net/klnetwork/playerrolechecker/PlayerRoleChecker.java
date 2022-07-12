package net.klnetwork.playerrolechecker;

import net.klnetwork.playerrolechecker.api.CustomDataBaseImpl;
import net.klnetwork.playerrolechecker.api.data.PlayerDataTable;
import net.klnetwork.playerrolechecker.api.data.checker.CheckerAPIHook;
import net.klnetwork.playerrolechecker.api.data.checker.CheckerCustomDataBase;
import net.klnetwork.playerrolechecker.api.data.checker.CheckerTemporaryTable;
import net.klnetwork.playerrolechecker.api.enums.HookedAPIType;
import net.klnetwork.playerrolechecker.event.JoinEvent;
import net.klnetwork.playerrolechecker.jda.JDA;
import net.klnetwork.playerrolechecker.table.PlayerData;
import net.klnetwork.playerrolechecker.table.LocalSQL;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerRoleChecker extends JavaPlugin implements CheckerAPIHook {

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
        if (JDA.INSTANCE != null) {
            JDA.INSTANCE.shutdown();
        }
    }

    @Override
    public Plugin getPlugin() {
        return this;
    }

    @Override
    public net.dv8tion.jda.api.JDA getJDA() {
        return JDA.INSTANCE;
    }

    @Override
    public PlayerDataTable getPlayerData() {
        return PlayerData.getInstance();
    }

    @Override
    public CheckerTemporaryTable getTemporary() {
        return LocalSQL.getInstance();
    }

    @Override
    public CheckerCustomDataBase getCustomDataBase() {
        return new CustomDataBaseImpl();
    }

    @Override
    public HookedAPIType getType() {
        return HookedAPIType.CHECKER;
    }
}