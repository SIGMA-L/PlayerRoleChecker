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

    private static Set<HookedAPIType> getAPIType() {
        return pairs.keySet();
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

    /**
     * @return APIのバージョン
     *
     * @implNote
     * プレイヤーロールチェッカーのバージョンを確認したい場合は {@link Plugin#getDescription()#getVersion()} を使用してください
     */
    public static String getVersion() {
        return "v4.21";
    }
}