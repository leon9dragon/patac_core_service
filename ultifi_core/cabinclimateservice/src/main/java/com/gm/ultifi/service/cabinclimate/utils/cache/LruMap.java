package com.gm.ultifi.service.cabinclimate.utils.cache;

import java.util.LinkedHashMap;
import java.util.Map;

final class LruMap<K, V> extends LinkedHashMap<K, V> {
    private final int mCapacity;

    public LruMap(int capacity) {
        super(capacity, 0.75F, true);

        mCapacity = capacity;
    }

    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > mCapacity;
    }
}