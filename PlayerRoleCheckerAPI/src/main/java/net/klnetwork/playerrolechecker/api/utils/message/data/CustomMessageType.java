package net.klnetwork.playerrolechecker.api.utils.message.data;

public enum CustomMessageType {
    /**
     * temp:
     *
     * MONITOR = 5
     * ALL = 4
     * Discord Something = 3
     * Minecraft = 2
     * OTHER = 1
     */

    MONITOR(5),
    ALL(4),
    DISCORD(3),
    /**
     * Nothing to using!?
     */
    DISCORD_FIELD(3),
    DISCORD_TITLE(3),
    DISCORD_DESCRIPTION(3),
    DISCORD_IMAGE(3),
    MINECRAFT(2),
    COLOR(1);

    private final int type;

    CustomMessageType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public boolean isDiscord() {
        return type == 3;
    }
}