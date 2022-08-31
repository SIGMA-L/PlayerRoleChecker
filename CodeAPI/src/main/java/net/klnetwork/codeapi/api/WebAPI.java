package net.klnetwork.codeapi.api;

import net.klnetwork.codeapi.table.LocalSQL;
import net.klnetwork.playerrolechecker.api.data.common.TemporaryData;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("v1")
public class WebAPI {
    @Path("get")
    @GET
    @Produces({MediaType.TEXT_PLAIN})
    public String get(@QueryParam("code") String code) {
        try {
            TemporaryData data = LocalSQL.getInstance().getUUID(Integer.parseInt(code));

            if (data != null) {
                LocalSQL.getInstance().remove(data.getUUID(), data.getCode());

                return data.getUUID().toString();
            }
        } catch (Exception ex) {
            /* ignored */
        }

        return "Unknown code";
    }

    @Path("post")
    @POST
    public Response post(@QueryParam("code") String code) {
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