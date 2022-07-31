package net.klnetwork.playerrolechecker.table;

import net.klnetwork.playerrolechecker.PlayerRoleChecker;

import net.klnetwork.playerrolechecker.api.data.PlayerData;
import net.klnetwork.playerrolechecker.api.enums.SQLType;
import net.klnetwork.playerrolechecker.api.data.PlayerDataTable;
import net.klnetwork.playerrolechecker.api.utils.CommonUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.*;
import java.util.UUID;
import java.util.function.Consumer;

public class PlayerDataSQL extends PlayerDataTable {

    private static PlayerDataTable table;

    private SQLType type;

    public static PlayerDataTable getInstance() {
        if (table == null) {
            table = new PlayerDataSQL();
        }

        return table;
    }

    public static void setInstance(PlayerDataTable data) {
        table = data;
    }

    @Override
    public void asyncUUID(String discordId, Consumer<PlayerData> uuid) {
        Bukkit.getScheduler().runTaskAsynchronously(PlayerRoleChecker.INSTANCE, () -> uuid.accept(getUUID(discordId)));
    }

    @Override
    public void asyncDiscordId(UUID uuid, Consumer<PlayerData> discordId) {
        asyncDiscordId(uuid.toString(), discordId);
    }

    @Override
    public void asyncDiscordId(String uuid, Consumer<PlayerData> discordId) {
        Bukkit.getScheduler().runTaskAsynchronously(PlayerRoleChecker.INSTANCE, () -> discordId.accept(getDiscordId(uuid)));
    }

    @Override
    public PlayerData getDiscordId(UUID uuid) {
        return getDiscordId(uuid.toString());
    }

    @Override
    public PlayerData getDiscordId(String uuid) {
        PreparedStatement statement = null;

        try {
            statement = getConnection().prepareStatement("select * from verifyplayer where uuid = ?");
            statement.setString(1, uuid);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new PlayerData(resultSet.getString(1), resultSet.getString(2), resultSet.getBoolean(3));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            CommonUtils.close(statement);
        }
        return null;
    }

    @Override
    public PlayerData getUUID(String discordId) {
        PreparedStatement statement = null;

        try {
            statement = getConnection().prepareStatement("select * from verifyplayer where discord = ?");
            statement.setString(1, discordId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new PlayerData(resultSet.getString(1), resultSet.getString(2), resultSet.getBoolean(3));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            CommonUtils.close(statement);
        }
        return null;
    }

    @Override
    public void put(UUID uuid, String discordId, boolean bedrock) {
        put(uuid.toString(), discordId, bedrock);
    }

    @Override
    public void put(String uuid, String discordId, boolean bedrock) {
        Bukkit.getScheduler().runTaskAsynchronously(PlayerRoleChecker.INSTANCE, () -> {
            PreparedStatement statement = null;
            try {
                statement = getConnection().prepareStatement("insert into verifyplayer values (?,?,?)");
                statement.setString(1, uuid);
                statement.setString(2, discordId);
                statement.setBoolean(3, bedrock);
                statement.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                CommonUtils.close(statement);
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
            PreparedStatement statement = null;
            try {
                statement = getConnection().prepareStatement("delete from verifyplayer where uuid = ? and discord = ?");
                statement.setString(1, uuid);
                statement.setString(2, discordId);
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                CommonUtils.close(statement);
            }
        });
    }

    @Override
    public void create() {
        Bukkit.getScheduler().runTaskAsynchronously(PlayerRoleChecker.INSTANCE, () -> {
            Statement statement = null;
            try {
                alter();

                statement = PlayerDataSQL.getInstance().getConnection().createStatement();

                statement.executeUpdate("create table if not exists verifyplayer (uuid VARCHAR(50), discord VARCHAR(50), bedrock BOOLEAN)");
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                CommonUtils.close(statement);
            }
        });
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed() || isConnectionDead()) {
            FileConfiguration config = PlayerRoleChecker.INSTANCE.getConfig();

            connection = DriverManager.getConnection("jdbc:mysql://" + config.getString("MySQL.Server") + ":" + config.getInt("MySQL.Port") + "/" + config.getString("MySQL.Database") + config.getString("MySQL.Option"), config.getString("MySQL.Username"), config.getString("MySQL.Password"));
        }
        return connection;
    }
    
    @Override
    public SQLType getType() {
        return type;
    }

    @Override
    public void setType(SQLType type) {
        this.type = type;
    }
}