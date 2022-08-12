package net.klnetwork.playerrolechecker.api.data.common;

import net.klnetwork.playerrolechecker.api.utils.CommonUtils;

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

    public long getXUID() {
        return CommonUtils.getXUID(uuid);
    }

    public Integer getCode() {
        return code;
    }

    public boolean isBedrock() {
        return bedrock;
    }
}
