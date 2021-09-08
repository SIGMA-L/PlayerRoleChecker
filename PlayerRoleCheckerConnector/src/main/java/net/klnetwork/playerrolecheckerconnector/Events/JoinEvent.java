package net.klnetwork.playerrolecheckerconnector.Events;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.klnetwork.playerrolecheckerconnector.Command.JoinMode;
import net.klnetwork.playerrolecheckerconnector.JDA.JDA;
import net.klnetwork.playerrolecheckerconnector.PlayerRoleCheckerConnector;
import net.klnetwork.playerrolecheckerconnector.Util.OtherUtil;
import net.klnetwork.playerrolecheckerconnector.Util.SQLUtil;
import net.klnetwork.playerrolecheckerconnector.Util.SQLiteUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public class JoinEvent implements Listener {

    @EventHandler
    public void onAsyncPreLoginEvent(AsyncPlayerPreLoginEvent e) {
        if (!JoinMode.joinMode) return;

        //SQLiteUtilはファイル管理であるため、非同期である必要はありません(位置的にここに必要)
        if (SQLiteUtil.getUUIDFromSQLite(e.getUniqueId().toString()) != null) {
            return;
        }

        if (!OtherUtil.CheckPlayer(e.getUniqueId())) {
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, ChatColor.GOLD + "あなたには参加権限がありません。\n" + ChatColor.AQUA + "Discordを確認してみてください。");
        }
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        e.setJoinMessage(ChatColor.GREEN + e.getPlayer().getName() + ChatColor.WHITE + "が入室しました");
        Bukkit.getScheduler().runTaskAsynchronously(PlayerRoleCheckerConnector.plugin, () -> {
            player.sendMessage(ChatColor.GOLD + "ご苦労さまです。" + ChatColor.AQUA + player.getName() + ChatColor.WHITE + "さん。");
            player.sendMessage(ChatColor.GREEN + "-----------------情報------------------");
            player.sendMessage("MinecraftName: " + player.getName());
            String[] result = SQLUtil.getDiscordFromSQL(player.getUniqueId().toString());
            if(result == null) return;
            player.sendMessage("DiscordID: " + result[1]);

            List<Role> roleList = null;
            String GuildID = PlayerRoleCheckerConnector.plugin.getConfig().getString("GuildID");
            if (GuildID != null) roleList = JDA.jda.getGuildById(GuildID).getMemberById(result[1]).getRoles();
            else for (Guild guild : JDA.jda.getGuilds()) roleList = guild.getMemberById(result[1]).getRoles();

            if (roleList == null) return;
            StringBuilder stringBuilder = new StringBuilder();
            for (Role role : roleList) {
                stringBuilder.append(role.getName()).append(" ");
            }
            player.sendMessage("DiscordRole: " + stringBuilder);
            player.sendMessage(ChatColor.GREEN + "-------------------------------------");
        });
        PlayerRoleCheckerConnector.commandlist.forEach(i -> Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(),OtherUtil.ReplaceString(i,player)));
    }
}