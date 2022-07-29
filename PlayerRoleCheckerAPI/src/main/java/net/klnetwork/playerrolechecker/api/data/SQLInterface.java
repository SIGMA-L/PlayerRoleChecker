package net.klnetwork.playerrolechecker.api.data;

import net.klnetwork.playerrolechecker.api.enums.SQLType;

import java.sql.Connection;
import java.sql.SQLException;

public interface SQLInterface {
    void create();

    Connection getConnection() throws SQLException;

    void setConnection(Connection connection);

    long getLastConnection();

    void setLastConnection(long lastConnection);

    /* ToDo: Recode */
    boolean isConnectionDead() throws SQLException;

    SQLType getType();

    SQLType setType(SQLType type);
}
