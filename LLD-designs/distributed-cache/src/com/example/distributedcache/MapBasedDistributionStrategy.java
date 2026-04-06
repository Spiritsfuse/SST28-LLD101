package com.example.distributedcache;

import java.util.Map;

public class MapBasedDistributionStrategy<K> implements DistributionStrategy<K> {
    private final Map<K, Integer> explicitRouting;
    private final DistributionStrategy<K> fallback;

    public MapBasedDistributionStrategy(Map<K, Integer> explicitRouting, DistributionStrategy<K> fallback) {
        if (explicitRouting == null || fallback == null) {
            throw new IllegalArgumentException("explicitRouting and fallback are required");
        }
        this.explicitRouting = explicitRouting;
        this.fallback = fallback;
    }

    @Override
    public int getNodeIndex(K key, int numberOfNodes) {
        Integer routedNode = explicitRouting.get(key);
        if (routedNode != null) {
            return Math.floorMod(routedNode, numberOfNodes);
        }
        return fallback.getNodeIndex(key, numberOfNodes);
    }
}
