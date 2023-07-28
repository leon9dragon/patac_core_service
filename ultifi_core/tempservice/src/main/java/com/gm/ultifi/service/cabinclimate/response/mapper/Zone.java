package com.gm.ultifi.service.cabinclimate.response.mapper;

import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UAuthority;
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UResource;
import com.gm.ultifi.sdk.uprotocol.uri.factory.UltifiUriFactory;
import com.gm.ultifi.service.cabinclimate.manager.propertymanager.CarPropertyExtensionManager;
import com.gm.ultifi.service.cabinclimate.response.config.PropertyConfig;
import com.google.protobuf.Any;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Zone related signal mapper
 */
public class Zone implements BaseMapper {

    private static final String TAG = Zone.class.getSimpleName();

    private static final int MAX_TEMP_SET_POINT = 127;

    private static final int MIN_VALUE_ZERO = 0;

    private static final List<Integer> mPropertyList = new ArrayList<>();

    private boolean isRepeatedSignal = false;

    private int mAreaID;

    private int mStatus;

    private final Map<String, com.ultifi.vehicle.body.cabin_climate.v1.Zone.Builder> zoneBuilderMap;

    static {
        //Todo should add all supported property id to mPropertyList
    }

    private Zone() {
        zoneBuilderMap = new HashMap<>();
    }

    public static Zone getInstance() {
        return new Zone();
    }

    public static List<Integer> getPropertyList() {
        return mPropertyList;
    }

    @Override
    public boolean isRepeatedSignal() {
        return isRepeatedSignal;
    }

    @Override
    public Map<String, Any> generateProtobufMessage(
            CarPropertyExtensionManager carPropertyExtensionManager,
            Object value,
            PropertyConfig config) {
        //Todo need implement logic
        //Todo 1. create the Zone message builder, and update the correct field value by parsing propertyConfig and value
        //Todo 2. Pack zone message to Any
        //Todo 3. should check if the property change effect multiple topics? If yes, generate each topic and map with the same zone message
        return null;
    }

    @Override
    public PropertyConfig getConfig(int propertyId) {
        //Todo should return  property config
        //return ZoneEnum.get(propertyId).getConfig();
        return null;
    }

    @Override
    public void setAreaId(Integer areaId) {
        mAreaID = areaId;
    }

    @Override
    public void setPropertyStatus(int status) {
        mStatus = status;
    }

    public String getUri(String name) {
        UResource uResource = new UResource(name, "", Zone.class.getSimpleName());
        return UltifiUriFactory.buildUProtocolUri(UAuthority.local(), SERVICE, uResource);
    }
}