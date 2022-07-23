package net.klnetwork.playerrolechecker.api.discord.data;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class CommandData {

    private final List<String> args;
    private final String commandName;

    private final MessageReceivedEvent event;

    public CommandData(String commandName, List<String> args, MessageReceivedEvent event) {
        this.commandName = commandName;
        this.args = args;

        this.event = event;
    }

    public List<String> getArgs() {
        return args;
    }

    public String getCommandName() {
        return commandName;
    }

    public Member getMember() {
        return event.getMember();
    }

    public Guild getGuild() {
        return event.getGuild();
    }

    public MessageReceivedEvent getEvent() {
        return event;
    }
}
