package net.klnetwork.playerrolecheckerconnector.playerrolecheckerconnector.Util;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.klnetwork.playerrolecheckerconnector.playerrolecheckerconnector.JDA.JDA;

import javax.security.auth.login.LoginException;
import java.util.List;

import static net.klnetwork.playerrolecheckerconnector.playerrolecheckerconnector.PlayerRoleCheckerConnector.plugin;



public class JDAUtil {
    public static List<Role> getRolesById(String id){
        for(Guild guild : JDA.jda.getGuilds()) return guild.retrieveMemberById(id).complete().getRoles();
    return null;
    }
}
