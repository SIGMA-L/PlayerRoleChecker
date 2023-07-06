package net.klnetwork.playerrolechecker.code;

import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CodeHolder {
    private final List<CodeData> list = new ArrayList<>();
    private final Plugin plugin;

    public CodeHolder(Plugin plugin) {
        this.plugin = plugin;
    }

    public List<CodeData> getList() {
        return list;
    }

    public boolean add(UUID uuid, int code, boolean bedrock) {
        return list.add(new CodeData(uuid, code, bedrock).startTask(this));
    }

    public CodeData get(UUID uuid) {
        return list.stream()
                .filter(d -> d.getUUID().equals(uuid))
                .findFirst().orElse(null);
    }

    public CodeData get(int code) {
        return list.stream()
                .filter(d -> d.getCode() == code)
                .findFirst().orElse(null);
    }

    public boolean has(int code) {
        return list.stream().anyMatch(data -> data.getCode() == code);
    }

    public void remove(UUID uuid) {
        list.removeIf(data -> data.getUUID().equals(uuid));
    }

    public void remove(UUID uuid, int code) {
        list.removeIf(data -> data.getUUID().equals(uuid) && data.getCode() == code);
    }

    public void remove(int code) {
        list.removeIf(data -> data.getCode() == code);
    }

    public void remove(CodeData data) {
        list.remove(data);
    }

    public Plugin getPlugin() {
        return plugin;
    }
}
