package net.klnetwork.playerrolecheckerconnector.command;

import net.klnetwork.playerrolecheckerconnector.util.OtherUtil;
import net.klnetwork.playerrolecheckerconnector.util.SQLiteUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class RemoveBypassCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            try {
                UUID uuid = OtherUtil.getUUID(args[0]);
                String result = SQLiteUtil.getUUIDFromSQLite(uuid);

                if (result == null) {
                    sender.sendMessage(ChatColor.RED + "処理に失敗しました！ 登録されていないようです！ (" + args[0] + ")");
                } else {
                    SQLiteUtil.removeSQLite(uuid);
                    sender.sendMessage(ChatColor.GREEN + "成功しました！ (" + uuid + ")");
                }
            } catch (Exception e) {
                sender.sendMessage(ChatColor.RED + "エラーが発生しました プレイヤー名を入力してください");
            }
        } else if (args.length > 1 && args[0].equalsIgnoreCase("force")) {
            if (SQLiteUtil.getUUIDFromSQLite(args[1]) == null) {
                sender.sendMessage(ChatColor.RED + "処理に失敗しました！ 登録されていないようです (" + args[1] + ")");
            } else {
                SQLiteUtil.removeSQLite(args[1]);
                sender.sendMessage(ChatColor.GREEN + "成功しました！ (" + args[1] + ")");
            }
        }

        return true;
    }
}

