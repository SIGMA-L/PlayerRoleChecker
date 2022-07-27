package net.klnetwork.playerrolechecker.api.utils.config;


import org.bukkit.Color;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.util.List;

public class ConfigManager {
    public ConfigManager(Plugin plugin) {
        try {
            for (Field field : getClass().getDeclaredFields()) {
                field.setAccessible(true);

                Class<?> type = field.getType();

                ConfigKey config = field.getAnnotation(ConfigKey.class);

                if (type.isAssignableFrom(List.class)) {
                    //only support StringList
                    //todo: support booleanList, shortList and more
                    field.set(this, plugin.getConfig().getStringList(config.key()));
                } else if (type.isAssignableFrom(String.class)) {
                    field.set(this, plugin.getConfig().getString(config.key()));
                } else if (type.isAssignableFrom(Integer.class)) {
                    field.set(this, plugin.getConfig().getInt(config.key()));
                } else if (type.isAssignableFrom(Double.class)) {
                    field.set(this, plugin.getConfig().getDouble(config.key()));
                } else if (type.isAssignableFrom(Boolean.class)) {
                    field.set(this, plugin.getConfig().getBoolean(config.key()));
                } else if (type.isAssignableFrom(Color.class)) {
                    field.set(this, plugin.getConfig().getColor(config.key()));
                } else {
                    throw new IllegalStateException("Not Supported Yet.");
                }

                field.setAccessible(false);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
