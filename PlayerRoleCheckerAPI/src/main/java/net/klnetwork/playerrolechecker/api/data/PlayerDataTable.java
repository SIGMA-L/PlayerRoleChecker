package net.klnetwork.playerrolechecker.api.data;

import java.util.UUID;
import java.util.function.Consumer;

public abstract class PlayerDataTable implements SQLInterface {
    public abstract void asyncDiscordId(UUID uuid, Consumer<String> discordId);

    public abstract void asyncDiscordId(String uuid, Consumer<String> discordId);

    public abstract void asyncUUID(String discordId, Consumer<String> uuid);

    public  abstract String getDiscordId(UUID uuid);

    public abstract String getDiscordId(String uuid);

    public abstract String getUUID(String discordId);

    public abstract void put(UUID uuid, String discordId);

    public abstract void put(String uuid, String discordId);

    public abstract void remove(UUID uuid, String discordId);

    public abstract void remove(String uuid, String discordId);
}
