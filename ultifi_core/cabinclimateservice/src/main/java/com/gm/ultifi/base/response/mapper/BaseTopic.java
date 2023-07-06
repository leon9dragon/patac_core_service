package com.gm.ultifi.base.response.mapper;

import com.gm.ultifi.base.propertymanager.CarPropertyExtensionManager;
import com.gm.ultifi.base.response.config.PropertyConfig;
import com.google.protobuf.Any;

import java.util.Map;

public interface BaseTopic {

    boolean isRepeatedSignal();

    /**
     * Convert the given car property value to protobuf message which defined in DevPortal
     *
     * @param carPropertyExtensionManager CarPropertyManager holder
     * @param value                       CarPropertyValue, defined as Object for convenience
     * @param config                      CarPropertyConfig for the carPropertyValue
     * @return A key-value map, the key means topic uri while the value means protobuf formatted message data
     */
    Map<String, Any> generateProtobufMessage(
            CarPropertyExtensionManager carPropertyExtensionManager,
            Object value,
            PropertyConfig config);

    default PropertyConfig getConfig(int propertyId) {
        return null;
    }

    default PropertyConfig getConfig(String signalName) {
        return null;
    }

    default void setAreaId(Integer areaId) {
    }

    default void setPropertyStatus(int status) {
    }
}