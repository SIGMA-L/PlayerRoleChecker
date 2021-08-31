package net.klnetwork.playerrolechecker.playerrolechecker.MySQL;

import java.sql.*;

import static net.klnetwork.playerrolechecker.playerrolechecker.PlayerRoleChecker.SQLLocate;

public class SQLite {
    public static boolean CheckCode(Integer code) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + SQLLocate);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            PreparedStatement preparedStatement = null;
            preparedStatement = connection.prepareStatement("select * from waitverify where code = ?" );
            preparedStatement.setString(1,code.toString());
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

}
