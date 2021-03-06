package net.klnetwork.playerrolechecker.jda;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.klnetwork.playerrolechecker.jda.event.ForceJoinCommand;
import net.klnetwork.playerrolechecker.jda.event.RemoveCommand;
import net.klnetwork.playerrolechecker.jda.event.JoinCommand;
import net.klnetwork.playerrolechecker.PlayerRoleChecker;

import javax.security.auth.login.LoginException;

public class JDA {

    public static net.dv8tion.jda.api.JDA INSTANCE;

    public static void init() {
        try {
            INSTANCE = JDABuilder.createDefault(PlayerRoleChecker.INSTANCE.getConfig().getString("Discord.DiscordToken"))
                    .setStatus(OnlineStatus.ONLINE)
                    .addEventListeners(new JoinCommand(), new RemoveCommand(), new ForceJoinCommand())
                    .build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
}
