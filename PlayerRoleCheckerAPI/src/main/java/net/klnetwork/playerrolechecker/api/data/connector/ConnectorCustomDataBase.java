package net.klnetwork.playerrolechecker.api.data.connector;

import net.klnetwork.playerrolechecker.api.data.CustomDataBase;

public interface ConnectorCustomDataBase extends CustomDataBase {
    void setBypass(ConnectorBypassTable table);
}
