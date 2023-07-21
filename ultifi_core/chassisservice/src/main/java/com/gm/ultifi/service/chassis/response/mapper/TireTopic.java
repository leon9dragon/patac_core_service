package com.gm.ultifi.service.chassis.response.mapper;

import android.util.Log;

import com.gm.ultifi.base.propertymanager.CarPropertyExtensionManager;
import com.gm.ultifi.base.response.config.PropertyConfig;
import com.gm.ultifi.base.response.mapper.BaseTopic;
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UAuthority;
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UResource;
import com.gm.ultifi.sdk.uprotocol.uri.factory.UltifiUriFactory;
import com.gm.ultifi.service.chassis.response.config.enums.TireEnum;
import com.gm.ultifi.service.constant.ResourceMappingConstants;
import com.gm.ultifi.service.constant.ServiceConstant;
import com.google.protobuf.Any;
import com.google.protobuf.Descriptors;
import com.ultifi.vehicle.chassis.v1.Tire;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TireTopic implements BaseTopic {

    private static final String TAG = TireTopic.class.getSimpleName();
    private static final List<Integer> mPropertyList = new ArrayList<>();
    private boolean isRepeatedSignal = false;
    private int mAreaID;
    private int mStatus;

    private Integer mPropertyId;

    static {
        mPropertyList.add(392168201);
        mPropertyList.add(557846647);
        mPropertyList.add(557846648);
        mPropertyList.add(557846653);
        mPropertyList.add(557846654);
    }

    private static Map<Integer, String> areaMap = new HashMap<>();
    static {
        areaMap.put(1,"tire.front_left");
        areaMap.put(2,"tire.front_right");
        areaMap.put(4,"tire.rear_left");
        areaMap.put(8,"tire.rear_right");
    }


    @Override
    public boolean isRepeatedSignal() {
        return isRepeatedSignal;
    }

    @Override
    public Map<String, Any> generateProtobufMessage(CarPropertyExtensionManager carPropertyExtensionManager, Object value, PropertyConfig config) {
        Descriptors.FieldDescriptor descriptor = Tire.getDescriptor().findFieldByName(config.getProtobufField());
        Tire.Builder builder = Tire.newBuilder();

        if ("pressure_warning".equals(config.getProtobufField())) {
            Tire.TirePressureState state = Tire.TirePressureState.forNumber((Integer) value);
            builder.setPressureWarning(state);
        }

        if ("pressure".equals(config.getProtobufField())) {
            builder.setPressure((Float) value);
        }


        Tire tire = builder.build();
        Log.i(TAG, "GenerateProtobufMessage: new value:" + value + ",field name:" + descriptor.getFullName());

        String TireDirection = areaMap.get(mAreaID);
        String topicUri = null;
        if (mPropertyList.equals(557846647)) {
            topicUri = getTopicUri(ResourceMappingConstants.TIRE_FRONT_LEFT);

        }

        if (mPropertyList.equals(557846648)) {
            topicUri = getTopicUri(ResourceMappingConstants.TIRE_REAR_LEFT);

        }

        if (mPropertyList.equals(557846653)) {
            topicUri = getTopicUri(ResourceMappingConstants.TIRE_FRONT_RIGHT);

        }

        if (mPropertyList.equals(557846654)) {
            topicUri = getTopicUri(ResourceMappingConstants.TIRE_REAR_RIGHT);

        }

        if (mPropertyList.equals(392168201)) {
            topicUri = getTopicUri(TireDirection);

        }
        Map<String, Any> res = new HashMap<>();
        if (topicUri != null) {
            res.put(topicUri, Any.pack(tire));
            Log.i(TAG, "generate protobuf message, topic name: " + topicUri);
            return res;
        }

        return res;
    }

    @Override
    public PropertyConfig getConfig(int propertyId) {
        TireEnum e= TireEnum.getPropertyIdMap().getOrDefault(propertyId,null);
        if (e == null){
            throw new IllegalArgumentException("illegal property id");
        }
        return e.getPropertyConfig();
    }
    public String getTopicUri(String resourceName){
       UResource uResource = new UResource(resourceName,"",Tire.class.getSimpleName());
       return UltifiUriFactory.buildUProtocolUri(UAuthority.local(), ServiceConstant.CHASSIS_SERVICE,uResource);
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
        mStatus=status;
    }

    @Override
    public void setPropertyId(int proper){
        mPropertyId=proper;
    }






    public static TireTopic getInstance() {
        return new TireTopic();
    }

    public static List<Integer> getPropertyList() {
        return mPropertyList;
    }







}
