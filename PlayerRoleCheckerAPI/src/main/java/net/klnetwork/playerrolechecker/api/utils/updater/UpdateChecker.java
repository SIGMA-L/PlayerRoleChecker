package net.klnetwork.playerrolechecker.api.utils.updater;

import net.klnetwork.playerrolechecker.api.utils.CommonUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class UpdateChecker {
    private final UpdateBuilder builder;

    private boolean releasedNewVersion;

    public UpdateChecker(UpdateBuilder builder) {
        if (builder == null) {
            throw new IllegalStateException();
        }

        this.builder = builder;
    }

    public void init() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(builder.plugin(), () -> {
            final boolean hasNewVersion = !isNewerVersion();

            //TODO: ALERT TO CONSOLE!
            if (!releasedNewVersion && hasNewVersion) {
                getPlugin().getLogger().info("new version found!");
            }

            this.releasedNewVersion = hasNewVersion;
        }, 0, builder.checkTicks());
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
