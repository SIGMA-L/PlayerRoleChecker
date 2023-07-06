package net.klnetwork.playerrolechecker.api.data.checker;

import net.klnetwork.playerrolechecker.api.data.APIHookCustom;

public interface CheckerAPIHook extends APIHookCustom {
    CheckerCodeHolder getCodeHolder();

    @Deprecated
    CheckerCustomDataBase getCustomDataBase();

    CheckerConfigManager getConfigManager();
}