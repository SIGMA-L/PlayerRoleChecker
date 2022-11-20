package net.klnetwork.playerrolechecker.api.data;

import net.dv8tion.jda.internal.utils.tuple.Pair;
import net.klnetwork.playerrolechecker.api.data.common.PlayerData;
import net.klnetwork.playerrolechecker.api.utils.CommonUtils;
import org.bukkit.Bukkit;

import java.sql.*;
import java.util.UUID;
import java.util.function.Consumer;

public abstract class PlayerDataTable extends SQLInterface {
    protected Pair<Integer, PlayerData> pair = Pair.of(0, null);

    public void asyncDiscordId(UUID uuid, Consumer<PlayerData> discordId) {
        asyncDiscordId(uuid.toString(), discordId);
    }

    public void asyncDiscordId(String uuid, Consumer<PlayerData> discordId) {
        Bukkit.getScheduler().runTaskAsynchronously(getPlugin(), () -> discordId.accept(getDiscordId(uuid)));
    }

    public void asyncDiscordId(UUID uuid, boolean bedrock, Consumer<PlayerData> discordId) {
        asyncDiscordId(uuid.toString(), bedrock, discordId);
    }

    public void asyncDiscordId(String uuid, boolean bedrock, Consumer<PlayerData> discordId) {
        Bukkit.getScheduler().runTaskAsynchronously(getPlugin(), () -> discordId.accept(getDiscordId(uuid, bedrock)));
    }

    public void asyncUUID(String discordId, Consumer<PlayerData> uuid) {
        Bukkit.getScheduler().runTaskAsynchronously(getPlugin(), () -> uuid.accept(getUUID(discordId)));
    }

    public PlayerData getDiscordId(UUID uuid) {
        return getDiscordId(uuid.toString());
    }

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

    public PlayerData getDiscordId(UUID uuid, boolean bedrock) {
        return getDiscordId(uuid.toString(), bedrock);
    }

    public PlayerData getDiscordId(String uuid, boolean bedrock) {
        PreparedStatement statement = null;

        try {
            statement = getConnection().prepareStatement("select * from verifyplayer where uuid = ? and bedrock = ?");
            statement.setString(1, uuid);
            statement.setBoolean(2, bedrock);

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

    public boolean hasData(UUID uuid, boolean bedrock) {
        return hasData(uuid.toString(), bedrock);
    }

    public boolean hasData(String uuid, boolean bedrock) {
        PreparedStatement statement = null;

        try {
            statement = getConnection().prepareStatement("select * from verifyplayer where uuid = ? and bedrock = ?");
            statement.setString(1, uuid);
            statement.setBoolean(2, bedrock);

            return statement.executeQuery().next();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            CommonUtils.close(statement);
        }
        return false;
    }

    public PlayerData getUUID(String discordId) {
        PreparedStatement statement = null;

        try {
            statement = getConnection().prepareStatement("select * from verifyplayer where discord = ?");
            statement.setString(1, discordId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new PlayerData(resultSet.getString(1), resultSet.getString(2), resultSet.getBoolean(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CommonUtils.close(statement);
        }
        return null;
    }

    public void put(UUID uuid, String discordId, boolean bedrock) {
        put(uuid.toString(), discordId, bedrock);
    }

    public void put(String uuid, String discordId, boolean bedrock) {
        Bukkit.getScheduler().runTaskAsynchronously(getPlugin(), () -> {
            PreparedStatement statement = null;
            try {
                statement = getConnection().prepareStatement("insert into verifyplayer values (?,?,?)");
                statement.setString(1, uuid);
                statement.setString(2, discordId);
                statement.setBoolean(3, bedrock);
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                CommonUtils.close(statement);
            }
        });
    }

    public void remove(UUID uuid, String discordId) {
        remove(uuid.toString(), discordId);
    }

    public void remove(String uuid, String discordId) {
        Bukkit.getScheduler().runTaskAsynchronously(getPlugin(), () -> {
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

    public Pair<Integer, PlayerData> getSize(String discordId) {
        PreparedStatement statement = null;

        try {
            statement = getConnection().prepareStatement("SELECT count(*), uuid, bedrock from verifyplayer where discord = ?");
            statement.setString(1, discordId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                final int size = resultSet.getInt(1);

                return size != 0 ? Pair.of(size, new PlayerData(resultSet.getString(2), discordId, resultSet.getBoolean(3))) : pair;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            CommonUtils.close(statement);
        }

        return pair;
    }

    public void create() {
        Bukkit.getScheduler().runTaskAsynchronously(getPlugin(), () -> {
            Statement statement = null;
            try {
                alter();

                statement = getConnection().createStatement();

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
            checkClass();

            this.connection = CommonUtils.createConnection(getSQLFormat(), getUser(), getPassword());
        }
        return connection;
    }

    public void alter() {
        Statement statement = null;
        try {
            statement = getConnection().createStatement();
            statement.executeUpdate("ALTER TABLE verifyplayer ADD bedrock boolean DEFAULT FALSE");
        } catch (SQLException ex) {
            /* ignored */
        } finally {
            CommonUtils.close(statement);
        }
    }
}
