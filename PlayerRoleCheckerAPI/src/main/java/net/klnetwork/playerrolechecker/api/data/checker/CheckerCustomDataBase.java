package net.klnetwork.playerrolechecker.api.data.checker;

import net.klnetwork.playerrolechecker.api.data.common.CustomDataBase;
import net.klnetwork.playerrolechecker.api.data.common.TemporaryTable;

public interface CheckerCustomDataBase extends CustomDataBase {
    void setTemporary(TemporaryTable table);
}
