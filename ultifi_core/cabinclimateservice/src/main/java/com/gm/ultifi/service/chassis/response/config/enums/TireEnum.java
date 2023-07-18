package com.gm.ultifi.service.chassis.response.config.enums;

import com.gm.ultifi.base.propertymanager.ProtobufMessageIds;
import com.gm.ultifi.base.response.config.PropertyConfig;
import com.gm.ultifi.base.response.config.SignalInfo;
import com.gm.ultifi.base.utils.StreamUtils;


import java.util.Arrays;
import java.util.Map;

public enum TireEnum implements SignalInfo {
    TIRE_PRESSURE("TIRE_PRESSURE",1,392168201,
            new PropertyConfig.Builder()
                    .setProtobufField(ProtobufMessageIds.PRESSURE)
                    .setClassType(Integer.class)
                    .build()
    ),

    TIRE_PRESSURE_WARNING_LF("TIRE_PRESSURE_WARNING_LF",2,557846647,
            new PropertyConfig.Builder()
                    .setProtobufField(ProtobufMessageIds.PRESSURE_WARNING)
                    .setClassType(Integer.class)
                    .build()
    ),

    TIRE_PRESSURE_WARNING_LR("TIRE_PRESSURE_WARNING_LR",3,557846648,
            new PropertyConfig.Builder()
                    .setProtobufField(ProtobufMessageIds.PRESSURE_WARNING)
                    .setClassType(Integer.class)
                    .build()
    ),

    TIRE_PRESSURE_WARNING_RF("TIRE_PRESSURE_WARNING_RF",4,557846653,
            new PropertyConfig.Builder()
                    .setProtobufField(ProtobufMessageIds.PRESSURE_WARNING)
                    .setClassType(Integer.class)
                    .build()
    ),

    TIRE_PRESSURE_WARNING_RR("TIRE_PRESSURE_WARNING_RR",5,557846654,
            new PropertyConfig.Builder()
                    .setProtobufField(ProtobufMessageIds.PRESSURE_WARNING)
                    .setClassType(Integer.class)
                    .build()
    ),

    ;


    private final PropertyConfig mConfig;

    private final Integer mPropertyId;

    private final String mSignalName;

    private static final Map<Integer,TireEnum> PROPERTY_ID_MAP= StreamUtils.toMap(Arrays.stream(TireEnum.values()),TireEnum::getPropertyId);

    TireEnum(String name,int ordinal, Integer propertyId, PropertyConfig propertyConfig){
        mPropertyId = propertyId;
        mConfig = propertyConfig;
        mSignalName = propertyConfig.getSignalName();
    }

    @Override
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

    @Override
    public float getRate() {
        return mConfig.getRate();
    }

    public static Map<Integer, TireEnum> getPropertyIdMap() {
        return PROPERTY_ID_MAP;
    }

    public PropertyConfig getPropertyConfig() {
        return mConfig;
    }




}
