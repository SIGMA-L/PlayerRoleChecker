package net.klnetwork.playerrolechecker.api.discord.data;

import net.dv8tion.jda.api.Permission;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

public abstract class CommandMessage {
    public abstract String getCommandName();

    public abstract Permission[] requirePermission();

    public abstract boolean isWork(CommandData data);

    //todo: make custom data type!
    public abstract void onMessageReceiveEvent(CommandData event) throws Exception;

    public abstract void onErrorCaught(CommandData event, Exception exception);

    public <T> T callEvent(T event) {
        if (event instanceof Event) {
            Bukkit.getPluginManager().callEvent((Event) event);
        }

        return event;
    }

}
