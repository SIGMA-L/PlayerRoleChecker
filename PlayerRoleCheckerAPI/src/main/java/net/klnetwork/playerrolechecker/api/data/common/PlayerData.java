package net.klnetwork.playerrolechecker.api.data.common;

import java.util.UUID;

public class PlayerData {

    private final String discordId;
    private final UUID uuid;
    private final boolean bedrock;

    public PlayerData(String uuid, String discordID, boolean bedrock) {
        this.uuid = UUID.fromString(uuid);
        this.discordId = discordID;
        this.bedrock = bedrock;
    }

    public PlayerData(UUID uuid, String discordId, boolean bedrock) {
        this.uuid = uuid;
        this.discordId = discordId;
        this.bedrock = bedrock;
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getDiscordId() {
        return discordId;
    }

    public boolean isBedrock() {
        return bedrock;
    }
}
