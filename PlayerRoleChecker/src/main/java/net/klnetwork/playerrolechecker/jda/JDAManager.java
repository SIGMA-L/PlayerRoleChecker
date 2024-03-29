package net.klnetwork.playerrolechecker.jda;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.klnetwork.playerrolechecker.PlayerRoleChecker;

public class JDAManager {
    public static net.dv8tion.jda.api.JDA INSTANCE;

    public static void init() {
        INSTANCE = JDABuilder.createDefault(PlayerRoleChecker.INSTANCE.getConfig().getString("Discord.DiscordToken"), GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.DIRECT_MESSAGE_REACTIONS, GatewayIntent.GUILD_MEMBERS)
                .disableCache(CacheFlag.VOICE_STATE, CacheFlag.EMOJI, CacheFlag.STICKER)
                .setStatus(OnlineStatus.ONLINE)
                .build();
    }
}
