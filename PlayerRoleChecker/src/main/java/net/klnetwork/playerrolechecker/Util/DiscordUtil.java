package net.klnetwork.playerrolechecker.Util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.klnetwork.playerrolechecker.JDA.JDA;
import net.klnetwork.playerrolechecker.PlayerRoleChecker;

import java.time.OffsetDateTime;

import static net.klnetwork.playerrolechecker.PlayerRoleChecker.plugin;

public class DiscordUtil {
    public static boolean ChannelChecker(String channelId) {
        if (plugin.getConfig().getString("Discord.ChannelID") == null) return true;
        return channelId.equals(plugin.getConfig().getString("Discord.ChannelID"));
    }

    public static void sendMessageToChannel(EmbedBuilder embedBuilder) {
        try {
            String getTextChannelById = plugin.getConfig().getString("Discord.AdminChannel");
            if (getTextChannelById == null) return;

            JDA.jda.getTextChannelById(getTextChannelById).sendMessageEmbeds(embedBuilder.build()).queue();
        } catch (Exception exception) {
            System.out.println("[エラーが発生しました] sendMessageToChannel メソッドを確認してください (PlayerRoleChecker)");
        }
    }

    public static void AddRole(Guild guild, Member member) {
        String roleID = PlayerRoleChecker.plugin.getConfig().getString("Discord.addToRole");
        if (roleID == null) return;
        Role role = guild.getRoleById(roleID);
        if (role == null || member == null) return;
        guild.addRoleToMember(member, role).queue();
    }

    public static void RemoveRole(Guild guild, Member member) {
        String roleID = PlayerRoleChecker.plugin.getConfig().getString("Discord.addToRole");
        if (roleID == null) return;
        Role role = guild.getRoleById(roleID);
        if (role == null || member == null) return;
        guild.removeRoleFromMember(member, role).queue();
    }

    public static EmbedBuilder embedBuilder(String configPath, OffsetDateTime offsetDateTime, String uuid, String discordID) {
        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setColor(OtherUtil.ColorFromString(plugin.getConfig().getString(configPath + ".color")))
                .setTitle(replaceString(plugin.getConfig().getString(configPath + ".title"), uuid, discordID))
                .setDescription(replaceString(plugin.getConfig().getString(configPath + ".description"), uuid, discordID))
                .setThumbnail(plugin.getConfig().getString(configPath + ".image") != null ? replaceString(plugin.getConfig().getString(configPath + ".image"), uuid, discordID) : null)
                .setTimestamp(plugin.getConfig().getBoolean(configPath + ".timestamp") ? offsetDateTime : null);
        return (splitBuilder(embedBuilder, configPath + ".message", uuid, discordID));
    }

    public static EmbedBuilder splitBuilder(EmbedBuilder embedBuilder, String configPath, String uuid, String discordID) {
        plugin.getConfig().getStringList(configPath).forEach(i -> {
            i = replaceString(i, uuid, discordID);
            String[] split = i.split("\\|");
            embedBuilder.addField(split[0], split[1], Boolean.parseBoolean(split[2]));
        });
        return embedBuilder;
    }

    public static String replaceString(String string, String uuid, String discordID) {
        if(string != null) {
            if (uuid != null) string = string.replaceAll("%uuid%", uuid);
            if (discordID != null) string = string.replaceAll("%discordid%", discordID);
        }
        return string;
    }
}
