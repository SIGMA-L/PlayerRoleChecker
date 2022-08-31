package net.klnetwork.playerrolechecker.api.data.common;

import net.klnetwork.playerrolechecker.api.data.SQLInterface;

import java.util.UUID;

public abstract class TemporaryTable extends SQLInterface {
    public abstract boolean hasUUID(Integer code);

    public abstract TemporaryData getUUID(Integer code);

    @Deprecated
    public abstract String[] getUUID(String code);

    public abstract TemporaryData getCode(UUID uuid);

    public abstract TemporaryData getCode(String uuid);

    public abstract void put(UUID uuid, String code, boolean bedrock);

    public abstract void put(UUID uuid, Integer code, boolean bedrock);

    public abstract void put(String uuid, String code, boolean bedrock);

    public abstract void put(String uuid, Integer code, boolean bedrock);

    public abstract void remove(UUID uuid, Integer code);

    public abstract void remove(String uuid, Integer code);

    public abstract void drop();

    public abstract void create();
}
