package net.klnetwork.playerrolechecker.api.discord.data;

import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface CommandMessage {
    String getCommandName();

    //todo: make custom data type!
    void onMessageReceiveEvent(MessageReceivedEvent event);
}
