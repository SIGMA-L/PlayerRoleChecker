package net.klnetwork.playerrolechecker.event;

import net.klnetwork.playerrolechecker.PlayerRoleChecker;
import net.klnetwork.playerrolechecker.api.CodeUtil;
import net.klnetwork.playerrolechecker.api.data.TemporaryData;
import net.klnetwork.playerrolechecker.table.LocalSQL;
import net.klnetwork.playerrolechecker.util.OtherUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class JoinEvent implements Listener {
    @EventHandler
    public void onAsyncPlayerPreLoginEvent(AsyncPlayerPreLoginEvent e) {
        TemporaryData data = LocalSQL.getInstance().getCode(e.getUniqueId());
        if (data != null) {
            String already = String.join("\n", PlayerRoleChecker.INSTANCE.getConfig().getStringList("JoinEvent.already-code"));

            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, ChatColor.translateAlternateColorCodes('&', already.replaceAll("%code%", String.valueOf(data.getCode()))));
        } else {
            final int code = CodeUtil.CodeIssue(e.getUniqueId());

            String already = String.join("\n", PlayerRoleChecker.INSTANCE.getConfig().getStringList("JoinEvent.code"));

            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, ChatColor.translateAlternateColorCodes('&', already.replaceAll("%code%", String.valueOf(code))));

            OtherUtil.waitTimer(e.getUniqueId());
        }
    }
}