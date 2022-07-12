package net.klnetwork.playerrolecheckerconnector.event;

import net.dv8tion.jda.api.entities.Role;
import net.klnetwork.playerrolecheckerconnector.command.JoinModeCommand;
import net.klnetwork.playerrolecheckerconnector.PlayerRoleCheckerConnector;
import net.klnetwork.playerrolecheckerconnector.table.LocalSQL;
import net.klnetwork.playerrolecheckerconnector.table.PlayerData;
import net.klnetwork.playerrolecheckerconnector.util.OtherUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public class JoinEvent implements Listener {

    @EventHandler
    public void onAsyncPreLoginEvent(AsyncPlayerPreLoginEvent e) {
        if (!JoinModeCommand.joinMode) return;

        //SQLiteUtilはファイル管理であるため、非同期である必要はありません(位置的にここに必要)
        if (PlayerRoleCheckerConnector.INSTANCE.getConfig().getBoolean("SQLite.useBypassCommand") && (LocalSQL.getInstance().getUUID(e.getUniqueId().toString()) != null || LocalSQL.getInstance().getUUID(e.getName().toLowerCase()) != null)) {
            return;
        }

        try {
            if (!OtherUtil.hasRole(e.getUniqueId())) {
                String kickMessage = String.join("\n", PlayerRoleCheckerConnector.INSTANCE.getConfig().getStringList("Minecraft.kickMessage"));

                e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, ChatColor.translateAlternateColorCodes('&', kickMessage));
            }
        } catch (Exception ex) {
            String errorCaught = String.join("\n", PlayerRoleCheckerConnector.INSTANCE.getConfig().getStringList("Minecraft.errorCaught"));

            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, ChatColor.translateAlternateColorCodes('&', errorCaught));
        }
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        PlayerRoleCheckerConnector.INSTANCE.getCommandList().forEach(string -> Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), OtherUtil.replaceString(string, e.getPlayer())));

        PlayerData.getInstance().asyncDiscordId(e.getPlayer().getUniqueId(), discordId -> {
            if (discordId != null) {

                List<Role> roles = OtherUtil.getRolesById(discordId);

                if (roles != null) {

                    StringBuilder stringBuilder = new StringBuilder();

                    roles.forEach(role -> stringBuilder.append(role.getName()).append(" "));

                    PlayerRoleCheckerConnector.INSTANCE.getConfig().getStringList("Minecraft.message").forEach(string -> e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', OtherUtil.replaceDiscord(string, discordId, e.getPlayer()).replaceAll("%role%", String.valueOf(stringBuilder)))));
                }
            }
        });
    }
}