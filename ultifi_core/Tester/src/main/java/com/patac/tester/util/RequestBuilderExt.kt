/*
 * Copyright (C) GM Global Technology Operations LLC 2022.
 * All Rights Reserved.
 * GM Confidential Restricted.
 */

package com.patac.tester.util

import com.google.protobuf.Any
import com.google.protobuf.FieldMask
import com.ultifi.core.rpc.Request
import com.ultifi.vehicle.body.access.v1.Sunroof
import com.ultifi.vehicle.body.access.v1.SunroofCommand
import com.ultifi.vehicle.body.cabin_climate.v1.SystemSettings
import com.ultifi.vehicle.body.cabin_climate.v1.UpdateSystemSettingsRequest
import com.ultifi.vehicle.body.cabin_climate.v1.ClimateCommand
import com.ultifi.vehicle.body.cabin_climate.v1.Zone
import com.ultifi.vehicle.chassis.v1.UpdateTireRequest
import com.ultifi.vehicle.chassis.v1.ElectronicStabilityControlSystem
import com.ultifi.vehicle.chassis.v1.UpdateTractionandStabilitySystemRequest

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

fun Request.Builder.executeSunroofCommand(
    featureFieldNumber: Int,
    sunroofProvider: (namePrefix: String) -> Sunroof
): Request.Builder {
    val uri = UriFactory.buildSunroofRequestUri("ExecuteSunroofCommand")

    val sunroofFieldMessageName = SunroofCommand.getDescriptor()
        .findFieldByNumber(SunroofCommand.SUNROOF_FIELD_NUMBER)
        .name

    val featureFieldMessageName = Sunroof.getDescriptor()
        .findFieldByNumber(featureFieldNumber)
        .name

    val mask = FieldMask.newBuilder()
        .addPaths("$sunroofFieldMessageName.$featureFieldMessageName")
        .build()

    val updateSunroofRequest = SunroofCommand.newBuilder()
        .setSunroof(sunroofProvider(sunroofFieldMessageName.plus("."))).setUpdateMask(mask)
        .build()

    return this
        .withMethodUri(uri)
        .withPayload(Any.pack(updateSunroofRequest))
}

//要看有没有field mask
fun Request.Builder.updateTractionandStabilitySystem(
    featureFieldNumber: UpdateTractionandStabilitySystemRequest.TractionandStabilitySystemRequest,
    tractionandStabilitySystemProvider: (namePrefix: String) -> UpdateTractionandStabilitySystemRequest
): Request.Builder {
    val uri = UriFactory.buildChassisRequestUri("UpdateTractionAndStabilitySystemRequest")
//    val TractionandStabilityMessageName = UpdateTractionandStabilitySystemRequest.getDescriptor()
//        .findFieldByNumber(UpdateTractionandStabilitySystemRequest.TRACTIONANDSTABILITYSYSTEMREQUEST_FIELD_NUMBER)
//        .name

//    val featureFieldNumber = UpdateTractionandStabilitySystemRequest.getDescriptor()
//        .findFieldByNumber(featureFieldNumber)
//        .name
//
//    val mask = FieldMask.newBuilder()
//        .addPaths("$featureFieldNumber")
//        .build()
    val updatesetting = UpdateTractionandStabilitySystemRequest.newBuilder()
        .setTractionandstabilitysystemrequest(featureFieldNumber).build();
    return this.withMethodUri(uri).withPayload(Any.pack(updatesetting))
}

fun Request.Builder.updateTireRequest(
    featureFieldNumber: Int,
    tireProvider: (namePrefix: String) -> UpdateTireRequest
):Request.Builder{
    val uri = UriFactory.buildChassisRequestUri("UpdateTire")

    val tire_is_leak_notifier_FieldMessageName = UpdateTireRequest.getDescriptor()
        .findFieldByNumber(UpdateTireRequest.IS_LEAK_NOTIFICATION_ENABLED_FIELD_NUMBER)
        .name

    val tire_is_leak_present_FieldMessageName = UpdateTireRequest.getDescriptor()
        .findFieldByNumber(UpdateTireRequest.IS_LEAK_PRESENT_FIELD_NUMBER)
        .name

    val featureFieldNumber = UpdateTireRequest.getDescriptor()
        .findFieldByNumber(featureFieldNumber)
        .name

//    val mask = FieldMask.newBuilder()
//        .addPaths("$tire_is_leak_notifier_FieldMessageName.$featureFieldNumber")
//        .build()


    val updateTireRequest = UpdateTireRequest.newBuilder()
//        .setIsLeakPresent(tireProvider(tire_is_leak_present_FieldMessageName.plus("."))).setIsLeakPresent(true)
//        .setIsLeakNotificationEnabled(tireProvider(tire_is_leak_notifier_FieldMessageName.plus("."))).setIsLeakNotificationEnabled(true)
        .setIsLeakPresent(true)
        .setIsLeakNotificationEnabled(true)
        .build()

    return this
        .withMethodUri(uri)
        .withPayload(Any.pack(updateTireRequest))


}

fun Request.Builder.updateLightInterior(
    featureFieldNumber: Int,
    systemSettingsProvider: (namePrefix: String) -> SystemSettings
): Request.Builder {
    val uri = UriFactory.buildLightInteriorRequestUri("AmbientMoodCommand")

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