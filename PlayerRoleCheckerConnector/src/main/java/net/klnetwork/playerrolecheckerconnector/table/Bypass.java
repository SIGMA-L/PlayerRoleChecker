package net.klnetwork.playerrolecheckerconnector.table;

import net.klnetwork.playerrolechecker.api.data.connector.ConnectorBypassTable;
import net.klnetwork.playerrolecheckerconnector.PlayerRoleCheckerConnector;

import java.sql.*;
import java.util.UUID;

public class Bypass implements ConnectorBypassTable {

    private static Bypass table;

    private Connection connection;

    public static Bypass getInstance() {
        if (table == null) {
            table = new Bypass();
        }

        return table;
    }

    @Override
    public String getUUID(UUID uuid) {
        return getUUID(uuid.toString());
    }

    @Override
    public String getUUID(String uuid) {
        String result = null;
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("select * from bypass where uuid = ?");
            preparedStatement.setString(1, uuid);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) result = resultSet.getString(1);

            preparedStatement.close();
            //PreparedStatementが閉じたらResultSetは閉じるはず

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public void put(String uuid) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("insert into bypass values (?)");
            preparedStatement.setString(1, uuid);
            preparedStatement.execute();

            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void remove(UUID uuid) {
        remove(uuid.toString());
    }

    @Override
    public void remove(String uuid) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("delete from bypass where uuid = ?");
            preparedStatement.setString(1, uuid);
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
            connection = DriverManager.getConnection("jdbc:sqlite:" + PlayerRoleCheckerConnector.INSTANCE.getConfig().getString("SQLite.SQLiteLocate"));
        }
        return connection;
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}