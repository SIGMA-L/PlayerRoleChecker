package net.klnetwork.playerrolechecker.api.data.codeapi;

import net.klnetwork.playerrolechecker.api.data.APIHook;
import org.glassfish.grizzly.http.server.HttpServer;

public interface CodeAPIHook extends APIHook {
    HttpServer getHttpServer();
}
