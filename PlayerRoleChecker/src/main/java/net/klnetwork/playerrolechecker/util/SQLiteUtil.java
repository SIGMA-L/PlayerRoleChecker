package net.klnetwork.playerrolechecker.util;

import net.klnetwork.playerrolechecker.PlayerRoleChecker;

import java.sql.*;
import java.util.UUID;

public class SQLiteUtil {

    private static Connection connection;

    public static boolean hasUUID(Integer code) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getSQLiteConnection().prepareStatement("select * from waitverify where code = ?");
            preparedStatement.setInt(1, code);

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();

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
        return false;
    }

    public static String getUUID(Integer code) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getSQLiteConnection().prepareStatement("select * from waitverify where code = ?");
            preparedStatement.setInt(1, code);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) return resultSet.getString(1);

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


    public static Integer getCode(UUID uuid) {
        return getCode(uuid.toString());
    }
    public static Integer getCode(String uuid) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getSQLiteConnection().prepareStatement("select * from waitverify where uuid = ?");
            preparedStatement.setString(1, uuid);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) return resultSet.getInt(2);

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

    public static void removeSQLite(UUID uuid, Integer code) {
        removeSQLite(uuid.toString(), code);
    }

    public static void removeSQLite(String uuid, Integer code) {
        try {
            PreparedStatement preparedStatement = getSQLiteConnection().prepareStatement("delete from waitverify where uuid = ? and code = ?");
            preparedStatement.setString(1, uuid);
            preparedStatement.setInt(2, code);
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
            connection = DriverManager.getConnection("jdbc:sqlite:" + PlayerRoleChecker.INSTANCE.getConfig().getString("SQLite.SQLiteLocate"));
        }
        return connection;
    }
}