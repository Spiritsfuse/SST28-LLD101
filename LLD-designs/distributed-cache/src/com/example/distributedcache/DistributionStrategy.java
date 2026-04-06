package com.example.distributedcache;

public interface DistributionStrategy<K> {
    int getNodeIndex(K key, int numberOfNodes);
}
