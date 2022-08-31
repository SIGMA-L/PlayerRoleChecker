package net.klnetwork.codeapi.api.v1;

import net.klnetwork.codeapi.table.LocalSQL;
import net.klnetwork.playerrolechecker.api.data.common.TemporaryData;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("v1")
public class GetUUIDv1 {
    @Path("get")
    @GET
    @Produces({MediaType.TEXT_PLAIN})
    public String get(String code) {
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
}
