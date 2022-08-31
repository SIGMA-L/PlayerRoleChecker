package net.klnetwork.codeapi;

import net.klnetwork.codeapi.api.StartAPI;
import net.klnetwork.codeapi.Events.JoinEvent;
import net.klnetwork.playerrolechecker.api.data.codeapi.CodeAPIHook;
import net.klnetwork.playerrolechecker.api.enums.HookedAPIType;
import net.klnetwork.playerrolechecker.api.utils.Metrics;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.glassfish.grizzly.http.server.HttpServer;

public final class CodeAPI extends JavaPlugin implements CodeAPIHook {
    public static Plugin plugin;
    private static HttpServer httpServer;


    private final Metrics metrics = new Metrics(this, 16320);

    @Override
    public void onEnable() {
        saveDefaultConfig();
        plugin = this;
        SQL.init();
        httpServer = StartAPI.startServer();
        getServer().getPluginManager().registerEvents(new JoinEvent(),this);

    }

    @Override
    public void onDisable() {
        if(httpServer != null) httpServer.shutdown(); //自動的に落としてくれるようですが、知りませんね。
        // Plugin shutdown logic
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
    public HookedAPIType getType() {
        return HookedAPIType.CODEAPI;
    }
}
