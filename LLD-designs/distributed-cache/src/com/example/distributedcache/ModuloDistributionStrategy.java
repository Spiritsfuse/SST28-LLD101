package com.example.distributedcache;

public class ModuloDistributionStrategy<K> implements DistributionStrategy<K> {
    @Override
    public int getNodeIndex(K key, int numberOfNodes) {
        if (numberOfNodes <= 0) {
            throw new IllegalArgumentException("numberOfNodes must be positive");
        }
        return Math.floorMod(key.hashCode(), numberOfNodes);
    }
}
