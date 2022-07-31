package net.klnetwork.playerrolechecker.api.data.checker;

import net.klnetwork.playerrolechecker.api.data.APIHook;

public interface CheckerAPIHook extends APIHook {
    CheckerTemporaryTable getTemporary();

    CheckerCustomDataBase getCustomDataBase();

    CheckerConfigManager getConfigManager();
}
