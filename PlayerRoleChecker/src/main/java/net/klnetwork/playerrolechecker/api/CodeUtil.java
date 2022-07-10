package net.klnetwork.playerrolechecker.api;


import net.klnetwork.playerrolechecker.PlayerRoleChecker;
import net.klnetwork.playerrolechecker.table.Temporary;

import java.util.UUID;

public class CodeUtil {

    public static int getRandom(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    public static int CodeIssue(UUID uuid) {
        int max = PlayerRoleChecker.INSTANCE.getConfig().getInt("CodeLimit.max");
        int min = PlayerRoleChecker.INSTANCE.getConfig().getInt("CodeLimit.min");

        int result = getRandom(min, max);

        while (Temporary.getInstance().hasUUID(result)) {
            result = getRandom(min, max);
        }

        Temporary.getInstance().put(uuid.toString(), Integer.toString(result));

        return result;
    }
}