package net.klnetwork.playerrolechecker.api.discord.data;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public interface CommandSlash {
    String getCommandName();

    //todo: make custom data type!
    void onSlashEvent(SlashCommandInteractionEvent event);
}