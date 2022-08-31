package net.klnetwork.codeapi.api.v2;

import com.google.gson.JsonObject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.container.AsyncResponse;
import jakarta.ws.rs.container.Suspended;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.klnetwork.codeapi.table.LocalSQL;
import net.klnetwork.codeapi.util.JsonFactory;
import net.klnetwork.playerrolechecker.api.data.codeapi.CodeAPICanRegister;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.TimeUnit;

@Path("v2/get/{body}")
public class AsyncGetUUID implements CodeAPICanRegister {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public void get(@Suspended final AsyncResponse response, @PathParam("body") int body) {
        response.setTimeoutHandler((res) -> res.resume(Response
                .status(Response.Status.SERVICE_UNAVAILABLE)
                .entity(JsonFactory.getErrorJson()).build()));

        response.setTimeout(15, TimeUnit.SECONDS);

        new Thread(() -> {
            try {
                response.resume(createJson(body).toString());
            } catch (Exception ex) {
                response.resume(JsonFactory.getErrorJson().toString());
            }
        }).start();
    }

    private JsonObject createJson(int body) {
        return JsonFactory.createJson(LocalSQL.getInstance().getUUID(body));
    }

    @Override
    public boolean canRegister(Plugin plugin) {
        return plugin.getConfig().getBoolean("Server.v2.get");
    }
}