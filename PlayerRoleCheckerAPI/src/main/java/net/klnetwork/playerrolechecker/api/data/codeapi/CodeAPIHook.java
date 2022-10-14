package net.klnetwork.playerrolechecker.api.data.codeapi;

import net.klnetwork.playerrolechecker.api.data.APIHook;
import net.klnetwork.playerrolechecker.api.data.common.TemporaryTable;
import org.glassfish.grizzly.http.server.HttpServer;

public interface CodeAPIHook extends APIHook {
    HttpServer getHttpServer();

    TemporaryTable getTemporary();

    void setTemporary(TemporaryTable table);

    CodeAPIConfigManager getConfigManager();
}
