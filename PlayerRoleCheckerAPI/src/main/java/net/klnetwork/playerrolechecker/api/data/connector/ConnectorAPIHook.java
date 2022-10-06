package net.klnetwork.playerrolechecker.api.data.connector;

import net.klnetwork.playerrolechecker.api.data.APIHookCustom;
import net.klnetwork.playerrolechecker.api.data.PlayerDataTable;

public interface ConnectorAPIHook extends APIHookCustom {
    ConnectorBypassTable getBypass();

    void setBypass(ConnectorBypassTable table);

    /**
     * @implNote
     * <br>わからない場合は使用しないでください
     *
     * @deprecated {@link #setPlayerData(PlayerDataTable)} などを使用してください
     */
    @Deprecated
    ConnectorCustomDataBase getCustomDataBase();

    /**
     *
     * @return
     */
    ConnectorConfigManager getConfigManager();
}