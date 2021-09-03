package net.klnetwork.playerrolechecker.JDA.Events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.klnetwork.playerrolechecker.PlayerRoleChecker;
import net.klnetwork.playerrolechecker.Util.DiscordUtil;
import net.klnetwork.playerrolechecker.Util.SQLUtil;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static net.klnetwork.playerrolechecker.Util.SQLUtil.getSQLConnection;

public class RemoveRoleEvent extends ListenerAdapter {
    @Override
    public void onGuildMemberRoleRemove(@NotNull GuildMemberRoleRemoveEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(PlayerRoleChecker.plugin,() -> {
            for(Role role : event.getRoles()){
                if(role.getId().equals(PlayerRoleChecker.plugin.getConfig().getString("Discord.addToRole"))) {
                        try {
                            PreparedStatement preparedStatement = getSQLConnection().prepareStatement("select * from verifyplayer where discord = ?");
                            preparedStatement.setString(1, event.getMember().getId());

                            ResultSet resultSet = preparedStatement.executeQuery();
                            while (resultSet.next()){

                                EmbedBuilder sendMessage = new EmbedBuilder()
                                        .setTitle("ロールが削除されました！")
                                        .setColor(Color.RED)
                                        .setDescription("以下のプレイヤーのロールが消されたため、データベースから削除されました！")
                                        .addField("UUID:", resultSet.getString(1), false)
                                        .addField("DiscordID:", resultSet.getString(2), false)
                                        .setThumbnail("https://crafatar.com/avatars/" + resultSet.getString(1));

                                SQLUtil.removeSQL(resultSet.getString(1),resultSet.getString(2));
                                DiscordUtil.sendMessageToChannel(sendMessage);
                            }

                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                    }
                }
            }
        });
    }
}
