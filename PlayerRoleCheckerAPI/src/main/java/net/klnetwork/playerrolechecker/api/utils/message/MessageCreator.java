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
import java.util.*;

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

    public Map<CustomMessageType, List<CustomMessage>> getProvider(Player player) {
        return new HashMap<CustomMessageType, List<CustomMessage>>() {{
            put(CustomMessageType.ALL, Arrays.asList(new PlayerProvider(player)));
        }};
    }

    private Map<CustomMessageType, List<CustomMessage>> provided;

    public Map<CustomMessageType, List<CustomMessage>> get() {
        return provided;
    }

    public MessageCreator set(Map<CustomMessageType, List<CustomMessage>> messages) {
        this.provided = messages;

        return this;
    }

    public MessageCreator put(CustomMessageType type, CustomMessage message) {
        if (provided == null) {
            getProvider(null);
        }

        //VERY WILD
        provided.getOrDefault(type, (List<CustomMessage>) new ArrayList<CustomMessage>()).add(message);

        return this;
    }

    public static void main(String[] args) {
        new MessageCreator(null).getEmbedMessage("bb", new MessageCreator(null).getProvider(null));
    }

    /**
     * @param key Yaml config key
     * @param messages CustomMessageProvider (Example: {@link PlayerProvider#execute(String)})
     * @return {@link MessageEmbed} using to discord Embeds
     */
    public MessageEmbed getEmbedMessage(String key, Map<CustomMessageType, List<CustomMessage>> messages) {
        if (messages == null || messages.isEmpty()) {
            return getEmbedMessage(key);
        } else {
            return getEmbedMessage(key, messages);
        }
    }

    public MessageEmbed getEmbedMessage(String path) {
        EmbedBuilder as = new EmbedBuilder()
                .setColor(CommonUtils.getColor(getString(path + ".color")))
                .setTitle(getString(path + ".title"))
                .setDescription(getString(path + ".description"))
                .setThumbnail(getString(path + ".image"))
                .setTimestamp(getBoolean(path + ".timestamp") ? OffsetDateTime.now() : null);

        return split(as, path).build();
    }

    public String getAsString(CustomMessageType type, String message, Map<CustomMessageType, List<CustomMessage>> provider) {
        if (provider == null) {
            return message;
        }

        for (CustomMessage customMessage : provider.get(type)) {
            message = getAsString(message, customMessage);
        }

        for (CustomMessage customMessage : provider.get(CustomMessageType.MONITOR)) {
            return get(customMessage);
        }

        return message;
    }

    /**
     * @return Provided String Message
     * @throws Exception throws on not String
     */
    private String getAsString(String message, CustomMessage customMessage) {
        try {
            return (String) customMessage.getClass()
                    .getMethod("execute", String.class)
                    .invoke(customMessage, message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public <T> T get(CustomMessage message) {
        try {
            return (T) message.getClass()
                    .getMethod("execute", String.class)
                    .invoke(message, null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    private EmbedBuilder split(EmbedBuilder embedBuilder, String path) {
        for (String message : plugin.getConfig().getStringList(path)) {
            String[] strings = getAsString(CustomMessageType.FIELD, message, provided).split("\\|", 3);

            Preconditions.checkArgument(strings.length == 3);

            embedBuilder.addField(strings[0], strings[1], Boolean.parseBoolean(strings[2]));
        }
        return embedBuilder;
    }

    public String getString(CustomMessageType type, String key) {
        return getAsString(type, getString(key), provided);
    }

    public String getString(String key) {
        return plugin.getConfig().getString(key);
    }

    public boolean getBoolean(String key) {
        return plugin.getConfig().getBoolean(key);
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }
}
