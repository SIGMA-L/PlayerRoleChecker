package net.klnetwork.playerrolechecker.api.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;
import java.util.function.Consumer;

public interface PlayerDataTable {
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

    void create();

    Connection getConnection() throws SQLException;

    void setConnection(Connection connection);

    long getLastConnection();

    void setLastConnection(long lastConnection);

    /* ToDo: Recode */
    boolean isConnectionDead() throws SQLException;
}
