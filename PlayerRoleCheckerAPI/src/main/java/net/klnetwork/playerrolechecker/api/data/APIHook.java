package net.klnetwork.playerrolechecker.api.data;

import net.klnetwork.playerrolechecker.api.enums.HookedAPIType;
import org.bukkit.plugin.Plugin;

public interface APIHook {
    Plugin getPlugin();

    PlayerDataTable getPlayerData();

    HookedAPIType getType();
}