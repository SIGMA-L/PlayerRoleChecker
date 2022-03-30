package net.klnetwork.playerrolechecker.api;


import net.klnetwork.playerrolechecker.PlayerRoleChecker;
import net.klnetwork.playerrolechecker.util.SQLiteUtil;

import java.util.UUID;

public class CodeUtil {

    public static int getRandom(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    public static int CodeIssue(UUID uuid) {
        int max = PlayerRoleChecker.INSTANCE.getConfig().getInt("CodeLimit.max");
        int min = PlayerRoleChecker.INSTANCE.getConfig().getInt("CodeLimit.min");

        int result = getRandom(min, max);

        while (SQLiteUtil.hasUUID(result)) {
            result = getRandom(min, max);
        }

        SQLiteUtil.putSQLite(uuid.toString(), Integer.toString(result));

        return result;
    }
}