package net.klnetwork.playerrolechecker.playerrolechecker.Util;

import net.klnetwork.playerrolechecker.playerrolechecker.MySQL.SQLite;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Timer {
    public void waitTimer(UUID uuid) {
        new Thread(()-> {
            try {
                TimeUnit.SECONDS.sleep(300);

                String[] result = SQLite.getCodeFromSQLite(uuid);
                if (result != null) {
                    SQLite.removeSQLite(result[0], result[1]);

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
