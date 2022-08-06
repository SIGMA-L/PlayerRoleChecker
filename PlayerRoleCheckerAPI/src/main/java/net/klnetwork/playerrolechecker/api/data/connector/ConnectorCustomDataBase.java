package net.klnetwork.playerrolechecker.api.data.connector;

import net.klnetwork.playerrolechecker.api.data.common.CustomDataBase;

public interface ConnectorCustomDataBase extends CustomDataBase {
    void setBypass(ConnectorBypassTable table);
}
