package net.klnetwork.playerrolecheckerconnector.command;

import net.klnetwork.playerrolecheckerconnector.PlayerRoleCheckerConnector;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class JoinModeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        final boolean joinMode = !PlayerRoleCheckerConnector.INSTANCE.getConfigManager().isJoinMode();

        PlayerRoleCheckerConnector.INSTANCE.getConfigManager().setJoinMode(joinMode);

        sender.sendMessage(ChatColor.GREEN + "[PlayerRoleCheckerConnector]" + joinMode + "に変更しました");
        return true;
    }
}
