package net.klnetwork.playerrolechecker.api.data;

import java.sql.SQLException;
import java.sql.SQLType;

public interface SQLInterface {
    long getLastConnection();

    void setLastConnection(long lastConnection);

    /* ToDo: Recode */
    boolean isConnectionDead() throws SQLException;

    SQLType getType();

    SQLType setType();
}
