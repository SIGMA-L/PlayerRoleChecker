package net.klnetwork.playerrolechecker.util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.klnetwork.playerrolechecker.PlayerRoleChecker;
import net.klnetwork.playerrolechecker.api.utils.CommonUtils;
import net.klnetwork.playerrolechecker.jda.JDA;

import java.time.OffsetDateTime;

//todo: recode!
public class DiscordUtil {

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
        try {
            guild.addRoleToMember(member, guild.getRoleById(PlayerRoleChecker.INSTANCE.getConfig().getLong("Discord.addToRole")));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void removeRole(Guild guild, Member member) {
        try {
            guild.addRoleToMember(member, guild.getRoleById(PlayerRoleChecker.INSTANCE.getConfig().getLong("Discord.addToRole")));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static EmbedBuilder embedBuilder(String configPath, OffsetDateTime offsetDateTime, Object uuid, String discordId) {
        return embedBuilder(configPath, offsetDateTime, uuid == null ? null : String.valueOf(uuid), discordId);
    }

    public static EmbedBuilder embedBuilder(String configPath, OffsetDateTime offsetDateTime, String uuid, String discordID) {
        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setColor(CommonUtils.getColor(PlayerRoleChecker.INSTANCE.getConfig().getString(configPath + ".color")))
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
