package com.gm.ultifi.service.constant;

import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UAuthority;
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UEntity;
import com.gm.ultifi.sdk.uprotocol.uri.factory.UltifiUriFactory;

public class ServiceConstant {
    public static final String VERSION = "1";
    public static final String ACCESS_URI = "body.access";
    public static final String SEATING_URI = "body.seating";
    public static final String CHASSIS_URI = "chassis";

    public static final UEntity ACCESS_SERVICE = new UEntity(ACCESS_URI, VERSION);
    public static final UEntity SEATING_SERVICE = new UEntity(SEATING_URI, VERSION);
    public static final UEntity CHASSIS_SERVICE = new UEntity(CHASSIS_URI, VERSION);
    //#region ACCESS RPC METHODS

    public static final String SUNROOF_RPC_METHOD = "ExecuteSunroofCommand";
    public static final String SUNROOF_RPC_METHOD_URI = UltifiUriFactory.buildMethodUri(UAuthority.local(), ACCESS_SERVICE, SUNROOF_RPC_METHOD);
    public static final String SUNROOF_RPC_METHOD_SOME_IP = "ExecuteSunroofCommandSomeIp";

    public static String SUNROOF_RPC_METHOD_URI_SOME_IP = UltifiUriFactory.buildMethodUri(UAuthority.local(), ACCESS_SERVICE, SUNROOF_RPC_METHOD_SOME_IP);

    //endregion ACCESS RPC METHODS

    public static final String SEATING_RPC_POSITION_METHOD = "UpdateSeatPositionSomeIp";
    public static final String SEATING_RPC_POSITION_METHOD_URI_SOME_IP = UltifiUriFactory.buildMethodUri(UAuthority.local(), SEATING_SERVICE, SEATING_RPC_POSITION_METHOD);

    public static final String SEATING_RPC_TEMPERATURE_METHOD = "UpdateSeatTemperature";
    public static final String SEATING_RPC_TEMPERATURE_METHOD_URI_SOME_IP = UltifiUriFactory.buildMethodUri(UAuthority.local(), SEATING_SERVICE, SEATING_RPC_TEMPERATURE_METHOD);


    public static final String CHASSIS_RPC_TRACTION_METHOD = "UpdateTractionandStabilitySystemRequest";
    public static final String CHASSIS_RPC_TRACTION_METHOD_URI = UltifiUriFactory.buildMethodUri(UAuthority.local(), CHASSIS_SERVICE, CHASSIS_RPC_TRACTION_METHOD);

}
