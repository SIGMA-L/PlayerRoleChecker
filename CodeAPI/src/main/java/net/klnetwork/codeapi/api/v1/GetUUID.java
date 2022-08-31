package net.klnetwork.codeapi.api.v1;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import net.klnetwork.codeapi.table.LocalSQL;
import net.klnetwork.playerrolechecker.api.data.codeapi.CodeAPICanRegister;
import net.klnetwork.playerrolechecker.api.data.common.TemporaryData;
import org.bukkit.plugin.Plugin;

@Path("v1/get/{body}")
public class GetUUID extends CodeAPICanRegister {
    public GetUUID(Plugin plugin) {
        super(plugin);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get(@PathParam("body") String code) {
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

    @Override
    public boolean canRegister() {
        return plugin.getConfig().getBoolean("Server.v1.get");
    }
}