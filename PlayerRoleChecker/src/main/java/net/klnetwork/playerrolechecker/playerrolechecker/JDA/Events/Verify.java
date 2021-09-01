package net.klnetwork.playerrolechecker.playerrolechecker.JDA.Events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.klnetwork.playerrolechecker.playerrolechecker.Util.DiscordUtil;
import net.klnetwork.playerrolechecker.playerrolechecker.Util.SQLUtil;
import net.klnetwork.playerrolechecker.playerrolechecker.Util.SQLiteUtil;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

@SuppressWarnings("deprecation")
public class Verify extends ListenerAdapter {
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
                            .addField("理由:", "数字ではありません", false);
                    event.getMessage().reply(embedBuilder.build()).queue();
                    return;
                }
                String[] result = SQLiteUtil.getUUIDFromSQLite(Integer.toString(integer));
                if (result == null) {
                    EmbedBuilder embedBuilder = new EmbedBuilder()
                            .setColor(Color.RED)
                            .setTitle("リクエストに失敗しました")
                            .addField("理由:", "不明な数字です", false);
                    event.getMessage().reply(embedBuilder.build()).queue();
                    return;
                }
                String[] alreadyUUID = SQLUtil.getDiscordFromSQL(result[0]);
                if(alreadyUUID != null){
                    EmbedBuilder embedBuilder = new EmbedBuilder()
                            .setColor(Color.RED)
                            .setTitle("リクエストに失敗しました")
                            .addField("理由:", "すでに登録されているようです ", false)
                            .addField("uuid:", alreadyUUID[0],true)
                            .addField("discordID:", alreadyUUID[1],true);
                    event.getMessage().reply(embedBuilder.build()).queue();
                    return;
                }
                SQLiteUtil.removeSQLite(result[0],result[1]);
                SQLUtil.putSQL(result[0], event.getAuthor().getId());
                EmbedBuilder embedBuilder = new EmbedBuilder()
                        .setColor(Color.GREEN)
                        .setTitle("リクエストに成功しました！")
                        .addField("uuid:", result[0], true)
                        .addField("discordID:", event.getAuthor().getId(), true);
                event.getMessage().reply(embedBuilder.build()).queue();
            } else {
                EmbedBuilder embedBuilder = new EmbedBuilder()
                        .setColor(Color.RED)
                        .setTitle("リクエストに失敗しました")
                        .addField("理由:", "length <= 2", false);
                event.getMessage().reply(embedBuilder.build()).queue();
            }
        }
    }
}
