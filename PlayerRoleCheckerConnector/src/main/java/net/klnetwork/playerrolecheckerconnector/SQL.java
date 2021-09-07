package net.klnetwork.playerrolecheckerconnector;


import net.klnetwork.playerrolecheckerconnector.Util.SQLUtil;
import net.klnetwork.playerrolecheckerconnector.Util.SQLiteUtil;

import java.sql.SQLException;
import java.sql.Statement;

import static net.klnetwork.playerrolecheckerconnector.PlayerRoleCheckerConnector.plugin;
import static org.bukkit.Bukkit.getServer;

public class SQL {

    public static void init() {
        try {
            Statement LiteSt = SQLiteUtil.getSQLiteConnection().createStatement();
            LiteSt.executeUpdate("create table if not exists bypass (uuid string)");

            Statement SQLSt = SQLUtil.getSQLConnection().createStatement();
            SQLSt.executeUpdate("create table if not exists verifyplayer (uuid VARCHAR(50),discord VARCHAR(50))");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

