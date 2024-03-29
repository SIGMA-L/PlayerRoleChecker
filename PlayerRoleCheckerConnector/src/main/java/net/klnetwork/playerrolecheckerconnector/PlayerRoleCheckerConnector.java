package net.klnetwork.playerrolecheckerconnector;

import net.klnetwork.playerrolechecker.api.data.JoinManager;
import net.klnetwork.playerrolechecker.api.data.PlayerDataTable;
import net.klnetwork.playerrolechecker.api.data.connector.ConnectorAPIHook;
import net.klnetwork.playerrolechecker.api.data.connector.ConnectorBypassTable;
import net.klnetwork.playerrolechecker.api.data.connector.ConnectorCustomDataBase;
import net.klnetwork.playerrolechecker.api.discord.CommandManager;
import net.klnetwork.playerrolechecker.api.enums.HookedAPIType;
import net.klnetwork.playerrolechecker.api.utils.Metrics;
import net.klnetwork.playerrolechecker.api.utils.updater.UpdateBuilder;
import net.klnetwork.playerrolechecker.api.utils.updater.UpdateAlert;
import net.klnetwork.playerrolecheckerconnector.api.ConfigValue;
import net.klnetwork.playerrolecheckerconnector.api.CustomDataBaseImpl;
import net.klnetwork.playerrolecheckerconnector.command.AddBypassCommand;
import net.klnetwork.playerrolecheckerconnector.command.JoinModeCommand;
import net.klnetwork.playerrolecheckerconnector.command.RemoveBypassCommand;
import net.klnetwork.playerrolecheckerconnector.event.JoinEvent;
import net.klnetwork.playerrolecheckerconnector.jda.JDAManager;
import net.klnetwork.playerrolecheckerconnector.table.LocalSQL;
import net.klnetwork.playerrolecheckerconnector.table.PlayerDataSQL;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerRoleCheckerConnector extends JavaPlugin implements ConnectorAPIHook {

    public static PlayerRoleCheckerConnector INSTANCE;

    private final JoinManager joinManager = new JoinManager(this);
    private final ConfigValue configManager = new ConfigValue(this);
    private final Metrics metrics = new Metrics(this,	16282);

    private final UpdateAlert updateAlert = new UpdateBuilder()
            .plugin(this)
            .messages(this.getConfig().getStringList("UpdateAlert.messages"))
            .checkTicks(this.getConfig().getLong("UpdateAlert.checkTicks"))
            .version(this.getConfig().getString("UpdateAlert.version").replaceAll("%hook_version%", this.getDescription().getVersion()))
            .consoleAlert(this.getConfig().getBoolean("UpdateAlert.console-alert"))
            .opPlayerAlert(this.getConfig().getBoolean("UpdateAlert.op-player-alert"))
            .enabled(this.getConfig().getBoolean("UpdateAlert.enabled"))
            .defaultCheck(true)
            .smartVersionCheck(true)
            .start();

    @Override
    public void onLoad() {
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        PlayerDataSQL.getInstance().create();

        updateAlert.registerTask();
        joinManager.init(EventPriority.HIGHEST);
        joinManager.register(new JoinEvent());

        JDAManager.init();

        getCommand("joinmode").setExecutor(new JoinModeCommand());
        if (getConfig().getBoolean("DataBase.BypassTable.useBypassCommand")) {
            LocalSQL.getInstance().create();

            getCommand("addbypass").setExecutor(new AddBypassCommand());
            getCommand("removebypass").setExecutor(new RemoveBypassCommand());
        }
    }

    @Override
    public void onDisable() {
        if (JDAManager.INSTANCE != null) {
            JDAManager.INSTANCE.shutdown();
        }
        updateAlert.stop();
    }

    @Override
    public Plugin getPlugin() {
        return this;
    }

    @Override
    public UpdateAlert getUpdateAlert() {
        return updateAlert;
    }

    @Override
    public net.dv8tion.jda.api.JDA getJDA() {
        return JDAManager.INSTANCE;
    }

    @Override
    public Metrics getMetrics() {
        return metrics;
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
        return null;
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
    public String getVersion() {
        return getDescription().getVersion();
    }

    @Override
    public HookedAPIType getType() {
        return HookedAPIType.CONNECTOR;
    }
}