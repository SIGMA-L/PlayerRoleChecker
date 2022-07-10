package net.klnetwork.playerrolechecker.api.data.connector;

import java.sql.*;
import java.util.UUID;

public interface ConnectorBypassTable {
    String getUUID(String uuid);

    String getUUID(UUID uuid);

    void put(String uuid);

    void remove(String uuid);

    void remove(UUID uuid);

    Connection getConnection() throws SQLException;
}