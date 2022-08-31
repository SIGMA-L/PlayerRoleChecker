package net.klnetwork.playerrolechecker.api.data.codeapi;

public interface CodeAPIConfigManager {
    int getMin();

    void setMin(int min);

    int getMax();

    void setMax(int max);

    int getDeleteSecond();

    void setDeleteSecond(int deleteSecond);
}