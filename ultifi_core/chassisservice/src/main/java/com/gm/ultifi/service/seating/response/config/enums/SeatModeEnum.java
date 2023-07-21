package com.gm.ultifi.service.seating.response.config.enums;

import com.gm.ultifi.base.propertymanager.ProtobufMessageIds;
import com.gm.ultifi.base.response.config.PropertyConfig;
import com.gm.ultifi.base.response.config.SignalInfo;
import com.gm.ultifi.base.utils.StreamUtils;

import java.util.Arrays;
import java.util.Map;

public enum SeatModeEnum implements SignalInfo {
    CURRENT_SEAT_MODE("CURRENT_SEAT_MODE", 1, 557856808,
                             new PropertyConfig.Builder()
    .setProtobufField(ProtobufMessageIds.POSITION).setClassType(Integer.class).build()),

    AVAILABLE_SEATED_MODE("AVAILABLE_SEATED_MODE", 2, 557928530,
            new PropertyConfig.Builder()
                    .setProtobufField(ProtobufMessageIds.POSITION).setClassType(Integer.class).build()),

    SUPPORTED_SEATED_MODE("SUPPORTED_SEATED_MODE", 3, 557928530,
                             new PropertyConfig.Builder()
    .setProtobufField(ProtobufMessageIds.POSITION).setClassType(Integer.class).build());
    private final PropertyConfig mConfig;

    private final Integer mPropertyId;

    private final String mSignalName;

    SeatModeEnum(String name, int ordinal, Integer propertyId, PropertyConfig propertyConfig) {
        mPropertyId = propertyId;
        mConfig = propertyConfig;
        mSignalName = propertyConfig.getSignalName();
    }

    private static final Map<Integer, SeatModeEnum> PROPERTY_ID_MAP = StreamUtils.toMap(Arrays.stream(SeatModeEnum.values()), SeatModeEnum::getPropertyId);


    @Override
    public String getResName() {
        return null;
    }

    public static Map<Integer, SeatModeEnum> getPropertyIdMap() {
        return PROPERTY_ID_MAP;
    }


    @Override
    public String getSignal() {
        return null;
    }

    @Override
    public Integer getPropertyId() {
        return SignalInfo.super.getPropertyId();
    }

    public PropertyConfig getPropertyConfig() {
        return mConfig;
    }

    @Override
    public float getRate() {
        return SignalInfo.super.getRate();
    }
}
