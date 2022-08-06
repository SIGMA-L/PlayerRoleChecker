package net.klnetwork.playerrolechecker.api.data;

import net.dv8tion.jda.api.JDA;
import net.klnetwork.playerrolechecker.api.discord.CommandManager;
import net.klnetwork.playerrolechecker.api.enums.HookedAPIType;
import org.bukkit.plugin.Plugin;

public interface APIHook {
    Plugin getPlugin();

    JDA getJDA();

    PlayerDataTable getPlayerData();

    void setPlayerData(PlayerDataTable table);

    JoinManager getJoinManager();

    CommandManager getCommandManager();

    HookedAPIType getType();
}