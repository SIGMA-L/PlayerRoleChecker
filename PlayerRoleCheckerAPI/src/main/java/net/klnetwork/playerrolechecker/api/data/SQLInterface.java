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
        if (getType() != SQLType.SQLITE) {
            final long now = System.currentTimeMillis();

            if (CommonUtils.checkIsValid(lastConnection, now)) {
                lastConnection = now;
                return !connection.isValid(1);
            }
        }

        return false;
    }

    public abstract Plugin getPlugin();

    public abstract String getPath();

    public void checkClass() {
        SQLType type = getType();

        if (type == SQLType.SQLITE) {
            CommonUtils.checkClass("org.sqlite.JDBC");
        } else if (type == SQLType.MYSQL) {
            CommonUtils.checkClass("com.mysql.jdbc.Driver");
        } else if (getPlugin() != null && getPath() != null) {
            CommonUtils.checkClass(getPlugin().getConfig().getString(getPath() + ".checkClass"));
        }
    }

    public @Nullable String getSQLFormat() {
        if (getPlugin() == null || getPath() == null) {
            return null;
        }

        SQLType type = getType();

        String format = null;

        switch (type) {
            case SQLITE: {
                format = getPlugin().getConfig().getString(getPath() + ".Location");
                break;
            }
            case MYSQL: {
                format = String.format("%s:%s/%s%s"
                        , getPlugin().getConfig().getString(getPath() + ".Server")
                        , getPlugin().getConfig().getString(getPath() + ".Port")
                        , getPlugin().getConfig().getString(getPath() + ".Database")
                        , getPlugin().getConfig().getString(getPath() + ".Option"));
                break;
            }
            case CUSTOM: {
                format = getPlugin().getConfig().getString(getPath() + ".Format");
                break;
            }
        }

        return type.getType() + format;

        //BypassTable:
        //    Location: 'bypass.db'
        //    useBypassCommand: true
        //    type: SQLITE
        //  PlayerDataTable:
        //    Server: 'localhost'
        //    Port: 3306
        //    Database: 'playerrolechecker'
        //    Username: 'playerrolecheckeruser'
        //    Password: 'playerrolecheckerpass'
        //    Option: '?allowPublicKeyRetrieval=true&useSSL=false&characterEncoding=latin1&autoReconnect=true'
        //    type: MYSQL
    }

    public @Nullable String getUser() {
        if (getPlugin() == null || getPath() == null) {
            return null;
        }

        return getPlugin().getConfig().getString(getPath() + ".User");
    }

    public @Nullable String getPassword() {
        if (getPlugin() == null || getPath() == null) {
            return null;
        }

        return getPlugin().getConfig().getString(getPath() + ".Password");
    }

    protected @NotNull SQLType getType0() {
        if (getPlugin() == null || getPath() == null) {
            return SQLType.CUSTOM;
        }

        return CommonUtils.getSQLType(getPlugin().getConfig().getString(getPath() + ".type"));
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