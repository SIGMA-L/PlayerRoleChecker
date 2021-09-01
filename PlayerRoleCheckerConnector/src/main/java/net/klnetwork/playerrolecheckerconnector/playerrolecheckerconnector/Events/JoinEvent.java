package net.klnetwork.playerrolecheckerconnector.playerrolecheckerconnector.Events;

import net.klnetwork.playerrolecheckerconnector.playerrolecheckerconnector.Util.CheckerUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent {
    @EventHandler
    public void JoinEvents(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (!CheckerUtil.CheckPlayer(player.getUniqueId())) {
            player.kickPlayer(ChatColor.GOLD + "あなたには参加権限がありません。\n" + ChatColor.AQUA + "Discordを確認してみてください。");
            return;
        }
        player.sendMessage();
    }
}
