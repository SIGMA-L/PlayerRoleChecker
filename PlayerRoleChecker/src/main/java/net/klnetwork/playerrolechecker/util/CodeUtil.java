package net.klnetwork.playerrolechecker.util;


import net.klnetwork.playerrolechecker.PlayerRoleChecker;
import net.klnetwork.playerrolechecker.api.utils.CommonUtils;

public class CodeUtil {
    public static int generateCode() {
        int max = PlayerRoleChecker.INSTANCE.getConfigManager().getMax();
        int min = PlayerRoleChecker.INSTANCE.getConfigManager().getMin();

        int result = CommonUtils.random(min, max);

        while (PlayerRoleChecker.INSTANCE.getCodeHolder().has(result)) {
            result = CommonUtils.random(min, max);
        }

        return result;
    }
}