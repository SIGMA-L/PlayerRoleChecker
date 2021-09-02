package net.klnetwork.playerrolecheckerconnector.playerrolecheckerconnector.Util;

import net.klnetwork.playerrolecheckerconnector.playerrolecheckerconnector.SQL;

import java.sql.*;


public class SQLiteUtil {

    private static Connection connection;

    public static String getUUIDFromSQLite(String uuid) {
        try {
            PreparedStatement preparedStatement = getSQLiteConnection().prepareStatement("select * from bypass where uuid = ?");
            preparedStatement.setString(1, uuid);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) return resultSet.getString(1);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
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

    public static Connection getSQLiteConnection() throws SQLException {
        if(connection == null || connection.isClosed()){
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            connection = DriverManager.getConnection("jdbc:sqlite:" + SQL.SQLiteLocate);
        }
    return connection;
    }
}
