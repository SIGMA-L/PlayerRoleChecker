package net.klnetwork.playerrolechecker.api;


import net.klnetwork.playerrolechecker.PlayerRoleChecker;
import net.klnetwork.playerrolechecker.api.utils.CommonUtils;
import net.klnetwork.playerrolechecker.table.LocalSQL;

public class CodeUtil {
    public static int generateCode() {
        int max = PlayerRoleChecker.INSTANCE.getConfigManager().getMax();
        int min = PlayerRoleChecker.INSTANCE.getConfigManager().getMin();

        int result = CommonUtils.random(min, max);

        while (LocalSQL.getInstance().hasUUID(result)) {
            result = CommonUtils.random(min, max);
        }

        return result;
    }
}