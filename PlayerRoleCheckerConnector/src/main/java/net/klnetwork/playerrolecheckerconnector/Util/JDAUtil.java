package net.klnetwork.playerrolecheckerconnector.Util;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.klnetwork.playerrolecheckerconnector.JDA.JDA;

import java.util.List;


public class JDAUtil {
    public static List<Role> getRolesById(String id){
        for(Guild guild : JDA.jda.getGuilds()) return guild.retrieveMemberById(id).complete().getRoles();
        return null;
    }
}
