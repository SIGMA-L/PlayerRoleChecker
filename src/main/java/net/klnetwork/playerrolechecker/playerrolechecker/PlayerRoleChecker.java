package net.klnetwork.playerrolechecker.playerrolechecker;

import net.klnetwork.playerrolechecker.playerrolechecker.Commands.SQLDebug;
import net.klnetwork.playerrolechecker.playerrolechecker.Events.JoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class PlayerRoleChecker extends JavaPlugin {
    public static Plugin plugin;
    public static String SQLLocate;
    public static String Server;
    public static Integer Port;
    public static String DB;
    public static String UserName;
    public static String PassWord;
    public static String Option;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        try {
            SQLLocate = plugin.getConfig().getString("SQLite.SQLiteLocate");
            Server = plugin.getConfig().getString("MySQL.Server");
            Port = plugin.getConfig().getInt("MySQL.Port");
            DB = plugin.getConfig().getString("MySQL.Database");
            UserName = plugin.getConfig().getString("MySQL.Username");
            PassWord = plugin.getConfig().getString("MySQL.Password");
            Option = plugin.getConfig().getString("MySQL.Option");
        } catch (Exception e) {
            plugin.getServer().getLogger().info("getConfigError");
        }
        Connection LiteCon = null;
        try {
            LiteCon = DriverManager.getConnection("jdbc:sqlite:" + SQLLocate);
            Statement LiteSt = LiteCon.createStatement();
            LiteSt.setQueryTimeout(30);
            LiteSt.executeUpdate("create table if not exists waitverify (uuid string, Code string)");
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
            SQL = DriverManager.getConnection("jdbc:mysql://" + Server + ":" + Port + "/" + DB + Option, UserName,PassWord);
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
        if (getConfig().getBoolean("UseSQLDebug")) {
            getCommand("sqldebug").setExecutor(new SQLDebug());
        }
        getServer().getPluginManager().registerEvents(new JoinEvent(),this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
