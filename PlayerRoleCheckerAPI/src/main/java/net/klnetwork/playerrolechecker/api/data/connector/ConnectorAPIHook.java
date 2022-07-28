package net.klnetwork.playerrolechecker.api.data.connector;

import net.klnetwork.playerrolechecker.api.data.APIHook;
import net.klnetwork.playerrolechecker.api.utils.config.ConfigManager;

public interface ConnectorAPIHook extends APIHook {
    ConnectorBypassTable getBypass();

    ConnectorCustomDataBase getCustomDataBase();

    ConfigManager getConfigManager();
}