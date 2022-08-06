package net.klnetwork.playerrolechecker.event;

import net.klnetwork.playerrolechecker.PlayerRoleChecker;
import net.klnetwork.playerrolechecker.api.data.common.JoinHandler;
import net.klnetwork.playerrolechecker.api.data.common.TemporaryData;
import net.klnetwork.playerrolechecker.api.utils.CommonUtils;
import net.klnetwork.playerrolechecker.table.LocalSQL;
import net.klnetwork.playerrolechecker.util.CodeUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.UUID;

public class JoinEvent extends JoinHandler {
    @Override
    public void onPreLoginEvent(AsyncPlayerPreLoginEvent event) {
        TemporaryData data = LocalSQL.getInstance().getCode(event.getUniqueId());

        if (data != null) {
            String message = String.join("\n", PlayerRoleChecker.INSTANCE.getConfig().getStringList("JoinEvent.already-code"));

            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, ChatColor.translateAlternateColorCodes('&', message.replaceAll("%code%", String.valueOf(data.getCode()))));
        } else {
            final boolean isFloodGateUser = CommonUtils.hasFloodGate()
                    && CommonUtils.isFloodgateUser(event.getUniqueId());

            final int code = CodeUtil.generateCode();
            LocalSQL.getInstance().put(event.getUniqueId(), code, isFloodGateUser);

            String message = String.join("\n", PlayerRoleChecker.INSTANCE.getConfig().getStringList("JoinEvent.code"));
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, ChatColor.translateAlternateColorCodes('&', message.replaceAll("%code%", String.valueOf(code))));
            run(event.getUniqueId());
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
