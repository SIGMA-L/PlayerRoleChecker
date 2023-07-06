package net.klnetwork.playerrolechecker.api.data.checker;

import java.util.UUID;

public interface CheckerCodeData {
    /**
     * @return UUID
     */
    UUID getUUID();

    /**
     * @return 認証コード
     */
    int getCode();

    /**
     * @return ユーザーがBE版で接続したか
     */
    boolean isBedrock();

    /**
     * @return このデータが作成された時間 を {@link System#currentTimeMillis()} (ミリ秒) で返します
     * @see System#currentTimeMillis()
     */
    long getCreatedAt();

    /**
     * データを削除するタイマーを開始します
     * <br>コンフィグに設定されている秒数後に削除されます
     * @param holder データを保持しているクラス
     * @return このクラスを返します
     */
    CheckerCodeData startTask(CheckerCodeHolder holder);

    /**
     * データを削除するタイマーを開始します
     * @param holder データを保持しているクラス
     * @param ticks 削除するtick
     * @return このクラスを返します
     */
    CheckerCodeData startTask(CheckerCodeHolder holder, int ticks);

    /**
     * タイマーが開始されている場合、キャンセルします
     */
    void cancelTask();
}
