package net.klnetwork.playerrolechecker.code;

import net.klnetwork.playerrolechecker.PlayerRoleChecker;
import net.klnetwork.playerrolechecker.api.data.checker.CheckerCodeData;
import net.klnetwork.playerrolechecker.api.data.checker.CheckerCodeHolder;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;

public class CodeData implements CheckerCodeData {
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

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public boolean isBedrock() {
        return bedrock;
    }

    @Override
    public long getCreatedAt() {
        return createdAt;
    }

    @Override
    public CheckerCodeData startTask(CheckerCodeHolder holder) {
        startTask(holder, PlayerRoleChecker.INSTANCE.getConfigManager().getDeleteSecond() * 20);
        return this;
    }

    @Override
    public CheckerCodeData startTask(CheckerCodeHolder holder, int ticks) {
        this.task = Bukkit.getScheduler().runTaskLaterAsynchronously(holder.getPlugin(), () -> holder.remove(this), ticks);
        return this;
    }

    @Override
    public void cancelTask() {
        if (this.task != null) {
            this.task.cancel();
            this.task = null;
        }
    }
}
