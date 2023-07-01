package com.gm.ultifi.service.access.response.mapper;

import com.gm.ultifi.base.propertymanager.CarPropertyExtensionManager;
import com.gm.ultifi.base.response.config.PropertyConfig;
import com.gm.ultifi.base.response.mapper.BaseMapper;
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UAuthority;
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UEntity;
import com.gm.ultifi.sdk.uprotocol.uri.factory.UltifiUriFactory;
import com.google.protobuf.Any;

import java.util.Map;

public class SeatMapper implements BaseMapper {

    public static final String BASE_URI_SERVICE = "body.access";

    public static final String VERSION = "1";

    public static final UEntity SERVICE = new UEntity(BASE_URI_SERVICE, VERSION);

    public static final String SEAT_RPC_SEAT_POSITION_METHOD = "UpdateSeatPosition";
    public static final String SEAT_RPC_SEAT_POSITION_GROUP_METHOD = "UpdateSeatPositionGroup";

    public static final String SEAT_RPC_SEAT_POSITION_METHOD_URI = UltifiUriFactory.buildMethodUri(UAuthority.local(), SERVICE, SEAT_RPC_SEAT_POSITION_METHOD);
    public static final String SEAT_RPC_SEAT_POSITION_GROUP_METHOD_URI = UltifiUriFactory.buildMethodUri(UAuthority.local(), SERVICE, SEAT_RPC_SEAT_POSITION_GROUP_METHOD);

    private static final String TAG = SeatMapper.class.getSimpleName();

    @Override
    public boolean isRepeatedSignal() {
        return false;
    }

    @Override
    public Map<String, Any> generateProtobufMessage(CarPropertyExtensionManager carPropertyExtensionManager, Object value, PropertyConfig config) {
        return null;
    }

    @Override
    public PropertyConfig getConfig(int propertyId) {
        return BaseMapper.super.getConfig(propertyId);
    }

    @Override
    public PropertyConfig getConfig(String signalName) {
        return BaseMapper.super.getConfig(signalName);
    }

    @Override
    public void setAreaId(Integer areaId) {
        BaseMapper.super.setAreaId(areaId);
    }

    @Override
    public void setPropertyStatus(int status) {
        BaseMapper.super.setPropertyStatus(status);
    }
}
