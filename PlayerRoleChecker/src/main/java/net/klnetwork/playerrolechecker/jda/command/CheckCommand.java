package net.klnetwork.playerrolechecker.jda.command;

import net.klnetwork.playerrolechecker.api.discord.data.CommandData;
import net.klnetwork.playerrolechecker.api.discord.data.CommandMessage;
import net.klnetwork.playerrolechecker.table.PlayerData;
import org.bukkit.plugin.Plugin;

public class CheckCommand extends CommandMessage {
    public CheckCommand(Plugin plugin) {
        super(plugin);
    }

    @Override
    public String getCommandName() {
        return "!check";
    }

    @Override
    public String getPath() {
        return "CheckCommand";
    }

    @Override
    public boolean isWork(CommandData data) {
        return true;
    }

    @Override
    public void onMessageReceiveEvent(CommandData event) throws Exception {
        final String memberId = event.getArgs().get(0).replaceAll("[^0-9]", "");

        String uuid = PlayerData.getInstance().getUUID(memberId);

        if (uuid != null) {

        }
    }

    @Override
    public void onErrorCaught(CommandData event, Exception exception) {

    }
}