package net.klnetwork.codeapi.Util;

import net.klnetwork.codeapi.CodeAPI;

import java.sql.*;


public class SQLiteUtil {

    private static Connection connection;

    public static boolean CheckCode(Integer code) {
        boolean result = false;
        try {
            PreparedStatement preparedStatement = getSQLiteConnection().prepareStatement("select * from waitverify where code = ?");
            preparedStatement.setString(1, code.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) result = true;

            preparedStatement.close();
            //PreparedStatementが閉じたらResultSetは閉じるはず

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public static String[] getCodeFromSQLite(String uuid) {
        String[] result = null;
        try {
            PreparedStatement preparedStatement = getSQLiteConnection().prepareStatement("select * from waitverify where uuid = ?");
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

    public static String[] getUUIDFromSQLite(String code) {
        String[] result = null;
        try {
            PreparedStatement preparedStatement = getSQLiteConnection().prepareStatement("select * from waitverify where code = ?");
            preparedStatement.setString(1, code);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) result = new String[]{resultSet.getString(1), resultSet.getString(2)};

            preparedStatement.close();
            //PreparedStatementが閉じたらResultSetは閉じるはず

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public static void putSQLite(String uuid, String code) {
        try {
            PreparedStatement preparedStatement = getSQLiteConnection().prepareStatement("insert into waitverify values (?,?)");
            preparedStatement.setString(1, uuid);
            preparedStatement.setString(2, code);
            preparedStatement.execute();

            preparedStatement.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void removeSQLite(String uuid, String code) {
        try {
            PreparedStatement preparedStatement = getSQLiteConnection().prepareStatement("delete from waitverify where uuid = ? and code = ?");
            preparedStatement.setString(1, uuid);
            preparedStatement.setString(2, code);
            preparedStatement.execute();

            preparedStatement.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static Connection getSQLiteConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            connection = DriverManager.getConnection("jdbc:sqlite:" + CodeAPI.plugin.getConfig().getString("SQLite.SQLiteLocate"));
        }
        return connection;
    }
}