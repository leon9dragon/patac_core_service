package com.gm.ultifi.base.response.config;

public interface SignalInfo {
    String getResName();

    String getSignal();

    default Integer getPropertyId() {
        return null;
    }

    default float getRate() {
        return 0;
    }
}