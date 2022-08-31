package net.klnetwork.playerrolechecker.api.data;

import net.klnetwork.playerrolechecker.api.enums.HookedAPIType;
import net.klnetwork.playerrolechecker.api.utils.Metrics;
import org.bukkit.plugin.Plugin;

public interface APIHook {
    Plugin getPlugin();

    Metrics getMetrics();

    HookedAPIType getType();
}