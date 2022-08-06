package net.klnetwork.playerrolechecker.api.data.checker;

import net.klnetwork.playerrolechecker.api.data.common.CustomDataBase;

public interface CheckerCustomDataBase extends CustomDataBase {
    void setTemporary(CheckerTemporaryTable table);
}
