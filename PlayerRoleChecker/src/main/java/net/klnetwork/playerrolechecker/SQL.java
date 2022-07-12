package net.klnetwork.playerrolechecker;

import net.klnetwork.playerrolechecker.table.PlayerData;
import net.klnetwork.playerrolechecker.table.LocalSQL;

import java.sql.SQLException;
import java.sql.Statement;

public class SQL {

    public static void init() {
        try {
            Statement LiteSt = LocalSQL.getInstance().getConnection().createStatement();
            LiteSt.executeUpdate("drop table if exists waitverify");
            LiteSt.executeUpdate("create table if not exists waitverify (uuid string, code int)");

            Statement SQLSt = PlayerData.getInstance().getConnection().createStatement();
            SQLSt.executeUpdate("create table if not exists verifyplayer (uuid VARCHAR(50),discord VARCHAR(50))");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
