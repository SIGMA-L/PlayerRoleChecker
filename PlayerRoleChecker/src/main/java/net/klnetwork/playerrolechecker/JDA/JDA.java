package net.klnetwork.playerrolechecker.JDA;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.klnetwork.playerrolechecker.JDA.Events.ForceVerifyCommand;
import net.klnetwork.playerrolechecker.JDA.Events.RemoveCommand;
import net.klnetwork.playerrolechecker.JDA.Events.VerifyCommand;

import javax.security.auth.login.LoginException;

import static net.klnetwork.playerrolechecker.PlayerRoleChecker.plugin;

public class JDA {

    public static net.dv8tion.jda.api.JDA jda;

    public static void init(){
        try {
            jda = JDABuilder.createDefault(plugin.getConfig().getString("Discord.DiscordToken"))
                    .setStatus(OnlineStatus.ONLINE)
                    .addEventListeners(new VerifyCommand(),new RemoveCommand(), new ForceVerifyCommand())
                    .build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
}
