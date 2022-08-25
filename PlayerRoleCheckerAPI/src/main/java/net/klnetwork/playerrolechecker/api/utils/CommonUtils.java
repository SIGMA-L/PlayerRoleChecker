package net.klnetwork.playerrolechecker.api.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.klnetwork.playerrolechecker.api.PlayerRoleCheckerAPI;
import net.klnetwork.playerrolechecker.api.enums.SQLType;
import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.geysermc.floodgate.api.FloodgateApi;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class CommonUtils {
    
    @Deprecated
    public static String GEYSER_API_METHOD = "https://api.geysermc.org/v2/xbox/xuid/";

    public static String GEYSER_SKIN_API = "https://api.geysermc.org/v2/skin/";
    public static String MINECRAFT_NAME_API = "https://api.mojang.com/users/profiles/minecraft/";
    public static String MINECRAFT_SKIN_API = "https://textures.minecraft.net/texture/";

    /**
     * @param name player name
     * @return player uuid
     * @throws Exception if player name is Unknown
     */
    public static UUID getUUID(String name) throws Exception {
        JsonObject UUIDObject = new Gson().fromJson(getContent(MINECRAFT_NAME_API + name), JsonObject.class);

        return UUID.fromString(UUIDObject.get("id").getAsString().replaceFirst("([0-9a-fA-F]{8})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]+)", "$1-$2-$3-$4-$5"));
    }

    public static boolean hasFloodGate() {
        return Bukkit.getPluginManager().getPlugin("floodgate") != null;
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

    public static String getSkinId(UUID uuid) {
        return getSkinId(getFloodgateUserUUID(uuid));
    }

    public static String getSkinId(String xuid) {
        JsonObject object = new Gson().fromJson(getContent(GEYSER_SKIN_API + xuid), JsonObject.class);

        return object.get("texture_id").getAsString();
    }

    public static BufferedImage getHeadSkin(String textureId) {
        if (textureId == null) {
            throw new IllegalStateException();
        }

        BufferedImage skin = getImages(MINECRAFT_SKIN_API + textureId);

        if (skin == null) {
            throw new IllegalStateException();
        }

        BufferedImage head = skin.getSubimage(8, 8, 8, 8);
        skin.flush();

        return head;
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



    public static long getXUID(UUID uuid) {
        //Floodgate not using mostSignificantBits!
        if (uuid.getMostSignificantBits() != 0) {
            return 0;
        }

        return uuid.getLeastSignificantBits();
    }

    public static boolean isNewerVersion() {
        return isNewerVersion("SIGMA-L", "PlayerRoleChecker", PlayerRoleCheckerAPI.getVersion());
    }

    public static boolean isNewerVersion(String owner, String repo, String version) {
        String ver = getVersion(owner, repo);

        return ver != null && ver.equals(version);
    }

    public static String getVersion(String owner, String repo) {
        try {
            String url = "https://api.github.com/repos/" + owner + "/" + repo + "/releases/latest";

            JsonObject object = new Gson().fromJson(getContent(url), JsonObject.class);

            return object.get("tag_name").getAsString();
        } catch (Exception ex) {
            return null;
        }
    }

    public static BufferedImage getImages(String url) {
        InputStream stream = null;
        try {
            stream = new URL(url).openStream();

            return ImageIO.read(stream);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            close(stream);
        }
        return null;
    }

    public static String getContent(String url) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new URL(url).openStream());

            return scanner.nextLine();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            close(scanner);
        }

        return null;
    }

    public static boolean hasRole(List<Role> roles, List<String> id) {
        for (Role role : roles) {
            if (id.contains(role.getId())) {
                return true;
            }
        }
        return false;
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