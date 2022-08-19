package net.klnetwork.playerrolechecker.api.data;

/**
 * todo: uses in 4.1 ? 4.0
 */
public class Options {
    private boolean createSQL = true;

    public boolean isCreateSQL() {
        return createSQL;
    }

    public void setCreateSQL(boolean createSQL) {
        this.createSQL = createSQL;
    }
}
