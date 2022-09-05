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

        init();
    }

    @ConfigKey(key = "Minecraft.defaultJoinMode")
    private boolean joinMode = true;

    @ConfigKey(key = "Minecraft.whitelistSkip")
    private boolean whitelistSkipped = true;

    @ConfigKey(key = "Discord.RoleID")
    private List<String> roleList = new ArrayList<>();

    @ConfigKey(key = "JoinCommand")
    private List<String> joinCommand = new ArrayList<>();

    @ConfigKey(key = "Minecraft.debug")
    private boolean debug = false;

    @Override
    public boolean isJoinMode() {
        return joinMode;
    }

    @Override
    public void setJoinMode(boolean joinMode) {
        this.joinMode = joinMode;
    }

    @Override
    public boolean isWhitelistSkipped() {
        return whitelistSkipped;
    }

    @Override
    public void setWhitelistSkipped(boolean whitelistSkipped) {
        this.whitelistSkipped = whitelistSkipped;
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

    @Override
    public boolean isDebug() {
        return debug;
    }

    @Override
    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}