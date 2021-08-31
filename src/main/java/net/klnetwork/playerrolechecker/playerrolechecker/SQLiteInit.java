package net.klnetwork.playerrolechecker.playerrolechecker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static net.klnetwork.playerrolechecker.playerrolechecker.PlayerRoleChecker.plugin;
import static org.bukkit.Bukkit.getServer;

public class SQLiteInit {

    public static String SQLLocate;
    public static String Server;
    public static Integer Port;
    public static String DB;
    public static String UserName;
    public static String PassWord;
    public static String Option;

    public static void init() {
        try {
            SQLLocate = plugin.getConfig().getString("SQLite.SQLiteLocate");
            Server = plugin.getConfig().getString("MySQL.Server");
            Port = plugin.getConfig().getInt("MySQL.Port");
            DB = plugin.getConfig().getString("MySQL.Database");
            UserName = plugin.getConfig().getString("MySQL.Username");
            PassWord = plugin.getConfig().getString("MySQL.Password");
            Option = plugin.getConfig().getString("MySQL.Option");
        } catch (Exception e) {
            getServer().getLogger().info("getConfigError");
        }
        Connection LiteCon = null;
        try {
            LiteCon = DriverManager.getConnection("jdbc:sqlite:" + SQLLocate);
            Statement LiteSt = LiteCon.createStatement();
            LiteSt.setQueryTimeout(30);
            LiteSt.executeUpdate("drop table if exists waitverify");
            LiteSt.executeUpdate("create table if not exists waitverify (uuid string, code string)");
            LiteSt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (LiteCon != null) {
                    LiteCon.close();
                }
            } catch (SQLException throwables) {
                System.err.println(throwables.getMessage());
            }
        }

        Connection SQL = null;
        try {
            SQL = DriverManager.getConnection("jdbc:mysql://" + Server + ":" + Port + "/" + DB + Option, UserName, PassWord);
            Statement SQLSt = SQL.createStatement();
            SQLSt.setQueryTimeout(30);
            SQLSt.executeUpdate("create table if not exists  verifyplayer (uuid string,discord string)");
            SQLSt.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (SQL != null) {
                    SQL.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                System.err.println(throwables.getMessage());
            }
        }
    }
}
