package net.klnetwork.playerrolechecker.util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.klnetwork.playerrolechecker.PlayerRoleChecker;
import net.klnetwork.playerrolechecker.jda.JDA;

import java.time.OffsetDateTime;

public class DiscordUtil {
    public static boolean channelChecker(String channelId) {
        if (PlayerRoleChecker.INSTANCE.getConfig().getString("Discord.ChannelID") == null) return true;
        return channelId.equals(PlayerRoleChecker.INSTANCE.getConfig().getString("Discord.ChannelID"));
    }

    public static boolean limitChecker(String channelId) {
        return !PlayerRoleChecker.INSTANCE.getConfig().getBoolean("Discord.limitCommand") || channelChecker(channelId);
    }

    public static void sendMessageToChannel(EmbedBuilder embedBuilder) {
        try {
            String getTextChannelById = PlayerRoleChecker.INSTANCE.getConfig().getString("Discord.AdminChannel");
            if (getTextChannelById == null) return;

            JDA.INSTANCE.getTextChannelById(getTextChannelById).sendMessageEmbeds(embedBuilder.build()).queue();
        } catch (Exception exception) {
            System.out.println("[エラーが発生しました] sendMessageToChannel メソッドを確認してください (PlayerRoleChecker)");
        }
    }

    public static void addRole(Guild guild, Member member) {
        String roleID = PlayerRoleChecker.INSTANCE.getConfig().getString("Discord.addToRole");
        if (roleID == null) return;
        Role role = guild.getRoleById(roleID);
        if (role == null || member == null) return;
        guild.addRoleToMember(member, role).queue();
    }

    public static void removeRole(Guild guild, Member member) {
        String roleID = PlayerRoleChecker.INSTANCE.getConfig().getString("Discord.addToRole");
        if (roleID == null) return;
        Role role = guild.getRoleById(roleID);
        if (role == null || member == null) return;
        guild.removeRoleFromMember(member, role).queue();
    }

    public static EmbedBuilder embedBuilder(String configPath, OffsetDateTime offsetDateTime, Object uuid, String discordId) {
        return embedBuilder(configPath, offsetDateTime, String.valueOf(uuid), discordId);
    }

    public static EmbedBuilder embedBuilder(String configPath, OffsetDateTime offsetDateTime, String uuid, String discordID) {
        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setColor(OtherUtil.ColorFromString(PlayerRoleChecker.INSTANCE.getConfig().getString(configPath + ".color")))
                .setTitle(replaceString(PlayerRoleChecker.INSTANCE.getConfig().getString(configPath + ".title"), uuid, discordID))
                .setDescription(replaceString(PlayerRoleChecker.INSTANCE.getConfig().getString(configPath + ".description"), uuid, discordID))
                .setThumbnail(PlayerRoleChecker.INSTANCE.getConfig().getString(configPath + ".image") != null ? replaceString(PlayerRoleChecker.INSTANCE.getConfig().getString(configPath + ".image"), uuid, discordID) : null)
                .setTimestamp(PlayerRoleChecker.INSTANCE.getConfig().getBoolean(configPath + ".timestamp") ? offsetDateTime : null);
        return (splitBuilder(embedBuilder, configPath + ".message", uuid, discordID));
    }

    public static EmbedBuilder splitBuilder(EmbedBuilder embedBuilder, String configPath, String uuid, String discordID) {
        PlayerRoleChecker.INSTANCE.getConfig().getStringList(configPath).forEach(i -> {
            String[] split = replaceString(i, uuid, discordID).split("\\|");
            embedBuilder.addField(split[0], split[1], Boolean.parseBoolean(split[2]));
        });
        return embedBuilder;
    }

    public static String replaceString(String string, String uuid, String discordID) {
        if (string != null) {
            if (uuid != null) string = string.replaceAll("%uuid%", uuid);
            if (discordID != null) string = string.replaceAll("%discordid%", discordID);
        }
        return string;
    }
}
