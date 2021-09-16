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

import static net.klnetwork.playerrolecheckerconnector.PlayerRoleCheckerConnector.plugin;
import java.util.List;

public class JoinEvent implements Listener {

    @EventHandler
    public void onAsyncPreLoginEvent(AsyncPlayerPreLoginEvent e) {
        if (!JoinMode.joinMode) return;

        //SQLiteUtilはファイル管理であるため、非同期である必要はありません(位置的にここに必要)
        if (SQLiteUtil.getUUIDFromSQLite(e.getUniqueId().toString()) != null || SQLiteUtil.getUUIDFromSQLite(e.getName().toLowerCase()) != null) {
            return;
        }

        if (!OtherUtil.CheckPlayer(e.getUniqueId())) {
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Minecraft.kickMessage.line1") + "\n" + plugin.getConfig().getString("Minecraft.kickMessage.line2")));
        }
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        PlayerRoleCheckerConnector.commandlist.forEach(i -> Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), OtherUtil.ReplaceString(i, player)));

        if (plugin.getConfig().getBoolean("Minecraft.joinMessageBoolean")) e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', OtherUtil.ReplaceString(plugin.getConfig().getString("Minecraft.joinMessage"), player)));
        Bukkit.getScheduler().runTaskAsynchronously(PlayerRoleCheckerConnector.plugin, () -> {
            String[] result = SQLUtil.getDiscordFromSQL(player.getUniqueId().toString());
            if (result == null) return;

            List<Role> roleList = null;
            String GuildID = PlayerRoleCheckerConnector.plugin.getConfig().getString("GuildID");
            if (GuildID != null) roleList = JDA.jda.getGuildById(GuildID).retrieveMemberById(result[1]).complete().getRoles();
            else for (Guild guild : JDA.jda.getGuilds()) roleList = guild.retrieveMemberById(result[1]).complete().getRoles();

            if (roleList == null) return;

            StringBuilder stringBuilder = new StringBuilder();
            for (Role role : roleList) {
                stringBuilder.append(role.getName()).append(" ");
            }

            plugin.getConfig().getStringList("Minecraft.message").forEach(i -> player.sendMessage(ChatColor.translateAlternateColorCodes('&', i.replaceAll("%name%", player.getName()).replaceAll("%uuid%", String.valueOf(player.getUniqueId())).replaceAll("%discordid%", result[1]).replaceAll("%role%", String.valueOf(stringBuilder)))));
        });
    }
}