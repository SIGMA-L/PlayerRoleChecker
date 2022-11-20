package net.klnetwork.playerrolechecker.api.data.codeapi;

import org.bukkit.plugin.Plugin;

public interface CodeAPICanRegister {
    boolean canRegister(Plugin plugin);
}