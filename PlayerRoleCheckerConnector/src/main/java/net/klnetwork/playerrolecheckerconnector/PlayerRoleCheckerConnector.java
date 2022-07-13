package net.klnetwork.playerrolecheckerconnector;

import net.klnetwork.playerrolechecker.api.data.PlayerDataTable;
import net.klnetwork.playerrolechecker.api.data.connector.ConnectorAPIHook;
import net.klnetwork.playerrolechecker.api.data.connector.ConnectorBypassTable;
import net.klnetwork.playerrolechecker.api.data.connector.ConnectorCustomDataBase;
import net.klnetwork.playerrolechecker.api.enums.HookedAPIType;
import net.klnetwork.playerrolecheckerconnector.api.CustomDataBaseImpl;
import net.klnetwork.playerrolecheckerconnector.command.AddBypassCommand;
import net.klnetwork.playerrolecheckerconnector.command.JoinModeCommand;
import net.klnetwork.playerrolecheckerconnector.command.RemoveBypassCommand;
import net.klnetwork.playerrolecheckerconnector.event.JoinEvent;
import net.klnetwork.playerrolecheckerconnector.jda.JDA;
import net.klnetwork.playerrolecheckerconnector.table.LocalSQL;
import net.klnetwork.playerrolecheckerconnector.table.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class PlayerRoleCheckerConnector extends JavaPlugin implements ConnectorAPIHook {

    public static PlayerRoleCheckerConnector INSTANCE;

    private final List<String> roleList = new ArrayList<>();
    private final List<String> commandList = new ArrayList<>();


    @Override
    public void onEnable() {
        INSTANCE = this;

        saveDefaultConfig();

        PlayerData.getInstance().create();
        JDA.init();

        roleList.addAll(getConfig().getStringList("Discord.RoleID"));
        commandList.addAll(getConfig().getStringList("JoinCommand"));

        Bukkit.getPluginManager().registerEvents(new JoinEvent(), this);

        getCommand("joinmode").setExecutor(new JoinModeCommand());
        if (getConfig().getBoolean("SQLite.useBypassCommand")) {
            LocalSQL.getInstance().create();

            getCommand("addbypass").setExecutor(new AddBypassCommand());
            getCommand("removebypass").setExecutor(new RemoveBypassCommand());
        }
    }

    @Override
    public void onDisable() {
        if (JDA.INSTANCE != null) {
            JDA.INSTANCE.shutdown();
        }
    }

    public List<String> getRoleList() {
        return roleList;
    }

    public List<String> getCommandList() {
        return commandList;
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
    public ConnectorBypassTable getBypass() {
        return LocalSQL.getInstance();
    }

    @Override
    public ConnectorCustomDataBase getCustomDataBase() {
        return new CustomDataBaseImpl();
    }

    @Override
    public HookedAPIType getType() {
        return HookedAPIType.CONNECTOR;
    }
}