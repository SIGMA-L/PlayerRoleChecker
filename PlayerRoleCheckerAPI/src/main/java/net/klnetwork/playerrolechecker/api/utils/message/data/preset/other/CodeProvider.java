package net.klnetwork.playerrolechecker.api.utils.message.data.preset.other;

import net.klnetwork.playerrolechecker.api.utils.message.data.CustomMessage;
import net.klnetwork.playerrolechecker.api.utils.message.data.CustomMessageType;

public class CodeProvider extends CustomMessage {
    private final String code;

    public CodeProvider(String code) {
        this.code = code;
    }

    public CodeProvider(Long lon) {
        this.code = lon.toString();
    }

    public String execute(String message) {
        return message.replaceAll("%code%", code);
    }

    @Override
    public CustomMessageType key() {
        return CustomMessageType.MINECRAFT;
    }
}