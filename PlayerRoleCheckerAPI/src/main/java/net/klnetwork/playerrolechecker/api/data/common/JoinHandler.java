package net.klnetwork.playerrolechecker.api.data.common;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public abstract class JoinHandler implements Listener {
    public void onPreLoginEvent(AsyncPlayerPreLoginEvent event) {}

    public void onLoginEvent(PlayerJoinEvent event) {}

    public void onErrorCaught(AsyncPlayerPreLoginEvent event, Exception ex) {}

    public void disallow(AsyncPlayerPreLoginEvent event, String message) {
        event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, ChatColor.translateAlternateColorCodes('&', message));
    }

    public void disallow(AsyncPlayerPreLoginEvent event, List<String> list) {
        disallow(event, String.join("\n", list));
    }

    public String replace(String target, Player player, PlayerData data) {
        if (player != null) {
            target = target.replaceAll("%name%", player.getName());
            target = target.replaceAll("%uuid%", player.getUniqueId().toString());
        }

        if (data != null) {
            target = target.replaceAll("%discordid%", data.getDiscordId());
        }

        return target;
    }

    public <T> T callEvent(T event) {
        if (event instanceof Event) {
            Bukkit.getPluginManager().callEvent((Event) event);
        }

        return event;
    }
}