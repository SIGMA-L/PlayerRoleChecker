package net.klnetwork.playerrolechecker.api.utils.message.data.preset.time;

import net.klnetwork.playerrolechecker.api.utils.message.data.CustomMessage;
import net.klnetwork.playerrolechecker.api.utils.message.data.CustomMessageType;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeProvider extends CustomMessage {
    private static final DateFormat formatter = DateFormat.getDateInstance(DateFormat.FULL, new Locale("ja"));

    public String execute(String message) {
        return message.replaceAll("%standard_time%", formatter.format(new Date()));
    }

    @Override
    public CustomMessageType key() {
        return CustomMessageType.ALL;
    }
}
