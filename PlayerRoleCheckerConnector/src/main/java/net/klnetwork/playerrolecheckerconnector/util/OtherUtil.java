package net.klnetwork.playerrolecheckerconnector.util;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.klnetwork.playerrolecheckerconnector.jda.JDA;
import net.klnetwork.playerrolecheckerconnector.PlayerRoleCheckerConnector;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class OtherUtil {

    public static List<Role> getRolesById(String id) {
        String guildID = PlayerRoleCheckerConnector.INSTANCE.getConfig().getString("GuildID");
        if (guildID != null) return JDA.INSTANCE.getGuildById(guildID).retrieveMemberById(id).complete().getRoles();
        else for (Guild guild : JDA.INSTANCE.getGuilds()) return guild.retrieveMemberById(id).complete().getRoles();
        return null;
    }

    public static UUID getUUID(String name) throws Exception {
        Scanner scanner = new Scanner(new URL("https://api.mojang.com/users/profiles/minecraft/" + name).openStream());
        String input = scanner.nextLine();
        scanner.close();

        JSONObject UUIDObject = (JSONObject) JSONValue.parseWithException(input);
        return UUID.fromString(UUIDObject.get("id").toString().replaceFirst("([0-9a-fA-F]{8})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]+)", "$1-$2-$3-$4-$5"));
    }

    public static boolean hasRole(UUID uuid) {
        String discordId = SQLUtil.getDiscordId(uuid.toString());
        if (discordId == null) return false;

        List<Role> role = getRolesById(discordId);
        if (role != null) for (Role role1 : role) if (PlayerRoleCheckerConnector.INSTANCE.getRoleList().contains(role1.getId())) return true;
        return false;
    }

    public static String replaceString(String string, Player player) {
        return string.replaceAll("%name%", player.getName()).replaceAll("%uuid%", player.getUniqueId().toString());
    }

    public static String replaceDiscord(String base, String discordId, Player player) {
        return replaceString(base, player).replaceAll("%discordid%", discordId);
    }
}
