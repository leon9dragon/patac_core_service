package com.gm.ultifi.base.utils.cache;

import java.util.LinkedHashMap;

final class LruMap<K, V> extends LinkedHashMap<K, V> {
    private final int mCapacity;

    public LruMap(int capacity) {
        super(capacity, 0.75F, true);

        mCapacity = capacity;
    }

    protected boolean removeEldestEntry(Entry<K, V> eldest) {
        return size() > mCapacity;
    }
}