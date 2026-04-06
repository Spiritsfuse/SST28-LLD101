package com.example.distributedcache;

import java.util.LinkedHashSet;
import java.util.NoSuchElementException;

public class LruEvictionPolicy<K> implements EvictionPolicy<K> {
    private final LinkedHashSet<K> accessOrder = new LinkedHashSet<>();

    @Override
    public void onKeyRead(K key) {
        moveToMostRecent(key);
    }

    @Override
    public void onKeyWrite(K key) {
        moveToMostRecent(key);
    }

    @Override
    public void onKeyDelete(K key) {
        accessOrder.remove(key);
    }

    @Override
    public K keyToEvict() {
        if (accessOrder.isEmpty()) {
            throw new NoSuchElementException("No key available for eviction");
        }
        return accessOrder.iterator().next();
    }

    private void moveToMostRecent(K key) {
        accessOrder.remove(key);
        accessOrder.add(key);
    }
}
