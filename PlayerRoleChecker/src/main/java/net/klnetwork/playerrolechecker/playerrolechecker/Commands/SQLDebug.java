package net.klnetwork.playerrolechecker.playerrolechecker.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SQLDebug implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (player.hasPermission("debug")) {
            //Connection connection = "jdbc"
        }
        return false;
    }
}
