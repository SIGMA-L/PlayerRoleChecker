package net.klnetwork.playerrolechecker.playerrolechecker.MySQL;

import java.sql.*;

import static net.klnetwork.playerrolechecker.playerrolechecker.PlayerRoleChecker.SQLLocate;

public class SQLite {
    public static boolean CheckCode(Integer code) {
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + SQLLocate);
            statement = connection.createStatement();
            statement.setQueryTimeout(30);
            preparedStatement = connection.prepareStatement("select * from waitverify where code = ?" );
            preparedStatement.setString(1,code.toString());
            resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            if(connection != null && statement != null && preparedStatement != null && resultSet != null){
                try {
                    connection.close();
                    statement.close();
                    preparedStatement.close();
                    resultSet.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        }
        return false;
    }

}
