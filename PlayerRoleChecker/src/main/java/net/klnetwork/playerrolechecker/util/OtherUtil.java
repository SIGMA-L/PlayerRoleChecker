package net.klnetwork.playerrolechecker.util;

import net.klnetwork.playerrolechecker.table.LocalSQL;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

//todo: recode
public class OtherUtil {
    public static void waitTimer(UUID uuid) {
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(300);

                Integer integer = LocalSQL.getInstance().getCode(uuid);
                if (integer != null) {
                    LocalSQL.getInstance().remove(uuid, integer);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
