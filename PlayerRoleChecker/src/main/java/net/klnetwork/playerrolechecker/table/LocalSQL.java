package net.klnetwork.playerrolechecker.table;

import net.klnetwork.playerrolechecker.PlayerRoleChecker;
import net.klnetwork.playerrolechecker.api.data.common.TemporaryTable;
import org.bukkit.plugin.Plugin;

public class LocalSQL extends TemporaryTable {
    private static TemporaryTable table;

    public static TemporaryTable getInstance() {
        if (table == null) {
            table = new LocalSQL();
        }

        return table;
    }

    public static void setInstance(TemporaryTable data) {
        table = data;
    }

    @Override
    public Plugin getPlugin() {
        return PlayerRoleChecker.INSTANCE;
    }

    @Override
    public String getPath() {
        return "DataBase.TemporaryTable";
    }
}