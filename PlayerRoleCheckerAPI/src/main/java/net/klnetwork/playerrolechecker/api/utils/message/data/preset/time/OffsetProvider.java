package net.klnetwork.playerrolechecker.api.utils.message.data.preset.time;

import net.klnetwork.playerrolechecker.api.utils.message.data.CustomMessage;
import net.klnetwork.playerrolechecker.api.utils.message.data.CustomMessageType;

import java.time.OffsetDateTime;

public class OffsetProvider extends CustomMessage {
    public String execute(String message) {
        return message.replaceAll("%offset_time%", OffsetDateTime.now().toString());
    }

    @Override
    public CustomMessageType key() {
        return CustomMessageType.ALL;
    }
}
