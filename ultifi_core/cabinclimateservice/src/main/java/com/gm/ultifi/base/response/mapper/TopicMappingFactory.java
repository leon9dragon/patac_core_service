package com.gm.ultifi.base.response.mapper;

import com.gm.ultifi.service.access.response.mapper.SunroofTopic;

public class TopicMappingFactory {
    private static final String TAG = TopicMappingFactory.class.getSimpleName();

    private static class TopicMapperFactorySingleton {
        private static final TopicMappingFactory INSTANCE = new TopicMappingFactory();
    }

    public static TopicMappingFactory getInstance() {
        return TopicMapperFactorySingleton.INSTANCE;
    }

    /*
     * to find the processor by property/signal
     */

    public BaseTopic getMapper(int propertyId) {
        if (SunroofTopic.getPropertyList().contains(propertyId)) {
            return SunroofTopic.getInstance();
        }
        throw new IllegalArgumentException("illegal property id: " + propertyId);
    }

    public BaseTopic getMapper(String signalName) {
        throw new IllegalArgumentException("illegal signal name: " + signalName);
    }
}
