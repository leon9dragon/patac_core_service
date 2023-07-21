package com.gm.ultifi.factory;

import com.gm.ultifi.base.response.mapper.BaseTopic;
import com.gm.ultifi.service.access.response.mapper.SunroofTopic;
import com.gm.ultifi.service.chassis.response.mapper.ElectronicStabilityControlSystemTopic;
import com.gm.ultifi.service.chassis.response.mapper.TractionControlSystemTopic;
import com.gm.ultifi.service.seating.response.mapper.SeatModeTopic;
import com.gm.ultifi.service.chassis.response.mapper.TireTopic;
import com.gm.ultifi.service.seating.response.mapper.SeatTemperatureTopic;

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
        if(SeatTemperatureTopic.getPropertyList().contains(propertyId)){
            return SeatTemperatureTopic.getInstance();
        }
        if(SeatModeTopic.getPropertyList().contains(propertyId)){
            return SeatModeTopic.getInstance();
        }
        if(TireTopic.getPropertyList().contains(propertyId)){
            return TireTopic.getInstance();
        }
        if(TractionControlSystemTopic.getPropertyList().contains(propertyId)){
            return TractionControlSystemTopic.getInstance();
        }
        if( ElectronicStabilityControlSystemTopic.getPropertyList().contains(propertyId)){
            return ElectronicStabilityControlSystemTopic.getInstance();
        }
        throw new IllegalArgumentException("illegal property id: " + propertyId);
    }

    public BaseTopic getMapper(String signalName) {
        throw new IllegalArgumentException("illegal signal name: " + signalName);
    }
}
