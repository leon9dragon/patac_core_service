package com.gm.ultifi.service.access.response.mapper;

import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UAuthority;
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UEntity;
import com.gm.ultifi.sdk.uprotocol.uri.factory.UltifiUriFactory;
import com.gm.ultifi.service.access.manager.propertymanager.CarPropertyExtensionManager;
import com.gm.ultifi.service.access.response.config.PropertyConfig;
import com.gm.ultifi.service.access.utils.cache.Cache;
import com.gm.ultifi.service.access.utils.cache.LruCache;
import com.google.protobuf.Any;

import java.util.Map;

/**
 * TODO: 2023/4/26 框架代码抽离，那些订阅URI的常量，应该放在继承的类里面。
 */
public interface BaseMapper {

    String BASE_URI_SERVICE = "body.access";

    String VERSION = "1";

    UEntity SERVICE = new UEntity(BASE_URI_SERVICE, VERSION);

    String SUNROOF_RESOURCE = "ExecuteSunroofCommand";

    String SUNROOF_RESOURCE_URI = UltifiUriFactory.buildMethodUri(UAuthority.local(), SERVICE, SUNROOF_RESOURCE);

    Cache cache = LruCache.getInstance();

    /*
     * to find the processor by property/signal
     */
    static BaseMapper getMapper(int propertyId) {
        if (SunroofMapper.getPropertyList().contains(propertyId)) {
            return SunroofMapper.getInstance();
        }
        throw new IllegalArgumentException("illegal property id: " + propertyId);
    }

    static BaseMapper getMapper(String signalName) {
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