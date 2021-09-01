package net.klnetwork.playerrolechecker.playerrolechecker.Util;

import static net.klnetwork.playerrolechecker.playerrolechecker.PlayerRoleChecker.plugin;

public class DiscordUtil {
    public static boolean ChannelChecker(String channelId){
        if(plugin.getConfig().getString("Discord.ChannelID") == null) return true;
        return channelId.equals(plugin.getConfig().getString("Discord.ChannelID"));
    }
}
