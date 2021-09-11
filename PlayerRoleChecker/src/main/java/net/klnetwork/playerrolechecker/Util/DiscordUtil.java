package net.klnetwork.playerrolechecker.Util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.klnetwork.playerrolechecker.JDA.JDA;
import net.klnetwork.playerrolechecker.PlayerRoleChecker;

import static net.klnetwork.playerrolechecker.PlayerRoleChecker.plugin;

public class DiscordUtil {
    public static boolean ChannelChecker(String channelId){
        if(plugin.getConfig().getString("Discord.ChannelID") == null) return true;
        return channelId.equals(plugin.getConfig().getString("Discord.ChannelID"));
    }

    public static void sendMessageToChannel(EmbedBuilder embedBuilder) {
        try {
            String getTextChannelById = plugin.getConfig().getString("Discord.AdminChannel");
            if (getTextChannelById == null) return;

            JDA.jda.getTextChannelById(getTextChannelById).sendMessage(embedBuilder.build()).queue();
        }catch (Exception exception){
            System.out.println("[エラーが発生しました] sendMessageToChannel メソッドを確認してください (PlayerRoleChecker)");
        }
    }

    public static void AddRole(Guild guild, Member member){
        String roleID = PlayerRoleChecker.plugin.getConfig().getString("Discord.addToRole");
        if (roleID == null) return;
        Role role = guild.getRoleById(roleID);
        if (role == null || member == null) return;
        guild.removeRoleFromMember(member, role).queue();
    }

    public static void RemoveRole(Guild guild, Member member){
        String roleID = PlayerRoleChecker.plugin.getConfig().getString("Discord.addToRole");
        if (roleID == null) return;
        Role role = guild.getRoleById(roleID);
        if (role == null || member == null) return;
        guild.removeRoleFromMember(member, role).queue();
    }
}
