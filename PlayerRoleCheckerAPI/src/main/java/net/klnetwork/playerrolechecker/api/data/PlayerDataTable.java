package net.klnetwork.playerrolechecker.api.data;

import net.klnetwork.playerrolechecker.api.data.common.PlayerData;

import java.util.UUID;
import java.util.function.Consumer;

public abstract class PlayerDataTable extends SQLInterface {
    public abstract void asyncDiscordId(UUID uuid, Consumer<PlayerData> discordId);

    public abstract void asyncDiscordId(String uuid, Consumer<PlayerData> discordId);

    public abstract void asyncUUID(String discordId, Consumer<PlayerData> uuid);

    public abstract PlayerData getDiscordId(UUID uuid);

    public abstract PlayerData getDiscordId(String uuid);

    public abstract PlayerData getUUID(String discordId);

    public abstract void put(UUID uuid, String discordId);

    public abstract void put(String uuid, String discordId);

    public abstract void remove(UUID uuid, String discordId);

    public abstract void remove(String uuid, String discordId);
}
