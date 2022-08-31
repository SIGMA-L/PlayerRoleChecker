package net.klnetwork.codeapi.api;


import net.klnetwork.codeapi.CodeAPI;
import net.klnetwork.codeapi.table.LocalSQL;
import net.klnetwork.playerrolechecker.api.utils.CommonUtils;

public class CodeUtil {
    public static int generateCode() {
        int max = CodeAPI.INSTANCE.getConfigManager().getMax();
        int min = CodeAPI.INSTANCE.getConfigManager().getMin();

        int result = CommonUtils.random(min, max);

        while (LocalSQL.getInstance().hasUUID(result)) {
            result = CommonUtils.random(min, max);
        }

        return result;
    }
}
