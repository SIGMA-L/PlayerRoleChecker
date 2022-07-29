package net.klnetwork.playerrolechecker.api.data.checker;

import net.klnetwork.playerrolechecker.api.data.SQLInterface;

import java.sql.*;
import java.util.UUID;

public abstract class CheckerTemporaryTable extends SQLInterface {
    public abstract boolean hasUUID(Integer code);

    public abstract String getUUID(Integer code);

    @Deprecated
    public abstract String[] getUUID(String code);

    public abstract Integer getCode(UUID uuid);

    public abstract Integer getCode(String uuid);

    public abstract void put(String uuid, String code);

    public abstract void remove(UUID uuid, Integer code);

    public abstract void remove(String uuid, Integer code);

    public abstract void create();

    public abstract Connection getConnection() throws SQLException;

    public abstract void setConnection(Connection connection);
}
