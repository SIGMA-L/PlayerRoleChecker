package net.klnetwork.playerrolechecker.api.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.klnetwork.playerrolechecker.api.PlayerRoleCheckerAPI;
import net.klnetwork.playerrolechecker.api.enums.SQLType;
import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.geysermc.floodgate.api.FloodgateApi;

import java.awt.*;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class CommonUtils {
    /**
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

    public static boolean hasFloodGate() {
        return Bukkit.getPluginManager().getPlugin("Floodgate") != null;
    }

    public static boolean isFloodgateUser(UUID uuid) {
        if (hasFloodGate()) return FloodgateApi.getInstance().isFloodgatePlayer(uuid);
        else return false;

    }

    public static UUID getFloodgateUserUUID(UUID uuid) {
        return FloodgateApi.getInstance().getPlayer(uuid).getJavaUniqueId();
    }

    public static String getFloodgateXuid(UUID uuid) {
        return FloodgateApi.getInstance().getPlayer(uuid).getXuid();
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

    /**
     * @param color get Color from {@link Color} class
     * @return {@link Color}
     * @see Color
     */
    public static Color getColor(String color) {
        try {
            return (Color) Color.class
                    .getField(color.toUpperCase())
                    .get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean isNewerVersion() {
        try {
            return isNewerVersion("SIGMA-L", "PlayerRoleChecker", PlayerRoleCheckerAPI.getVersion());
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isNewerVersion(String owner, String repo, String version) throws Exception {
        String url = "https://api.github.com/repos/" + owner + "/" + repo + "/releases/latest";

        Scanner scanner = new Scanner(new URL(url).openStream());
        String input = scanner.nextLine();
        scanner.close();

        JsonObject object = new Gson().fromJson(input, JsonObject.class);

        return object.get("tag_name").getAsString().equals(version);
    }

    public static boolean hasPermission(Member member, List<Permission> permissions) {
        if (permissions == null || permissions.isEmpty()) {
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

    public static boolean checkIsValid(final long time, final long now) {
        return now - time > 1_000_000;
    }

    public static SQLType getSQLType(String type) {
        try {
            return SQLType.valueOf(type);
        } catch (Exception ex) {
            return SQLType.CUSTOM;
        }
    }

    public static int random(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }
}