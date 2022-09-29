package net.klnetwork.playerrolechecker.api.utils.message.data.preset.other;

import net.klnetwork.playerrolechecker.api.utils.message.data.CustomMessage;
import net.klnetwork.playerrolechecker.api.utils.message.data.CustomMessageType;
import org.bukkit.ChatColor;

public class ColorCodeProvider extends CustomMessage {
    public String execute(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    @Override
    public CustomMessageType key() {
        return CustomMessageType.MINECRAFT;
    }
}