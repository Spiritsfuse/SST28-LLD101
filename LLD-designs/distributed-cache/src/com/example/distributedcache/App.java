package com.example.distributedcache;

import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        InMemoryDatabase<String, String> database = new InMemoryDatabase<>();
        database.put("user:1", "Alice");
        database.put("user:2", "Bob");
        database.put("user:3", "Charlie");

        DistributedCache<String, String> cache = new DistributedCache<>(
                3,
                2,
                new ModuloDistributionStrategy<>(),
                LruEvictionPolicy::new,
                database
        );

        System.out.println("=== Cache miss then DB read-through ===");
        String user1 = cache.get("user:1");
        System.out.println("get(user:1) -> " + user1 + " [loaded from DB and cached]");

        System.out.println("=== Put (write-through) ===");
        cache.put("user:4", "Diana");
        System.out.println("put(user:4, Diana) stored in cache node " + cache.getNodeIndexForKey("user:4"));

        System.out.println("=== LRU eviction at a single node (capacity=2) ===");
        InMemoryDatabase<Integer, String> intDb = new InMemoryDatabase<>();
        DistributedCache<Integer, String> intCache = new DistributedCache<>(
            3,
            2,
            new ModuloDistributionStrategy<>(),
            LruEvictionPolicy::new,
            intDb
        );

        // Keys 0, 3, and 6 all route to node 0 for modulo-3 distribution.
        intCache.put(0, "K0");
        intCache.put(3, "K3");
        intCache.get(0);
        intCache.put(6, "K6");

        String evictedCheck = intCache.getIfPresent(3).orElse(null);
        System.out.println("getIfPresent(3) after eviction -> " + evictedCheck + " (expected null)");

        System.out.println("=== Map-based routing strategy demo ===");
        Map<String, Integer> explicitRouting = new HashMap<>();
        explicitRouting.put("tenant:premium", 0);

        DistributedCache<String, String> routedCache = new DistributedCache<>(
                3,
                2,
                new MapBasedDistributionStrategy<>(explicitRouting, new ModuloDistributionStrategy<>()),
                LruEvictionPolicy::new,
                database
        );

        routedCache.put("tenant:premium", "pinned-node-0");
        System.out.println("tenant:premium routed to node " + routedCache.getNodeIndexForKey("tenant:premium"));
    }
}
