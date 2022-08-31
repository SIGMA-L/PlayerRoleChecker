package net.klnetwork.playerrolechecker.api.discord.data;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.bukkit.plugin.Plugin;

public abstract class CommandSlash extends Command {
    public CommandSlash(Plugin plugin) {
        super(plugin);
    }

    //todo: make custom data type!
    public abstract void onSlashEvent(SlashCommandInteractionEvent event);
}