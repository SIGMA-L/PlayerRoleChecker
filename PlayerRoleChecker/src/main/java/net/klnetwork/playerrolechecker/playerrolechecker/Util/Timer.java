package net.klnetwork.playerrolechecker.playerrolechecker.Util;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Timer {
    public void waitTimer(UUID uuid) {
        new Thread(()-> {
            try {
                TimeUnit.SECONDS.sleep(300);

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
