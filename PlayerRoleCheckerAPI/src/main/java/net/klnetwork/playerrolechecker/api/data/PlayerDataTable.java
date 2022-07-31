package net.klnetwork.playerrolechecker.api.data;

import net.klnetwork.playerrolechecker.api.utils.CommonUtils;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;
import java.util.function.Consumer;

public abstract class PlayerDataTable extends SQLInterface {
    public abstract void asyncDiscordId(UUID uuid, Consumer<PlayerData> discordId);

    public abstract void asyncDiscordId(String uuid, Consumer<PlayerData> discordId);

    public abstract void asyncUUID(String discordId, Consumer<PlayerData> uuid);

    public abstract PlayerData getDiscordId(UUID uuid);

    public abstract PlayerData getDiscordId(String uuid);

    public abstract PlayerData getUUID(String discordId);

    public abstract void put(UUID uuid, String discordId, boolean bedrock);

    public abstract void put(String uuid, String discordId, boolean bedrock);

    public abstract void remove(UUID uuid, String discordId);

    public abstract void remove(String uuid, String discordId);

    public void alter() {
        Statement statement = null;
        try {
            statement = getConnection().createStatement();
            statement.executeUpdate("ALTER TABLE verifyplayer ADD bedrock boolean DEFAULT FALSE");
        } catch (SQLException ex) {
            /* ignored */
        } finally {
            CommonUtils.close(statement);
        }
    }
}
