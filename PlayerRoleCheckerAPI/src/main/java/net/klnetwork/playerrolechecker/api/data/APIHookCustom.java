package net.klnetwork.playerrolechecker.api.data;

import net.dv8tion.jda.api.JDA;
import net.klnetwork.playerrolechecker.api.discord.CommandManager;
import org.jetbrains.annotations.Nullable;

public interface APIHookCustom extends APIHook {
    @Nullable
    CommandManager getCommandManager();

    JDA getJDA();

    PlayerDataTable getPlayerData();

    void setPlayerData(PlayerDataTable table);
}
