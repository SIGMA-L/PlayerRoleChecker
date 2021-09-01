package net.klnetwork.playerrolecheckerconnector.playerrolecheckerconnector.JDA;

import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

import static net.klnetwork.playerrolecheckerconnector.playerrolecheckerconnector.PlayerRoleCheckerConnector.plugin;

public class JDA {
    public static net.dv8tion.jda.api.JDA jda;

    public static void init() {
        try {
            jda = JDABuilder.createDefault(plugin.getConfig().getString("Discord.DiscordToken"))
                    .build();
        } catch (
                LoginException e) {
            e.printStackTrace();
        }
    }
}
