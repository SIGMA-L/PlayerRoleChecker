package net.klnetwork.playerrolechecker.table;

import net.klnetwork.playerrolechecker.PlayerRoleChecker;
import net.klnetwork.playerrolechecker.api.data.checker.CheckerTemporaryTable;
import net.klnetwork.playerrolechecker.api.utils.CommonUtils;

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
        PreparedStatement statement = null;
        try {
            statement = getConnection().prepareStatement("select * from waitverify where code = ?");
            statement.setInt(1, code);

            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            CommonUtils.close(statement);
        }
        return false;
    }

    @Override
    public String getUUID(Integer code) {
        PreparedStatement statement = null;
        try {
            statement = getConnection().prepareStatement("select * from waitverify where code = ?");
            statement.setInt(1, code);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            CommonUtils.close(statement);
        }
        return null;
    }

    @Override
    @Deprecated
    public String[] getUUID(String code) {
        String[] result = null;
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("select * from waitverify where code = ?");
            preparedStatement.setString(1, code);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                result = new String[]{resultSet.getString(1), resultSet.getString(2)};
            }
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
        PreparedStatement statement = null;
        try {
            statement = getConnection().prepareStatement("select * from waitverify where uuid = ?");
            statement.setString(1, uuid);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(2);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            CommonUtils.close(statement);
        }
        return null;
    }

    @Override
    public void put(String uuid, String code) {
        PreparedStatement statement = null;
        try {
            statement = getConnection().prepareStatement("insert into waitverify values (?,?)");
            statement.setString(1, uuid);
            statement.setString(2, code);
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            CommonUtils.close(statement);
        }
    }

    @Override
    public void remove(UUID uuid, Integer code) {
        remove(uuid.toString(), code);
    }

    @Override
    public void remove(String uuid, Integer code) {
        PreparedStatement statement = null;
        try {
            statement = getConnection().prepareStatement("delete from waitverify where uuid = ? and code = ?");
            statement.setString(1, uuid);
            statement.setInt(2, code);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CommonUtils.close(statement);
        }
    }

    @Override
    public void create() {
        Statement statement = null;
        try {
            statement = PlayerData.getInstance().getConnection().createStatement();

            statement.executeUpdate("drop table if exists waitverify");
            statement.executeUpdate("create table if not exists waitverify (uuid string, code int)");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CommonUtils.close(statement);
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection("jdbc:sqlite:" + PlayerRoleChecker.INSTANCE.getConfig().getString("SQLite.SQLiteLocate"));
        }
        return connection;
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public long getLastConnection() {
        return 0;
    }

    @Override
    public void setLastConnection(long lastConnection) {

    }

    @Override
    public boolean isConnectionDead() throws SQLException {
        return false;
    }

    @Override
    public SQLType getType() {
        return null;
    }

    @Override
    public SQLType setType(SQLType type) {
        return null;
    }
}