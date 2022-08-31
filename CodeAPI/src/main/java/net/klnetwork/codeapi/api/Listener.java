package net.klnetwork.codeapi.api;

import net.klnetwork.codeapi.api.v1.GetUUID;
import net.klnetwork.codeapi.api.v1.PostUUID;
import net.klnetwork.codeapi.api.v2.AsyncGetUUID;
import net.klnetwork.codeapi.api.v2.AsyncPostUUID;
import net.klnetwork.playerrolechecker.api.data.codeapi.CodeAPICanRegister;
import org.bukkit.plugin.Plugin;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class Listener {
    public static HttpServer startServer(Plugin plugin) {
        final ResourceConfig rc = new ResourceConfig().packages("net.klnetwork.codeapi.api");

        register(rc, new GetUUID(plugin));
        register(rc, new PostUUID(plugin));
        register(rc, new AsyncGetUUID(plugin));
        register(rc, new AsyncPostUUID(plugin));

        return GrizzlyHttpServerFactory.createHttpServer(URI.create(plugin.getConfig().getString("Server.url")), rc);
    }

    public static void register(ResourceConfig config, CodeAPICanRegister object) {
        if (object.canRegister()) {
            config.register(object);
        }
    }
}
