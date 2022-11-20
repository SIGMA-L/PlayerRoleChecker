package net.klnetwork.playerrolechecker.api.data.codeapi;

public interface CodeAPIConfigManager {
    /**
     * @return コードが生成される最小値
     */
    int getMin();

    /**
     * @param min 最小値
     */
    void setMin(int min);

    /**
     * @return コードが生成される最大値
     */
    int getMax();

    /**
     * @param max 最大値
     */
    void setMax(int max);

    /**
     * @return 一時的なテーブルから削除される時間
     */
    int getDeleteSecond();

    /**
     * @param deleteSecond 一時的なテーブルから削除する時間
     */
    void setDeleteSecond(int deleteSecond);
}