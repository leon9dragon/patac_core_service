package com.gm.ultifi.base.utils.cache;

import java.util.Map;

public class LruCache<K, V> implements Cache<K, V> {

    private static final int DEFAULT_CAPACITY = 10;

    private static final int REMOVE_ALL = -1;

    private final Map<K, V> mMap;

    private final int mMaxMemorySize;

    private int mMemorySize;

    private LruCache() {
        this(DEFAULT_CAPACITY);
    }

    private LruCache(int capacity) {
        if (capacity > 0) {
            mMap = new LruMap<>(capacity);
            mMaxMemorySize = capacity * 1024 * 1024;
            return;
        }
        throw new IllegalArgumentException("capacity <= 0");
    }

    private static class LruCacheSingleton {
        private static final LruCache INSTANCE = new LruCache<>();
    }

    public static LruCache getInstance() {
        return LruCacheSingleton.INSTANCE;
    }

    private void trimToSize(int maxSize) {
        while (true) {
            if (mMemorySize <= maxSize) {
                return;
            } else if (mMap.isEmpty()) {
                return;
            } else {
                if (mMemorySize >= 0) {
                    Map.Entry<K, V> entry = mMap.entrySet().iterator().next();
                    mMap.remove(entry.getKey());
                    mMemorySize -= getValueSize(entry.getValue());
                    continue;
                }
                throw new IllegalStateException(getClassName() + ".getValueSize() is reporting inconsistent results");
            }
        }
    }

    @Override
    public void clear() {

    }

    @Override
    public V get(K paramK) {
        return null;
    }

    @Override
    public int getMaxMemorySize() {
        return 0;
    }

    @Override
    public int getMemorySize() {
        return 0;
    }

    @Override
    public V put(K paramK, V paramV) {
        return null;
    }

    @Override
    public V remove(K paramK) {
        return null;
    }

    protected int getValueSize(V value) {
        return 1;
    }

    protected String getClassName() {
        return LruCache.class.getName();
    }
}