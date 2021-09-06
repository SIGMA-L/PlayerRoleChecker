package net.klnetwork.codeapi.API;

import net.klnetwork.codeapi.PlayerRoleChecker;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class StartAPI {
    public static void startServer() {

        final ResourceConfig rc = new ResourceConfig().packages("net.klnetwork.codeapi.API.WebAPI");

        GrizzlyHttpServerFactory.createHttpServer(URI.create(PlayerRoleChecker.plugin.getConfig().getString("URL")), rc);
    }
}
