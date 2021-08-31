package net.klnetwork.playerrolechecker.playerrolechecker.Events;

import net.klnetwork.playerrolechecker.playerrolechecker.API.CodeUtil;
import net.klnetwork.playerrolechecker.playerrolechecker.MySQL.SQLite;
import net.klnetwork.playerrolechecker.playerrolechecker.Util.Timer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.UUID;

public class JoinEvent implements Listener {
    @EventHandler
    public void CodeIssue(AsyncPlayerPreLoginEvent e) {
        String[] result = SQLite.getCodeFromSQLLite(e.getUniqueId());
        if(result != null){
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, ChatColor.RED + "(発行済み) あなたのコードは \n「" + result[1] + "」 です 5分以内に認証してください");
            return;
        }
        e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, ChatColor.RED + "あなたのコードは \n「" + CodeUtil.CodeIssue(e.getUniqueId()) + "」 です 5分以内に認証してください");
        new Timer().waitTimer(e.getUniqueId());
    }
}
