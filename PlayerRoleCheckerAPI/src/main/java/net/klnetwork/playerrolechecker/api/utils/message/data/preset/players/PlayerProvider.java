package net.klnetwork.playerrolechecker.api.utils.message.data.preset.players;

import net.klnetwork.playerrolechecker.api.utils.CommonUtils;
import net.klnetwork.playerrolechecker.api.utils.message.data.CustomMessage;
import net.klnetwork.playerrolechecker.api.utils.message.data.CustomMessageType;
import org.bukkit.entity.Player;

public class PlayerProvider extends CustomMessage {
    private final Player player;

    public PlayerProvider(Player player) {
        this.player = player;
    }

    public String execute(String message) {
        return message.replaceAll("%name%", player.getName())
                .replaceAll("%uuid%", player.getUniqueId().toString())
                .replaceAll("%xuid%", ((Long) CommonUtils.getXUID(player.getUniqueId())).toString());
    }

    @Override
    public CustomMessageType key() {
        return CustomMessageType.ALL;
    }
}
