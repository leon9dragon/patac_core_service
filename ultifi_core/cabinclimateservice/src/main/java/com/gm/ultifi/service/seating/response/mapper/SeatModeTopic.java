package com.gm.ultifi.service.seating.response.mapper;

import static com.gm.ultifi.service.constant.ResourceMappingConstants.SUNROOF_FRONT;

import android.util.Log;

import com.gm.ultifi.base.propertymanager.CarPropertyExtensionManager;
import com.gm.ultifi.base.response.config.PropertyConfig;
import com.gm.ultifi.base.response.mapper.BaseTopic;
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UAuthority;
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UResource;
import com.gm.ultifi.sdk.uprotocol.uri.factory.UltifiUriFactory;
import com.gm.ultifi.service.constant.ServiceConstant;
import com.google.protobuf.Any;
import com.google.protobuf.Descriptors;
import com.ultifi.vehicle.body.access.v1.Sunroof;

import java.util.HashMap;
import java.util.Map;

public class SeatModeTopic implements BaseTopic {

    private static final String TAG = SeatModeTopic.class.getSimpleName();

    @Override
    public boolean isRepeatedSignal() {
        return false;
    }

    @Override
    public Map<String, Any> generateProtobufMessage(CarPropertyExtensionManager carPropertyExtensionManager, Object value, PropertyConfig config) {
//        Descriptors.FieldDescriptor descriptor = SeatMode.getDescriptor().findFieldByName(config.getProtobufField());
//
//        Sunroof.Builder builder = Sunroof.newBuilder();
//        Sunroof sunroof = builder.setField(descriptor, value).build();
//        Log.i(TAG, "generateProtobufMessage: new value:" + value + ", field name: " + descriptor.getFullName());
//
//        String topicUri = getTopicUri(SUNROOF_FRONT);
//
//        Map<String, Any> res = new HashMap<>();
//        res.put(topicUri, Any.pack(sunroof));
//        Log.i(TAG, "generate protobuf message, topic name: " + topicUri);
//        return res;

        return null;
    }

//    public String getTopicUri(String resourceName) {
//        UResource uResource = new UResource(resourceName, "", SeatMode.class.getSimpleName());
//        return UltifiUriFactory.buildUProtocolUri(UAuthority.local(), ServiceConstant.SEATING_SERVICE, uResource);
//    }

    @Override
    public PropertyConfig getConfig(int propertyId) {
        return BaseTopic.super.getConfig(propertyId);
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
