package net.klnetwork.playerrolechecker.API;


import net.klnetwork.playerrolechecker.PlayerRoleChecker;
import net.klnetwork.playerrolechecker.Util.SQLiteUtil;

import static net.klnetwork.playerrolechecker.PlayerRoleChecker.plugin;
import static net.klnetwork.playerrolechecker.Util.SQLiteUtil.CheckCode;

import java.util.UUID;

public class CodeUtil {

    public static int getRandom(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    public static int CodeIssue(UUID uuid) {
        int result = getRandom(plugin.getConfig().getInt("CodeLimit.min"),plugin.getConfig().getInt("CodeLimit.max"));
        while (CheckCode(result)) {
            result = getRandom(plugin.getConfig().getInt("CodeLimit.min"),plugin.getConfig().getInt("CodeLimit.max"));
        }
        SQLiteUtil.putSQLite(uuid.toString(), Integer.toString(result));
        return result;
    }
}
