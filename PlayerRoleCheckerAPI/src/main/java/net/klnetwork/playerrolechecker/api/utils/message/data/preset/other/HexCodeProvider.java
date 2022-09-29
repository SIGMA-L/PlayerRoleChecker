package net.klnetwork.playerrolechecker.api.utils.message.data.preset.other;

import net.klnetwork.playerrolechecker.api.utils.message.data.CustomMessage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HexCodeProvider extends CustomMessage {
    private final static Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
    private final static String COLOR_CHAR = "\u00A7";

    public String execute(String message) {
        Matcher matcher = pattern.matcher(message);

        while (matcher.find()) {
            String hexCode = matcher.group();

            message = message.replaceFirst(hexCode, COLOR_CHAR + "x"
                    + COLOR_CHAR + hexCode.charAt(1) + COLOR_CHAR + hexCode.charAt(2)
                    + COLOR_CHAR + hexCode.charAt(3) + COLOR_CHAR + hexCode.charAt(4)
                    + COLOR_CHAR + hexCode.charAt(5) + COLOR_CHAR + hexCode.charAt(6));
        }

        return message;
    }
}
