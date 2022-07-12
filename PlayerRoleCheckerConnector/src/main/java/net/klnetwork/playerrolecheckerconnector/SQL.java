package net.klnetwork.playerrolecheckerconnector;


import net.klnetwork.playerrolecheckerconnector.table.LocalSQL;
import net.klnetwork.playerrolecheckerconnector.table.PlayerData;

import java.sql.SQLException;
import java.sql.Statement;

public class SQL {

    public static void init() {
        try {
            Statement SQLSt = PlayerData.getInstance().getConnection().createStatement();
            SQLSt.executeUpdate("create table if not exists verifyplayer (uuid VARCHAR(50),discord VARCHAR(50))");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void sqlite_init() {
        try {
            Statement LiteSt = LocalSQL.getInstance().getConnection().createStatement();
            LiteSt.executeUpdate("create table if not exists bypass (uuid string)");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}