package net.klnetwork.playerrolechecker.api.data.codeapi;

import org.bukkit.plugin.Plugin;

public abstract class CodeAPICanRegister {
    public CodeAPICanRegister(Plugin plugin) {
        this.plugin = plugin;
    }

    protected final Plugin plugin;

    public abstract boolean canRegister();
}
