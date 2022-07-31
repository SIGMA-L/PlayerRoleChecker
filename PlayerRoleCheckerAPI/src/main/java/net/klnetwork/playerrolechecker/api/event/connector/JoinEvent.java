package net.klnetwork.playerrolechecker.api.event.connector;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.klnetwork.playerrolechecker.api.enums.JoinEventType;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class JoinEvent extends Event implements Cancellable {

    private Member member;
    private UUID uuid;
    private int code;

    private JoinEventType type;

    private Message message;

    private static final HandlerList HANDLER_LIST = new HandlerList();
    private boolean isCancelled, bedrock;

    public JoinEvent(UUID uuid, int code, boolean bedrock, Message message, JoinEventType type) {
        super(true);

        this.member = message.getMember();
        this.uuid = uuid;
        this.code = code;
        this.bedrock = bedrock;

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

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public JDA getInstance() {
        return this.member.getJDA();
    }

    public Guild getGuild() {
        return this.member.getGuild();
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public boolean isBedrock() {
        return bedrock;
    }

    public void setBedrock(boolean bedrock) {
        this.bedrock = bedrock;
    }

    public JoinEventType getType() {
        return type;
    }

    public void setType(JoinEventType type) {
        this.type = type;
    }
}
