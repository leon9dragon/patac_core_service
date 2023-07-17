package com.gm.ultifi.service.seating.response.mapper;

import static com.gm.ultifi.base.utils.SeatAreaIdConst.GLOBAL_AREA_ID;

import android.util.Log;

import com.gm.ultifi.base.propertymanager.CarPropertyExtensionManager;
import com.gm.ultifi.base.response.config.PropertyConfig;
import com.gm.ultifi.base.response.mapper.BaseTopic;
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UAuthority;
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UResource;
import com.gm.ultifi.sdk.uprotocol.uri.factory.UltifiUriFactory;
import com.gm.ultifi.service.constant.ServiceConstant;
import com.gm.ultifi.service.seating.response.config.enums.SeatModeEnum;
import com.gm.ultifi.service.seating.response.config.enums.SeatTemperatureEnum;
import com.gm.ultifi.vehicle.body.seating.v1.SeatMode;
import com.google.protobuf.Any;
import com.google.protobuf.Descriptors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeatModeTopic implements BaseTopic {

    private static final String TAG = SeatModeTopic.class.getSimpleName();

    private boolean isRepeatedSignal = false;

    private static final List<Integer> mPropertyList = new ArrayList<>();

    private int mAreaID;

    private int propertyId;

    private int mStatus;

    @Override
    public boolean isRepeatedSignal() {
        return false;
    }

    public static SeatModeTopic getInstance() {
        return new SeatModeTopic();
    }

    static {
        //should add all supported property id to mPropertyList
        mPropertyList.add(557928535);
    }

    @Override
    public Map<String, Any> generateProtobufMessage(CarPropertyExtensionManager carPropertyExtensionManager, Object value, PropertyConfig config) {
        Descriptors.FieldDescriptor support_des = SeatMode.getDescriptor().findFieldByName("supported_modes");
        Descriptors.FieldDescriptor available_des = SeatMode.getDescriptor().findFieldByName("available_modes");

        SeatMode.Builder builder = SeatMode.newBuilder();
        SeatMode.SeatModeStatus.Builder statusBuilder = SeatMode.SeatModeStatus.newBuilder();
        int val = (int) value;

        Integer[] valList = getListModes(carPropertyExtensionManager, 557928535);

        Integer[] supportedMode = getListModes(carPropertyExtensionManager, 557928530);
        Integer[] availableMode = getListModes(carPropertyExtensionManager, 557928531);

        Integer currentVal = getCurrentVal(valList, val);
        boolean inPosition = false;
        boolean inMotion = false;
        if (currentVal == 1) {
            inPosition = true;
        } else if (currentVal == 2) {
            inMotion = true;
        }

        SeatMode.SeatModeStatus status = statusBuilder.setModeValue(val)
                .setIsInModeMotion(inMotion)
                .setIsInModePosition(inPosition)
                .build();

        builder.setCurrentModes(0, status);
        for (int i = 0; i < supportedMode.length; i++) {
            builder.setRepeatedField(support_des, i, supportedMode[i]);
        }
        for (int i = 0; i < availableMode.length; i++) {
            builder.setRepeatedField(available_des, i, availableMode[i]);
        }
        SeatMode mode = builder.build();

        String topicUri = getTopicUri("seat_mode");

        Map<String, Any> res = new HashMap<>();
        res.put(topicUri, Any.pack(mode));
        Log.i(TAG, "generate protobuf message, topic name: " + topicUri);
        return res;
    }

    protected Integer getCurrentVal(Integer[] valList, Integer modeVal) {
        int index = modeVal - 1;
        return valList[index];
    }

    protected Integer[] getListModes(CarPropertyExtensionManager carPropertyExtensionManager, Integer propertyId) {
        return carPropertyExtensionManager.getListProperty(propertyId, GLOBAL_AREA_ID);
    }

    public String getTopicUri(String resourceName) {
        UResource uResource = new UResource(resourceName, "", "SeatMode");
        return UltifiUriFactory.buildUProtocolUri(UAuthority.local(), ServiceConstant.SEATING_SERVICE, uResource);
    }

    @Override
    public PropertyConfig getConfig(int propertyId) {
        SeatModeEnum e = SeatModeEnum.getPropertyIdMap().getOrDefault(propertyId, null);
        if (e == null) {
            throw new IllegalArgumentException("illegal property id");
        }
        return e.getPropertyConfig();
    }

    public static List<Integer> getPropertyList() {
        return mPropertyList;
    }

    @Override
    public PropertyConfig getConfig(String signalName) {
        return BaseTopic.super.getConfig(signalName);
    }

    @Override
    public void setAreaId(Integer areaId) {
        BaseTopic.super.setAreaId(areaId);
    }

    @Override
    public void setPropertyStatus(int status) {
        BaseTopic.super.setPropertyStatus(status);
    }
}
