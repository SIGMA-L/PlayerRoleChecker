package net.klnetwork.playerrolechecker.api.utils;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.net.URL;
import java.util.Scanner;
import java.util.UUID;

public class CommonUtils {
    public static UUID getUUID(String name) throws Exception {
        Scanner scanner = new Scanner(new URL("https://api.mojang.com/users/profiles/minecraft/" + name).openStream());
        String input = scanner.nextLine();
        scanner.close();

        JSONObject UUIDObject = (JSONObject) JSONValue.parseWithException(input);
        return UUID.fromString(UUIDObject.get("id").toString().replaceFirst("([0-9a-fA-F]{8})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]+)", "$1-$2-$3-$4-$5"));
    }

    public static boolean isUpdated(String owner, String repo, String version) throws Exception{
        String url = "https://api.github.com/repos/" + owner + "/" + repo + "/releases/latest";

        Scanner scanner = new Scanner(new URL(url).openStream());
        String input = scanner.nextLine();
        scanner.close();

        JSONObject jsonObject = (JSONObject) JSONValue.parseWithException(input);

        return jsonObject.get("tag_name").equals(version);
    }

    public static void close(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static int random(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    public static long generate(final int min, final int max) {
        return random(min, max);
    }
}