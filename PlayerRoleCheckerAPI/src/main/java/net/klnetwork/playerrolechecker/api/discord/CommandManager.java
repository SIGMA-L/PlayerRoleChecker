package net.klnetwork.playerrolechecker.api.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
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
                        .filter(name -> equalsCommandName(getCommandName(name), data)
                                && hasPermission(name, data)
                                && (isGlobalCommand(name) || isWorkCommand(name, data))
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

    public String getCommandName(CommandMessage message) {
        if (message.getPlugin() == null || message.getPath() == null) {
            return message.getCommandName();
        }

        String name = message.getPlugin().getConfig().getString(message.getPath() + ".name");

        return name == null ? message.getCommandName() : name;
    }

    public boolean isWorkCommand(CommandMessage message, CommandData data) {
        if (message.getPlugin() == null) {
            return true;
        }

        return message.getPlugin().getConfig().getLong("Discord.ChannelID") == data.getTextChannel().getIdLong();
    }

    public boolean isGlobalCommand(CommandMessage message) {
        if (message.getPlugin() == null || message.getPath() == null) {
            return true;
        }

        return message.getPlugin().getConfig().getBoolean(message.getPath() + ".globalCommand");
    }

    public boolean equalsCommandName(String commandName, CommandData data) {
        if (commandName == null || commandName.isEmpty()) {
            return true;
        }

        return commandName.equalsIgnoreCase(data.getCommandName());
    }

    public boolean hasPermission(CommandMessage message, CommandData data) {
        if (message.getPlugin() == null || message.getPath() == null) {
            return true;
        }

        List<Permission> permissions = new ArrayList<>();

        for (String permission : message.getPlugin().getConfig().getStringList(message.getPath() + ".require-permission")) {
            permissions.add(Permission.valueOf(permission));
        }

        return CommonUtils.hasPermission(data.getMember(), permissions);
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
            jda.addEventListener(this);
        }
        this.jda = jda;
    }
}