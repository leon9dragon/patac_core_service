/*
 * Copyright (C) GM Global Technology Operations LLC 2022.
 * All Rights Reserved.
 * GM Confidential Restricted.
 */

package com.ultifi.demo.ultifi

import com.ultifi.core.rpc.Request
import com.ultifi.vehicle.body.cabin_climate.v1.SystemSettings


fun createAcUpdateRequest(): Request {
    return Request.newBuilder()
        .updateSystemSettings(SystemSettings.AC_COMPRESSOR_SETTING_FIELD_NUMBER) {
            SystemSettings.newBuilder()
                .setAcCompressorSetting(SystemSettings.CompressorSetting.CS_ON)
                .build()
        }.build()
}


