package net.klnetwork.playerrolechecker.table;

import com.sun.tools.javac.comp.Check;
import net.klnetwork.playerrolechecker.PlayerRoleChecker;
import net.klnetwork.playerrolechecker.api.data.checker.CheckerTemporaryTable;

import java.sql.*;
import java.util.UUID;

public class LocalSQL implements CheckerTemporaryTable {

    private Connection connection;

    private static CheckerTemporaryTable table;

    public static CheckerTemporaryTable getInstance() {
        if (table == null) {
            table = new LocalSQL();
        }

        return table;
    }

    public static void setInstance(CheckerTemporaryTable data) {
        table = data;
    }

    @Override
    public boolean hasUUID(Integer code) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getConnection().prepareStatement("select * from waitverify where code = ?");
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

    @Override
    public String getUUID(Integer code) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getConnection().prepareStatement("select * from waitverify where code = ?");
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

    @Override
    public String[] getUUID(String code) {
        String[] result = null;
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("select * from waitverify where code = ?");
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

    @Override
    public Integer getCode(UUID uuid) {
        return getCode(uuid.toString());
    }

    @Override
    public Integer getCode(String uuid) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getConnection().prepareStatement("select * from waitverify where uuid = ?");
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

    @Override
    public void put(String uuid, String code) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("insert into waitverify values (?,?)");
            preparedStatement.setString(1, uuid);
            preparedStatement.setString(2, code);
            preparedStatement.execute();

            preparedStatement.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void remove(UUID uuid, Integer code) {
        remove(uuid.toString(), code);
    }

    @Override
    public void remove(String uuid, Integer code) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("delete from waitverify where uuid = ? and code = ?");
            preparedStatement.setString(1, uuid);
            preparedStatement.setInt(2, code);
            preparedStatement.execute();

            preparedStatement.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
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

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}