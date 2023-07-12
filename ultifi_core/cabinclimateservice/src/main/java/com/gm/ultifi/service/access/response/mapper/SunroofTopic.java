package com.gm.ultifi.service.access.response.mapper;

import static com.gm.ultifi.base.utils.SeatAreaIdConst.GLOBAL_AREA_ID;

import com.gm.ultifi.service.access.response.config.enums.SunroofEnum;
import com.gm.ultifi.service.constant.ResourceMappingConstants;
import com.gm.ultifi.service.constant.ServiceConstant;
import android.util.Log;

import com.gm.ultifi.base.response.mapper.BaseTopic;
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UAuthority;
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UResource;
import com.gm.ultifi.sdk.uprotocol.uri.factory.UltifiUriFactory;
import com.gm.ultifi.base.propertymanager.CarPropertyExtensionManager;
import com.gm.ultifi.base.response.config.PropertyConfig;
import com.google.protobuf.Any;
import com.google.protobuf.Descriptors;

import com.ultifi.vehicle.body.access.v1.Sunroof;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Sunroof related signal mapper
 */
public class SunroofTopic implements BaseTopic {

    private static final String TAG = SunroofTopic.class.getSimpleName();

    private static final List<Integer> mPropertyList = new ArrayList<>();

    private boolean isRepeatedSignal = false;

    private int mAreaID;

    private int mStatus;

    // TODO: 2023/7/1 这个Map用来干嘛的? 未知
    private final Map<String, Sunroof> sunroofBuilderMap;

    static {
        //should add all supported property id to mPropertyList
        mPropertyList.add(557856808);
    }

    private SunroofTopic() {
        sunroofBuilderMap = new HashMap<>();
    }

    public static SunroofTopic getInstance() {
        return new SunroofTopic();
    }

    public static List<Integer> getPropertyList() {
        return mPropertyList;
    }

    @Override
    public boolean isRepeatedSignal() {
        return isRepeatedSignal;
    }

    /**
     * implement logic FOR TOPIC
     * 1. create the SUNROOF message builder, and update the correct field value by parsing propertyConfig and value
     * 2. Pack sunroof message to Any
     * 3. should check if the property change effect multiple topics? If yes, generate each topic and map with the same sunroof message
     */
    @Override
    public Map<String, Any> generateProtobufMessage(
            CarPropertyExtensionManager carPropertyExtensionManager,
            Object value,
            PropertyConfig config) {

//        if (!checkSunroofInfo(carPropertyExtensionManager)) {
//            // TODO: 2023/4/21 confirm the field name and set value
//            Descriptors.FieldDescriptor sunroofMotionAllowed = Sunroof.getDescriptor().findFieldByName("sunroof_motion_allowed");
//            builder.setField(sunroofMotionAllowed, false);
//        }

        Descriptors.FieldDescriptor descriptor = Sunroof.getDescriptor().findFieldByName(config.getProtobufField());

        Sunroof.Builder builder = Sunroof.newBuilder();
        Sunroof sunroof = builder.setField(descriptor, value).build();
        Log.i(TAG, "generateProtobufMessage: new value:" + value + ", field name: " + descriptor.getFullName());

        String topicUri = getTopicUri(ResourceMappingConstants.SUNROOF_FRONT);

        Map<String, Any> res = new HashMap<>();
        res.put(topicUri, Any.pack(sunroof));
        Log.i(TAG, "generate protobuf message, topic name: " + topicUri);
        return res;
    }

    /**
     * should return property config
     */
    @Override
    public PropertyConfig getConfig(int propertyId) {
        SunroofEnum e = SunroofEnum.getPropertyIdMap().getOrDefault(propertyId, null);
        if (e == null) {
            throw new IllegalArgumentException("illegal property id");
        }
        return e.getPropertyConfig();
    }

    public String getTopicUri(String resourceName) {
        UResource uResource = new UResource(resourceName, "", Sunroof.class.getSimpleName());
        return UltifiUriFactory.buildUProtocolUri(UAuthority.local(), ServiceConstant.ACCESS_SERVICE, uResource);
    }

    @Override
    public void setAreaId(Integer areaId) {
        mAreaID = areaId;
    }

    @Override
    public void setPropertyStatus(int status) {
        mStatus = status;
    }

    public boolean checkSunroofInfo(CarPropertyExtensionManager carPropertyManager) {
        Boolean infotainmentSunroofMotionControlAvailable
                = carPropertyManager.isPropertyAvailable(557847224, GLOBAL_AREA_ID);

        Log.i(TAG, "infotainmentSunroofMotionControlAvailable: " + infotainmentSunroofMotionControlAvailable);

        Boolean infotainmentSunroofMotionControlAllowed =
                carPropertyManager.getBooleanProperty(555750073, GLOBAL_AREA_ID);

        Log.i(TAG, "infotainmentSunroofMotionControlAllowed: " + infotainmentSunroofMotionControlAllowed);

        return infotainmentSunroofMotionControlAvailable && infotainmentSunroofMotionControlAllowed;
    }
}