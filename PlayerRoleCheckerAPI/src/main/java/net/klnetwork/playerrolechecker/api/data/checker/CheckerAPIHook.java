package net.klnetwork.playerrolechecker.api.data.checker;

import net.klnetwork.playerrolechecker.api.data.APIHookCustom;

public interface CheckerAPIHook extends APIHookCustom {
    CheckerTemporaryTable getTemporary();

    void setTemporary(CheckerTemporaryTable table);

    @Deprecated
    CheckerCustomDataBase getCustomDataBase();

    CheckerConfigManager getConfigManager();
}