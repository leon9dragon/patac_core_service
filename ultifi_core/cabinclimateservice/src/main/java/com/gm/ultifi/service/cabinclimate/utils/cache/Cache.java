package com.gm.ultifi.service.cabinclimate.utils.cache;

public interface Cache<K, V> {
    void clear();

    V get(K paramK);

    int getMaxMemorySize();

    int getMemorySize();

    V put(K paramK, V paramV);

    V remove(K paramK);
}