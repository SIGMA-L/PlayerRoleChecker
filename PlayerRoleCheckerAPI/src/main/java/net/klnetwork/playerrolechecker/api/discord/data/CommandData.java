package net.klnetwork.playerrolechecker.api.discord.data;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
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

    public int length() {
        return args.size();
    }

    public Message getMessage() {
        return event.getMessage();
    }

    public String getCommandName() {
        return commandName;
    }

    public TextChannel getTextChannel() {
        return event.getChannel().asTextChannel();
    }

    public Member getMember() {
        return event.getMember();
    }

    public Guild getGuild() {
        return event.getGuild();
    }

    public JDA getJDA() {
        return event.getJDA();
    }

    public MessageReceivedEvent getEvent() {
        return event;
    }
}
