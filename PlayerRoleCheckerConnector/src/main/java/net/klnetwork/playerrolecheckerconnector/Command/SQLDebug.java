package net.klnetwork.playerrolecheckerconnector.Command;

import net.klnetwork.playerrolecheckerconnector.Util.SQLUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLDebug implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            try {
                PreparedStatement preparedStatement = SQLUtil.getSQLConnection().prepareStatement("select * from verifyplayer");
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()) System.out.println("debug:" + resultSet.getString(1) + resultSet.getString(2));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (String arg : args) {
                stringBuilder.append(arg).append(" ");
            }
            sender.sendMessage(stringBuilder.toString());
        }

        return false;
    }
}
