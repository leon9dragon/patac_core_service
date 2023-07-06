package com.gm.ultifi.service.seating.response.config.enums;

import com.gm.ultifi.base.propertymanager.ProtobufMessageIds;
import com.gm.ultifi.base.response.config.PropertyConfig;
import com.gm.ultifi.base.response.config.SignalInfo;
import com.gm.ultifi.base.utils.StreamUtils;
import com.gm.ultifi.service.access.response.config.enums.SunroofEnum;

import java.util.Arrays;
import java.util.Map;

public enum SeatEnum implements SignalInfo {

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

    private static final Map<Integer, SeatEnum> PROPERTY_ID_MAP = StreamUtils.toMap(Arrays.stream(SeatEnum.values()), SeatEnum::getPropertyId);

    SeatEnum(String name, int ordinal, Integer propertyId, PropertyConfig propertyConfig) {
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

    @Override
    public Integer getPropertyId() {
        return SignalInfo.super.getPropertyId();
    }

    @Override
    public float getRate() {
        return SignalInfo.super.getRate();
    }
}
