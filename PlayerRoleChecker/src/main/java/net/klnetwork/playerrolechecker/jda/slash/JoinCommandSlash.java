package net.klnetwork.playerrolechecker.jda.slash;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.klnetwork.playerrolechecker.api.discord.data.CommandSlash;
import org.bukkit.plugin.Plugin;

//TODO: IMPL
public class JoinCommandSlash extends CommandSlash {
    public JoinCommandSlash(Plugin plugin) {
        super(plugin);
    }

    @Override
    public String getCommandName() {
        return null;
    }

    @Override
    public String getPath() {
        return null;
    }

    @Override
    public void onSlashEvent(SlashCommandInteractionEvent event) {

    }
}
