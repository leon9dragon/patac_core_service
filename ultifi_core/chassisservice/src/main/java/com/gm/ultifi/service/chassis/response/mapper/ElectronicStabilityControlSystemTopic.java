package com.gm.ultifi.service.chassis.response.mapper;

import android.util.Log;

import com.gm.ultifi.base.propertymanager.CarPropertyExtensionManager;
import com.gm.ultifi.base.response.config.PropertyConfig;
import com.gm.ultifi.base.response.mapper.BaseTopic;
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UAuthority;
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UResource;
import com.gm.ultifi.sdk.uprotocol.uri.factory.UltifiUriFactory;
import com.gm.ultifi.service.chassis.response.config.enums.ESCEnum;
import com.gm.ultifi.service.chassis.response.config.enums.TractionEnum;
import com.gm.ultifi.service.constant.ResourceMappingConstants;
import com.gm.ultifi.service.constant.ServiceConstant;
import com.google.protobuf.Any;
import com.google.protobuf.Descriptors;
import com.ultifi.vehicle.chassis.v1.ElectronicStabilityControlSystem;
import com.ultifi.vehicle.chassis.v1.TractionControlSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElectronicStabilityControlSystemTopic implements BaseTopic {
    private static final String TAG= ElectronicStabilityControlSystemTopic.class.getSimpleName();
    private static final List<Integer> mPropertyList = new ArrayList<>();
    private boolean isRepeatedSignal = false;
    private int mAreaID;
    private int mStatus;

    static {
        mPropertyList.add(557857071);

    }

    public PropertyConfig getConfig(int propertyId) {
        ESCEnum e = ESCEnum.getPropertyIdMap().getOrDefault(propertyId,null);
        if (e.equals(null)){
            throw new IllegalArgumentException("illegal property id");
        }
        return e.getPropertyConfig();
    }
    public String getTopicUri(String resourceName) {
        UResource uResource = new UResource(resourceName,"", ElectronicStabilityControlSystemTopic.class.getSimpleName());
        return UltifiUriFactory.buildUProtocolUri(UAuthority.local(), ServiceConstant.CHASSIS_SERVICE,uResource);
    }

    @Override
    public boolean isRepeatedSignal() {
        return isRepeatedSignal;
    }

    @Override
    public Map<String, Any> generateProtobufMessage(CarPropertyExtensionManager carPropertyExtensionManager, Object value, PropertyConfig config) {
        Descriptors.FieldDescriptor descriptor= ElectronicStabilityControlSystem.getDescriptor().findFieldByName(config.getProtobufField());
        ElectronicStabilityControlSystem.Builder builder=ElectronicStabilityControlSystem.newBuilder();
        ElectronicStabilityControlSystem electronicStabilityControlSystem;

        if(value.equals(1)){
            electronicStabilityControlSystem = builder.setField(descriptor,false).build();
        }
        else {
            electronicStabilityControlSystem = builder.setField(descriptor,true).build();
        }


        Log.i(TAG,"GenerateProtobufMessage: new value: " + value + ",field name:" + descriptor.getFullName());

        String topicUri = getTopicUri(ResourceMappingConstants.ELECTRONIC_STABILITY_CONTROL);
        Map<String, Any> res = new HashMap<>();
        res.put(topicUri,Any.pack(electronicStabilityControlSystem));
        Log.i(TAG, "generate protobuf message, topic name: " + topicUri);

        return res;
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
    public void setPropertyId(int proper) {
        BaseTopic.super.setPropertyId(proper);
    }

    public static ElectronicStabilityControlSystemTopic getInstance(){
        return new ElectronicStabilityControlSystemTopic();
    }

    public static List<Integer> getPropertyList() {
        return mPropertyList;
    }



}
