package net.klnetwork.playerrolecheckerconnector.Util;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.klnetwork.playerrolecheckerconnector.JDA.JDA;
import net.klnetwork.playerrolecheckerconnector.PlayerRoleCheckerConnector;

import java.util.List;


public class JDAUtil {

    public static List<Role> getRolesById(String id){
        String GuildID = PlayerRoleCheckerConnector.plugin.getConfig().getString("GuildID");
        if (GuildID != null) return JDA.jda.getGuildById(GuildID).retrieveMemberById(id).complete().getRoles();
        else for (Guild guild : JDA.jda.getGuilds()) return guild.retrieveMemberById(id).complete().getRoles();
        return null;
    }
}
