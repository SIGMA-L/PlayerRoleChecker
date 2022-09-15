package net.klnetwork.playerrolechecker.api.event.checker;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.klnetwork.playerrolechecker.api.data.common.PlayerData;
import net.klnetwork.playerrolechecker.api.enums.CheckResultEnum;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class CheckResultEvent extends Event {

    private boolean result;

    private static final HandlerList HANDLER_LIST = new HandlerList();
    private PlayerData data;
    private Guild guild;

    private CheckResultEnum type;
    private AsyncPlayerPreLoginEvent event;

    public CheckResultEvent(AsyncPlayerPreLoginEvent event, PlayerData data, CheckResultEnum type, boolean result) {
        super(true);

        this.event = event;
        this.data = data;

        this.type = type;
        this.result = result;
    }

    public CheckResultEvent(AsyncPlayerPreLoginEvent event, PlayerData data, CheckResultEnum type) {
        super(true);

        this.event = event;
        this.data = data;

        this.type = type;
        this.result = type.getDefaultResult();
    }

    public CheckResultEvent(AsyncPlayerPreLoginEvent event, Guild guild, PlayerData data, CheckResultEnum type) {
        super(true);

        this.event = event;

        this.guild = guild;
        this.data = data;

        this.type = type;
        this.result = type.getDefaultResult();
    }

    public boolean getResult() {
        return this.result;
    }

    public void setResult(boolean result) {
        this.result = result;
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

    public @Nullable Guild getGuild() {
        return guild;
    }

    public void setGuild(Guild guild) {
        this.guild = guild;
    }

    public @Nullable JDA getJDA() {
        return guild.getJDA();
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