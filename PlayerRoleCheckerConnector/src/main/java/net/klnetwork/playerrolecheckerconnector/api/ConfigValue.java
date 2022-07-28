package net.klnetwork.playerrolecheckerconnector.api;

import net.klnetwork.playerrolechecker.api.data.connector.ConnectorConfigManager;
import net.klnetwork.playerrolechecker.api.utils.config.ConfigKey;
import net.klnetwork.playerrolechecker.api.utils.config.ConfigManager;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class ConfigValue extends ConfigManager implements ConnectorConfigManager {
    public ConfigValue(Plugin plugin) {
        super(plugin);
    }

    @ConfigKey(key = "Minecraft.defaultJoinMode")
    private boolean joinMode = true;

    @ConfigKey(key = "Discord.RoleID")
    private List<String> roleList = new ArrayList<>();

    @ConfigKey(key = "JoinCommand")
    private List<String> joinCommand = new ArrayList<>();

    @Override
    public boolean isJoinMode() {
        return joinMode;
    }

    @Override
    public void setJoinMode(boolean joinMode) {
        this.joinMode = joinMode;
    }

    @Override
    public List<String> getRoleList() {
        return roleList;
    }

    @Override
    public void setRoleList(List<String> roleList) {
        this.roleList = roleList;
    }

    @Override
    public List<String> getJoinCommand() {
        return joinCommand;
    }

    @Override
    public void setJoinCommand(List<String> joinCommand) {
        this.joinCommand = joinCommand;
    }
}
