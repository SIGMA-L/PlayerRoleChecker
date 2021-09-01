package net.klnetwork.playerrolecheckerconnector.playerrolecheckerconnector.Util;

import java.util.List;
import java.util.UUID;

import net.dv8tion.jda.api.entities.Role;
import static net.klnetwork.playerrolecheckerconnector.playerrolecheckerconnector.PlayerRoleCheckerConnector.*;

import static net.klnetwork.playerrolecheckerconnector.playerrolecheckerconnector.Util.SQLUtil.getDiscordFromSQL;
import static net.klnetwork.playerrolecheckerconnector.playerrolecheckerconnector.Util.JDAUtil.getRolesById;

public class CheckerUtil {

    public static boolean CheckPlayer(UUID uuid) {
        String[] DiscordID = getDiscordFromSQL(uuid.toString());
        if (DiscordID == null) {
            return false;
        }
        List<Role> role = getRolesById(DiscordID[1]);
        for (Role role1 : role) {
            if (list.contains(role1.getId())) {
                return true;
            }

        }


      return false;
    }
}
