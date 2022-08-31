package net.klnetwork.playerrolechecker.api;

import net.klnetwork.playerrolechecker.api.data.PlayerDataTable;
import net.klnetwork.playerrolechecker.api.data.checker.CheckerCustomDataBase;
import net.klnetwork.playerrolechecker.api.data.common.TemporaryTable;
import net.klnetwork.playerrolechecker.table.LocalSQL;
import net.klnetwork.playerrolechecker.table.PlayerDataSQL;

public class CustomDataBaseImpl implements CheckerCustomDataBase {
    @Override
    public void setPlayerData(PlayerDataTable table) {
        PlayerDataSQL.setInstance(table);
    }

    @Override
    public void setTemporary(TemporaryTable table) {
        LocalSQL.setInstance(table);
    }
}