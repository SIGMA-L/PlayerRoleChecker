package net.klnetwork.playerrolechecker;

import net.klnetwork.playerrolechecker.api.ConfigValue;
import net.klnetwork.playerrolechecker.api.CustomDataBaseImpl;
import net.klnetwork.playerrolechecker.api.data.JoinManager;
import net.klnetwork.playerrolechecker.api.data.PlayerDataTable;
import net.klnetwork.playerrolechecker.api.data.checker.CheckerAPIHook;
import net.klnetwork.playerrolechecker.api.data.checker.CheckerCustomDataBase;
import net.klnetwork.playerrolechecker.api.discord.CommandManager;
import net.klnetwork.playerrolechecker.api.enums.HookedAPIType;
import net.klnetwork.playerrolechecker.api.utils.Metrics;
import net.klnetwork.playerrolechecker.api.utils.updater.UpdateAlert;
import net.klnetwork.playerrolechecker.api.utils.updater.UpdateBuilder;
import net.klnetwork.playerrolechecker.code.CodeHolder;
import net.klnetwork.playerrolechecker.event.JoinEvent;
import net.klnetwork.playerrolechecker.jda.JDAManager;
import net.klnetwork.playerrolechecker.jda.command.ForceJoinCommand;
import net.klnetwork.playerrolechecker.jda.command.JoinCommand;
import net.klnetwork.playerrolechecker.jda.command.RemoveCommand;
import net.klnetwork.playerrolechecker.table.PlayerDataSQL;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerRoleChecker extends JavaPlugin implements CheckerAPIHook {
    public static PlayerRoleChecker INSTANCE;

    private final JoinManager joinManager = new JoinManager(this);
    private CommandManager commandManager;
    private final ConfigValue configManger = new ConfigValue(this);
    private final CodeHolder codeHolder = new CodeHolder(this);

    private final Metrics metrics = new Metrics(this, 16281);

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
        INSTANCE = this;

        saveDefaultConfig();

        PlayerDataSQL.getInstance().create();
        //LocalSQL.getInstance().create();

        updateAlert.registerTask();

        joinManager.init(EventPriority.HIGHEST);
        joinManager.register(new JoinEvent());

        JDAManager.init();
        this.commandManager = new CommandManager(this.getJDA());
        commandManager.register(new ForceJoinCommand(this));
        commandManager.register(new JoinCommand(this));
        commandManager.register(new RemoveCommand(this));
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
    public net.dv8tion.jda.api.JDA getJDA() {
        return JDAManager.INSTANCE;
    }

    @Override
    public UpdateAlert getUpdateAlert() {
        return updateAlert;
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
        return commandManager;
    }

    @Override
    public CodeHolder getCodeHolder() {
        return codeHolder;
    }

    @Override
    public ConfigValue getConfigManager() {
        return configManger;
    }

    @Override
    public CheckerCustomDataBase getCustomDataBase() {
        return new CustomDataBaseImpl();
    }

    @Override
    public String getVersion() {
        return getDescription().getVersion();
    }

    @Override
    public HookedAPIType getType() {
        return HookedAPIType.CHECKER;
    }
}