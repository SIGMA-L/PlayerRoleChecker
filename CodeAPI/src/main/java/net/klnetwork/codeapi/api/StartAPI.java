package net.klnetwork.codeapi.api;

import net.klnetwork.codeapi.CodeAPI;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class StartAPI {
    public static HttpServer startServer() {
        final ResourceConfig rc = new ResourceConfig().packages("net.klnetwork.codeapi.API");

        rc.registerClasses(WebAPI.class);
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(CodeAPI.INSTANCE.getConfig().getString("URL")), rc);
    }
}
