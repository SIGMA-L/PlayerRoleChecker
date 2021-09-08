package net.klnetwork.playerrolecheckerconnector.Util;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.klnetwork.playerrolecheckerconnector.JDA.JDA;
import net.klnetwork.playerrolecheckerconnector.PlayerRoleCheckerConnector;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import static net.klnetwork.playerrolecheckerconnector.PlayerRoleCheckerConnector.list;
import static net.klnetwork.playerrolecheckerconnector.Util.SQLUtil.getDiscordFromSQL;


public class OtherUtil {

    public static List<Role> getRolesById(String id){
        String GuildID = PlayerRoleCheckerConnector.plugin.getConfig().getString("GuildID");
        if (GuildID != null) return JDA.jda.getGuildById(GuildID).retrieveMemberById(id).complete().getRoles();
        else for (Guild guild : JDA.jda.getGuilds()) return guild.retrieveMemberById(id).complete().getRoles();
        return null;
    }

    public static UUID getUUID(String name) throws Exception {
        Scanner scanner = new Scanner(new URL("https://api.mojang.com/users/profiles/minecraft/" + name).openStream());
        String input = scanner.nextLine();
        scanner.close();

        JSONObject UUIDObject = (JSONObject) JSONValue.parseWithException(input);
        return UUID.fromString(UUIDObject.get("id").toString().replaceFirst("([0-9a-fA-F]{8})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]+)", "$1-$2-$3-$4-$5"));
    }

    public static boolean CheckPlayer(UUID uuid) {
        String[] DiscordID = getDiscordFromSQL(uuid.toString());
        if (DiscordID == null) return false;

        List<Role> role = getRolesById(DiscordID[1]);
        if (role == null) return false;
        for (Role role1 : role) if (list.contains(role1.getId())) return true;


        return false;
    }

    public static String ReplaceString(String string,Player player) {
        return string.replaceAll("%name%",player.getName()).replaceAll("%uuid%",player.getUniqueId().toString());
    }
}
