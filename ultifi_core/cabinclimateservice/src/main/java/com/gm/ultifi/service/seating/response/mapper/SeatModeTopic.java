package com.gm.ultifi.service.seating.response.mapper;

import com.gm.ultifi.base.propertymanager.CarPropertyExtensionManager;
import com.gm.ultifi.base.response.config.PropertyConfig;
import com.gm.ultifi.base.response.mapper.BaseTopic;
import com.google.protobuf.Any;

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
