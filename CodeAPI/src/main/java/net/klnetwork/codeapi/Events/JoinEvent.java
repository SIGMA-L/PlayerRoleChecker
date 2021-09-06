package net.klnetwork.codeapi.Events;

import net.klnetwork.codeapi.API.CodeUtil;
import net.klnetwork.codeapi.Util.SQLiteUtil;
import net.klnetwork.codeapi.Util.Timer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class JoinEvent implements Listener {
    @EventHandler
    public void CodeIssue(AsyncPlayerPreLoginEvent e) {
        String[] result = SQLiteUtil.getCodeFromSQLite(e.getUniqueId().toString());
        if (result != null) {
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, ChatColor.RED + "(発行済み) あなたのコードは \n「" + result[1] + "」 です (発行されてから) 5分以内に認証してください");
            return;
        }
        e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, ChatColor.RED + "あなたのコードは \n「" + CodeUtil.CodeIssue(e.getUniqueId()) + "」 です 5分以内に認証してください");
        new Timer().waitTimer(e.getUniqueId());
    }
}
