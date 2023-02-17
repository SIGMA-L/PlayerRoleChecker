package net.klnetwork.playerrolechecker.api.utils.updater;

import net.klnetwork.playerrolechecker.api.utils.CommonUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

public class UpdateAlert implements Listener {
    private final UpdateBuilder builder;

    private boolean releasedNewVersion;
    private BukkitTask task;

    private String version;
    private String current;

    public UpdateAlert(UpdateBuilder builder) {
        if (builder == null) {
            throw new IllegalStateException();
        }

        this.builder = builder;
    }

    public void registerTask() {
        if (isEnabled()) {
            stop();

            task = Bukkit.getScheduler().runTaskTimerAsynchronously(builder.plugin(), () -> {
                final boolean hasNewVersion = !isNewerVersion();

                if (isConsoleAlert() && !releasedNewVersion && hasNewVersion) {
                    getMessage().forEach(message -> Bukkit.getConsoleSender().sendMessage(replace(message)));
                }

                this.releasedNewVersion = hasNewVersion;
            }, 0, builder.checkTicks());
            Bukkit.getPluginManager().registerEvents(this, getPlugin());
        }
    }

    @EventHandler
    public void onJoinEvent(PlayerJoinEvent event) {
        if (isOpPlayerAlert() && event.getPlayer().isOp() && releasedNewVersion) {
            getMessage().forEach(message -> {
                event.getPlayer().sendMessage(replace(message));
            });
        }
    }

    public String replace(String string) {
        return ChatColor.translateAlternateColorCodes('&', string)
                .replaceAll("%version%", current)
                .replaceAll("%release_version%", version);
    }

    public boolean isNewerVersion() {
        version = replaceRegex(CommonUtils.getVersion(getOwner(), getRepo()));

        current = replaceRegex(isDefaultCheck() ? getVersion() : getPlugin().getDescription().getVersion());

        return isSmartVersionCheck() ? Double.parseDouble(current) >= Double.parseDouble(version)
                : version.equalsIgnoreCase(current);
    }

    private String replaceRegex(String string) {
        if (!isSmartVersionCheck()) {
            return string;
        }

        return string.replaceAll(getRegex(), "");
    }

    public void stop() {
        if (task != null && !task.isCancelled()) {
            task.cancel();
            task = null;
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

    public boolean isOpPlayerAlert() {
        return builder.opPlayerAlert();
    }

    public boolean isConsoleAlert() {
        return builder.consoleAlert();
    }

    public boolean isEnabled() {
        return builder.enabled();
    }

    public List<String> getMessage() {
        return builder.messages();
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
