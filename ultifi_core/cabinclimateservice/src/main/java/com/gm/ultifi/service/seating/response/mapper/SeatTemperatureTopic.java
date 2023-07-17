package com.gm.ultifi.service.seating.response.mapper;

import android.util.Log;

import com.gm.ultifi.base.propertymanager.CarPropertyExtensionManager;
import com.gm.ultifi.base.response.config.PropertyConfig;
import com.gm.ultifi.base.response.mapper.BaseTopic;
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UAuthority;
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UResource;
import com.gm.ultifi.sdk.uprotocol.uri.factory.UltifiUriFactory;

import com.gm.ultifi.service.constant.ServiceConstant;
import com.gm.ultifi.service.seating.response.config.enums.SeatTemperatureEnum;
import com.gm.ultifi.vehicle.body.seating.v1.SeatTemperature;
import com.google.protobuf.Any;
import com.google.protobuf.Descriptors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeatTemperatureTopic implements BaseTopic {
    public final String TAG = SeatTemperatureTopic.class.getSimpleName();

    private static final List<Integer> mPropertyList = new ArrayList<>();
    static {
        //should add all supported property id to mPropertyList
        mPropertyList.add(356517131);
        mPropertyList.add(624971864);
        mPropertyList.add(624971865);
        mPropertyList.add(356517139);
        mPropertyList.add(624971866);
        mPropertyList.add(624971867);
    }

    private static Map<Integer, String> areaMap = new HashMap<>();
    static {
        areaMap.put(1, "seat.row1_left");
//        areaMap.put("seat.row1_center", ?);
        areaMap.put(4, "seat.row1_right");
        areaMap.put(16, "seat.row2_left");
//        areaMap.put("seat.row2_center", ?);
        areaMap.put(64, "seat.row2_right");
        areaMap.put(256, "seat.row3_left");
//        areaMap.put("seat.row3_center", ?);
        areaMap.put(1024, "seat.row3_right");
    }

    private static Map<Integer, List<Integer>> properMap = new HashMap<>();
    static {
//        [component, mode]heat:4, vent:3 ;  seat:2and3 ; leg:11?
        properMap.put(356517131, Arrays.asList(3,4)); //seat heated
        properMap.put(624971864, Arrays.asList(11,4));//LEG  HEATED
        properMap.put(624971865, Arrays.asList(6,4));//NECK HEATED
        properMap.put(356517139, Arrays.asList(3,3));//SEAT  VENT
        properMap.put(624971866, Arrays.asList(11,3));//LEG  VENT
        properMap.put(624971867, Arrays.asList(6,3));//NECK  VENT
    }

    private boolean isRepeatedSignal = false;

    private int mAreaID;

    private int propertyId;

    private int mStatus;
    @Override
    public boolean isRepeatedSignal() {
        return false;
    }

    public static SeatTemperatureTopic getInstance() {
        return new SeatTemperatureTopic();
    }

    public static List<Integer> getPropertyList() {
        return mPropertyList;
    }

    @Override
    public Map<String, Any> generateProtobufMessage(CarPropertyExtensionManager carPropertyExtensionManager, Object value, PropertyConfig config) {
        Descriptors.FieldDescriptor descriptor = SeatTemperature.getDescriptor().findFieldByName(config.getProtobufField());

        SeatTemperature.Builder builder = SeatTemperature.newBuilder();
        SeatTemperature.SeatComponentTemperatureStatus.Builder statusBuilder = SeatTemperature.SeatComponentTemperatureStatus.newBuilder();

        Log.i(TAG, "generateProtobufMessage: new value:" + value + ", field name: " + descriptor.getFullName());
        int val = (int) value;
        String seatName = areaMap.get(mAreaID);
        List<Integer> componentMode = properMap.get(propertyId);
        assert componentMode != null;
        SeatTemperature seatTemperature;
        int component = componentMode.get(0);
        int mode = componentMode.get(1);
        if(component == 3){
            // when component equals 3(seat), it includes 2 components(SC_BACK,SC_CUSHION)
            SeatTemperature.SeatComponentTemperatureStatus status1 = statusBuilder
                    .setTemperatureLevelValue(val)
                    .setComponentValue(2)
                    .setModeValue(mode)
                    .setTemperatureLevelValue(val)
                    .build();
            SeatTemperature.SeatComponentTemperatureStatus status2 = statusBuilder
                    .setTemperatureLevelValue(val)
                    .setComponentValue(3)
                    .setModeValue(mode)
                    .setTemperatureLevelValue(val)
                    .build();
            seatTemperature = builder.setSeatTemperatureStatus(0, status1)
                    .setSeatTemperatureStatus(1, status2).build();
        }
        else {
            SeatTemperature.SeatComponentTemperatureStatus status = statusBuilder
                    .setComponentValue(component)
                    .setModeValue(mode)
                    .setTemperatureLevelValue(val)
                    .build();
            seatTemperature = builder.setSeatTemperatureStatus(0, status).build();
        }
        String topicUri = getTopicUri(seatName);

        Map<String, Any> res = new HashMap<>();
        res.put(topicUri, Any.pack(seatTemperature));
        Log.i(TAG, "generate protobuf message, topic name: " + topicUri);
        return res;
    }

    public String getTopicUri(String resourceName) {
        UResource uResource = new UResource(resourceName, "", "SeatTemperature");
        return UltifiUriFactory.buildUProtocolUri(UAuthority.local(), ServiceConstant.SEATING_SERVICE, uResource);
    }


    @Override
    public PropertyConfig getConfig(int propertyId) {
        SeatTemperatureEnum e = SeatTemperatureEnum.getPropertyIdMap().getOrDefault(propertyId, null);
        if (e == null) {
            throw new IllegalArgumentException("illegal property id");
        }
        return e.getPropertyConfig();
    }
    @Override
    public PropertyConfig getConfig(String signalName) {
        return BaseTopic.super.getConfig(signalName);
    }

    @Override
    public void setAreaId(Integer areaId) {
        mAreaID = areaId;
    }

    @Override
    public void setPropertyStatus(int status) {
        mStatus = status;
    }

    @Override
    public void setPropertyId(int properId){
        propertyId = properId;
    }
}
