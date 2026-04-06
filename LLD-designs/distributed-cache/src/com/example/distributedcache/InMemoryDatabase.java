package com.example.distributedcache;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryDatabase<K, V> implements Database<K, V> {
    private final Map<K, V> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<V> get(K key) {
        return Optional.ofNullable(storage.get(key));
    }

    @Override
    public void put(K key, V value) {
        storage.put(key, value);
    }
}
