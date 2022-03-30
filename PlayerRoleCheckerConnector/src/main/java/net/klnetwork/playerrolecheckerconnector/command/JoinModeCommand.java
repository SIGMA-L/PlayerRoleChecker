package net.klnetwork.playerrolecheckerconnector.command;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class JoinModeCommand implements CommandExecutor {

    public static boolean joinMode = true;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        joinMode = !joinMode;
        sender.sendMessage(ChatColor.GREEN + "[PlayerRoleCheckerConnector]" + joinMode + "に変更しました");
    return true;
    }
}
