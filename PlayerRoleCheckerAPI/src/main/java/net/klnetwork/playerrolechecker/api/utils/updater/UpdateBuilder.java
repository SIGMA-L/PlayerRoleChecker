package net.klnetwork.playerrolechecker.api.utils.updater;

import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class UpdateBuilder {
    // 5000seconds = 100000 / 20
    private long ticks = 100000;
    private boolean versionChecks;
    private boolean defaultChecks;
    private boolean consoleAlert = true;
    private boolean opPlayerAlert = true;
    private boolean enabled = true;
    private Plugin plugin;

    private String regex = "[^0-9.]";
    private String owner = "SIGMA-L";
    private String repo = "PlayerRoleChecker";
    private String version = "v4.0";

    private List<String> messages = new ArrayList<>();

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

    public List<String> messages() {
        return messages;
    }

    public UpdateBuilder messages(List<String> messages) {
        this.messages = messages;

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

    public boolean consoleAlert() {
        return this.consoleAlert;
    }

    public UpdateBuilder consoleAlert(boolean consoleAlert) {
        this.consoleAlert = consoleAlert;

        return this;
    }

    public boolean opPlayerAlert() {
        return this.opPlayerAlert;
    }

    public UpdateBuilder opPlayerAlert(boolean opPlayerAlert) {
        this.opPlayerAlert = opPlayerAlert;

        return this;
    }

    public boolean enabled() {
        return this.enabled;
    }

    public UpdateBuilder enabled(boolean enabled) {
        this.enabled = enabled;

        return this;
    }

    public Plugin plugin() {
        return this.plugin;
    }

    public UpdateBuilder plugin(Plugin plugin) {
        this.plugin = plugin;

        return this;
    }

    public UpdateAlert start() {
        return new UpdateAlert(this);
    }
}
