package com.gm.ultifi.service.seating.response.mapper;

import com.gm.ultifi.base.propertymanager.CarPropertyExtensionManager;
import com.gm.ultifi.base.response.config.PropertyConfig;
import com.gm.ultifi.base.response.mapper.BaseMapper;
import com.google.protobuf.Any;

import java.util.Map;

public class SeatMapper implements BaseMapper {

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
