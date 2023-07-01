package com.gm.ultifi.base.response.mapper;

import com.gm.ultifi.service.access.response.mapper.SunroofMapper;

public class TopicMapperFactory {
    private static final String TAG = TopicMapperFactory.class.getSimpleName();

    private static class TopicMapperFactorySingleton {
        private static final TopicMapperFactory INSTANCE = new TopicMapperFactory();
    }

    public static TopicMapperFactory getInstance() {
        return TopicMapperFactorySingleton.INSTANCE;
    }

    /*
     * to find the processor by property/signal
     */

    public BaseMapper getMapper(int propertyId) {
        if (SunroofMapper.getPropertyList().contains(propertyId)) {
            return SunroofMapper.getInstance();
        }
        throw new IllegalArgumentException("illegal property id: " + propertyId);
    }

    public BaseMapper getMapper(String signalName) {
        throw new IllegalArgumentException("illegal signal name: " + signalName);
    }
}
