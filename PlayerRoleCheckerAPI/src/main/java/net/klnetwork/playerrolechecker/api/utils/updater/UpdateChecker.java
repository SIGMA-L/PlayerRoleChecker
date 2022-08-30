package net.klnetwork.playerrolechecker.api.utils.updater;

import net.klnetwork.playerrolechecker.api.utils.CommonUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class UpdateChecker {
    private final UpdateBuilder builder;

    private boolean releasedNewVersion;
    private int id = -1;

    public UpdateChecker(UpdateBuilder builder) {
        if (builder == null) {
            throw new IllegalStateException();
        }

        this.builder = builder;

        init();
    }

    public void init() {
        stop();

        BukkitTask task = Bukkit.getScheduler().runTaskTimerAsynchronously(builder.plugin(), () -> {
            final boolean hasNewVersion = !isNewerVersion();

            //TODO: ALERT TO CONSOLE!
            if (!releasedNewVersion && hasNewVersion) {
            }

            this.releasedNewVersion = hasNewVersion;
        }, 0, builder.checkTicks());

        id = task.getTaskId();
    }

    public boolean isNewerVersion() {
        String version = replaceRegex(CommonUtils.getVersion(getOwner(), getRepo()));

        String current = replaceRegex(isDefaultCheck() ? getVersion() : getPlugin().getDescription().getVersion());

        return isSmartVersionCheck() ? Double.parseDouble(current) <= Double.parseDouble(version)
                : version.equalsIgnoreCase(current);
    }

    private String replaceRegex(String string) {
        if (!isSmartVersionCheck()) {
            return string;
        }

        return string.replaceAll(getRegex(), "");
    }

    public void stop() {
        if (id != -1) {
            Bukkit.getScheduler().cancelTask(id);

            id = -1;
        }
    }

    public boolean isReleasedNewVersion() {
        return releasedNewVersion;
    }

    public boolean isDefaultCheck() {
        return builder.defaultCheck();
    }

    public String getVersion() {
        return builder.version();
    }

    public boolean isSmartVersionCheck() {
        return builder.smartVersionCheck();
    }

    public String getOwner() {
        return builder.owner();
    }

    public String getRepo() {
        return builder.repo();
    }

    public String getRegex() {
        return builder.regex();
    }

    public Plugin getPlugin() {
        return builder.plugin();
    }

    public UpdateBuilder getBuilder() {
        return builder;
    }
}
