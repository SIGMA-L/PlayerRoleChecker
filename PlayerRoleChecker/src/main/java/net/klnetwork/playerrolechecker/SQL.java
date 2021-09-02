package net.klnetwork.playerrolechecker;

import net.klnetwork.playerrolechecker.Util.SQLUtil;
import net.klnetwork.playerrolechecker.Util.SQLiteUtil;

import java.sql.SQLException;
import java.sql.Statement;

import static net.klnetwork.playerrolechecker.PlayerRoleChecker.plugin;
import static org.bukkit.Bukkit.getServer;

public class SQL {

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
        try {
            Statement LiteSt = SQLiteUtil.getSQLiteConnection().createStatement();
            LiteSt.executeUpdate("drop table if exists waitverify");
            LiteSt.executeUpdate("create table if not exists waitverify (uuid string, code string)");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            Statement SQLSt = SQLUtil.getSQLConnection().createStatement();
            SQLSt.executeUpdate("create table if not exists verifyplayer (uuid VARCHAR(50),discord VARCHAR(50))");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
