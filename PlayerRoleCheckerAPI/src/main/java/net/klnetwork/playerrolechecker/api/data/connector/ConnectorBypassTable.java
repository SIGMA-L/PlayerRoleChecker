package net.klnetwork.playerrolechecker.api.data.connector;

import net.klnetwork.playerrolechecker.api.data.SQLInterface;

import java.util.UUID;

public abstract class ConnectorBypassTable extends SQLInterface {
    public abstract boolean hasUUID(UUID uuid);

    public abstract boolean hasUUID(String uuid);

    public abstract String getUUID(UUID uuid);

    public abstract String getUUID(String uuid);

    public abstract void put(String uuid);

    public abstract void remove(UUID uuid);

    public abstract void remove(String uuid);

    public abstract boolean isCreated();

    public abstract void create();
}