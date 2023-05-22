/*
 * Copyright (C) GM Global Technology Operations LLC 2022.
 * All Rights Reserved.
 * GM Confidential Restricted.
 */

package com.ultifi.demo.ultifi

import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UAuthority
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UEntity
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UResource
import com.gm.ultifi.sdk.uprotocol.uri.factory.UltifiUriFactory

object UriFactory {
    private const val CABIN_CLIMATE_VERSION = "1"
    private val CABIN_CLIMATE_UE = UEntity("body.cabin_climate", CABIN_CLIMATE_VERSION)
    private val LOCAL_AUTHORITY: UAuthority = UAuthority.local()

    fun buildCabinClimateUri(uResource: UResource): String {
        return UltifiUriFactory.buildUProtocolUri(LOCAL_AUTHORITY, CABIN_CLIMATE_UE, uResource)
    }

    fun buildCabinClimateRequestUri(methodName: String): String {
        return UltifiUriFactory.buildMethodUri(LOCAL_AUTHORITY, CABIN_CLIMATE_UE, methodName)
    }
}