package com.example.distributedcache;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CacheNode<K, V> {
    private final int nodeId;
    private final int capacity;
    private final EvictionPolicy<K> evictionPolicy;
    private final Map<K, V> storage;

    public CacheNode(int nodeId, int capacity, EvictionPolicy<K> evictionPolicy) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be positive");
        }
        if (evictionPolicy == null) {
            throw new IllegalArgumentException("evictionPolicy is required");
        }
        this.nodeId = nodeId;
        this.capacity = capacity;
        this.evictionPolicy = evictionPolicy;
        this.storage = new HashMap<>();
    }

    public synchronized Optional<V> get(K key) {
        V value = storage.get(key);
        if (value == null) {
            return Optional.empty();
        }
        evictionPolicy.onKeyRead(key);
        return Optional.of(value);
    }

    public synchronized void put(K key, V value) {
        if (storage.containsKey(key)) {
            storage.put(key, value);
            evictionPolicy.onKeyWrite(key);
            return;
        }

        if (storage.size() >= capacity) {
            K evicted = evictionPolicy.keyToEvict();
            storage.remove(evicted);
            evictionPolicy.onKeyDelete(evicted);
        }

        storage.put(key, value);
        evictionPolicy.onKeyWrite(key);
    }

    public int getNodeId() {
        return nodeId;
    }

    public synchronized int size() {
        return storage.size();
    }
}
