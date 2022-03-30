package net.klnetwork.playerrolecheckerconnector.jda;

import net.dv8tion.jda.api.JDABuilder;
import net.klnetwork.playerrolecheckerconnector.PlayerRoleCheckerConnector;

import javax.security.auth.login.LoginException;

public class JDA {
    public static net.dv8tion.jda.api.JDA INSTANCE;

    public static void init() {
        try {
            INSTANCE = JDABuilder.createDefault(PlayerRoleCheckerConnector.INSTANCE.getConfig().getString("Discord.DiscordToken"))
                    .build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
}
