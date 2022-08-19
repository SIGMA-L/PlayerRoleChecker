package net.klnetwork.playerrolecheckerconnector.table;

import net.klnetwork.playerrolechecker.api.data.connector.ConnectorBypassTable;
import net.klnetwork.playerrolechecker.api.enums.SQLType;
import net.klnetwork.playerrolechecker.api.utils.CommonUtils;
import net.klnetwork.playerrolecheckerconnector.PlayerRoleCheckerConnector;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.UUID;

public class LocalSQL extends ConnectorBypassTable {

    private static ConnectorBypassTable table;

    private boolean created;

    public LocalSQL() {
        type = CommonUtils.getSQLType(PlayerRoleCheckerConnector.INSTANCE.getPlugin().getConfig().getString("DataBase.BypassTable.type"));
    }

    public static ConnectorBypassTable getInstance() {
        if (table == null) {
            table = new LocalSQL();
        }

        return table;
    }

    public static void setInstance(ConnectorBypassTable data) {
        table = data;
    }

    @Override
    public boolean hasUUID(UUID uuid) {
        return hasUUID(uuid.toString());
    }

    @Override
    public boolean hasUUID(String uuid) {
        PreparedStatement statement = null;
        try {
            statement = getConnection().prepareStatement("select * from bypass where uuid = ?");
            statement.setString(1, uuid);

            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CommonUtils.close(statement);
        }
        return false;
    }

    @Override
    public String getUUID(UUID uuid) {
        return getUUID(uuid.toString());
    }

    @Override
    public String getUUID(String uuid) {
        PreparedStatement statement = null;
        try {
            statement = getConnection().prepareStatement("select * from bypass where uuid = ?");
            statement.setString(1, uuid);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CommonUtils.close(statement);
        }
        return null;
    }

    @Override
    public void put(String uuid) {
        PreparedStatement statement = null;
        try {
            statement = getConnection().prepareStatement("insert into bypass values (?)");
            statement.setString(1, uuid);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CommonUtils.close(statement);
        }
    }

    @Override
    public void remove(UUID uuid) {
        remove(uuid.toString());
    }

    @Override
    public void remove(String uuid) {
        PreparedStatement statement = null;
        try {
            statement = getConnection().prepareStatement("delete from bypass where uuid = ?");
            statement.setString(1, uuid);
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
            statement = getConnection().createStatement();
            created = statement.executeUpdate("create table if not exists bypass (uuid string)") >= 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CommonUtils.close(statement);
        }
    }

    @Override
    public boolean isCreated() {
        return created;
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection("jdbc:sqlite:" + PlayerRoleCheckerConnector.INSTANCE.getConfig().getString("SQLite.SQLiteLocate"));
        }
        return connection;
    }

    @Override
    public Plugin getPlugin() {
        return PlayerRoleCheckerConnector.INSTANCE;
    }

    @Override
    public String getPath() {
        return null;
    }

    @Override
    public @NotNull SQLType getType() {
        return type;
    }

    @Override
    public void setType(SQLType type) {
        this.type = type;
    }
}