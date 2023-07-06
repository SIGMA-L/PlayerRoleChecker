package net.klnetwork.playerrolechecker.api.data.checker;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public interface CheckerCodeHolder {
    /**
     * @return 現在保存されているデータを取得します
     */
    List<CheckerCodeData> getList();

    /**
     * 新しくデータを追加します
     * @param uuid UUID
     * @param code 認証コード
     * @param bedrock ユーザーがBE版で接続したか
     * @return 追加に成功
     */
    boolean add(UUID uuid, int code, boolean bedrock);

    /**
     * データが存在している場合取得します
     * @param uuid UUID
     * @return データ
     */
    @Nullable
    CheckerCodeData get(UUID uuid);

    /**
     * データが存在している場合取得します
     * @param code 認証コード
     * @return データ
     */
    @Nullable
    CheckerCodeData get(int code);

    /**
     * データが存在しているか確認します
     * @param code 認証コード
     * @return 存在しているか
     */
    boolean has(int code);

    /**
     * データが存在していたら削除します
     * @param uuid UUID
     */
    void remove(UUID uuid);

    /**
     * データが存在していたら削除します
     * @param code 認証コード
     */
    void remove(int code);

    /**
     * データが存在していたら削除します
     * @param uuid UUID
     * @param code 認証コード
     */
    void remove(UUID uuid, int code);

    /**
     * データが存在していたら削除します
     * @param data データ
     */
    void remove(CheckerCodeData data);

    /**
     * @return Bukkit プラグイン
     */
    Plugin getPlugin();
}
