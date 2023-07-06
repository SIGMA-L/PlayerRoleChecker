package net.klnetwork.playerrolechecker.event;

import net.klnetwork.playerrolechecker.PlayerRoleChecker;
import net.klnetwork.playerrolechecker.api.data.checker.CheckerCodeData;
import net.klnetwork.playerrolechecker.api.data.common.JoinHandler;
import net.klnetwork.playerrolechecker.api.utils.CommonUtils;
import net.klnetwork.playerrolechecker.table.PlayerDataSQL;
import net.klnetwork.playerrolechecker.util.CodeUtil;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class JoinEvent extends JoinHandler {
    @Override
    // Todo: Wrapped AsyncPlayerPreLoginEvent
    public void onPreLoginEvent(AsyncPlayerPreLoginEvent event) {
        if (PlayerRoleChecker.INSTANCE.getConfigManager().isVerifiedPlayerIgnore()
            && PlayerDataSQL.getInstance().hasData(event.getUniqueId(), CommonUtils.isFloodgateUser(event.getUniqueId()))) {
            return;
        }
        CheckerCodeData data = PlayerRoleChecker.INSTANCE.getCodeHolder().get(event.getUniqueId());
        if (data != null) {
            disallow(event, String.join("\n", PlayerRoleChecker.INSTANCE.getConfig().getStringList("JoinEvent.already-code")).replaceAll("%code%", String.valueOf(data.getCode())));
        } else {
            final int code = CodeUtil.generateCode();
            if (PlayerRoleChecker.INSTANCE.getCodeHolder().add(event.getUniqueId(), code, CommonUtils.isFloodgateUser(event.getUniqueId()))) {
                disallow(event, String.join("\n", PlayerRoleChecker.INSTANCE.getConfig().getStringList("JoinEvent.code")).replaceAll("%code%", Integer.toString(code)));
            } else {
                disallow(event, "&cSomething went Wrong!");
            }
        }
    }

    @Override
    public void onErrorCaught(AsyncPlayerPreLoginEvent event, Exception ex) {
        ex.printStackTrace();
    }
}
