package net.klnetwork.playerrolecheckerconnector.Command;

import net.klnetwork.playerrolecheckerconnector.Util.SQLiteUtil;
import net.klnetwork.playerrolecheckerconnector.Util.OtherUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RemoveBypass implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            try {
                String uuid = OtherUtil.getUUID(args[0]).toString();
                String result = SQLiteUtil.getUUIDFromSQLite(uuid);
                if(result == null) {
                    sender.sendMessage(ChatColor.RED + "処理に失敗しました！ data=登録されていないようです (" + uuid + ")");
                    return true;
                }
                SQLiteUtil.removeSQLite(uuid);
                sender.sendMessage(ChatColor.GREEN + "成功しました！ data=" + uuid);
            } catch (Exception e) {
                sender.sendMessage(ChatColor.RED + "エラーが発生しました プレイヤー名を入力してください");
            }

        }
        return true;
    }
}

