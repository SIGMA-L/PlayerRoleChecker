package net.klnetwork.playerrolechecker.api;

import net.klnetwork.playerrolechecker.api.data.PlayerDataTable;
import net.klnetwork.playerrolechecker.api.data.checker.CheckerCustomDataBase;
import net.klnetwork.playerrolechecker.api.data.checker.CheckerTemporaryTable;
import net.klnetwork.playerrolechecker.table.LocalSQL;
import net.klnetwork.playerrolechecker.table.PlayerData;

public class CustomDataBaseImpl implements CheckerCustomDataBase {
    @Override
    public void setPlayerData(PlayerDataTable table) {
        PlayerData.setInstance(table);
    }

    @Override
    public void setTemporary(CheckerTemporaryTable table) {
        LocalSQL.setInstance(table);
    }
}