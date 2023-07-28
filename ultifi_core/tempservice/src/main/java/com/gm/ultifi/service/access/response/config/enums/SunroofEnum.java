package com.gm.ultifi.service.access.response.config.enums;

import com.gm.ultifi.service.access.manager.propertymanager.ProtobufMessageIds;
import com.gm.ultifi.service.access.response.config.PropertyConfig;
import com.gm.ultifi.utils.StreamUtils;

import java.util.Arrays;
import java.util.Map;

/**
 * should define Sunroof related signal enum
 */
public enum SunroofEnum implements SignalInfo {

    //此处需要定义service涉及到的所有CarProperty, 557856808
    SUNROOF_PERCENTAGE_POSITION_STATUS("SUNROOF_PERCENTAGE_POSITION_STATUS", 1,
            557856808,
            new PropertyConfig.Builder()
                    .setProtobufField(ProtobufMessageIds.POSITION)
                    .setClassType(Integer.class)
                    .build()
    );

    private final PropertyConfig mConfig;

    private final Integer mPropertyId;

    private final String mSignalName;

    private static final Map<Integer, SunroofEnum> PROPERTY_ID_MAP = StreamUtils.toMap(Arrays.stream(SunroofEnum.values()), SunroofEnum::getPropertyId);

    SunroofEnum(String name, int ordinal, Integer propertyId, PropertyConfig propertyConfig) {
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

    public static Map<Integer, SunroofEnum> getPropertyIdMap() {
        return PROPERTY_ID_MAP;
    }

    public PropertyConfig getPropertyConfig() {
        return mConfig;
    }
}