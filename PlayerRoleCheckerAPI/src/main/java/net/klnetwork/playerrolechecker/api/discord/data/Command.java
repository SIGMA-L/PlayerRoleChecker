package net.klnetwork.playerrolechecker.api.discord.data;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.klnetwork.playerrolechecker.api.utils.CommonUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public abstract class Command {
    private final Plugin plugin;

    public Command(Plugin plugin) {
        this.plugin = plugin;
    }

    public <T> T callEvent(T event) {
        if (event instanceof Event) {
            Bukkit.getPluginManager().callEvent((Event) event);
        }

        return event;
    }

    /**
     * @return Config Path
     */
    public abstract String getPath();

    public abstract String getCommandName();

    public String selectCommandName() {
        if (plugin == null || getPath() == null) {
            return getCommandName();
        }

        String name = plugin.getConfig().getString(getPath() + ".name");

        return name != null ? name : getCommandName();
    }

    public boolean isWorkCommand(CommandData data) {
        if (plugin == null) {
            return true;
        }

        return plugin.getConfig().getLong("Discord.ChannelID") == data.getTextChannel().getIdLong();
    }

    public boolean isGlobalCommand() {
        if (plugin == null || getPath() == null) {
            return true;
        }

        return plugin.getConfig().getBoolean(getPath() + ".globalCommand");
    }

    public boolean hasPermission(Member member) {
        if (plugin == null || getPath() == null) {
            return true;
        }

        List<Permission> permissions = new ArrayList<>();

        for (String permission : plugin.getConfig().getStringList(getPath() + ".require-permission")) {
            permissions.add(Permission.valueOf(permission));
        }

        return CommonUtils.hasPermission(member, permissions);
    }

    public Plugin getPlugin() {
        return plugin;
    }
}
