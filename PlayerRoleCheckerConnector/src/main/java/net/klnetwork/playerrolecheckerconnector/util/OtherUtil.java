package net.klnetwork.playerrolecheckerconnector.util;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.klnetwork.playerrolecheckerconnector.PlayerRoleCheckerConnector;
import net.klnetwork.playerrolecheckerconnector.jda.JDA;
import net.klnetwork.playerrolecheckerconnector.table.PlayerData;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class OtherUtil {

    public static List<Role> getRolesById(String id) {
        String guildID = PlayerRoleCheckerConnector.INSTANCE.getConfig().getString("GuildID");
        if (guildID != null) return JDA.INSTANCE.getGuildById(guildID).retrieveMemberById(id).complete().getRoles();
        else for (Guild guild : JDA.INSTANCE.getGuilds()) return guild.retrieveMemberById(id).complete().getRoles();
        return null;
    }

    public static boolean hasRole(UUID uuid) {
        String discordId = PlayerData.getInstance().getDiscordId(uuid);

        if (discordId == null) {
            return false;
        }

        List<Role> roles = getRolesById(discordId);

        if (roles == null) {
            return false;
        }

        for (Role role : roles) {
            if (PlayerRoleCheckerConnector.INSTANCE.getRoleList().contains(role.getId())) {
                return true;
            }
        }

        return false;
    }

    public static String replaceString(String string, Player player) {
        return string.replaceAll("%name%", player.getName()).replaceAll("%uuid%", player.getUniqueId().toString());
    }

    public static String replaceDiscord(String base, String discordId, Player player) {
        return replaceString(base, player).replaceAll("%discordid%", discordId);
    }
}