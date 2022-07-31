package net.klnetwork.playerrolechecker.util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.klnetwork.playerrolechecker.PlayerRoleChecker;
import net.klnetwork.playerrolechecker.api.utils.CommonUtils;
import net.klnetwork.playerrolechecker.jda.JDA;

import java.time.OffsetDateTime;
import java.util.Arrays;

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
            guild.addRoleToMember(member, guild.getRoleById(PlayerRoleChecker.INSTANCE.getConfig().getLong("Discord.addToRole")))
                    .queue();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void removeRole(Guild guild, Member member) {
        try {
            guild.addRoleToMember(member, guild.getRoleById(PlayerRoleChecker.INSTANCE.getConfig().getLong("Discord.addToRole")))
                    .queue();
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
                .setTitle(addString(PlayerRoleChecker.INSTANCE.getConfig().getString(configPath + ".title"), uuid, discordID))
                .setDescription(addString(PlayerRoleChecker.INSTANCE.getConfig().getString(configPath + ".description"), uuid, discordID))
                .setThumbnail(PlayerRoleChecker.INSTANCE.getConfig().getString(configPath + ".image") != null ? addString(PlayerRoleChecker.INSTANCE.getConfig().getString(configPath + ".image"), uuid, discordID) : null)
                .setTimestamp(PlayerRoleChecker.INSTANCE.getConfig().getBoolean(configPath + ".timestamp") ? offsetDateTime : null);
        return (splitBuilder(embedBuilder, configPath + ".message", uuid, discordID));
    }

    public static EmbedBuilder splitBuilder(EmbedBuilder embedBuilder, String configPath, String uuid, String discordId) {
        for (String c : PlayerRoleChecker.INSTANCE.getConfig().getStringList(configPath)) {
            String[] strings = addString(c, uuid, discordId).split("\\|", 3);

            if (strings.length != 3) {
                throw new IllegalStateException("Illegal format=" + Arrays.toString(strings));
            }
            embedBuilder.addField(strings[0], strings[1], Boolean.parseBoolean(strings[2]));
        }
        return embedBuilder;
    }

    public static String addString(String target, String uuid, String discordId) {
        if (target == null) {
            return null;
        }
        if (uuid != null)
            target = target.replaceAll("%uuid%", uuid);
        if (discordId != null)
            target = target.replaceAll("%discordid%", discordId);

        return target;
    }
}
