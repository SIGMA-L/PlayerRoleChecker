package net.klnetwork.playerrolechecker.api.data.checker;

public interface CheckerConfigManager {
    int getDeleteSecond();

    /**
     * @param deleteSecond 削除時間を上書きします
     */
    void setDeleteSecond(int deleteSecond);

    int getMax();

    void setMax(int max);

    int getMin();

    void setMin(int min);

    boolean isVerifiedPlayerIgnore();

    void setVerifiedPlayerIgnore(boolean verifiedPlayerIgnore);

    boolean canRegisterUnlimitedAccount();

    void setCanRegisterUnlimitedAccount(boolean canRegisterUnlimitedAccount);

    int getAccountPerDiscord();

    /**
     * @param accountPerDiscord Discordアカウントごとに最大登録数を制御します
     */
    void setAccountPerDiscord(int accountPerDiscord);
}