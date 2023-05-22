/*
 * Copyright (C) GM Global Technology Operations LLC 2022.
 * All Rights Reserved.
 * GM Confidential Restricted.
 */

package com.ultifi.demo.ultifi

import com.google.protobuf.Any
import com.google.protobuf.FieldMask
import com.ultifi.core.rpc.Request
import com.ultifi.vehicle.body.cabin_climate.v1.SystemSettings
import com.ultifi.vehicle.body.cabin_climate.v1.UpdateSystemSettingsRequest
import com.ultifi.vehicle.body.cabin_climate.v1.ClimateCommand
import com.ultifi.vehicle.body.cabin_climate.v1.Zone

fun Request.Builder.updateZone(
    featureFieldNumber: Int,
    zoneProvider: (namePrefix: String) -> Zone
): Request.Builder {
    val uri = UriFactory.buildCabinClimateRequestUri("ExecuteClimateCommand")

    val zoneFieldMessageName = ClimateCommand.getDescriptor()
        .findFieldByNumber(ClimateCommand.ZONE_FIELD_NUMBER)
        .name

    val featureFieldMessageName = Zone.getDescriptor()
        .findFieldByNumber(featureFieldNumber)
        .name

    val mask = FieldMask.newBuilder()
        .addPaths("$zoneFieldMessageName.$featureFieldMessageName")
        .build()

    val updateZoneRequest = ClimateCommand.newBuilder()
        .setZone(zoneProvider(zoneFieldMessageName.plus("."))).setUpdateMask(mask)
        .build()

    return this
        .withMethodUri(uri)
        .withPayload(Any.pack(updateZoneRequest))
}

fun Request.Builder.updateSystemSettings(
    featureFieldNumber: Int,
    systemSettingsProvider: (namePrefix: String) -> SystemSettings
): Request.Builder {
    val uri = UriFactory.buildCabinClimateRequestUri("UpdateSystemSettings")

    val systemSettingsFieldMessageName = UpdateSystemSettingsRequest.getDescriptor()
        .findFieldByNumber(UpdateSystemSettingsRequest.SETTINGS_FIELD_NUMBER)
        .name

    val featureFieldMessageName = SystemSettings.getDescriptor()
        .findFieldByNumber(featureFieldNumber)
        .name

    val mask = FieldMask.newBuilder()
        .addPaths("$systemSettingsFieldMessageName.$featureFieldMessageName")
        .build()

    val updateSystemSettingsRequest = UpdateSystemSettingsRequest.newBuilder()
        .setSettings(systemSettingsProvider(systemSettingsFieldMessageName.plus(".")))
        .setUpdateMask(mask)
        .build()

    return this
        .withMethodUri(uri)
        .withPayload(Any.pack(updateSystemSettingsRequest))
}