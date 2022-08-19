package net.klnetwork.playerrolechecker.event;

import net.klnetwork.playerrolechecker.PlayerRoleChecker;
import net.klnetwork.playerrolechecker.api.data.common.JoinHandler;
import net.klnetwork.playerrolechecker.api.data.common.TemporaryData;
import net.klnetwork.playerrolechecker.api.utils.CommonUtils;
import net.klnetwork.playerrolechecker.table.LocalSQL;
import net.klnetwork.playerrolechecker.util.CodeUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.UUID;

public class JoinEvent extends JoinHandler {
    @Override
    public void onPreLoginEvent(AsyncPlayerPreLoginEvent event) {
        TemporaryData data = LocalSQL.getInstance().getCode(event.getUniqueId());

        if (data != null) {
            disallow(event, String.join("\n", PlayerRoleChecker.INSTANCE.getConfig().getStringList("JoinEvent.already-code")).replaceAll("%code%", data.getCode().toString()));
        } else {
            final Integer code = CodeUtil.generateCode();

            LocalSQL.getInstance().put(event.getUniqueId(), code, CommonUtils.isFloodgateUser(event.getUniqueId()));

            disallow(event, String.join("\n", PlayerRoleChecker.INSTANCE.getConfig().getStringList("JoinEvent.code")).replaceAll("%code%", code.toString()));

            run(event.getUniqueId());
        }
    }

    @Override
    public void onErrorCaught(AsyncPlayerPreLoginEvent event, Exception ex) {
        Bukkit.getLogger().severe("error detected on LoginEvent");

        Bukkit.getLogger().severe("----------------------------------------");
        ex.printStackTrace();
        Bukkit.getLogger().severe("----------------------------------------");
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
