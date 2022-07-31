package net.klnetwork.playerrolechecker.util;

import net.klnetwork.playerrolechecker.api.data.TemporaryData;
import net.klnetwork.playerrolechecker.table.LocalSQL;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

//todo: recode
public class OtherUtil {
    public static void waitTimer(UUID uuid) {
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(300);

                TemporaryData data = LocalSQL.getInstance().getCode(uuid);

                if (data != null) {
                    LocalSQL.getInstance().remove(data.getUUID(), data.getCode());
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
