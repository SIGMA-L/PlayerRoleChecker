package net.klnetwork.playerrolechecker.api.data.checker;

public interface CheckerConfigManager {
    int getDeleteSecond();

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

    void setAccountPerDiscord(int accountPerDiscord);
}
