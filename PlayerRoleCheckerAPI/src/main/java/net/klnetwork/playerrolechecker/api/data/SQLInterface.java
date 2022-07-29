package net.klnetwork.playerrolechecker.api.data;

import net.klnetwork.playerrolechecker.api.enums.SQLType;
import net.klnetwork.playerrolechecker.api.utils.CommonUtils;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class SQLInterface {

    protected Connection connection;
    protected long lastConnection;
    protected SQLType type;

    public abstract void create();

    public abstract Connection getConnection() throws SQLException;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public long getLastConnection() {
        return lastConnection;
    }

    public void setLastConnection(long lastConnection) {
        this.lastConnection = lastConnection;
    }

    public boolean isConnectionDead() throws SQLException {
        final long now = System.currentTimeMillis();

        if (CommonUtils.checkIsValid(lastConnection, now)) {
            lastConnection = now;
            return !connection.isValid(1);
        }

        return false;
    }

    public abstract SQLType getType();

    public abstract void setType(SQLType type);
}
