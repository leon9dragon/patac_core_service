package com.gm.ultifi.service.constant;

/**
 * for topics are not sensitive to zone configuration
 * such as ultifi://<authorty>/body.cabin_climate/1/system_settings#SystemSettings
 */
public final class ResourceMappingConstants {

    public static final String SUNROOF_FRONT = "sunroof.front";

    public static final String DRIVER_SEAT = "seat.row1_left";
    public static final String PASSENGER_SEAT = "seat.row1_right";
    public static final String SECOND_LEFT_SEAT = "seat.row2_left";
    public static final String SECOND_RIGHT_SEAT = "seat.row2_right";
    public static final String THIRD_LEFT_SEAT = "seat.row3_left";
    public static final String THIRD_RIGHT_SEAT = "seat.row3_right";
    public static final String SEAT_MODE = "seat_mode";


    public static final String TIRE_FRONT_LEFT = "tire.front_left";
    public static final String TIRE_FRONT_RIGHT = "tire.front_right";
    public static final String TIRE_REAR_LEFT = "tire.rear_left";
    public static final String TIRE_REAR_RIGHT = "tire.rear_right";
    //public static final String TIRE_REAR_LEFT_INNER = "tire.rear_left_inner";
    //public static final String TIRE_REAR_RIGHT_INNER = "tire.rear_right_inner";

    public static final String TRACTION_CONTROL = "traction_control_system";
    public static final String ELECTRONIC_STABILITY_CONTROL = "electronic_stability_control_system";
    public static final String STEERING_ANGLE = "steering_angle";
}