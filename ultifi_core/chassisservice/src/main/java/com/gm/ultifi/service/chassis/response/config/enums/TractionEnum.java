package com.gm.ultifi.service.chassis.response.config.enums;

import com.gm.ultifi.base.propertymanager.ProtobufMessageIds;
import com.gm.ultifi.base.response.config.PropertyConfig;
import com.gm.ultifi.base.response.config.SignalInfo;
import com.gm.ultifi.base.utils.StreamUtils;

import java.util.Arrays;
import java.util.Map;

public enum TractionEnum implements SignalInfo {
    TRACTION_IS_ENABLED("TRACTION_IS_ENABLED",1,557857071, new PropertyConfig.Builder()
            .setProtobufField(ProtobufMessageIds.IS_ENABLED)
            .setClassType(Boolean.class)
            .build()
    );
    private final PropertyConfig mConfig;

    private final Integer mPropertyId;

    private final String mSignalName;

    private static final Map<Integer,TractionEnum> PROPERTY_ID_MAP= StreamUtils.toMap(Arrays.stream(TractionEnum.values()),TractionEnum::getPropertyId);

    TractionEnum(String name, int ordinal, Integer propertyId,PropertyConfig propertyConfig){
        mPropertyId = propertyId;
        mConfig = propertyConfig;
        mSignalName = propertyConfig.getSignalName();
    }




    public Integer getPropertyId(){
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

    public static Map<Integer, TractionEnum> getPropertyIdMap() {
        return PROPERTY_ID_MAP;
    }

    public PropertyConfig getPropertyConfig() {
        return mConfig;
    }
}
