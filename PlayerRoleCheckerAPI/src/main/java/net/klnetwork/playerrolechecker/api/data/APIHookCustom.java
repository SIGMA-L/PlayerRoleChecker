package net.klnetwork.playerrolechecker.api.data;

import net.dv8tion.jda.api.JDA;
import net.klnetwork.playerrolechecker.api.discord.CommandManager;

public interface APIHookCustom extends APIHook {
    /**
     * @return Discordのコマンドのインスタンスを返します
     *
     * @implNote
     * <br>Discordのコマンドのインスタンスを返します
     * <br>自分のコマンドを登録することやコマンドを削除することが可能
     *
     * @see CommandManager
     */
    CommandManager getCommandManager();

    /**
     * @return <a href="https://github.com/DV8FromTheWorld/JDA">Java Discord API</a> のインスタンスを返します
     *
     * @see <a href="https://github.com/DV8FromTheWorld/JDA">Java Discord API</a>
     * @see JDA
     */
    JDA getJDA();

    PlayerDataTable getPlayerData();

    void setPlayerData(PlayerDataTable table);
}
