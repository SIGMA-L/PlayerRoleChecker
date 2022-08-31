package net.klnetwork.codeapi.api;

import net.klnetwork.playerrolechecker.api.data.codeapi.CodeAPIConfigManager;
import net.klnetwork.playerrolechecker.api.utils.config.ConfigKey;
import net.klnetwork.playerrolechecker.api.utils.config.ConfigManager;
import org.bukkit.plugin.Plugin;

public class ConfigValue extends ConfigManager implements CodeAPIConfigManager {
    public ConfigValue(Plugin plugin) {
        super(plugin);
    }

    @ConfigKey(key = "JoinEvent.limit.min")
    private int min;
    @ConfigKey(key = "JoinEvent.limit.max")
    private int max;
    @ConfigKey(key = "JoinEvent.deleteSeconds")
    private int deleteSecond;

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getDeleteSecond() {
        return deleteSecond;
    }

    public void setDeleteSecond(int deleteSecond) {
        this.deleteSecond = deleteSecond;
    }
}
