/*
 * Copyright (C) GM Global Technology Operations LLC 2022.
 * All Rights Reserved.
 * GM Confidential Restricted.
 */

package com.patac.tester.util

import com.gm.ultifi.vehicle.body.seating.v1.SeatMode
import com.google.protobuf.Enum
import com.ultifi.core.rpc.Request
import com.ultifi.vehicle.body.access.v1.Sunroof
import com.ultifi.vehicle.body.cabin_climate.v1.SystemSettings
import com.ultifi.vehicle.chassis.v1.UpdateTireRequest
import com.ultifi.vehicle.chassis.v1.UpdateTractionandStabilitySystemRequest
import com.ultifi.vehicle.body.seating.v1.UpdateSeatModeRequest
import com.gm.ultifi.vehicle.body.seating.v1.SeatMode.Mode
//import com.ultifi.vehicle.body.seating.v1.SeatMode


fun createAcUpdateRequest(): Request {
    return Request.newBuilder()
        .updateLightInterior(SystemSettings.AC_COMPRESSOR_SETTING_FIELD_NUMBER) {
            SystemSettings.newBuilder()
                .setAcCompressorSetting(SystemSettings.CompressorSetting.CS_ON)
                .build()
        }.build()
}


/**
 * 创建天窗控制请求
 * position: 天窗位置
 */
fun createSunroofUpdateRequest(position: Int): Request {
    return Request.newBuilder()
        .executeSunroofCommand(Sunroof.POSITION_FIELD_NUMBER) {
            Sunroof.newBuilder()
                .setPosition(position)
                .build()
        }.build()
}

//createUpdateTractionandStabilitySystemRequest 看传入参数
fun createUpdateTractionandStabilitySystemRequest(request: UpdateTractionandStabilitySystemRequest.TractionandStabilitySystemRequest):Request{
    return Request.newBuilder()
            //根据传入参数的数量写（或者说要set的值），如果有多个，就写多套下面代码
        .updateTractionandStabilitySystem(request){
            UpdateTractionandStabilitySystemRequest.newBuilder()
                .setTractionandstabilitysystemrequest(request)
                .build()
        }.build()
}
//seat mode
fun createUpdateSeatModeRequest(request: Int):Request{

    return Request.newBuilder()
        .UpdateSeatModeRequest(request){
            UpdateSeatModeRequest.newBuilder()
                .setModeValue(request)
                .build()
        }.build()
}
//fun createUpdateSeatModeRequest(request: SeatMode.Mode):Request{
//
//    return Request.newBuilder()
//        .UpdateSeatModeRequest(request){
//            UpdateSeatModeRequest.newBuilder()
//                .setMode(request)
//                .build()
//        }.build()
//}



/**
 * create Update Tire Request
 * is_leak_notification_enabled: True = Notification for tire leak status are enabled.False = Notification for tire leak status are disabled.
 * is_leak_present: True = Request to re-evaluate tire pressure if a leak persists, False = Do not re-evaluate tire pressure if a leak persists
 */
//fun RPCUpdateTireRequest(is_leak_notification_enabled: Boolean, is_leak_present: Boolean): Request {
//    return Request.newBuilder()
//        .updateTireRequest(UpdateTireRequest.IS_LEAK_NOTIFICATION_ENABLED_FIELD_NUMBER,UpdateTireRequest.IS_LEAK_PRESENT_FIELD_NUMBER){
//            UpdateTireRequest.newBuilder()
//                .setIsLeakNotificationEnabled(is_leak_notification_enabled)
//                .setIsLeakPresent(is_leak_present)
//                .build()
//        }.build()
//
//}