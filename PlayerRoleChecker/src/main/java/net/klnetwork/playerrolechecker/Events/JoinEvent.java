package net.klnetwork.playerrolechecker.Events;

import net.klnetwork.playerrolechecker.API.CodeUtil;
import net.klnetwork.playerrolechecker.Util.OtherUtil;
import net.klnetwork.playerrolechecker.Util.SQLiteUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import static net.klnetwork.playerrolechecker.PlayerRoleChecker.plugin;

public class JoinEvent implements Listener {
    @EventHandler
    public void CodeIssue(AsyncPlayerPreLoginEvent e) {
        String[] result = SQLiteUtil.getCodeFromSQLite(e.getUniqueId().toString());
        if (result != null) {
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("JoinEvent.already-code.line1").replaceAll("%code%", result[1]) + "\n" + plugin.getConfig().getString("JoinEvent.already-code.line2").replaceAll("%code%", result[1])));
            return;
        }
        int code = CodeUtil.CodeIssue(e.getUniqueId());
        e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("JoinEvent.code.line1").replaceAll("%code%", Integer.toString(code)) + "\n" + plugin.getConfig().getString("JoinEvent.code.line2").replaceAll("%code%", Integer.toString(code))));
        new OtherUtil().waitTimer(e.getUniqueId());
    }
}
