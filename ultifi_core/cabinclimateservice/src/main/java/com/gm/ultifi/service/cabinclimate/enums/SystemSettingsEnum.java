package com.gm.ultifi.service.cabinclimate.enums;

import com.gm.ultifi.service.cabinclimate.manager.propertymanager.ProtobufMessageIds;
import com.gm.ultifi.service.cabinclimate.response.config.PropertyConfig;

/**
 * Todo should define SystemSettings related signal enum
 */
public enum SystemSettingsEnum implements SignalInfo {

    //此处需要定义service涉及到的所有CarProperty
    HVAC_POWER_ON("HVAC_SYNC_ON", 9,
            0x252013C1, //VendorProperty.HVAC_SYNC_ON,
            new PropertyConfig.Builder()
                    .setProtobufField(ProtobufMessageIds.SYNC_ALL)
                    .setClassType(Boolean.class)
                    .build()
    );

    private final PropertyConfig mConfig;

    private final Integer mPropertyId;

    private final String mSignalName;

    SystemSettingsEnum(String name, int ordinal, Integer propertyId, PropertyConfig propertyConfig) {
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