package net.klnetwork.codeapi;

import net.klnetwork.codeapi.api.StartAPI;
import net.klnetwork.codeapi.Events.JoinEvent;
import net.klnetwork.playerrolechecker.api.data.JoinManager;
import net.klnetwork.playerrolechecker.api.data.codeapi.CodeAPIHook;
import net.klnetwork.playerrolechecker.api.enums.HookedAPIType;
import net.klnetwork.playerrolechecker.api.utils.Metrics;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.glassfish.grizzly.http.server.HttpServer;

public final class CodeAPI extends JavaPlugin implements CodeAPIHook {
    public static Plugin plugin;
    private static HttpServer server;

    private final JoinManager joinManager = new JoinManager(this);
    private final Metrics metrics = new Metrics(this, 16320);

    @Override
    public void onEnable() {
        saveDefaultConfig();
        plugin = this;
        SQL.init();
        server = StartAPI.startServer();

        joinManager.init();

        //TODO: REMOVE
        getServer().getPluginManager().registerEvents(new JoinEvent(),this);
    }

    @Override
    public void onDisable() {
        if (server.isStarted()) {
            server.shutdown();
        }
    }

    @Override
    public HttpServer getHttpServer() {
        return server;
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
