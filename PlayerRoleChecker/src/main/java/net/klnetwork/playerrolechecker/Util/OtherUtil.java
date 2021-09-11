package net.klnetwork.playerrolechecker.Util;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.awt.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class OtherUtil {
    public void waitTimer(UUID uuid) {
        new Thread(()-> {
            try {
                TimeUnit.SECONDS.sleep(300);
                String[] result = SQLiteUtil.getCodeFromSQLite(uuid.toString());
                if (result != null) {
                    SQLiteUtil.removeSQLite(result[0], result[1]);

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static UUID getUUID(String name) throws Exception {
        Scanner scanner = new Scanner(new URL("https://api.mojang.com/users/profiles/minecraft/" + name).openStream());
        String input = scanner.nextLine();
        scanner.close();

        JSONObject UUIDObject = (JSONObject) JSONValue.parseWithException(input);
        return UUID.fromString(UUIDObject.get("id").toString().replaceFirst("([0-9a-fA-F]{8})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]+)", "$1-$2-$3-$4-$5"));
    }

    public static Color ColorFromString(String String){
        Color color;
        try {
            Field field = Class.forName("java.awt.Color").getField(String);
            color = (Color)field.get(null);
        } catch (Exception e) {
            color = null; // Not defined
        }
    return color;
    }

}
