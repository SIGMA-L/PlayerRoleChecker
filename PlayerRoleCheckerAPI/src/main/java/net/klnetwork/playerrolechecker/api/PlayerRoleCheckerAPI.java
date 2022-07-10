package net.klnetwork.playerrolechecker.api;

import net.klnetwork.playerrolechecker.api.enums.HookedAPIType;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerRoleCheckerAPI {

    public static boolean isConnector() {
        final HookedAPIType type = getHookedAPIType();

        return type == HookedAPIType.BOTH || type == HookedAPIType.CONNECTOR;
    }

    public static boolean isChecker() {
        final HookedAPIType type = getHookedAPIType();

        return type == HookedAPIType.BOTH || type == HookedAPIType.CHECKER;
    }

    public static HookedAPIType getHookedAPIType() {
        return HookedAPIType.NONE;
    }
}