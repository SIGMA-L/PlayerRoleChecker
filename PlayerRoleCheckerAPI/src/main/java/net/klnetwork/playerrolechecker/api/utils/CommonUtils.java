package net.klnetwork.playerrolechecker.api.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

import java.awt.*;
import java.net.URL;
import java.util.Scanner;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * ランダムなUtilほとんどは内部で使われています
 */
public class CommonUtils {

    public static void asyncUUID(String name, Consumer<UUID> consumer) {
        new Thread(() -> {
            try {
                consumer.accept(getUUID(name));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     *
     * @param name player name
     * @return player uuid
     * @throws Exception if player name is Unknown
     */
    public static UUID getUUID(String name) throws Exception {
        Scanner scanner = new Scanner(new URL("https://api.mojang.com/users/profiles/minecraft/" + name).openStream());
        String input = scanner.nextLine();
        scanner.close();

        JsonObject UUIDObject = new Gson().fromJson(input, JsonObject.class);
        return UUID.fromString(UUIDObject.get("id").getAsString().replaceFirst("([0-9a-fA-F]{8})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]+)", "$1-$2-$3-$4-$5"));
    }

    /**
     * @deprecated Not Testing
     * @param event any event
     * @return isCancelled
     */
    public static boolean callEvent(Event event) {
        Bukkit.getPluginManager().callEvent(event);

        if (event instanceof Cancellable) {
            return ((Cancellable) event).isCancelled();
        }

        return false;
    }

    public static Color getColor(String color) {
        try {
            return (Color) Color.class
                    .getField(color)
                    .get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean isNewerVersion(String owner, String repo, String version) throws Exception {
        String url = "https://api.github.com/repos/" + owner + "/" + repo + "/releases/latest";

        Scanner scanner = new Scanner(new URL(url).openStream());
        String input = scanner.nextLine();
        scanner.close();

        JsonObject object = new Gson().fromJson(input, JsonObject.class);

        return object.get("tag_name").getAsString().equals(version);
    }

    public static boolean hasPermission(Member member, Permission[] permissions) {
        if (permissions == null) {
            return true;
        }

        for (Permission permission : permissions) {
            if (permission == Permission.UNKNOWN || member.getPermissions().contains(permission)) {
                return true;
            }
        }
        return false;
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
}