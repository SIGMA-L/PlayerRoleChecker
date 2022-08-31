package net.klnetwork.playerrolechecker.api.data;

import net.dv8tion.jda.api.JDA;
import net.klnetwork.playerrolechecker.api.discord.CommandManager;

public interface APIHookCustom extends APIHook {
    CommandManager getCommandManager();

    JoinManager getJoinManager();

    JDA getJDA();

    PlayerDataTable getPlayerData();

    void setPlayerData(PlayerDataTable table);
}
