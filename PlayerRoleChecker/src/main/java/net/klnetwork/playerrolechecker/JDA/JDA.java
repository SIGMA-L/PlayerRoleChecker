package net.klnetwork.playerrolechecker.JDA;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.klnetwork.playerrolechecker.JDA.Events.ForceJoinCommand;
import net.klnetwork.playerrolechecker.JDA.Events.RemoveCommand;
import net.klnetwork.playerrolechecker.JDA.Events.JoinCommand;

import javax.security.auth.login.LoginException;

import static net.klnetwork.playerrolechecker.PlayerRoleChecker.plugin;

public class JDA {

    public static net.dv8tion.jda.api.JDA jda;

    public static void init(){
        try {
            jda = JDABuilder.createDefault(plugin.getConfig().getString("Discord.DiscordToken"))
                    .setStatus(OnlineStatus.ONLINE)
                    .addEventListeners(new JoinCommand(),new RemoveCommand(), new ForceJoinCommand())
                    .build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
}
