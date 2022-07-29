package net.klnetwork.playerrolechecker.api.event.connector;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.klnetwork.playerrolechecker.api.enums.ForceJoinEventType;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class ForceJoinEvent extends Event implements Cancellable {
    private Member executor;

    private String memberId;
    private UUID uuid;

    private ForceJoinEventType type;

    private Message message;

    private static final HandlerList HANDLER_LIST = new HandlerList();
    private boolean isCancelled;

    public ForceJoinEvent(String memberId, UUID uuid, Message message, ForceJoinEventType type) {
        super(true);

        this.memberId = memberId;
        this.uuid = uuid;

        this.message = message;
        this.type = type;
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

    public Guild getGuild() {
        return this.executor.getGuild();
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public ForceJoinEventType getType() {
        return type;
    }

    public void setType(ForceJoinEventType type) {
        this.type = type;
    }
}
