package net.klnetwork.playerrolechecker.event;

import net.klnetwork.playerrolechecker.PlayerRoleChecker;
import net.klnetwork.playerrolechecker.api.CodeUtil;
import net.klnetwork.playerrolechecker.api.data.TemporaryData;
import net.klnetwork.playerrolechecker.api.utils.CommonUtils;
import net.klnetwork.playerrolechecker.table.LocalSQL;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.UUID;

public class JoinEvent implements Listener {
    @EventHandler
    public void onAsyncPlayerPreLoginEvent(AsyncPlayerPreLoginEvent e) {
        TemporaryData data = LocalSQL.getInstance().getCode(e.getUniqueId());
        if (data != null) {
            String already = String.join("\n", PlayerRoleChecker.INSTANCE.getConfig().getStringList("JoinEvent.already-code"));

            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, ChatColor.translateAlternateColorCodes('&', already.replaceAll("%code%", String.valueOf(data.getCode()))));
        } else {

            final boolean isFloodGateUser = CommonUtils.hasFloodGate()
                    && CommonUtils.isFloodgateUser(e.getUniqueId());

            final int code = CodeUtil.generateCode();

            LocalSQL.getInstance().put(e.getUniqueId(), code, isFloodGateUser);

            String already = String.join("\n", PlayerRoleChecker.INSTANCE.getConfig().getStringList("JoinEvent.code"));

            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, ChatColor.translateAlternateColorCodes('&', already.replaceAll("%code%", String.valueOf(code))));

            run(e.getUniqueId());
        }
    }

    public void run(UUID uuid) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(PlayerRoleChecker.INSTANCE, () -> {
            TemporaryData data = LocalSQL.getInstance().getCode(uuid);

            if (data != null) {
                LocalSQL.getInstance().remove(data.getUUID(), data.getCode());
            }
        },PlayerRoleChecker.INSTANCE.getConfigManager().getDeleteSecond() * 20L);
    }
}