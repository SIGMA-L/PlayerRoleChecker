package net.klnetwork.playerrolechecker.api.utils.message;

import com.google.common.base.Preconditions;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.klnetwork.playerrolechecker.api.utils.CommonUtils;
import net.klnetwork.playerrolechecker.api.utils.message.data.CustomMessage;
import net.klnetwork.playerrolechecker.api.utils.message.data.CustomMessageType;
import net.klnetwork.playerrolechecker.api.utils.message.data.preset.other.ColorCodeProvider;
import net.klnetwork.playerrolechecker.api.utils.message.data.preset.other.HexCodeProvider;
import net.klnetwork.playerrolechecker.api.utils.message.data.preset.players.PlayerProvider;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MessageCreator {
    protected Plugin plugin;

    public MessageCreator(Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * @param player using to {@link PlayerProvider#execute(String)}
     * @return convertor {@link CustomMessage}
     */
    @Deprecated
    public CustomMessage[] getBasicsMessage(Player player) {
        return new CustomMessage[]{new PlayerProvider(player)};
    }

    /**
     * @param player using to {@link PlayerProvider#execute(String)}
     * @return convertor {@link CustomMessage}
     *
     * {@link ColorCodeProvider#execute(String)}
     * {@link HexCodeProvider#execute(String)}
     */
    @Deprecated
    public CustomMessage[] getMinecraftMessage(Player player) {
        return new CustomMessage[]{new PlayerProvider(player), new ColorCodeProvider(), new HexCodeProvider()};
    }

    public Map<CustomMessageType, CustomMessage> getProvider(Player player) {
        return new HashMap<CustomMessageType, CustomMessage>() {{
            put(CustomMessageType.ALL, new PlayerProvider(player));
        }};
    }


    public static void main(String[] args) {
        new MessageCreator(null).getEmbedMessage("bb", new CustomMessage() {
            @Override
            public CustomMessageType key() {
                return null;
            }
        });
    }

    /**
     * @param key Yaml config key
     * @param messages CustomMessageProvider (Example: {@link PlayerProvider#execute(String)})
     * @return {@link MessageEmbed} using to discord Embeds
     */
    public MessageEmbed getEmbedMessage(String key, CustomMessage... messages) {
        if (messages == null) {
            return getEmbedMessage(key);
        } else {
            System.out.println(Optional.ofNullable(run(CustomMessageType.MONITOR,  "sure #10005f", new HexCodeProvider())));
        }

        return null;
    }

    public String getAsString(CustomMessageType type, String message, CustomMessage... provider) {
        for (CustomMessage provided : provider) {
            message = run(type, message, provided);
        }

        return message;
    }

    /**
     * @return <T> Provider Type (String, Time)
     */
    private <T> T run(CustomMessageType type, String message, CustomMessage provider) {
        try {
            return (T) provider.getClass()
                    .getMethod("execute", String.class)
                    .invoke(provider, message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
        //throw new IllegalStateException("Invalid CustomMessage Detected: " + message.getClass().getName() + "!");
    }

    public MessageEmbed getEmbedMessage(String path) {
        EmbedBuilder as = new EmbedBuilder()
                .setColor(CommonUtils.getColor(plugin.getConfig().getString(path + ".color")))
                .setTitle(plugin.getConfig().getString(path + ".title"))
                .setDescription(plugin.getConfig().getString(path + ".description"))
                .setThumbnail(plugin.getConfig().getString(path + ".image"))
                .setTimestamp(plugin.getConfig().getBoolean(path + ".timestamp") ? OffsetDateTime.now() : null);

        return split(as, path).build();
    }

    private EmbedBuilder split(EmbedBuilder embedBuilder, String path, CustomMessage... messages) {
        for (String message : plugin.getConfig().getStringList(path)) {
            String[] strings = getAsString(CustomMessageType.FIELD, message, messages).split("\\|", 3);

            Preconditions.checkArgument(strings.length == 3);

            embedBuilder.addField(strings[0], strings[1], Boolean.parseBoolean(strings[2]));
        }
        return embedBuilder;
    }


    public String getString(String key) {
        return plugin.getConfig().getString(key);
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }
}
