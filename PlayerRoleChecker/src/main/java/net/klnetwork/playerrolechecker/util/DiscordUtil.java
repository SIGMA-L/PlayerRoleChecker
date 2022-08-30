package net.klnetwork.playerrolechecker.util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import net.dv8tion.jda.api.utils.FileUpload;
import net.klnetwork.playerrolechecker.PlayerRoleChecker;
import net.klnetwork.playerrolechecker.api.utils.CommonUtils;

import java.awt.image.BufferedImage;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.UUID;

//todo: recode!
public class DiscordUtil {

    public static void sendMessage(MessageEmbed embed, BufferedImage image) {
        String textChannel = PlayerRoleChecker.INSTANCE.getConfig().getString("Discord.AdminChannel");

        if (textChannel == null) {
            return;
        }

        MessageCreateAction action = PlayerRoleChecker.INSTANCE.getJDA().getTextChannelById(textChannel).sendMessageEmbeds(embed);

        if (image != null) {
            action.addFiles(FileUpload.fromData(CommonUtils.toByteArray(image), "user.png"));
        }

        action.queue();
    }

    public static Role getRole() {
        final long id = PlayerRoleChecker.INSTANCE.getConfig().getLong("Discord.addToRole");

        if (id == 0L) {
            return null;
        }

        return PlayerRoleChecker.INSTANCE.getJDA().getRoleById(id);
    }

    public static void addRole(Member member) {
        final Role role = getRole();

        if (role != null) {
            member.getGuild().addRoleToMember(member, role).queue();
        }
    }


    public static void removeRole(Member member) {
        final Role role = getRole();

        if (role != null) {
            member.getGuild().removeRoleFromMember(member, role).queue();
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

    public static String BEDROCK_SUFFIX = "-bedrock";

    public static MessageEmbed createEmbedMessage(String path, UUID uuid, String discordId, boolean bedrock) {
        return createEmbedMessage(bedrock ? path + BEDROCK_SUFFIX : path, uuid.toString(), discordId);
    }

    public static MessageEmbed createEmbedMessage(String path, String uuid, String discordId, boolean bedrock) {
        return createEmbedMessage(bedrock ? path + BEDROCK_SUFFIX : path, uuid, discordId);
    }

    //new version of embedBuilder
    private static MessageEmbed createEmbedMessage(String path, String uuid, String discordId) {
        EmbedBuilder builder = new EmbedBuilder()
                .setColor(CommonUtils.getColor(PlayerRoleChecker.INSTANCE.getConfig().getString(path + ".color")))
                .setTitle(addString(PlayerRoleChecker.INSTANCE.getConfig().getString(path + ".title"), uuid, discordId))
                .setDescription(addString(PlayerRoleChecker.INSTANCE.getConfig().getString(path + ".description"), uuid, discordId))
                .setThumbnail(addString(PlayerRoleChecker.INSTANCE.getConfig().getString(path + ".image"), uuid, discordId))
                .setTimestamp(PlayerRoleChecker.INSTANCE.getConfig().getBoolean(path + ".timestamp") ? OffsetDateTime.now() : null);

        return (splitBuilder(builder, path + ".message", uuid,discordId)).build();
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
        if (uuid != null) {
            target = target.replaceAll("%uuid%", uuid);
            target = target.replaceAll("%xuid%", ((Long) CommonUtils.getXUID(UUID.fromString(uuid))).toString());
        }
        if (discordId != null) {
            target = target.replaceAll("%discordid%", discordId);
        }

        return target;
    }
}
