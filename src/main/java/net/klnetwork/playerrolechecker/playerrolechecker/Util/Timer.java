package net.klnetwork.playerrolechecker.playerrolechecker.Util;

import net.klnetwork.playerrolechecker.playerrolechecker.MySQL.SQLite;
import net.klnetwork.playerrolechecker.playerrolechecker.PlayerRoleChecker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Timer{
    public void waitTimer(UUID uuid){
        Bukkit.getScheduler().runTaskLater(PlayerRoleChecker.plugin, () -> {
            try {
                Thread.sleep(300000);
                String[] result = SQLite.getCodeFromSQLLite(uuid);
                if (result != null) {
                    SQLite.removeSQLLite(result[0], result[1]);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },6000);
    }
}
