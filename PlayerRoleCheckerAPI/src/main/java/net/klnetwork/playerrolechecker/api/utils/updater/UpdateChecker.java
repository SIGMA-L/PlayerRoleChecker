package net.klnetwork.playerrolechecker.api.utils.updater;

import net.klnetwork.playerrolechecker.api.utils.CommonUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class UpdateChecker {
    private final UpdateBuilder builder;

    public UpdateChecker(UpdateBuilder builder) {
        if (builder == null) {
            throw new IllegalStateException();
        }

        this.builder = builder;
    }

    public void init() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(builder.plugin(), () -> {

        }, 0, builder.checkTicks());
    }

    public boolean isNewerVersion() {
        String version = replaceRegex(CommonUtils.getVersion(getOwner(), getRepo()));

        //todo: default check impl
        String current = replaceRegex(isDefaultCheck() ? null : getPlugin().getDescription().getVersion());

        if (isSmartVersionCheck()) {

        }

        //temporary
        return true;
    }

    private String replaceRegex(String string) {
        if (!isSmartVersionCheck()) {
            return string;
        }

        return string.replaceAll(getRegex(), "");
    }

    public boolean isDefaultCheck() {
        return builder.defaultCheck();
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
