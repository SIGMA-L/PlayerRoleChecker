package net.klnetwork.playerrolechecker.api.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.klnetwork.playerrolechecker.api.discord.data.CommandMessage;
import net.klnetwork.playerrolechecker.api.discord.data.CommandSlash;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class CommandManager extends ListenerAdapter {

    private JDA jda;

    private final List<CommandSlash> slashType = new ArrayList<>();
    private final List<CommandMessage> messageType = new ArrayList<>();

    public CommandManager(JDA jda) {
        this.jda = jda;

        jda.addEventListener(this);
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.isFromGuild()) {

        }
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.isFromGuild()) {
        }
    }

    public void register(Object obj) {
        if (obj instanceof CommandSlash) {
            slashType.add((CommandSlash) obj);
        } else if (obj instanceof CommandMessage) {
            messageType.add((CommandMessage) obj);
        }
    }

    public List<CommandSlash> getSlashType() {
        return slashType;
    }

    public List<CommandMessage> getMessageType() {
        return messageType;
    }
}
