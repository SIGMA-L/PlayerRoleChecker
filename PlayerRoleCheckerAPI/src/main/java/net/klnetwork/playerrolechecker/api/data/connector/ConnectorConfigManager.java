package net.klnetwork.playerrolechecker.api.data.connector;

import java.util.List;

public interface ConnectorConfigManager {
    boolean isJoinMode();

    void setJoinMode(boolean joinMode);

    List<String> getRoleList();

    void setRoleList(List<String> roleList);

    List<String> getJoinCommand();

    void setJoinCommand(List<String> joinCommand);
}
