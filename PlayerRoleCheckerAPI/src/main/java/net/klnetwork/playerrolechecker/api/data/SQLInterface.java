package net.klnetwork.playerrolechecker.api.data;

import net.klnetwork.playerrolechecker.api.enums.SQLType;
import net.klnetwork.playerrolechecker.api.utils.CommonUtils;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

    public abstract Plugin getPlugin();

    public abstract String getPath();

    public @Nullable String getSQLFormat() {
        if (getPlugin() == null || getPath() == null) {
            return null;
        }

        switch (getType()) {
            case SQLITE:
            case MYSQL:
            case CUSTOM: {
                //todo: impl
            }
        }

        //current null
        return null;
    }

    public @Nullable String getPassword() {
        if (getPlugin() == null || getPath() == null) {
            return null;
        }

        return getPlugin().getConfig().getString(getPath() + ".password");
    }

    protected @NotNull SQLType getType0() {
        if (getPlugin() == null || getPath() == null) {
            return SQLType.CUSTOM;
        }

        try {
            return SQLType.valueOf(getPlugin().getConfig().getString(getPath() + ".type"));
        } catch (Exception ex) {
            return SQLType.CUSTOM;
        }
    }

    public @NotNull SQLType getType() {
        if (type == null) {
            type = getType0();
        }

        return type;
    }

    public void setType(SQLType type) {
        this.type = type;
    }
}