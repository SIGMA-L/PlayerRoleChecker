package net.klnetwork.playerrolechecker.code;

import net.klnetwork.playerrolechecker.PlayerRoleChecker;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;

public class CodeData {
    private final long createdAt = System.currentTimeMillis();
    private final UUID uuid;
    private final int code;
    private final boolean bedrock;
    private BukkitTask task;

    public CodeData(UUID uuid, final int code, final boolean bedrock) {
        this.uuid = uuid;
        this.code = code;
        this.bedrock = bedrock;
    }

    public UUID getUUID() {
        return uuid;
    }

    public int getCode() {
        return code;
    }

    public boolean isBedrock() {
        return bedrock;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public CodeData startTask(CodeHolder holder) {
        startTask(holder, PlayerRoleChecker.INSTANCE.getConfigManager().getDeleteSecond() * 20);
        return this;
    }

    public CodeData startTask(CodeHolder holder, int ticks) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(holder.getPlugin(), () -> holder.remove(this), ticks);
        return this;
    }

    public void cancelTask() {
        if (this.task != null) {
            this.task.cancel();
            this.task = null;
        }
    }
}
