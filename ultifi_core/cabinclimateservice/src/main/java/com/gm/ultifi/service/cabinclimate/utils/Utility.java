package com.gm.ultifi.service.cabinclimate.utils;

import android.util.Log;

import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UAuthority;
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UEntity;
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UResource;
import com.gm.ultifi.sdk.uprotocol.uri.factory.UltifiUriFactory;
import com.gm.ultifi.service.cabinclimate.manager.propertymanager.AreaPropertyMapper;
import com.gm.ultifi.service.cabinclimate.response.mapper.BaseMapper;
import com.ultifi.vehicle.body.cabin_climate.v1.SystemSettings;
import com.ultifi.vehicle.body.cabin_climate.v1.Zone;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import gm.ultifi.canbridge.Signal;

public class Utility {

    private static final String TAG = "Utility";

    // Todo should replace with actual available signals
    public static List<String> buildTopicsList() {
        ArrayList<String> topics = new ArrayList<>();
        UEntity uEntity = new UEntity(BaseMapper.BASE_URI_SERVICE, BaseMapper.VERSION);

        // Zone related topics are sensitive to zone configuration
        for (String key : AreaPropertyMapper.getZonePropertyKeys()) {
            UResource uResource1 = new UResource(key, "", Zone.class.getSimpleName());
            topics.add(UltifiUriFactory.buildUProtocolUri(UAuthority.local(), uEntity, uResource1));
        }

        UResource uResource = new UResource(ResourceMappingConstants.SYSTEM_SETTINGS,
                "", SystemSettings.class.getSimpleName());
        topics.add(UltifiUriFactory.buildUProtocolUri(UAuthority.local(), uEntity, uResource));

        return topics;
    }

    public static boolean convertToBoolean(String value) {
        return "1".equalsIgnoreCase(value) || "true".equalsIgnoreCase(value);
    }

    public static Boolean getBooleanValue(Signal signal) {
        Log.i(TAG, "signal.intValue: " + signal.intValue);
        return signal.intValue != 0L;
    }

    public static byte[] getBytes(Signal signal) {
        return signal.bytesValue;
    }

    public static Float getFloatValue(Signal signal) {
        Log.i(TAG, "signal.floatValue: " + signal.floatValue);
        return (float) signal.floatValue;
    }

    public static Integer getIntValue(Signal signal) {
        Log.i(TAG, "signal.intValue: " + signal.intValue);
        return (int) signal.intValue;
    }

    public static <K, V> K getKey(Map<K, V> map, V value) {
        Stream<Map.Entry<K, V>> stream = map
                .entrySet()
                .stream()
                .filter(entry -> value.equals(entry.getValue()));
        Optional<Map.Entry<K, V>> optional = stream.findFirst();
        return optional.map(Map.Entry::getKey).orElse(null);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getSignalValues(Signal signal) {
        // Todo need make clear the signal type's definition, because haven't found the value enumeration in ultifi-can-manager-1.0.1-javadoc
        switch (signal.type) {
            case 3:
                return (T) getBytes(signal);
            case 2:
                return (T) getFloatValue(signal);
            case 1:
                return (T) getIntValue(signal);
            default:
                return (T) getBooleanValue(signal);
        }
    }

    public static boolean between(int i, int minValue, int maxValue) {
        return i >= minValue && i <= maxValue;
    }

    public static boolean isInRange(double value, double min, double max) {
        return value >= min && value <= max;
    }

    public static boolean isInRange(float value, float min, float max) {
        return value >= min && value <= max;
    }
}