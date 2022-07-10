package net.klnetwork.playerrolecheckerconnector.util;

import net.klnetwork.playerrolecheckerconnector.PlayerRoleCheckerConnector;

import java.sql.*;
import java.util.UUID;

public class SQLiteUtil {

    private static Connection connection;

    public static String getUUIDFromSQLite(String uuid) {
        String result = null;
        try {
            PreparedStatement preparedStatement = getSQLiteConnection().prepareStatement("select * from bypass where uuid = ?");
            preparedStatement.setString(1, uuid);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) result = resultSet.getString(1);

            preparedStatement.close();
            //PreparedStatementが閉じたらResultSetは閉じるはず

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public static String getUUIDFromSQLite(UUID uuid) {
        return getUUIDFromSQLite(uuid.toString());
    }

    public static void putSQLite(String uuid) {
        try {
            PreparedStatement preparedStatement = getSQLiteConnection().prepareStatement("insert into bypass values (?)");
            preparedStatement.setString(1, uuid);
            preparedStatement.execute();

            preparedStatement.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void removeSQLite(String uuid) {
        try {
            PreparedStatement preparedStatement = getSQLiteConnection().prepareStatement("delete from bypass where uuid = ?");
            preparedStatement.setString(1, uuid);
            preparedStatement.execute();

            preparedStatement.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void removeSQLite(UUID uuid) {
        removeSQLite(uuid.toString());
    }


    public static Connection getSQLiteConnection() throws SQLException {
        if(connection == null || connection.isClosed()){
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            connection = DriverManager.getConnection("jdbc:sqlite:" + PlayerRoleCheckerConnector.INSTANCE.getConfig().getString("SQLite.SQLiteLocate"));
        }
        return connection;
    }
}