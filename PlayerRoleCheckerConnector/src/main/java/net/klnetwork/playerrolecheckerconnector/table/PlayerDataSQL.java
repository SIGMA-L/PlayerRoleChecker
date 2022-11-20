package net.klnetwork.playerrolecheckerconnector.table;

import net.klnetwork.playerrolechecker.api.data.PlayerDataTable;
import net.klnetwork.playerrolecheckerconnector.PlayerRoleCheckerConnector;
import org.bukkit.plugin.Plugin;

public class PlayerDataSQL extends PlayerDataTable {
    private static PlayerDataTable table;

    public static PlayerDataTable getInstance() {
        if (table == null) {
            table = new PlayerDataSQL();
        }

        return table;
    }

    public static void setInstance(PlayerDataTable data) {
        table = data;
    }

    @Override
    public Plugin getPlugin() {
        return PlayerRoleCheckerConnector.INSTANCE;
    }

    @Override
    public String getPath() {
        return "DataBase.PlayerDataTable";
    }
}