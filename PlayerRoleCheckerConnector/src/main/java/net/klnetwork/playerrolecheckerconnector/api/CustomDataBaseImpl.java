package net.klnetwork.playerrolecheckerconnector.api;

import net.klnetwork.playerrolechecker.api.data.PlayerDataTable;
import net.klnetwork.playerrolechecker.api.data.connector.ConnectorBypassTable;
import net.klnetwork.playerrolechecker.api.data.connector.ConnectorCustomDataBase;
import net.klnetwork.playerrolecheckerconnector.table.LocalSQL;
import net.klnetwork.playerrolecheckerconnector.table.PlayerDataSQL;

public class CustomDataBaseImpl implements ConnectorCustomDataBase {

    @Override
    public void setPlayerData(PlayerDataTable table) {
        PlayerDataSQL.setInstance(table);
    }

    @Override
    public void setBypass(ConnectorBypassTable table) {
        LocalSQL.setInstance(table);
    }
}