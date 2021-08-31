package net.klnetwork.playerrolechecker.playerrolechecker.Util;

import net.klnetwork.playerrolechecker.playerrolechecker.SQL;
import org.sqlite.SQLiteConnection;

import java.sql.*;

import static net.klnetwork.playerrolechecker.playerrolechecker.SQL.SQLLocate;

import java.util.UUID;

public class SQLiteUtil {

    private static Connection connection;

    public static boolean CheckCode(Integer code) {
        try {
            PreparedStatement preparedStatement = getSQLiteConnection().prepareStatement("select * from waitverify where code = ?");
            preparedStatement.setString(1, code.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public static String[] getCodeFromSQLite(UUID uuid) {
        try {
            PreparedStatement preparedStatement = getSQLiteConnection().prepareStatement("select * from waitverify where uuid = ?");
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) return new String[] {resultSet.getString(1),resultSet.getString(2)};

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    return null;
    }

    public static String[] getUUIDFromSQLite(Integer code) {
        try {
            PreparedStatement preparedStatement = getSQLiteConnection().prepareStatement("select * from waitverify where code = ?");
            preparedStatement.setString(1, code.toString());
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) return new String[] {resultSet.getString(1),resultSet.getString(2)};

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
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
        if(connection == null){
            connection = DriverManager.getConnection("jdbc:sqlite:" + SQLLocate);
        }
    return connection;
    }
}
