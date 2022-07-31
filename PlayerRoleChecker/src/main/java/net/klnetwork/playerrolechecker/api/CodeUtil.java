package net.klnetwork.playerrolechecker.api;


import net.klnetwork.playerrolechecker.PlayerRoleChecker;
import net.klnetwork.playerrolechecker.table.LocalSQL;

/* TODO: REFACTOR */
public class CodeUtil {

    public static int getRandom(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    public static int generateCode() {
        int max = PlayerRoleChecker.INSTANCE.getConfig().getInt("CodeLimit.max");
        int min = PlayerRoleChecker.INSTANCE.getConfig().getInt("CodeLimit.min");

        int result = getRandom(min, max);

        while (LocalSQL.getInstance().hasUUID(result)) {
            result = getRandom(min, max);
        }

        return result;
    }
}