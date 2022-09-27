package net.klnetwork.playerrolechecker.api.utils.config;


import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class ConfigManager {
    private final Plugin plugin;

    public ConfigManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void init() {
        try {
            for (Field field : getClass().getDeclaredFields()) {
                field.setAccessible(true);

                Class<?> type = field.getType();

                ConfigKey config = field.getAnnotation(ConfigKey.class);

                if (type.isAssignableFrom(List.class)) {
                    Type genericType = field.getGenericType();

                    if (genericType instanceof ParameterizedType) {
                        Class<?> actualClass = (Class<?>) ((ParameterizedType) genericType).getActualTypeArguments()[0];

                        //TODO: way better programming
                        if (actualClass.isAssignableFrom(Boolean.class)) {
                            field.set(this, plugin.getConfig().getBooleanList(config.key()));
                        } else if (actualClass.isAssignableFrom(Short.class)) {
                            field.set(this, plugin.getConfig().getShortList(config.key()));
                        } else if (actualClass.isAssignableFrom(Byte.class)) {
                            field.set(this, plugin.getConfig().getByteList(config.key()));
                        } else if (actualClass.isAssignableFrom(Float.class)) {
                            field.set(this, plugin.getConfig().getFloatList(config.key()));
                        } else if (actualClass.isAssignableFrom(Character.class)) {
                            field.set(this, plugin.getConfig().getCharacterList(config.key()));
                        } else if (actualClass.isAssignableFrom(Double.class)) {
                            field.set(this, plugin.getConfig().getDoubleList(config.key()));
                        } else if (actualClass.isAssignableFrom(Integer.class)) {
                            field.set(this, plugin.getConfig().getIntegerList(config.key()));
                        } else if (actualClass.isAssignableFrom(String.class)) {
                            field.set(this, plugin.getConfig().getStringList(config.key()));
                        } else if (actualClass.isAssignableFrom(Long.class)) {
                            field.set(this, plugin.getConfig().getLongList(config.key()));
                        } else {
                            //not stable...
                            field.set(this, plugin.getConfig().getMapList(config.key()));
                        }
                    } else {
                        //What Happened?
                        field.set(this, plugin.getConfig().getStringList(config.key()));

                        Bukkit.getLogger().warning("Illegal state detected! (" + config.key() + ") genericType:" + genericType);
                    }
                } else if (type.isAssignableFrom(String.class)) {
                    field.set(this, plugin.getConfig().getString(config.key()));
                } else if (type.isAssignableFrom(Integer.class) || type.isAssignableFrom(int.class)) {
                    field.set(this, plugin.getConfig().getInt(config.key()));
                } else if (type.isAssignableFrom(Double.class) || type.isAssignableFrom(double.class)) {
                    field.set(this, plugin.getConfig().getDouble(config.key()));
                } else if (type.isAssignableFrom(Boolean.class) || type.isAssignableFrom(boolean.class)) {
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

    public Plugin getPlugin() {
        return plugin;
    }
}
