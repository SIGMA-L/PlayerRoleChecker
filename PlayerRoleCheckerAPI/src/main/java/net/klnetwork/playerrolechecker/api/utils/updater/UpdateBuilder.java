package net.klnetwork.playerrolechecker.api.utils.updater;

import org.bukkit.plugin.Plugin;

public class UpdateBuilder {
    // 5000seconds = 100000 / 20
    private long ticks = 100000;
    private boolean versionChecks;
    private boolean defaultChecks;
    private Plugin plugin;

    private String regex = "^[0-9]";
    private String owner = "SIGMA-L";
    private String repo = "PlayerRoleChecker";
    private String version = "v4.0";

    public static UpdateBuilder getInstance() {
        return new UpdateBuilder();
    }

    public long checkTicks() {
        return ticks;
    }

    public UpdateBuilder checkTicks(long ticks) {
        if (ticks < 0) {
            throw new IllegalStateException("ticks is negative! (" + ticks + ")");
        }

        this.ticks = ticks;

        return this;
    }

    public boolean smartVersionCheck() {
        return this.versionChecks;
    }

    public UpdateBuilder smartVersionCheck(boolean check) {
        this.versionChecks = check;

        return this;
    }

    public String owner() {
        return owner;
    }

    public UpdateBuilder owner(String owner) {
        this.owner = owner;

        return this;
    }

    public String repo() {
        return repo;
    }

    public UpdateBuilder repo(String repo) {
        this.repo = repo;

        return this;
    }

    public String version() {
        return version;
    }

    public UpdateBuilder version(String version) {
        this.version = version;

        return this;
    }

    public String regex() {
        return regex;
    }

    public UpdateBuilder regex(String regex) {
        this.regex = regex;

        return this;
    }

    public boolean defaultCheck() {
        return this.defaultChecks;
    }

    public UpdateBuilder defaultCheck(boolean check) {
        this.defaultChecks = check;

        return this;
    }

    public Plugin plugin() {
        return this.plugin;
    }

    public UpdateBuilder plugin(Plugin plugin) {
        this.plugin = plugin;

        return this;
    }

    public UpdateChecker start() {
        return new UpdateChecker(this);
    }
}
