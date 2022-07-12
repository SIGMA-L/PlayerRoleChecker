package net.klnetwork.playerrolechecker.table;

import net.klnetwork.playerrolechecker.PlayerRoleChecker;
import net.klnetwork.playerrolechecker.api.data.PlayerDataTable;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.*;
import java.util.UUID;
import java.util.function.Consumer;

public class PlayerData implements PlayerDataTable {

    private static PlayerDataTable table;

    private long lastConnection;
    private Connection connection;

    public static PlayerDataTable getInstance() {
        if (table == null) {
            table = new PlayerData();
        }

        return table;
    }

    public static void setInstance(PlayerDataTable data) {
        table = data;
    }

    @Override
    public void asyncUUID(String discordId, Consumer<String> uuid) {
        Bukkit.getScheduler().runTaskAsynchronously(PlayerRoleChecker.INSTANCE, () -> uuid.accept(getUUID(discordId)));
    }

    @Override
    public void asyncDiscordId(UUID uuid, Consumer<String> discordId) {
        asyncDiscordId(uuid.toString(), discordId);
    }

    @Override
    public void asyncDiscordId(String uuid, Consumer<String> discordId) {
        Bukkit.getScheduler().runTaskAsynchronously(PlayerRoleChecker.INSTANCE, () -> discordId.accept(getDiscordId(uuid)));
    }

    @Override
    public String getDiscordId(UUID uuid) {
        return getDiscordId(uuid.toString());
    }

    @Override
    public String getDiscordId(String uuid) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = getConnection().prepareStatement("select * from verifyplayer where uuid = ?");
            preparedStatement.setString(1, uuid);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString(2);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public String getUUID(String discordId) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = getConnection().prepareStatement("select * from verifyplayer where discord = ?");
            preparedStatement.setString(1, discordId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public void put(UUID uuid, String discordId) {
        put(uuid.toString(), discordId);
    }

    @Override
    public void put(String uuid, String discordId) {
        Bukkit.getScheduler().runTaskAsynchronously(PlayerRoleChecker.INSTANCE, () -> {
            try {
                PreparedStatement preparedStatement = getConnection().prepareStatement("insert into verifyplayer values (?,?)");
                preparedStatement.setString(1, uuid);
                preparedStatement.setString(2, discordId);
                preparedStatement.execute();

                preparedStatement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    @Override
    public void remove(UUID uuid, String discordId) {
        remove(uuid.toString(), discordId);
    }

    @Override
    public void remove(String uuid, String discordId) {
        Bukkit.getScheduler().runTaskAsynchronously(PlayerRoleChecker.INSTANCE, () -> {
            try {
                PreparedStatement preparedStatement = getConnection().prepareStatement("delete from verifyplayer where uuid = ? and discord = ?");
                preparedStatement.setString(1, uuid);
                preparedStatement.setString(2, discordId);
                preparedStatement.execute();

                preparedStatement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed() || isConnectionDead()) {

            //todo: add to utils
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            FileConfiguration config = PlayerRoleChecker.INSTANCE.getConfig();

            connection = DriverManager.getConnection("jdbc:mysql://" + config.getString("MySQL.Server") + ":" + config.getInt("MySQL.Port") + "/" + config.getString("MySQL.Database") + config.getString("MySQL.Option"), config.getString("MySQL.Username"), config.getString("MySQL.Password"));
        }
        return connection;
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public long getLastConnection() {
        return this.lastConnection;
    }

    @Override
    public void setLastConnection(long lastConnection) {
        this.lastConnection = lastConnection;
    }

    @Override
    public boolean isConnectionDead() throws SQLException {
        final long now = System.currentTimeMillis();

        if (now - lastConnection > 900000) {
            lastConnection = now;
            return !connection.isValid(1);
        }
        return false;
    }
}