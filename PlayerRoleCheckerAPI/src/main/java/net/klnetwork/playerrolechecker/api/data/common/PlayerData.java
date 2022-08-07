package net.klnetwork.playerrolechecker.api.data.common;

import java.util.UUID;

public class PlayerData {

    private final String discordId;
    private final UUID uuid;

    public PlayerData(String uuid, String discordID) {
        this.uuid = UUID.fromString(uuid);
        this.discordId = discordID;
    }

    public PlayerData(UUID uuid, String discordId) {
        this.uuid = uuid;
        this.discordId = discordId;
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getDiscordId() {
        return discordId;
    }
}
