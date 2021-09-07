package net.klnetwork.playerrolechecker.JDA.Events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.klnetwork.playerrolechecker.PlayerRoleChecker;
import net.klnetwork.playerrolechecker.Util.DiscordUtil;
import net.klnetwork.playerrolechecker.Util.SQLUtil;
import net.klnetwork.playerrolechecker.Util.SQLiteUtil;
import org.jetbrains.annotations.NotNull;

import java.awt.*;


@SuppressWarnings("deprecation")
public class VerifyCommand extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (!event.getAuthor().isBot() && event.isFromType(ChannelType.TEXT) && DiscordUtil.ChannelChecker(event.getTextChannel().getId())) {
            String[] args = event.getMessage().getContentRaw().split("\\s+");
            if (args.length == 1) {
                int integer;
                try {
                    integer = Integer.parseInt(args[0]);
                } catch (Exception ignored) {
                    EmbedBuilder embedBuilder = new EmbedBuilder()
                            .setColor(Color.RED)
                            .setTitle("リクエストに失敗しました")
                            .setDescription("理由: 数字ではありません")
                            .setTimestamp(event.getMessage().getTimeCreated());
                    event.getMessage().reply(embedBuilder.build()).queue();
                    return;
                }
                String[] result = SQLiteUtil.getUUIDFromSQLite(Integer.toString(integer));
                if (result == null) {
                    EmbedBuilder embedBuilder = new EmbedBuilder()
                            .setColor(Color.RED)
                            .setTitle("リクエストに失敗しました")
                            .setDescription("理由: 不明な数字です")
                            .setTimestamp(event.getMessage().getTimeCreated());
                    event.getMessage().reply(embedBuilder.build()).queue();
                    return;
                }
                String[] alreadyUUID = SQLUtil.getDiscordFromSQL(result[0]);
                if (alreadyUUID != null) {
                    EmbedBuilder embedBuilder = new EmbedBuilder()
                            .setColor(Color.RED)
                            .setTitle("リクエストに失敗しました")
                            .setDescription("理由: すでに登録されているようです")
                            .addField("uuid:", alreadyUUID[0], false)
                            .addField("discordID:", alreadyUUID[1], false)
                            .setThumbnail("https://crafatar.com/avatars/" + alreadyUUID[0])
                            .setTimestamp(event.getMessage().getTimeCreated());
                    event.getMessage().reply(embedBuilder.build()).queue();
                    return;
                }
                SQLiteUtil.removeSQLite(result[0], result[1]);
                SQLUtil.putSQL(result[0], event.getAuthor().getId());
                
                EmbedBuilder embedBuilder = new EmbedBuilder()
                        .setColor(Color.GREEN)
                        .setTitle("リクエストに成功しました！")
                        .addField("uuid:", result[0], false)
                        .addField("discordID:", event.getAuthor().getId(), false)
                        .setThumbnail("https://crafatar.com/avatars/" + result[0])
                        .setTimestamp(event.getMessage().getTimeCreated());
                event.getMessage().reply(embedBuilder.build()).queue();

                EmbedBuilder sendMessage = new EmbedBuilder()
                        .setTitle("登録が完了したようです！")
                        .addField("UUID:", result[0], false)
                        .addField("DiscordID:", result[1], false)
                        .setThumbnail("https://crafatar.com/avatars/" + result[0])
                        .setTimestamp(event.getMessage().getTimeCreated());

                DiscordUtil.sendMessageToChannel(sendMessage);

                String roleID = PlayerRoleChecker.plugin.getConfig().getString("Discord.addToRole");
                if (roleID == null) return;
                Role role = event.getGuild().getRoleById(roleID);
                if (role == null || event.getMember() == null) return;
                event.getGuild().addRoleToMember(event.getMember(), role).queue();
            } else {
                EmbedBuilder embedBuilder = new EmbedBuilder()
                        .setColor(Color.RED)
                        .setTitle("リクエストに失敗しました")
                        .setDescription("理由: length <= 2")
                        .setTimestamp(event.getMessage().getTimeCreated());
                event.getMessage().reply(embedBuilder.build()).queue();
            }
        }
    }
}
