package com.example.distributedcache;

import java.util.Optional;

public interface Database<K, V> {
    Optional<V> get(K key);

    void put(K key, V value);
}
