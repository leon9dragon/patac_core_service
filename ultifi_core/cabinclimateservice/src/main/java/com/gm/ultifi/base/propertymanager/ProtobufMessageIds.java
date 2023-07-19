package com.gm.ultifi.base.propertymanager;

/**
 * Define all message and field mask align to Protobuf message
 */
public class ProtobufMessageIds {

    //For example
    public static final String POSITION = "position";
    public static final String PRESSURE = "pressure";
    public static final String PRESSURE_WARNING = "pressure_warning";
    public static final String IS_ENABLED = "is_enabled";

    public static final class SunroofFieldMask {

        public static final String SUNROOF = "sunroof.";

        public static final String POWER_ON_FIELD_MASK = SUNROOF + POSITION;
    }

    public static final String TEMPERATURE_LEVEL = "temperature_level";


}