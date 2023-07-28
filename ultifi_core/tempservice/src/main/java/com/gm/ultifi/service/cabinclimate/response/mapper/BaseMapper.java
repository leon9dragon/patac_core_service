package com.gm.ultifi.service.cabinclimate.response.mapper;

import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UAuthority;
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UEntity;
import com.gm.ultifi.sdk.uprotocol.uri.factory.UltifiUriFactory;
import com.gm.ultifi.service.cabinclimate.manager.propertymanager.CarPropertyExtensionManager;
import com.gm.ultifi.service.cabinclimate.response.config.PropertyConfig;
import com.gm.ultifi.service.cabinclimate.utils.cache.Cache;
import com.gm.ultifi.service.cabinclimate.utils.cache.LruCache;
import com.google.protobuf.Any;

import java.util.Map;

public interface BaseMapper {

    String BASE_URI_SERVICE = "body.cabin_climate";

    String VERSION = "1";

    UEntity SERVICE = new UEntity(BASE_URI_SERVICE, VERSION);

    String CLIMATE_ZONE = "ExecuteClimateCommand";

    String CLIMATE_ZONE_URI = UltifiUriFactory.buildMethodUri(UAuthority.local(), SERVICE, CLIMATE_ZONE);


    String SYSTEM_SETTINGS = "UpdateSystemSettings";

    String SYSTEM_SETTINGS_URI = UltifiUriFactory.buildMethodUri(UAuthority.local(), SERVICE, SYSTEM_SETTINGS);

    Cache cache = LruCache.getInstance();

    static BaseMapper getMapper(int propertyId) {
        if (Zone.getPropertyList().contains(propertyId)) {
            return Zone.getInstance();
        }
        if (SystemSettings.getPropertyList().contains(propertyId)) {
            return SystemSettings.getInstance();
        }
        throw new IllegalArgumentException("illegal property id: " + propertyId);
    }

    static BaseMapper getMapper(String signalName) {
        if (SystemSettings.getSignals().contains(signalName)) {
            return SystemSettings.getInstance();
        }
        throw new IllegalArgumentException("illegal signal name: " + signalName);
    }

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