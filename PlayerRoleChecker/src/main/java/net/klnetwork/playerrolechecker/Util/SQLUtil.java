package net.klnetwork.playerrolechecker.Util;


import org.bukkit.Bukkit;

import java.sql.*;
import java.util.function.Consumer;

import static net.klnetwork.playerrolechecker.PlayerRoleChecker.plugin;

@SuppressWarnings("DeprecatedIsStillUsed")
public class SQLUtil {

    private static Connection connection;
    private static long connectionAlive = 0;


    /**
     * @param uuid - プレイヤーのUUID
     * @return - discordIDとUUIDを非同期で返します
     */
    public static void getDiscordFromSQL(String uuid, Consumer<String[]> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            String[] result = SQLUtil.getDiscordFromSQL(uuid);
            consumer.accept(result);
        });
    }

    /**
     * @param uuid - プレイヤーのUUID
     * @return - discordIDとUUIDを非同期で返します
     */
    public static void getDiscordFromSQL_NoAsyncReturn(String uuid, Consumer<String[]> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            String[] result = SQLUtil.getDiscordFromSQL(uuid);
            Bukkit.getScheduler().runTask(plugin,() -> consumer.accept(result));
        });
    }

    /**
     * @param discord - プレイヤーのUUID
     * @return - UUIDとdiscordIDを非同期で返します
     */
    public static void getUUIDFromSQL(String discord, Consumer<String[]> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            String[] result = SQLUtil.getUUIDFromSQL(discord);
            consumer.accept(result);
        });
    }

    /**
     * @param discord - プレイヤーのUUID
     * @return - UUIDとdiscordIDを非同期で返します
     */
    public static void getUUIDFromSQL_NoAsyncReturn(String discord, Consumer<String[]> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            String[] result = SQLUtil.getUUIDFromSQL(discord);
            Bukkit.getScheduler().runTask(plugin,() -> consumer.accept(result));
        });
    }


    /**
     * @param uuid - - discordID
     * @return - discordIDとUUIDを返します
     * @deprecated - 2.2
     */
    public static String[] getDiscordFromSQL(String uuid) {
        String[] result = null;
        try {
            PreparedStatement preparedStatement = getSQLConnection().prepareStatement("select * from verifyplayer where uuid = ?");
            preparedStatement.setString(1, uuid);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) result = new String[]{resultSet.getString(1), resultSet.getString(2)};

            preparedStatement.close();
            //PreparedStatementが閉じたらResultSetは閉じるはず

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    /**
     * @param discord - discordID
     * @return - UUIDとdiscordIDを返します
     * @deprecated - 2.2
     */
    public static String[] getUUIDFromSQL(String discord) {
        String[] result = null;
        try {
            PreparedStatement preparedStatement = getSQLConnection().prepareStatement("select * from verifyplayer where discord = ?");
            preparedStatement.setString(1, discord);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) result = new String[]{resultSet.getString(1), resultSet.getString(2)};

            preparedStatement.close();
            //PreparedStatementが閉じたらResultSetは閉じるはず

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public static void putSQL(String uuid, String discord) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
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

    public static void removeSQL(String uuid, String discord) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
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
            connection = DriverManager.getConnection("jdbc:mysql://" + plugin.getConfig().getString("MySQL.Server") + ":" + plugin.getConfig().getInt("MySQL.Port") + "/" + plugin.getConfig().getString("MySQL.Database") + plugin.getConfig().getString("MySQL.Option"), plugin.getConfig().getString("MySQL.Username"), plugin.getConfig().getString("MySQL.Password"));
            System.out.println("[DEBUG] Connection is Regenerated!");
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

