package com.gm.ultifi.service.seating.response.config.enums;

import com.gm.ultifi.base.propertymanager.ProtobufMessageIds;
import com.gm.ultifi.base.response.config.PropertyConfig;
import com.gm.ultifi.base.response.config.SignalInfo;
import com.gm.ultifi.base.utils.StreamUtils;

import java.util.Arrays;
import java.util.Map;

public enum SeatTemperatureEnum implements SignalInfo {

    //此处需要定义service涉及到的所有CarProperty, 557856808
    SEAT_HEATED("SEAT_HEATED", 1,
            356517131,
            new PropertyConfig.Builder()
                    .setProtobufField(ProtobufMessageIds.TEMPERATURE_LEVEL)
                    .setClassType(Integer.class)
                    .build()
    ),
    SEAT_VENT("SEAT_VENT", 2,
            356517139,
            new PropertyConfig.Builder()
                    .setProtobufField(ProtobufMessageIds.TEMPERATURE_LEVEL)
                    .setClassType(Integer.class)
                    .build()
    ),
    LEG_HEATED("LEG_HEATED", 3,
            624971864, new PropertyConfig.Builder()
            .setProtobufField(ProtobufMessageIds.TEMPERATURE_LEVEL)
            .setClassType(Integer.class)
            .build()
    ),
    LEG_VENT("LEG_VENT", 4,
            624971866, new PropertyConfig.Builder()
            .setProtobufField(ProtobufMessageIds.TEMPERATURE_LEVEL)
            .setClassType(Integer.class)
            .build()
    ),
    NECK_HEATED("LEG_VENT", 5,
            624971865, new PropertyConfig.Builder()
            .setProtobufField(ProtobufMessageIds.TEMPERATURE_LEVEL)
            .setClassType(Integer.class)
            .build()
    ),
    NECK_VENT("LEG_VENT", 6,
            624971867, new PropertyConfig.Builder()
            .setProtobufField(ProtobufMessageIds.TEMPERATURE_LEVEL)
            .setClassType(Integer.class)
            .build()
    );

    private final PropertyConfig mConfig;

    private final Integer mPropertyId;

    private final String mSignalName;

    private static final Map<Integer, SeatTemperatureEnum> PROPERTY_ID_MAP = StreamUtils.toMap(Arrays.stream(SeatTemperatureEnum.values()), SeatTemperatureEnum::getPropertyId);

    SeatTemperatureEnum(String name, int ordinal, Integer propertyId, PropertyConfig propertyConfig) {
        mPropertyId = propertyId;
        mConfig = propertyConfig;
        mSignalName = propertyConfig.getSignalName();
    }

    @Override
    public String getResName() {
        return null;
    }

    @Override
    public String getSignal() {
        return null;
    }

    public static Map<Integer, SeatTemperatureEnum> getPropertyIdMap() {
        return PROPERTY_ID_MAP;
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
