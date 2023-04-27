package com.example.labs.cache;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Cache<K, V> {
    Map<K, V> cache = new HashMap<>();

    public boolean contain(K key) {
        return cache.containsKey(key);
    }

    public void push(K key, V value) {
        cache.put(key, value);
    }

    public V get(K key) {
        return cache.get(key);
    }
}
