package net.klnetwork.codeapi.api;

import net.klnetwork.codeapi.Util.SQLiteUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("api")
public class WebAPI {
    @Path("get")
    @GET
    @Produces({MediaType.TEXT_PLAIN})
    public String get(@QueryParam("code") String code) {
        String[] result = SQLiteUtil.getUUIDFromSQLite(code);
        if (result != null) {
            //安全のために削除します
            SQLiteUtil.removeSQLite(result[0], result[1]);
            return String.format("%s", result[0]);
        }
        return "Unknown code";
    }

    @Path("post")
    @POST
    public Response post(@QueryParam("code") String code) {
        String[] result = SQLiteUtil.getUUIDFromSQLite(code);
        if (result != null) {
            //安全のために削除します
            SQLiteUtil.removeSQLite(result[0], result[1]);
            return Response.ok(result[0]).build();
        }
        return Response.status(400).build();
    }
}
