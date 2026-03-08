package com.example.map;

import java.util.HashMap;
import java.util.Map;

/**
 * Flyweight Factory: Caches and returns shared MarkerStyle instances.
 * 
 * Instead of creating a new MarkerStyle for each marker, this factory:
 * 1. Generates a unique key from the style parameters
 * 2. Checks if a MarkerStyle with that key already exists in cache
 * 3. Returns cached instance if available, otherwise creates and caches a new one
 * 
 * This dramatically reduces memory usage when many markers share the same style.
 */
public class MarkerStyleFactory {

    private final Map<String, MarkerStyle> cache = new HashMap<>();

    public MarkerStyle get(String shape, String color, int size, boolean filled) {
        String key = shape + "|" + color + "|" + size + "|" + (filled ? "F" : "O");
        
        // Check cache first
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        
        // Create new style, cache it, and return
        MarkerStyle style = new MarkerStyle(shape, color, size, filled);
        cache.put(key, style);
        System.out.println("[Factory] Created new MarkerStyle: " + key);
        return style;
    }

    public int cacheSize() {
        return cache.size();
    }
}
