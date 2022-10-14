package net.klnetwork.playerrolechecker.api;

import net.klnetwork.playerrolechecker.api.data.APIHook;
import net.klnetwork.playerrolechecker.api.data.checker.CheckerAPIHook;
import net.klnetwork.playerrolechecker.api.data.codeapi.CodeAPIHook;
import net.klnetwork.playerrolechecker.api.data.connector.ConnectorAPIHook;
import net.klnetwork.playerrolechecker.api.enums.HookedAPIType;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class PlayerRoleCheckerAPI {
    private static final Map<HookedAPIType, APIHook> pairs = new HashMap<>();

    public static boolean isHookedConnector() {
        return getHookedAPIType().contains(HookedAPIType.CONNECTOR);
    }

    /**
     * @return {@link ConnectorAPIHook}
     *
     * @implNote
     * <br><strong>注意:</strong>
     * <br>※1 このメソッドは他のプラグインが返される可能性があります
     * <br>※2 複数のAPIに接続できた場合は最後に見つかったAPIが返されます
     */
    public static ConnectorAPIHook getConnectorAPI() {
        if (!pairs.containsKey(HookedAPIType.CONNECTOR) && !isHookedConnector()) {
            init();
        }

        return (ConnectorAPIHook) pairs.get(HookedAPIType.CONNECTOR);
    }

    /**
     * @return {@link ConnectorAPIHook}
     *
     * @implNote
     * <br> コネクターのAPIを返します
     *
     * @see ConnectorAPIHook
     * @see org.bukkit.plugin.PluginManager#getPlugin(String)
     */
    public static ConnectorAPIHook getConnector() {
        return (ConnectorAPIHook) Bukkit.getPluginManager().getPlugin("PlayerRoleCheckerConnector");
    }

    public static boolean isHookedChecker() {
        return getHookedAPIType().contains(HookedAPIType.CHECKER);
    }

    /**
     * @implNote
     * <br><strong>注意:</strong>
     * <br>※1 このメソッドは他のプラグインが返される可能性があります
     * <br>※2 複数のAPIに接続できた場合は最後に見つかったAPIが返されます
     */
    public static CheckerAPIHook getCheckerAPI() {
        if (!pairs.containsKey(HookedAPIType.CHECKER) && !isHookedChecker()) {
            init();
        }

        return (CheckerAPIHook) pairs.get(HookedAPIType.CHECKER);
    }

    public static CheckerAPIHook getChecker() {
        return (CheckerAPIHook) Bukkit.getPluginManager().getPlugin("PlayerRoleChecker");
    }

    /**
     * @return CodeAPIのAPIに接続されているか
     *
     * @implNote
     * <br><strong>注意:</strong>
     * <br>※1 このメソッドは他のプラグインが返される可能性があります
     * <br>※2 複数のAPIに接続できた場合は最後に見つかったAPIが返されます
     *
     * @see #getHookedAPIType()
     */
    public static boolean isHookedCodeAPI() {
        return getHookedAPIType().contains(HookedAPIType.CODEAPI);
    }

    /**
     * @return {@link CodeAPIHook}
     *
     * @implNote
     * <br><strong>注意:</strong>
     * <br>※1 このメソッドは他のプラグインが返される可能性があります
     * <br>※2 複数のAPIに接続できた場合は最後に見つかったAPIが返されます
     *
     * @see CodeAPIHook
     * @see #isHookedCodeAPI()
     * @see #getHookedAPIType()
     */
    public static CodeAPIHook getCodeAPI() {
        if (!pairs.containsKey(HookedAPIType.CHECKER) && !isHookedCodeAPI()) {
            init();
        }

        return (CodeAPIHook) pairs.get(HookedAPIType.CODEAPI);
    }

    public static List<HookedAPIType> getHookedAPIType() {
        if (pairs.isEmpty()) {
            init();
        }

        return new ArrayList<>(getAPIType());
    }

    public static Set<HookedAPIType> getAPIType() {
        return pairs.keySet();
    }

    /**
     * @return キャッシュされた使用可能なAPI
     */
    public static Map<HookedAPIType, APIHook> getPairs() {
        return pairs;
    }

    /**
     * @implNote
     * <br>使用できるAPIを取得します
     * <br>このメソッドが使われた場合 {@link #getPairs()} にキャッシュされます
     */
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

    /**
     * @return APIのバージョンを返します
     *
     * @see APIHook
     */
    public static String getVersion() {
        return "v4.21";
    }
}