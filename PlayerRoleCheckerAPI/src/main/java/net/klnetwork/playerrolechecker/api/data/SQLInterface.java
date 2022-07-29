package net.klnetwork.playerrolechecker.api.data;

import net.klnetwork.playerrolechecker.api.enums.SQLType;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class SQLInterface {

    protected Connection connection;
    protected long lastConnection;

    public abstract void create();

    public abstract Connection getConnection() throws SQLException;

    public abstract void setConnection(Connection connection);

    public abstract long getLastConnection();

    public abstract void setLastConnection(long lastConnection);

    /* ToDo: Recode */
    public abstract boolean isConnectionDead() throws SQLException;

    public abstract SQLType getType();

    public abstract void setType(SQLType type);
}
