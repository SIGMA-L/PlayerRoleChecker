package net.klnetwork.codeapi.util;

import com.google.gson.JsonObject;
import net.klnetwork.codeapi.table.LocalSQL;
import net.klnetwork.playerrolechecker.api.data.common.TemporaryData;

public class JsonFactory {

    public static JsonObject error = makeJson();

    public static JsonObject getErrorJson() {
        return error;
    }

    public static JsonObject createJson(TemporaryData data) {
        JsonObject object = new JsonObject();

        object.addProperty("type", "success");
        object.addProperty("uuid", data.getUUID().toString());
        object.addProperty("code", data.getCode());
        object.addProperty("bedrock", data.isBedrock());

        //for safes! (100% safe)
        LocalSQL.getInstance().remove(data.getUUID(), data.getCode());

        return object;
    }

    private static JsonObject makeJson() {
        JsonObject object = new JsonObject();

        object.addProperty("type", "error");
        object.add("uuid", null);
        object.add("code", null);
        object.add("bedrock", null);

        return object;
    }
}
