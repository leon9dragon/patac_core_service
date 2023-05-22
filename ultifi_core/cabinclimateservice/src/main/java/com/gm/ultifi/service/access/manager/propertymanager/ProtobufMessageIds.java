package com.gm.ultifi.service.access.manager.propertymanager;

/**
 * Define all message and field mask align to Protobuf message
 */
public class ProtobufMessageIds {

    //For example
    public static final String POSITION = "position";

    public static final class SunroofFieldMask {

        public static final String SUNROOF = "sunroof.";

        public static final String POWER_ON_FIELD_MASK = SUNROOF + POSITION;
    }
}