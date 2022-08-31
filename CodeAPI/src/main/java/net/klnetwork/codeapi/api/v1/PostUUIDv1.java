package net.klnetwork.codeapi.api.v1;

import net.klnetwork.codeapi.table.LocalSQL;
import net.klnetwork.playerrolechecker.api.data.common.TemporaryData;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("v1")
public class PostUUIDv1 {
    @Path("post")
    @POST
    public Response post(String code) {
        try {
            TemporaryData data = LocalSQL.getInstance().getUUID(Integer.parseInt(code));

            if (data != null) {
                LocalSQL.getInstance().remove(data.getUUID(), data.getCode());

                return Response.ok(data.getUUID().toString()).build();
            }
        } catch (Exception ex) {
            /* ignored */
        }
        return Response.status(400).build();
    }
}
