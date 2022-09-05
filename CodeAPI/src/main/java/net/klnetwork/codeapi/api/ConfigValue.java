package net.klnetwork.codeapi.api;

import net.klnetwork.playerrolechecker.api.data.codeapi.CodeAPIConfigManager;
import net.klnetwork.playerrolechecker.api.utils.config.ConfigKey;
import net.klnetwork.playerrolechecker.api.utils.config.ConfigManager;
import org.bukkit.plugin.Plugin;

public class ConfigValue extends ConfigManager implements CodeAPIConfigManager {
    public ConfigValue(Plugin plugin) {
        super(plugin);

        init();
    }

    @ConfigKey(key = "JoinEvent.limit.min")
    private int min;
    @ConfigKey(key = "JoinEvent.limit.max")
    private int max;
    @ConfigKey(key = "JoinEvent.deleteSecond")
    private int deleteSecond;

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

    @Override
    public int getDeleteSecond() {
        return deleteSecond;
    }

    @Override
    public void setDeleteSecond(int deleteSecond) {
        this.deleteSecond = deleteSecond;
    }
}
