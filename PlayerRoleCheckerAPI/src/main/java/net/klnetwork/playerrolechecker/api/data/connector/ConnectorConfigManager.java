package net.klnetwork.playerrolechecker.api.data.connector;

import java.util.List;

public interface ConnectorConfigManager {
    boolean isJoinMode();

    void setJoinMode(boolean joinMode);

    /**
     * @return ホワイトリストに入っているプレイヤーはスキップ可能か
     */
    boolean isWhitelistSkipped();

    void setWhitelistSkipped(boolean skipped);

    List<String> getRoleList();

    void setRoleList(List<String> roleList);

    /**
     * @return 参加したときに実行するコマンドを返します
     */
    List<String> getJoinCommand();

    void setJoinCommand(List<String> joinCommand);

    boolean isDebug();

    void setDebug(boolean debug);
}
