package net.klnetwork.playerrolechecker.util;


import net.klnetwork.playerrolechecker.PlayerRoleChecker;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.*;
import java.util.UUID;
import java.util.function.Consumer;

public class SQLUtil {

    private static Connection connection;
    private static long connectionAlive = 0;

    public static void asyncUUID(String discordID, Consumer<String> uuid) {
        Bukkit.getScheduler().runTaskAsynchronously(PlayerRoleChecker.INSTANCE, () -> uuid.accept(getUUID(discordID)));
    }

    public static void asyncDiscordId(UUID uuid, Consumer<String> discordId) {
        asyncDiscordId(uuid.toString(), discordId);
    }

    public static void asyncDiscordId(String uuid, Consumer<String> discordId) {
        Bukkit.getScheduler().runTaskAsynchronously(PlayerRoleChecker.INSTANCE, () -> discordId.accept(getDiscordId(uuid)));
    }

    public static String getDiscordId(UUID uuid) {
        return getDiscordId(uuid.toString());
    }


    public static String getDiscordId(String uuid) {

        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = getSQLConnection().prepareStatement("select * from verifyplayer where uuid = ?");
            preparedStatement.setString(1, uuid);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString(2);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static String getUUID(String discord) {

        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = getSQLConnection().prepareStatement("select * from verifyplayer where discord = ?");
            preparedStatement.setString(1, discord);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString(1);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static void putSQL(UUID uuid, String discord) {
        putSQL(uuid.toString(), discord);
    }

    public static void putSQL(String uuid, String discord) {
        Bukkit.getScheduler().runTaskAsynchronously(PlayerRoleChecker.INSTANCE, () -> {
            try {
                PreparedStatement preparedStatement = getSQLConnection().prepareStatement("insert into verifyplayer values (?,?)");
                preparedStatement.setString(1, uuid);
                preparedStatement.setString(2, discord);
                preparedStatement.execute();

                preparedStatement.close();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    public static void removeSQL(UUID uuid, String discord) {
        removeSQL(uuid.toString(), discord);
    }

    public static void removeSQL(String uuid, String discord) {
        Bukkit.getScheduler().runTaskAsynchronously(PlayerRoleChecker.INSTANCE, () -> {
            try {
                PreparedStatement preparedStatement = getSQLConnection().prepareStatement("delete from verifyplayer where uuid = ? and discord = ?");
                preparedStatement.setString(1, uuid);
                preparedStatement.setString(2, discord);
                preparedStatement.execute();

                preparedStatement.close();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    public static Connection getSQLConnection() throws SQLException {
        if (connection == null || connection.isClosed() || !ConnectionIsDead()) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            FileConfiguration config = PlayerRoleChecker.INSTANCE.getConfig();

            connection = DriverManager.getConnection("jdbc:mysql://" + config.getString("MySQL.Server") + ":" + config.getInt("MySQL.Port") + "/" + config.getString("MySQL.Database") + config.getString("MySQL.Option"), config.getString("MySQL.Username"), config.getString("MySQL.Password"));
        }
        return connection;
    }


    /*
     * https://github.com/Elikill58/Negativity/blob/0d17657f05b869ea191e3a7a95101c37b3af009b/src/com/elikill58/negativity/universal/Database.java#L47
     */
    public static boolean ConnectionIsDead() throws SQLException {
        long nowTime = System.currentTimeMillis();
        if (nowTime - connectionAlive > 900000) {
            connectionAlive = nowTime;
            return connection.isValid(1);
        }
        return true;
    }
}