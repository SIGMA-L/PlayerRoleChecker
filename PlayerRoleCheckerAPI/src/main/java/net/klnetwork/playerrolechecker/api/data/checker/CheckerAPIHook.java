package net.klnetwork.playerrolechecker.api.data.checker;

import net.klnetwork.playerrolechecker.api.data.APIHook;

public interface CheckerAPIHook extends APIHook {
    CheckerTemporaryTable getTemporary();

    void setTemporary(CheckerTemporaryTable table);

    @Deprecated
    CheckerCustomDataBase getCustomDataBase();

    CheckerConfigManager getConfigManager();
}
