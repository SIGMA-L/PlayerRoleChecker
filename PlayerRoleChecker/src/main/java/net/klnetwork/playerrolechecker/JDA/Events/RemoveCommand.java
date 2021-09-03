package net.klnetwork.playerrolechecker.JDA.Events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.klnetwork.playerrolechecker.PlayerRoleChecker;
import net.klnetwork.playerrolechecker.Util.SQLUtil;
import net.klnetwork.playerrolechecker.Util.UUIDUtil;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

@SuppressWarnings("deprecation")
public class RemoveCommand extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (!event.getAuthor().isBot() && event.isFromType(ChannelType.TEXT)) {
            if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                String[] args = event.getMessage().getContentRaw().split("\\s+");
                if (args.length == 2 && args[0].equals("!remove")) {
                    try {
                        String uuid = UUIDUtil.getUUID(args[1]).toString();
                        String[] result = SQLUtil.getDiscordFromSQL(uuid);
                        if (result == null) {
                            EmbedBuilder embedBuilder = new EmbedBuilder()
                                    .setTitle("エラーが発生しました！")
                                    .setColor(Color.RED)
                                    .setDescription("エラー: このUUIDはSQL内に登録されてないようです")
                                    .addField("追加情報(UUID):", uuid, false)
                                    .setTimestamp(event.getMessage().getTimeCreated());
                            event.getMessage().reply(embedBuilder.build()).queue();
                            return;
                        }
                        SQLUtil.removeSQL(result[0], result[1]);
                        EmbedBuilder embedBuilder = new EmbedBuilder()
                                .setTitle("削除完了！")
                                .setColor(Color.GREEN)
                                .addField("UUID", result[0], false)
                                .addField("Discord:", result[1], false)
                                .setThumbnail("https://crafatar.com/avatars/" + result[0])
                                .setTimestamp(event.getMessage().getTimeCreated());
                        event.getMessage().reply(embedBuilder.build()).queue();


                        String roleID = PlayerRoleChecker.plugin.getConfig().getString("Discord.addToRole");
                        if (roleID == null) return;
                        Role role = event.getGuild().getRoleById(roleID);
                        if (role == null || event.getMember() == null) return;
                        event.getGuild().removeRoleFromMember(event.getMember(), role).queue();
                    } catch (Exception exception) {
                        EmbedBuilder embedBuilder = new EmbedBuilder()
                                .setTitle("エラーが発生しました！")
                                .setColor(Color.RED)
                                .setDescription("エラー: 内部でエラーが発生したようです。")
                                .setTimestamp(event.getMessage().getTimeCreated());
                        event.getMessage().reply(embedBuilder.build()).queue();
                    }
                }
            }
        }
    }
}
