package net.klnetwork.playerrolecheckerconnector.playerrolecheckerconnector.Util;


import java.sql.*;

import static net.klnetwork.playerrolecheckerconnector.playerrolecheckerconnector.SQL.*;


public class SQLUtil {

    private static Connection connection;

    public static String[] getDiscordFromSQL(String uuid) {
        try {
            PreparedStatement preparedStatement = getSQLConnection().prepareStatement("select * from verifyplayer where uuid = ?");
            preparedStatement.setString(1, uuid);
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) return new String[]{resultSet.getString(1), resultSet.getString(2)};

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static String[] getUUIDFromSQL(String discord) {
        try {
            PreparedStatement preparedStatement = getSQLConnection().prepareStatement("select * from verifyplayer where discord = ?");
            preparedStatement.setString(1, discord);
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) return new String[]{resultSet.getString(1), resultSet.getString(2)};

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static void putSQL(String uuid, String discord) {
        new Thread(() -> {
            try {
                PreparedStatement preparedStatement = getSQLConnection().prepareStatement("insert into verifyplayer values (?,?)");
                preparedStatement.setString(1, uuid);
                preparedStatement.setString(2, discord);
                preparedStatement.execute();

                preparedStatement.close();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }).start();
    }

    public static void removeSQL(String uuid, String discord) {
        new Thread(()-> {
            try {
                PreparedStatement preparedStatement = getSQLConnection().prepareStatement("delete from verifyplayer where uuid = ? and discord = ?");
                preparedStatement.setString(1, uuid);
                preparedStatement.setString(2, discord);
                preparedStatement.execute();

                preparedStatement.close();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }).start();
    }

    public static Connection getSQLConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection("jdbc:mysql://" + Server + ":" + Port + "/" + DB + Option, UserName, PassWord);
        }
        return connection;
    }
}

