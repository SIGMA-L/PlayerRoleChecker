package net.klnetwork.playerrolechecker.api.event.checker;

import net.klnetwork.playerrolechecker.api.enums.SkippedReasonEnum;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CheckSkippedEvent extends Event implements Cancellable {

    private boolean isCancelled;

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private SkippedReasonEnum reason;

    public CheckSkippedEvent(SkippedReasonEnum reason) {
        this.reason = reason;
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

    public SkippedReasonEnum getReason() {
        return reason;
    }

    public void setReason(SkippedReasonEnum reason) {
        this.reason = reason;
    }
}
