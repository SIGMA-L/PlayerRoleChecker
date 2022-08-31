package net.klnetwork.playerrolechecker.api.data.connector;

import net.klnetwork.playerrolechecker.api.data.APIHookCustom;

public interface ConnectorAPIHook extends APIHookCustom {
    ConnectorBypassTable getBypass();

    void setBypass(ConnectorBypassTable table);

    @Deprecated
    ConnectorCustomDataBase getCustomDataBase();

    ConnectorConfigManager getConfigManager();
}