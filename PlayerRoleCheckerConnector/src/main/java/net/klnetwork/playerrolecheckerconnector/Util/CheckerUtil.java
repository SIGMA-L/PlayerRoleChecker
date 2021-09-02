package net.klnetwork.playerrolecheckerconnector.Util;

import java.util.List;
import java.util.UUID;

import net.dv8tion.jda.api.entities.Role;
import static net.klnetwork.playerrolecheckerconnector.PlayerRoleCheckerConnector.*;

import static net.klnetwork.playerrolecheckerconnector.Util.JDAUtil.getRolesById;
import static net.klnetwork.playerrolecheckerconnector.Util.SQLUtil.getDiscordFromSQL;

public class CheckerUtil {

    public static boolean CheckPlayer(UUID uuid) {
        String[] DiscordID = getDiscordFromSQL(uuid.toString());
        if (DiscordID == null) return false;

        List<Role> role = getRolesById(DiscordID[1]);
        if (role == null) return false;
        for (Role role1 : role) if (list.contains(role1.getId())) return true;


        return false;
    }
}
