package net.klnetwork.playerrolechecker.api.data.common;

import java.util.UUID;

public class TemporaryData {

    private final UUID uuid;
    private final int code;
    private final boolean bedrock;

    public TemporaryData(UUID uuid, int code, boolean bedrock) {
        this.uuid = uuid;
        this.code = code;
        this.bedrock = bedrock;
    }

    public TemporaryData(String uuid, int code, boolean bedrock) {
        this.uuid = UUID.fromString(uuid);
        this.code = code;
        this.bedrock = bedrock;
    }

    public UUID getUUID() {
        return uuid;
    }

    public int getCode() {
        return code;
    }

    public boolean isBedrock() {
        return bedrock;
    }
}
