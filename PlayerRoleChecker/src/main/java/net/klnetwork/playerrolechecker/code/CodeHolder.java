package net.klnetwork.playerrolechecker.code;

import net.klnetwork.playerrolechecker.api.data.checker.CheckerCodeData;
import net.klnetwork.playerrolechecker.api.data.checker.CheckerCodeHolder;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.function.Predicate;

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
        catchRemoveIf(list, data -> data.getUUID().equals(uuid));
    }

    @Override
    public void remove(UUID uuid, int code) {
        catchRemoveIf(list, data -> data.getUUID().equals(uuid) && data.getCode() == code);
    }

    @Override
    public void remove(int code) {
        catchRemoveIf(list, data -> data.getCode() == code);
    }

    @Override
    public void remove(CheckerCodeData data) {
        list.remove(data);
        data.cancelTask();
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    public <E extends CheckerCodeData> void catchRemoveIf(List<E> list, Predicate<? super E> filter) {
        final Iterator<E> each = list.iterator();
        while (each.hasNext()) {
            E value = each.next();
            if (filter.test(value)) {
                each.remove();
                value.cancelTask();
            }
        }
    }
}