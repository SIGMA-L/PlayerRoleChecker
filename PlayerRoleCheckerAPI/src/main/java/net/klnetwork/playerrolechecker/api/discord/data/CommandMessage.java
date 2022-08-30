package net.klnetwork.playerrolechecker.api.discord.data;

import org.bukkit.plugin.Plugin;

public abstract class CommandMessage extends Command {
    public CommandMessage(Plugin plugin) {
        super(plugin);
    }

    public abstract boolean isWork(CommandData data);

    public abstract void onMessageReceiveEvent(CommandData event) throws Exception;

    public abstract void onErrorCaught(CommandData event, Exception exception);
}