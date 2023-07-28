package com.gm.ultifi.service.access.utils;

import static com.gm.ultifi.service.access.utils.ResourceMappingConstants.SUNROOF_FRONT;

import android.util.Log;

import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UAuthority;
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UEntity;
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UResource;
import com.gm.ultifi.sdk.uprotocol.uri.factory.UltifiUriFactory;
import com.gm.ultifi.service.access.response.mapper.BaseMapper;
import com.ultifi.vehicle.body.access.v1.Sunroof;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import gm.ultifi.canbridge.Signal;

public class Utility {

    private static final String TAG = "Utility";
    /**
     * replace with actual available signals
     * TODO: 2023/4/26 业务代码抽离
     */
    public static List<String> buildTopicsList() {
        ArrayList<String> topics = new ArrayList<>();
        UEntity uEntity = new UEntity(BaseMapper.BASE_URI_SERVICE, BaseMapper.VERSION);

        // Sunroof related topics
        UResource uResource = new UResource(SUNROOF_FRONT, "", Sunroof.class.getSimpleName());
        UResource uResourceSomeip = new UResource(SUNROOF_FRONT + ".someip", "", Sunroof.class.getSimpleName());
        topics.add(UltifiUriFactory.buildUProtocolUri(UAuthority.local(), uEntity, uResource));
        topics.add(UltifiUriFactory.buildUProtocolUri(UAuthority.local(), uEntity, uResourceSomeip));

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
        // TODO: 2023/4/26 need make clear the signal type's definition, because haven't found the value enumeration in ultifi-can-manager-1.0.1-javadoc
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