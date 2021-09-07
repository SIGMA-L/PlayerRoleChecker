package net.klnetwork.codeapi;

import net.klnetwork.codeapi.API.StartAPI;
import net.klnetwork.codeapi.Events.JoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.glassfish.grizzly.http.server.HttpServer;

public final class CodeAPI extends JavaPlugin {
    public static Plugin plugin;
    private static HttpServer httpServer;

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
}
