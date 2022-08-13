package net.klnetwork.playerrolechecker.api;

import net.klnetwork.playerrolechecker.api.data.APIHook;
import net.klnetwork.playerrolechecker.api.data.checker.CheckerAPIHook;
import net.klnetwork.playerrolechecker.api.data.connector.ConnectorAPIHook;
import net.klnetwork.playerrolechecker.api.enums.HookedAPIType;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class PlayerRoleCheckerAPI {

    private static final Map<HookedAPIType, APIHook> pairs = new HashMap<>();

    public static boolean isHookedConnector() {
        final HookedAPIType type = getHookedAPIType();

        return type == HookedAPIType.BOTH || type == HookedAPIType.CONNECTOR;
    }

    public static ConnectorAPIHook getConnectorAPI() {
        if (!pairs.containsKey(HookedAPIType.CONNECTOR) && !isHookedConnector()) {
            init();
        }

        return (ConnectorAPIHook) pairs.get(HookedAPIType.CONNECTOR);
    }

    public static boolean isHookedChecker() {
        final HookedAPIType type = getHookedAPIType();

        return type == HookedAPIType.BOTH || type == HookedAPIType.CHECKER;
    }

    public static CheckerAPIHook getCheckerAPI() {
        if (!pairs.containsKey(HookedAPIType.CHECKER) && !isHookedChecker()) {
            init();
        }

        return (CheckerAPIHook) pairs.get(HookedAPIType.CHECKER);
    }

    public static HookedAPIType getHookedAPIType() {
        if (pairs.isEmpty()) {
            init();
        }

        return getAPIType();
    }

    private static HookedAPIType getAPIType() {
        List<HookedAPIType> requireType = new ArrayList<>(Arrays.asList(HookedAPIType.CHECKER, HookedAPIType.CONNECTOR));
        pairs.keySet().forEach(requireType::remove);

        if (requireType.size() == 0) {
            return HookedAPIType.BOTH;
        } else if (requireType.size() == 1) {
            return requireType.get(0);
        } else {
            return HookedAPIType.NONE;
        }
    }

    public static void init() {
        pairs.clear();

        List<Plugin> plugins = Arrays.asList(Bukkit.getPluginManager().getPlugins());

        plugins.forEach(plugin -> {
            if (plugin instanceof APIHook) {
                APIHook hook = (APIHook) plugin;

                pairs.put(hook.getType(), hook);
            }
        });
    }

    public static String getVersion() {
        return "v4.0";
    }
}