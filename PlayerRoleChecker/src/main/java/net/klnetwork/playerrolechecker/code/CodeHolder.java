package net.klnetwork.playerrolechecker.code;

import net.klnetwork.playerrolechecker.api.data.checker.CheckerCodeData;
import net.klnetwork.playerrolechecker.api.data.checker.CheckerCodeHolder;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CodeHolder implements CheckerCodeHolder {
    private final List<CheckerCodeData> list = new ArrayList<>();
    private final Plugin plugin;

    public CodeHolder(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<CheckerCodeData> getList() {
        return list;
    }

    @Override
    public boolean add(UUID uuid, int code, boolean bedrock) {
        return list.add(new CodeData(uuid, code, bedrock).startTask(this));
    }

    @Override
    public CheckerCodeData get(UUID uuid) {
        return list.stream()
                .filter(d -> d.getUUID().equals(uuid))
                .findFirst().orElse(null);
    }

    @Override
    public CheckerCodeData get(int code) {
        return list.stream()
                .filter(d -> d.getCode() == code)
                .findFirst().orElse(null);
    }

    @Override
    public boolean has(int code) {
        return list.stream().anyMatch(data -> data.getCode() == code);
    }

    @Override
    public void remove(UUID uuid) {
        list.removeIf(data -> data.getUUID().equals(uuid));
    }

    @Override
    public void remove(UUID uuid, int code) {
        list.removeIf(data -> data.getUUID().equals(uuid) && data.getCode() == code);
    }

    @Override
    public void remove(int code) {
        list.removeIf(data -> data.getCode() == code);
    }

    @Override
    public void remove(CheckerCodeData data) {
        list.remove(data);
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }
}
