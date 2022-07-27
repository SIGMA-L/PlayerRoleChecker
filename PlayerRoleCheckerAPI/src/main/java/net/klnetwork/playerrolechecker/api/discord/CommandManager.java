package net.klnetwork.playerrolechecker.api.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.klnetwork.playerrolechecker.api.discord.data.CommandData;
import net.klnetwork.playerrolechecker.api.discord.data.CommandMessage;
import net.klnetwork.playerrolechecker.api.discord.data.CommandSlash;
import net.klnetwork.playerrolechecker.api.utils.CommonUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class CommandManager extends ListenerAdapter {

    private JDA jda;

    private final List<CommandSlash> slashType = new ArrayList<>();
    private final List<CommandMessage> messageType = new ArrayList<>();

    public CommandManager(JDA jda) {
        if (jda != null) {
            this.jda = jda;

            jda.addEventListener(this);
        }
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.isFromGuild() && !event.getAuthor().isBot() && !event.getAuthor().isSystem() && !event.isWebhookMessage()) {
            new Thread(() -> {
                String[] args = event.getMessage()
                        .getContentRaw()
                        .split("\\s+");

                String commandName = args[0];

                List<String> finalArgs = Arrays.stream(args)
                        .filter(d -> commandName != d)
                        .collect(Collectors.toList());

                CommandData data = new CommandData(commandName, finalArgs, event);

                messageType.stream()
                        .filter(name -> (name == null || commandName.equalsIgnoreCase(name.getCommandName()))
                                && CommonUtils.hasPermission(data.getMember(), name.requirePermission())
                                && name.isWork(data))
                        .collect(Collectors.toList())
                        .forEach(message -> {
                            try {
                                message.onMessageReceiveEvent(data);
                            } catch (Exception e) {
                                message.onErrorCaught(data, e);
                            }
                        });
            }).start();
        }
    }



    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.isFromGuild()) {
            final Optional<CommandSlash> commandSlash = slashType.stream()
                    .filter(name -> event.getName().equals(event.getName()))
                    .findFirst();

            commandSlash.ifPresent(slash -> slash
                    .onSlashEvent(event));
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

    public JDA getJDA() {
        return jda;
    }

    public void setJDA(JDA jda) {
        if (this.jda != null) {
            this.jda = jda;

            jda.addEventListener(this);
        }
    }
}