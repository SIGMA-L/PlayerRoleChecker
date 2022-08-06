package net.klnetwork.playerrolecheckerconnector;

import net.klnetwork.playerrolechecker.api.data.JoinManager;
import net.klnetwork.playerrolechecker.api.data.PlayerDataTable;
import net.klnetwork.playerrolechecker.api.data.connector.ConnectorAPIHook;
import net.klnetwork.playerrolechecker.api.data.connector.ConnectorBypassTable;
import net.klnetwork.playerrolechecker.api.data.connector.ConnectorCustomDataBase;
import net.klnetwork.playerrolechecker.api.discord.CommandManager;
import net.klnetwork.playerrolechecker.api.enums.HookedAPIType;
import net.klnetwork.playerrolecheckerconnector.api.ConfigValue;
import net.klnetwork.playerrolecheckerconnector.api.CustomDataBaseImpl;
import net.klnetwork.playerrolecheckerconnector.command.AddBypassCommand;
import net.klnetwork.playerrolecheckerconnector.command.JoinModeCommand;
import net.klnetwork.playerrolecheckerconnector.command.RemoveBypassCommand;
import net.klnetwork.playerrolecheckerconnector.event.JoinEvent;
import net.klnetwork.playerrolecheckerconnector.jda.JDA;
import net.klnetwork.playerrolecheckerconnector.table.LocalSQL;
import net.klnetwork.playerrolecheckerconnector.table.PlayerDataSQL;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class PlayerRoleCheckerConnector extends JavaPlugin implements ConnectorAPIHook {

    public static PlayerRoleCheckerConnector INSTANCE;

    private final JoinManager joinManager = new JoinManager(this);
    private final CommandManager commandManager = new CommandManager(null);
    private final ConfigValue configManager = new ConfigValue(this);

    @Override
    public void onEnable() {
        INSTANCE = this;

        saveDefaultConfig();

        PlayerDataSQL.getInstance().create();

        joinManager.register(new JoinEvent());

        JDA.init();
        commandManager.setJDA(getJDA());

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

    @Deprecated
    public List<String> getRoleList() {
        return configManager.getRoleList();
    }

    @Deprecated
    public List<String> getCommandList() {
        return configManager.getJoinCommand();
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
        return PlayerDataSQL.getInstance();
    }

    @Override
    public void setPlayerData(PlayerDataTable table) {
        PlayerDataSQL.setInstance(table);
    }

    @Override
    public JoinManager getJoinManager() {
        return joinManager;
    }

    @Override
    public CommandManager getCommandManager() {
        return commandManager;
    }

    @Override
    public ConfigValue getConfigManager() {
        return configManager;
    }

    @Override
    public ConnectorBypassTable getBypass() {
        return LocalSQL.getInstance();
    }

    @Override
    public void setBypass(ConnectorBypassTable table) {
        LocalSQL.setInstance(table);
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