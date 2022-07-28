package net.klnetwork.playerrolechecker.api.data.connector;

import java.sql.*;
import java.util.UUID;

public interface ConnectorBypassTable {
    String getUUID(UUID uuid);

    String getUUID(String uuid);

    void put(String uuid);

    void remove(UUID uuid);

    void remove(String uuid);

    boolean isCreated();

    void create();

    Connection getConnection() throws SQLException;

    void setConnection(Connection connection);
}