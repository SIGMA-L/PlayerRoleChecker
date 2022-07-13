package net.klnetwork.playerrolechecker.api.data.checker;

import java.sql.*;
import java.util.UUID;

public interface CheckerTemporaryTable {
    boolean hasUUID(Integer code);

    String getUUID(Integer code);

    @Deprecated
    String[] getUUID(String code);

    Integer getCode(UUID uuid);

    Integer getCode(String uuid);

    void put(String uuid, String code);

    void remove(UUID uuid, Integer code);

    void remove(String uuid, Integer code);

    void create();

    Connection getConnection() throws SQLException;

    void setConnection(Connection connection);
}
