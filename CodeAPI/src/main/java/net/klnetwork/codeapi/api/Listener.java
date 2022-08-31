package net.klnetwork.codeapi.api;

import net.klnetwork.codeapi.api.v1.GetUUID;
import net.klnetwork.codeapi.api.v1.PostUUID;
import net.klnetwork.codeapi.api.v2.AsyncGetUUID;
import net.klnetwork.codeapi.api.v2.AsyncPostUUID;
import org.bukkit.plugin.Plugin;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;

public class Listener {
    public static HttpServer startServer(Plugin plugin) {
        final ResourceConfig rc = new ResourceConfig().packages("net.klnetwork.codeapi.api");

        register(rc, plugin, GetUUID.class);
        register(rc, plugin, PostUUID.class);
        register(rc, plugin, AsyncGetUUID.class);
        register(rc, plugin, AsyncPostUUID.class);

        return GrizzlyHttpServerFactory.createHttpServer(URI.create(plugin.getConfig().getString("Server.url")), rc);
    }

    public static void register(ResourceConfig config, Plugin plugin, Class<?> c) {
        try {
            final boolean canRegister = (boolean) c.getDeclaredMethod("canRegister", Plugin.class)
                    .invoke(c.newInstance(), plugin);

            if (canRegister) {
                config.registerClasses(c);
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}
