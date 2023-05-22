package com.gm.ultifi.service.cabinclimate.response.mapper;

import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UAuthority;
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UResource;
import com.gm.ultifi.sdk.uprotocol.uri.factory.UltifiUriFactory;
import com.gm.ultifi.service.cabinclimate.manager.propertymanager.CarPropertyExtensionManager;
import com.gm.ultifi.service.cabinclimate.response.config.PropertyConfig;
import com.google.protobuf.Any;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * SystemSettings related signal mapper
 */
public class SystemSettings implements BaseMapper {

    private static final String TAG = SystemSettings.class.getSimpleName();

    private static final double MAX_ESTD_CBN_TEMP = 87.5D;
    private static final int MIN_ESTD_CBN_TEMP = -40;

    private static final List<Integer> mPropertyList = new ArrayList<>();
    protected static final List<String> mSystemSettingsSignals = new ArrayList<>();

    private boolean isRepeatedSignal = false;

    private int mAreaID;

    static {
        //Todo should add all supported signal's name to mSystemSettingsSignals
    }

    private SystemSettings() {
    }

    public static SystemSettings getInstance() {
        return new SystemSettings();
    }

    public static List<Integer> getPropertyList() {
        return mPropertyList;
    }

    public static List<String> getSignals() {
        return mSystemSettingsSignals;
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
        //Todo 1. create the SystemSettings message builder, and update the correct field value by parsing propertyConfig and value
        //Todo 2. Pack SystemSettings message to Any
        //Todo 3. should check if the property change effect multiple topics? If yes, generate each topic and map with the same SystemSettings message
        return null;
    }

    @Override
    public PropertyConfig getConfig(int propertyId) {
        //Todo should return property config
        //return SystemSettingsEnum.get(propertyId).getConfig();
        return null;
    }

    @Override
    public PropertyConfig getConfig(String signalName) {
        //Todo should return property config
        //return SystemSettingsEnum.get(signalName).getConfig();
        return null;
    }

    @Override
    public void setAreaId(Integer areaId) {
        mAreaID = areaId;
    }

    public String getUri() {
        UResource uResource = new UResource(SYSTEM_SETTINGS, "", SystemSettings.class.getSimpleName());
        return UltifiUriFactory.buildUProtocolUri(UAuthority.local(), SERVICE, uResource);
    }
}