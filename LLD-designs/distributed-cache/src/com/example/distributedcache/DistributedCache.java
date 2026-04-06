package com.example.distributedcache;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class DistributedCache<K, V> {
    private final List<CacheNode<K, V>> nodes;
    private final DistributionStrategy<K> distributionStrategy;
    private final Database<K, V> database;

    public DistributedCache(
            int numberOfNodes,
            int nodeCapacity,
            DistributionStrategy<K> distributionStrategy,
            Supplier<EvictionPolicy<K>> evictionPolicyFactory,
            Database<K, V> database
    ) {
        if (numberOfNodes <= 0) {
            throw new IllegalArgumentException("numberOfNodes must be positive");
        }
        if (distributionStrategy == null || evictionPolicyFactory == null || database == null) {
            throw new IllegalArgumentException("distributionStrategy, evictionPolicyFactory and database are required");
        }

        this.nodes = new ArrayList<>();
        this.distributionStrategy = distributionStrategy;
        this.database = database;

        for (int i = 0; i < numberOfNodes; i++) {
            nodes.add(new CacheNode<>(i, nodeCapacity, evictionPolicyFactory.get()));
        }
    }

    public V get(K key) {
        CacheNode<K, V> node = nodeFor(key);
        Optional<V> valueInCache = node.get(key);
        if (valueInCache.isPresent()) {
            return valueInCache.get();
        }

        Optional<V> valueFromDb = database.get(key);
        valueFromDb.ifPresent(value -> node.put(key, value));
        return valueFromDb.orElse(null);
    }

    public Optional<V> getIfPresent(K key) {
        return nodeFor(key).get(key);
    }

    public void put(K key, V value) {
        CacheNode<K, V> node = nodeFor(key);
        node.put(key, value);
        database.put(key, value);
    }

    public int getNodeIndexForKey(K key) {
        return distributionStrategy.getNodeIndex(key, nodes.size());
    }

    public int getNodeSize(int nodeIndex) {
        return nodes.get(nodeIndex).size();
    }

    private CacheNode<K, V> nodeFor(K key) {
        int nodeIndex = distributionStrategy.getNodeIndex(key, nodes.size());
        return nodes.get(nodeIndex);
    }
}
