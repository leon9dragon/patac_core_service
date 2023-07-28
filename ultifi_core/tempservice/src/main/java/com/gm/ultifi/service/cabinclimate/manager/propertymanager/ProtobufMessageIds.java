package com.gm.ultifi.service.cabinclimate.manager.propertymanager;

/**
 * Define all message and field mask align to Protobuf message
 */
public class ProtobufMessageIds {

    //For example
    public static final String POWER_ON = "power_on";

    public static final String SYNC_ALL = "sync_all";

    public static final class ZoneFieldMask {

        public static final String ZONE = "zone.";

        public static final String POWER_ON_FIELD_MASK = ZONE + POWER_ON;
    }

    public static final class SettingsFieldMask {

        public static final String SYSTEM_SETTINGS = "settings.";

        public static final String SYNC_ALL_FIELD_MASK = SYSTEM_SETTINGS + SYNC_ALL;
    }
}