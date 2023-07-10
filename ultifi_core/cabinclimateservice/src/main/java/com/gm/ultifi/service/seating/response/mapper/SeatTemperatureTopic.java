package com.gm.ultifi.service.seating.response.mapper;

import android.util.Log;

import com.gm.ultifi.base.propertymanager.CarPropertyExtensionManager;
import com.gm.ultifi.base.response.config.PropertyConfig;
import com.gm.ultifi.base.response.mapper.BaseTopic;
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UAuthority;
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UResource;
import com.gm.ultifi.sdk.uprotocol.uri.factory.UltifiUriFactory;

import com.gm.ultifi.service.constant.ServiceConstant;
import com.gm.ultifi.service.seating.response.config.enums.SeatEnum;
import com.gm.ultifi.vehicle.body.seating.v1.SeatTemperature;
import com.google.protobuf.Any;
import com.google.protobuf.Descriptors;

import java.util.ArrayList;
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

    private boolean isRepeatedSignal = false;

    private int mAreaID;

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
        SeatTemperature seatTemperature = builder.setField(descriptor, value).build();
        Log.i(TAG, "generateProtobufMessage: new value:" + value + ", field name: " + descriptor.getFullName());

        String seatName = seatTemperature.getName();

        String topicUri = getTopicUri(seatName);

        Map<String, Any> res = new HashMap<>();
        res.put(topicUri, Any.pack(seatTemperature));
        Log.i(TAG, "generate protobuf message, topic name: " + topicUri);
        return res;
    }

    public String getTopicUri(String resourceName) {
        UResource uResource = new UResource(resourceName, "", SeatTemperature.class.getSimpleName());
        return UltifiUriFactory.buildUProtocolUri(UAuthority.local(), ServiceConstant.SEATING_SERVICE, uResource);
    }


    @Override
    public PropertyConfig getConfig(int propertyId) {
        SeatEnum e = SeatEnum.getPropertyIdMap().getOrDefault(propertyId, null);
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
        BaseTopic.super.setAreaId(areaId);
    }

    @Override
    public void setPropertyStatus(int status) {
        BaseTopic.super.setPropertyStatus(status);
    }
}
