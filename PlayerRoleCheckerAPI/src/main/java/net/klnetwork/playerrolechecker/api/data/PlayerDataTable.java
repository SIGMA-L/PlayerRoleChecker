package net.klnetwork.playerrolechecker.api.data;

import java.util.UUID;
import java.util.function.Consumer;

public interface PlayerDataTable extends SQLInterface {
    void asyncDiscordId(UUID uuid, Consumer<String> discordId);

    void asyncDiscordId(String uuid, Consumer<String> discordId);

    void asyncUUID(String discordId, Consumer<String> uuid);

    String getDiscordId(UUID uuid);

    String getDiscordId(String uuid);

    String getUUID(String discordId);

    void put(UUID uuid, String discordId);

    void put(String uuid, String discordId);

    void remove(UUID uuid, String discordId);

    void remove(String uuid, String discordId);
}
