package net.klnetwork.playerrolechecker.api.data;

import net.klnetwork.playerrolechecker.api.enums.HookedAPIType;
import net.klnetwork.playerrolechecker.api.utils.Metrics;
import net.klnetwork.playerrolechecker.api.utils.updater.UpdateAlert;
import org.bukkit.plugin.Plugin;

public interface APIHook {
    Plugin getPlugin();

    /**
     * @return bStatsのMetricsを返します
     *
     * @see Metrics
     * @see <a href="https://bstats.org/">bStats</a>
     */
    Metrics getMetrics();

    /**
     *
     */
    JoinManager getJoinManager();

    /**
     * @return
     */
    UpdateAlert getUpdateAlert();

    /**
     * @return プラグインのバージョンを返します
     */
    String getVersion();

    /**
     *
     * @see HookedAPIType
     */
    HookedAPIType getType();
}