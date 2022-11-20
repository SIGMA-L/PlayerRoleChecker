package net.klnetwork.playerrolechecker.api;

import net.klnetwork.playerrolechecker.api.data.APIHook;
import net.klnetwork.playerrolechecker.api.data.checker.CheckerAPIHook;
import net.klnetwork.playerrolechecker.api.data.codeapi.CodeAPIHook;
import net.klnetwork.playerrolechecker.api.data.connector.ConnectorAPIHook;
import net.klnetwork.playerrolechecker.api.enums.HookedAPIType;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.*;

/**
 * プレイヤーロールチェッカーのインスタンス的な存在です
 * @implNote
 * <li>APIに侵入したい場合は {@link PluginManager#getPlugin(String)}でプラグインを取得し、{@link CodeAPIHook} にキャストしてください
 * <li>APIを上書きしたい場合は {@link CodeAPIHook} を拡張してください
 *
 * @see CodeAPIHook
 * @since 4.0
 */
public class PlayerRoleCheckerAPI {
    private static final Map<HookedAPIType, APIHook> pairs = new HashMap<>();

    /**
     * @return ConnectorのAPIに接続できているか
     *
     * @implNote
     * <li> このメソッドは他のプラグインが返される可能性があります
     * <li> 複数のAPIに接続できた場合は最後に見つかったAPIが返されます
     *
     * @see #getHookedAPIType()
     */
    public static boolean isHookedConnector() {
        return getHookedAPIType().contains(HookedAPIType.CONNECTOR);
    }

    /**
     * @return {@link ConnectorAPIHook}
     *
     * @implNote
     * <li> このメソッドは他のプラグインが返される可能性があります
     * <li> 複数のAPIに接続できた場合は最後に見つかったAPIが返されます
     */
    public static ConnectorAPIHook getConnectorAPI() {
        if (!pairs.containsKey(HookedAPIType.CONNECTOR) && !isHookedConnector()) {
            init();
        }

        return (ConnectorAPIHook) pairs.get(HookedAPIType.CONNECTOR);
    }

    /**
     * @return {@link ConnectorAPIHook} コネクターのAPIを返します
     *
     * @see ConnectorAPIHook
     * @see org.bukkit.plugin.PluginManager#getPlugin(String)
     */
    public static ConnectorAPIHook getConnector() {
        return (ConnectorAPIHook) Bukkit.getPluginManager().getPlugin("PlayerRoleCheckerConnector");
    }

    /**
     * @return CheckerのAPIに接続されているか
     *
     * @implNote
     * <li> このメソッドは他のプラグインが返される可能性があります
     * <li> 複数のAPIに接続できた場合は最後に見つかったAPIが返されます
     *
     * @see #getHookedAPIType()
     */
    public static boolean isHookedChecker() {
        return getHookedAPIType().contains(HookedAPIType.CHECKER);
    }

    /**
     * @implNote
     * <li> このメソッドは他のプラグインが返される可能性があります
     * <li> 複数のAPIに接続できた場合は最後に見つかったAPIが返されます
     */
    public static CheckerAPIHook getCheckerAPI() {
        if (!pairs.containsKey(HookedAPIType.CHECKER) && !isHookedChecker()) {
            init();
        }

        return (CheckerAPIHook) pairs.get(HookedAPIType.CHECKER);
    }

    /**
     * @return {@link CheckerAPIHook} チェッカーのAPIを返します
     *
     * @implNote
     *
     * @see ConnectorAPIHook
     * @see org.bukkit.plugin.PluginManager#getPlugin(String)
     */
    public static CheckerAPIHook getChecker() {
        return (CheckerAPIHook) Bukkit.getPluginManager().getPlugin("PlayerRoleChecker");
    }

    /**
     * @return CodeAPIのAPIに接続されているか
     *
     * @implNote
     * <li> このメソッドは他のプラグインが返される可能性があります
     * <li> 複数のAPIに接続できた場合は最後に見つかったAPIが返されます
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
     * <li> このメソッドは他のプラグインが返される可能性があります
     * <li> 複数のAPIに接続できた場合は最後に見つかったAPIが返されま
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

    /**
     * @return 現在接続可能なAPIを {@link List} で提供します
     *
     * @implNote
     * <li> キャッシュがされていない場合 {@link #init()} を実行します
     */
    public static List<HookedAPIType> getHookedAPIType() {
        if (pairs.isEmpty()) {
            init();
        }

        return new ArrayList<>(getAPIType());
    }

    /**
     * @return 現在接続可能なAPIを {@link Set} で提供します
     *
     * @implNote
     * <li> このメソッドは {@link #init()} を実行していない場合必ず空の {@link Set} で返されます
     */
    public static Set<HookedAPIType> getAPIType() {
        return pairs.keySet();
    }

    /**
     * @return 使用可能なAPIを返します
     *
     * @implNote
     * <li> このメソッドは {@link #init()} を実行していない場合必ず空の {@link HashMap} が返されます
     */
    public static Map<HookedAPIType, APIHook> getPairs() {
        return pairs;
    }

    /**
     * 使用できるAPIを取得します
     *
     * @implNote
     * <li> このメソッドが使われた場合 {@link #getPairs()} にキャッシュされます
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