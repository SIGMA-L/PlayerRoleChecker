package net.klnetwork.playerrolecheckerconnector.util;

import org.bukkit.entity.Player;

public class OtherUtil {
    public static String replaceString(String string, Player player) {
        return string.replaceAll("%name%", player.getName()).replaceAll("%uuid%", player.getUniqueId().toString());
    }

    public static String replaceDiscord(String base, String discordId, Player player) {
        return replaceString(base, player).replaceAll("%discordid%", discordId);
    }
}