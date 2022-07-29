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

    public abstract void setConnection(Connection connection);

    public abstract long getLastConnection();

    public abstract void setLastConnection(long lastConnection);

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
