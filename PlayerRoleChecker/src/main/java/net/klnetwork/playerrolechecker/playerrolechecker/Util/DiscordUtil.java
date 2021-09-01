package net.klnetwork.playerrolechecker.playerrolechecker.Util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.klnetwork.playerrolechecker.playerrolechecker.JDA.JDA;

import static net.klnetwork.playerrolechecker.playerrolechecker.PlayerRoleChecker.plugin;

public class DiscordUtil {
    public static boolean ChannelChecker(String channelId){
        if(plugin.getConfig().getString("Discord.ChannelID") == null) return true;
        return channelId.equals(plugin.getConfig().getString("Discord.ChannelID"));
    }

    public static void sendMessageToChannel(String uuid,String discordID) {
        try {
            String getTextChannelById = plugin.getConfig().getString("Discord.AdminChannel");
            if (getTextChannelById == null) return;
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle("登録が完了したようです！")
                    .addField("UUID:", uuid, false)
                    .addField("DiscordID:", discordID, false);
            JDA.jda.getTextChannelById(getTextChannelById).sendMessage(embedBuilder.build()).queue();
        }catch (Exception exception){
            System.out.println("[エラーが発生しました] sendMessageToChannel メソッドを確認してください (PlayerRoleChecker)");
        }
    }
}
