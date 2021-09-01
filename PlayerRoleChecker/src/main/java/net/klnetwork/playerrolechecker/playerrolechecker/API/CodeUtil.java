package net.klnetwork.playerrolechecker.playerrolechecker.API;


import net.klnetwork.playerrolechecker.playerrolechecker.Util.SQLiteUtil;

import static net.klnetwork.playerrolechecker.playerrolechecker.Util.SQLiteUtil.CheckCode;

import java.util.UUID;

public class CodeUtil {

    public static int getRandom(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    public static int CodeIssue(UUID uuid) {
        int result = getRandom(1000,9999);
        while (CheckCode(result)) {
            result = getRandom(1000,9999);
        }
        SQLiteUtil.putSQLite(uuid.toString(), Integer.toString(result));
        return result;
    }
}
