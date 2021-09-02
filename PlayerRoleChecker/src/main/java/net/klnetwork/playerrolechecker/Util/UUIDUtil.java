package net.klnetwork.playerrolechecker.Util;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.net.URL;
import java.util.Scanner;
import java.util.UUID;

public class UUIDUtil {
    public static UUID getUUID(String name) throws Exception {
        Scanner scanner = new Scanner(new URL("https://api.mojang.com/users/profiles/minecraft/" + name).openStream());
        String input = scanner.nextLine();
        scanner.close();

        JSONObject UUIDObject = (JSONObject) JSONValue.parseWithException(input);
        return UUID.fromString(UUIDObject.get("id").toString().replaceFirst("([0-9a-fA-F]{8})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]+)", "$1-$2-$3-$4-$5"));
    }
}
