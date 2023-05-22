package com.gm.ultifi.service.cabinclimate.enums;

import com.gm.ultifi.service.cabinclimate.manager.propertymanager.ProtobufMessageIds;
import com.gm.ultifi.service.cabinclimate.response.config.PropertyConfig;

/**
 * Todo should define Zone related signal enum
 */
public enum ZoneEnum implements SignalInfo {

    //此处需要定义service涉及到的所有CarProperty
    HVAC_POWER_ON("HVAC_POWER_ON", 1,
            0x15200510, //VehicleProperty.HVAC_POWER_ON,
            new PropertyConfig.Builder()
                    .setProtobufField(ProtobufMessageIds.POWER_ON)
                    .setClassType(Boolean.class)
                    .build()
    );

    private final PropertyConfig mConfig;

    private final Integer mPropertyId;

    private final String mSignalName;

    ZoneEnum(String name, int ordinal, Integer propertyId, PropertyConfig propertyConfig) {
        mPropertyId = propertyId;
        mConfig = propertyConfig;
        mSignalName = propertyConfig.getSignalName();
    }

    public Integer getPropertyId() {
        return mPropertyId;
    }

    @Override
    public String getResName() {
        return mConfig.getResName();
    }

    @Override
    public String getSignal() {
        return mSignalName;
    }

    public float getRate() {
        return mConfig.getRate();
    }
}