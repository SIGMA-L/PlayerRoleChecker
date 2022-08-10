package net.klnetwork.playerrolechecker.api;

import net.klnetwork.playerrolechecker.api.data.checker.CheckerConfigManager;
import net.klnetwork.playerrolechecker.api.utils.config.ConfigKey;
import net.klnetwork.playerrolechecker.api.utils.config.ConfigManager;
import org.bukkit.plugin.Plugin;

public class ConfigValue extends ConfigManager implements CheckerConfigManager {
    public ConfigValue(Plugin plugin) {
        super(plugin);
    }

    @ConfigKey(key = "JoinEvent.deleteSecond")
    private int deleteSeconds = 30;
    @ConfigKey(key = "CodeLimit.min")
    private int min;
    @ConfigKey(key = "CodeLimit.max")
    private int max;

    @Override
    public int getDeleteSecond() {
        return deleteSeconds;
    }

    @Override
    public void setDeleteSecond(int deleteSecond) {
        this.deleteSeconds = deleteSecond;
    }

    @Override
    public int getMin() {
        return min;
    }

    @Override
    public void setMin(int min) {
        this.min = min;
    }

    @Override
    public int getMax() {
        return max;
    }

    @Override
    public void setMax(int max) {
        this.max = max;
    }
}
