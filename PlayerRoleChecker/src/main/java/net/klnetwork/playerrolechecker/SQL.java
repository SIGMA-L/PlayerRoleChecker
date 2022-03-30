package net.klnetwork.playerrolechecker;

import net.klnetwork.playerrolechecker.util.SQLUtil;
import net.klnetwork.playerrolechecker.util.SQLiteUtil;

import java.sql.SQLException;
import java.sql.Statement;

public class SQL {

    public static void init() {
        try {
            Statement LiteSt = SQLiteUtil.getSQLiteConnection().createStatement();
            LiteSt.executeUpdate("drop table if exists waitverify");
            LiteSt.executeUpdate("create table if not exists waitverify (uuid string, code int)");

            Statement SQLSt = SQLUtil.getSQLConnection().createStatement();
            SQLSt.executeUpdate("create table if not exists verifyplayer (uuid VARCHAR(50),discord VARCHAR(50))");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
