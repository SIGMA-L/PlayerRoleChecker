package net.klnetwork.playerrolechecker.api.utils.message.data.preset.other;

import net.klnetwork.playerrolechecker.api.utils.CommonUtils;
import net.klnetwork.playerrolechecker.api.utils.message.data.CustomMessage;
import net.klnetwork.playerrolechecker.api.utils.message.data.CustomMessageType;

import java.awt.*;

public class ColorProvider extends CustomMessage {
    public Color execute(String color) {
        return CommonUtils.getColor(color);
    }

    @Override
    public CustomMessageType key() {
        return CustomMessageType.COLOR;
    }
}
