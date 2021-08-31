package net.klnetwork.playerrolechecker.playerrolechecker.API;


import static net.klnetwork.playerrolechecker.playerrolechecker.MySQL.SQLite.CheckCode;

public class CodeUtil {

    public static int getRandom(int min, int max) {
        int x = (int) (Math.random() * (max - min + 1)) + min;
        return x;
    }

    public void CodeIssue() {
        Boolean result = true;
        while (result) {
            Integer code = Integer.valueOf(getRandom(1000, 9999));
            result = CheckCode(code);
            if (!result) {
                break;
            } else {

            }

        }
    }

}
