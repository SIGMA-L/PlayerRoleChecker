package net.klnetwork.playerrolechecker.table;

import net.klnetwork.playerrolechecker.PlayerRoleChecker;
import net.klnetwork.playerrolechecker.api.data.common.TemporaryData;
import net.klnetwork.playerrolechecker.api.data.checker.CheckerTemporaryTable;
import net.klnetwork.playerrolechecker.api.enums.SQLType;
import net.klnetwork.playerrolechecker.api.utils.CommonUtils;

import java.sql.*;
import java.util.UUID;

public class LocalSQL extends CheckerTemporaryTable {

    private SQLType type;

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
    public TemporaryData getUUID(Integer code) {
        PreparedStatement statement = null;
        try {
            statement = getConnection().prepareStatement("select * from waitverify where code = ?");
            statement.setInt(1, code);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new TemporaryData(resultSet.getString(1), resultSet.getInt(2), resultSet.getBoolean(3));
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
                result = new String[]{resultSet.getString(1), resultSet.getString(2), String.valueOf(resultSet.getBoolean(3))};
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public TemporaryData getCode(UUID uuid) {
        return getCode(uuid.toString());
    }

    @Override
    public TemporaryData getCode(String uuid) {
        PreparedStatement statement = null;
        try {
            statement = getConnection().prepareStatement("select * from waitverify where uuid = ?");
            statement.setString(1, uuid);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new TemporaryData(resultSet.getString(1), resultSet.getInt(2), resultSet.getBoolean(3));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            CommonUtils.close(statement);
        }
        return null;
    }

    @Override
    public void put(UUID uuid, Integer code, boolean bedrock) {
        put(uuid.toString(), code.toString(), bedrock);
    }

    @Override
    public void put(String uuid, String code, boolean bedrock) {
        PreparedStatement statement = null;
        try {
            statement = getConnection().prepareStatement("insert into waitverify values (?,?)");
            statement.setString(1, uuid);
            statement.setString(2, code);
            statement.setBoolean(3, bedrock);
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
    public void drop() {
        Statement statement = null;
        try {
            statement = getConnection().createStatement();

            statement.executeUpdate("drop table if exists waitverify");
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
            drop();

            statement = getConnection().createStatement();
            statement.executeUpdate("create table if not exists waitverify (uuid string, code int, bedrock boolean)");
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
    public SQLType getType() {
        return type;
    }

    @Override
    public void setType(SQLType type) {
        this.type = type;
    }
}