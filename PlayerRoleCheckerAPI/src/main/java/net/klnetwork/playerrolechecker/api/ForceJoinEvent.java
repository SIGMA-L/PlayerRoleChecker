package net.klnetwork.playerrolechecker.api;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class ForceJoinEvent extends Event implements Cancellable {

    private Member executor;
    private String memberId;
    private UUID uuid;

    private static final HandlerList HANDLER_LIST = new HandlerList();
    private boolean isCancelled;

    public ForceJoinEvent(String memberId, UUID uuid, Member executor) {
        this.memberId = memberId;
        this.uuid = uuid;
        this.executor = executor;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }

    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public Member getExecutor() {
        return executor;
    }

    public void setExecutor(Member executor) {
        this.executor = executor;
    }

    public JDA getInstance() {
        return this.executor.getJDA();
    }
}
