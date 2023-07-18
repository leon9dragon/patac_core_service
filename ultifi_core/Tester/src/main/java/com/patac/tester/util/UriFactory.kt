/*
 * Copyright (C) GM Global Technology Operations LLC 2022.
 * All Rights Reserved.
 * GM Confidential Restricted.
 */

package com.patac.tester.util

import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UAuthority
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UEntity
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UResource
import com.gm.ultifi.sdk.uprotocol.uri.factory.UltifiUriFactory
import com.ultifi.vehicle.chassis.v1.TractionControlSystem
import com.ultifi.vehicle.chassis.v1.UpdateTractionandStabilitySystemRequest

object UriFactory {
    private const val CABIN_CLIMATE_VERSION = "1"
    private const val SUN_ROOF_VERSION = "1"
    private const val CHASSIS_VERSION = "1"
    private val CABIN_CLIMATE_UE = UEntity("body.cabin_climate", CABIN_CLIMATE_VERSION)
    private val SUN_ROOF_UE = UEntity("body.access", SUN_ROOF_VERSION)
    private val LOCAL_AUTHORITY: UAuthority = UAuthority.local()
    private val BODY_LIGHTING_INTERIOR = UEntity("body.lighting.interior", CABIN_CLIMATE_VERSION)
    private val CHASSIS_TCS = UEntity("chassis", CHASSIS_VERSION)

    fun buildCabinClimateUri(uResource: UResource): String {
        return UltifiUriFactory.buildUProtocolUri(LOCAL_AUTHORITY, CABIN_CLIMATE_UE, uResource)
    }

    fun buildCabinClimateRequestUri(methodName: String): String {
        return UltifiUriFactory.buildMethodUri(LOCAL_AUTHORITY, CABIN_CLIMATE_UE, methodName)
    }

    fun buildSunroofRequestUri(methodName: String): String {
        return UltifiUriFactory.buildMethodUri(LOCAL_AUTHORITY, SUN_ROOF_UE, methodName)
    }

    fun buildSunroofTopicUri(uResource: UResource): String {
        return UltifiUriFactory.buildUProtocolUri(LOCAL_AUTHORITY, SUN_ROOF_UE, uResource)
    }

    fun buildLightInteriorRequestUri(methodName: String): String {
        return UltifiUriFactory.buildMethodUri(LOCAL_AUTHORITY, BODY_LIGHTING_INTERIOR, methodName)
    }

    fun buildChassisTopicUri(uResource: UResource): String {
        return UltifiUriFactory.buildUProtocolUri(LOCAL_AUTHORITY, CHASSIS_TCS, uResource)
    }
    fun buildChassisRequestUri(methodName: String): String {
        return UltifiUriFactory.buildMethodUri(LOCAL_AUTHORITY, CHASSIS_TCS, methodName)
    }
}