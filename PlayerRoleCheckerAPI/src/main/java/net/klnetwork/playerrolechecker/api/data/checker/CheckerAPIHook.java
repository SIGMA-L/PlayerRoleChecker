package net.klnetwork.playerrolechecker.api.data.checker;

import net.klnetwork.playerrolechecker.api.data.APIHookCustom;
import net.klnetwork.playerrolechecker.api.data.common.TemporaryTable;

public interface CheckerAPIHook extends APIHookCustom {
    TemporaryTable getTemporary();

    void setTemporary(TemporaryTable table);

    @Deprecated
    CheckerCustomDataBase getCustomDataBase();

    CheckerConfigManager getConfigManager();
}