package com.example.distributedcache;

public interface EvictionPolicy<K> {
    void onKeyRead(K key);

    void onKeyWrite(K key);

    void onKeyDelete(K key);

    K keyToEvict();
}
