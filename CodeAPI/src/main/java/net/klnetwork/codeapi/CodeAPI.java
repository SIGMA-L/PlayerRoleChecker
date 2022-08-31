package net.klnetwork.codeapi;

import net.klnetwork.codeapi.api.ConfigValue;
import net.klnetwork.codeapi.api.Listener;
import net.klnetwork.codeapi.event.JoinEvent;
import net.klnetwork.codeapi.table.LocalSQL;
import net.klnetwork.playerrolechecker.api.data.JoinManager;
import net.klnetwork.playerrolechecker.api.data.codeapi.CodeAPIHook;
import net.klnetwork.playerrolechecker.api.data.common.TemporaryTable;
import net.klnetwork.playerrolechecker.api.enums.HookedAPIType;
import net.klnetwork.playerrolechecker.api.utils.Metrics;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.glassfish.grizzly.http.server.HttpServer;

public final class CodeAPI extends JavaPlugin implements CodeAPIHook {

    public static CodeAPI INSTANCE;

    private final HttpServer server = Listener.startServer(this);

    private final JoinManager joinManager = new JoinManager(this);
    private final ConfigValue configManger = new ConfigValue(this);
    private final Metrics metrics = new Metrics(this, 16320);

    @Override
    public void onEnable() {
        INSTANCE = this;

        saveDefaultConfig();

        LocalSQL.getInstance().create();

        joinManager.init();
        joinManager.register(new JoinEvent());

        //TODO: REMOVE
        getServer().getPluginManager().registerEvents(new JoinEvent(),this);
    }

    @Override
    public void onDisable() {
        if (server.isStarted()) {
            server.shutdown();
        }

        LocalSQL.getInstance().drop();
    }

    @Override
    public HttpServer getHttpServer() {
        return server;
    }

    @Override
    public TemporaryTable getTemporary() {
        return LocalSQL.getInstance();
    }

    @Override
    public void setTemporary(TemporaryTable table) {
        LocalSQL.setInstance(table);
    }

    @Override
    public ConfigValue getConfigManager() {
        return configManger;
    }

    @Override
    public Plugin getPlugin() {
        return this;
    }

    @Override
    public Metrics getMetrics() {
        return metrics;
    }

    @Override
    public JoinManager getJoinManager() {
        return joinManager;
    }

    @Override
    public HookedAPIType getType() {
        return HookedAPIType.CODEAPI;
    }
}
