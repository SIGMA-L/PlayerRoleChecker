package net.klnetwork.playerrolechecker.api.data.connector;

import net.klnetwork.playerrolechecker.api.data.APIHook;

public interface ConnectorAPIHook extends APIHook {
    ConnectorBypassTable getBypass();

    ConnectorCustomDataBase getCustomDataBase();

    ConnectorConfigManager getConfigManager();
}