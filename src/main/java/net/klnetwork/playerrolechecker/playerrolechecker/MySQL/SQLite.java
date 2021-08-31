package net.klnetwork.playerrolechecker.playerrolechecker.MySQL;

import java.sql.*;

import static net.klnetwork.playerrolechecker.playerrolechecker.SQLiteInit.SQLLocate;

import java.util.UUID;

public class SQLite {
    public static boolean CheckCode(Integer code) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + SQLLocate);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            PreparedStatement preparedStatement = connection.prepareStatement("select * from waitverify where code = ?");
            preparedStatement.setString(1, code.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public static String[] getCodeFromSQLLite(UUID uuid) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + SQLLocate);
            PreparedStatement preparedStatement = connection.prepareStatement("select * from waitverify where uuid = ?");
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) return new String[] {resultSet.getString(1),resultSet.getString(2)};

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    return null;
    }

    public static String[] getUUIDFromSQLLite(Integer code) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + SQLLocate);
            PreparedStatement preparedStatement = connection.prepareStatement("select * from waitverify where code = ?");
            preparedStatement.setString(1, code.toString());
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) return new String[] {resultSet.getString(1),resultSet.getString(2)};

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static void putSQLLite(String uuid, String code) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + SQLLocate);
            PreparedStatement preparedStatement = connection.prepareStatement("insert into waitverify values (?,?)");
            preparedStatement.setString(1, uuid);
            preparedStatement.setString(2, code);
            preparedStatement.execute();

            preparedStatement.close();
            connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void removeSQLLite(String uuid, String code) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + SQLLocate);
            PreparedStatement preparedStatement = connection.prepareStatement("delete from waitverify where uuid = ? and code = ?");
            preparedStatement.setString(1, uuid);
            preparedStatement.setString(2, code);
            preparedStatement.execute();

            preparedStatement.close();
            connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
