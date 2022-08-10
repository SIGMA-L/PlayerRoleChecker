package net.klnetwork.playerrolechecker.api.enums;

public enum CheckResultEnum {
    SUCCESS(true),
    ERROR(false),
    NOT_REGISTERED(false),
    GUILD_IS_INVALID(false),
    UNKNOWN(false);

    private boolean result;

    CheckResultEnum(boolean result) {
        this.result = result;
    }

    public boolean getDefaultResult() {
        return result;
    }

    public void setDefaultResult(boolean result) {
        this.result = result;
    }
}