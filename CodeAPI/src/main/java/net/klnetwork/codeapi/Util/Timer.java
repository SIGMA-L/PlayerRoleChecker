package net.klnetwork.codeapi.Util;

import net.klnetwork.codeapi.PlayerRoleChecker;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Timer {
    public void waitTimer(UUID uuid) {
        new Thread(()-> {
            try {
                TimeUnit.SECONDS.sleep(PlayerRoleChecker.plugin.getConfig().getInt("RemoveSecond"));
                String[] result = SQLiteUtil.getCodeFromSQLite(uuid.toString());
                if (result != null) {
                    SQLiteUtil.removeSQLite(result[0], result[1]);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
