package net.klnetwork.playerrolechecker.api.data.common;

import net.klnetwork.playerrolechecker.api.data.SQLInterface;
import net.klnetwork.playerrolechecker.api.utils.CommonUtils;

import java.sql.*;
import java.util.UUID;

public abstract class TemporaryTable extends SQLInterface {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public TemporaryData getCode(UUID uuid) {
        return getCode(uuid.toString());
    }

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

    public void put(String uuid, String code, boolean bedrock) {
        put(uuid, Integer.parseInt(code), bedrock);
    }

    public void put(UUID uuid, Integer code, boolean bedrock) {
        put(uuid.toString(), code, bedrock);
    }

    public void put(UUID uuid, String code, boolean bedrock) {
        put(uuid.toString(), Integer.parseInt(code), bedrock);
    }

    public void put(String uuid, Integer code, boolean bedrock) {
        PreparedStatement statement = null;
        try {
            statement = getConnection().prepareStatement("insert into waitverify values (?,?,?)");
            statement.setString(1, uuid);
            statement.setInt(2, code);
            statement.setBoolean(3, bedrock);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CommonUtils.close(statement);
        }
    }

    public void remove(UUID uuid, Integer code) {
        remove(uuid.toString(), code);
    }

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
        if (connection == null || connection.isClosed() || isConnectionDead()) {
            checkClass();

            this.connection = CommonUtils.createConnection(getSQLFormat(), getUser(), getPassword());
        }
        return connection;
    }
}
