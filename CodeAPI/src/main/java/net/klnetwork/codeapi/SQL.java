package net.klnetwork.codeapi;


import net.klnetwork.codeapi.Util.SQLiteUtil;

import java.sql.SQLException;
import java.sql.Statement;

public class SQL {

    public static void init() {
        try {
            Statement LiteSt = SQLiteUtil.getSQLiteConnection().createStatement();
            LiteSt.executeUpdate("drop table if exists waitverify");
            LiteSt.executeUpdate("create table if not exists waitverify (uuid string, code string)");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
