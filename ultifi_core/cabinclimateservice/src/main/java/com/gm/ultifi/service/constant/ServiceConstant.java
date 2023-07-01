package com.gm.ultifi.service.constant;

import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UAuthority;
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UEntity;
import com.gm.ultifi.sdk.uprotocol.uri.factory.UltifiUriFactory;

public class ServiceConstant {
    public static final String VERSION = "1";
    public static final String ACCESS_URI = "body.access";

    public static final UEntity ACCESS_SERVICE = new UEntity(ACCESS_URI, VERSION);

    //#region ACCESS RPC METHODS

    public static final String SUNROOF_RPC_METHOD = "ExecuteSunroofCommand";

    public static final String SUNROOF_RPC_METHOD_URI = UltifiUriFactory.buildMethodUri(UAuthority.local(), ACCESS_SERVICE, SUNROOF_RPC_METHOD);

    //endregion ACCESS RPC METHODS
}
