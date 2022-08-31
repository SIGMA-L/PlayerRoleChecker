package net.klnetwork.codeapi.api.v1;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.klnetwork.codeapi.table.LocalSQL;
import net.klnetwork.playerrolechecker.api.data.codeapi.CodeAPICanRegister;
import net.klnetwork.playerrolechecker.api.data.common.TemporaryData;
import org.bukkit.plugin.Plugin;

@Path("/v1/post/{body}")
public class PostUUID implements CodeAPICanRegister {

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response post(@PathParam("body") String body) {
        try {
            TemporaryData data = LocalSQL.getInstance().getUUID(Integer.parseInt(body));

            if (data != null) {
                LocalSQL.getInstance().remove(data.getUUID(), data.getCode());

                return Response.ok(data.getUUID().toString()).build();
            }
        } catch (Exception ex) {
            /* ignored */
        }
        return Response.status(400).build();
    }

    @Override
    public boolean canRegister(Plugin plugin) {
        return plugin.getConfig().getBoolean("Server.v1.post");
    }
}
