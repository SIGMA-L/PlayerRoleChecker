package net.klnetwork.playerrolechecker.api.event.checker;

import net.klnetwork.playerrolechecker.api.data.common.PlayerData;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class CheckStartEvent extends Event implements Cancellable {

    private boolean isCancelled;

    private static final HandlerList HANDLER_LIST = new HandlerList();
    private PlayerData data;
    private AsyncPlayerPreLoginEvent event;

    public CheckStartEvent(AsyncPlayerPreLoginEvent event, PlayerData data) {
        this.event = event;
        this.data = data;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }

    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    public AsyncPlayerPreLoginEvent getEvent() {
        return event;
    }

    public void setEvent(AsyncPlayerPreLoginEvent event) {
        this.event = event;
    }

    public UUID getUUID() {
        return event.getUniqueId();
    }

    public @Deprecated void setUUID(UUID uuid) {
        try {
            event.getClass()
                    .getField("uniqueId")
                    .set(event, uuid);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public PlayerData getPlayerData() {
        return data;
    }

    public void setPlayerData(PlayerData data) {
        this.data = data;
    }
}