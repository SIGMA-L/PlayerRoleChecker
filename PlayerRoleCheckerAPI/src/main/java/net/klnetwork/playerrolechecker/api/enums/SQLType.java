package net.klnetwork.playerrolechecker.api.enums;

/**
 * 4.0で使用予定
 */
public enum SQLType {
    MYSQL("jdbc:mysql://"),
    SQLITE("jdbc:sqlite:"),
    CUSTOM("");

    private final String type;

    SQLType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
