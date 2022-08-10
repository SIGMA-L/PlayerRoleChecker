package net.klnetwork.playerrolechecker.api.event.checker;

import net.klnetwork.playerrolechecker.api.data.common.PlayerData;
import net.klnetwork.playerrolechecker.api.enums.CheckResultEnum;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class CheckResultEvent extends Event {

    private boolean result;

    private static final HandlerList HANDLER_LIST = new HandlerList();
    private PlayerData data;

    private CheckResultEnum type;

    public CheckResultEvent(PlayerData data, CheckResultEnum type, boolean result) {
        super(true);

        this.data = data;
        this.result = result;

        this.type = type;
    }
    public boolean getResult() {
        return this.result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    public PlayerData getPlayerData() {
        return data;
    }

    public void setPlayerData(PlayerData data) {
        this.data = data;
    }

    public CheckResultEnum getType() {
        return type;
    }

    public void setType(CheckResultEnum type) {
        this.type = type;
    }
}