package com.gm.ultifi.service.seating.someip

import android.util.Log
import com.gm.ultifi.base.someip.BaseAppViewModel
import com.gm.ultifi.base.utils.SomeIpUtil
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UResource
import com.gm.ultifi.service.SeatingService
import com.gm.ultifi.service.constant.ResourceMappingConstants
import com.gm.ultifi.service.constant.ServiceConstant
import com.gm.ultifi.vehicle.body.seating.v1.SeatComponent
import com.google.protobuf.GeneratedMessageV3
import com.ultifi.vehicle.body.seating.v1.SeatPosition
import com.ultifi.vehicle.body.seating.v1.SeatPosition.SeatComponentPosition
import com.ultifi.vehicle.body.seating.v1.UpdateSeatPositionRequest
import plugin.SomeipS2SManagementInterface
import ts.car.someip.plugin.SomeIpTopic
import ts.car.someip.sdk.common.ResultValue
import ts.car.someip.sdk.common.SomeIpData

class SeatViewModel : BaseAppViewModel() {
    private val isBoolean = false
    private val testVal = 0
    override fun doOnRequest(data: SomeIpData): SomeIpData? {
        // method for server, no need to implement in client
        return null;
    }

    override fun doClientOk() {
        someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_SERVICE_1_AVAILABLE)
        someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_SERVICE_1_NOT_AVAILABLE)
    }

    override fun doStartClient(): Int? {
        return startClient(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_SERVICE_1_ID)
    }

    override fun doStopClient() {
        stopClient(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_SERVICE_1_ID)
    }

    override fun doOnSomeIpEvent(data: SomeIpData) {
        //notification
        Log.i(TAG, "onSomeIpEvent: " + data.topic)

        // record the server's status
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_SERVICE_1_AVAILABLE) {
            Log.i(TAG, "SERVICE_AVAILABLE")
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_DRIVER_SEAT_PERCENTAGE_POSITION_1)
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_DRIVER_SEAT_PERCENTAGE_POSITION_2)
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_DRIVER_SEAT_PERCENTAGE_POSITION_3)
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_PASSENGER_SEAT_PERCENTAGE_POSITION_1)
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_PASSENGER_SEAT_PERCENTAGE_POSITION_2)
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_LEFT_SEAT_PERCENTAGE_POSITION_1)
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_LEFT_SEAT_PERCENTAGE_POSITION_2)
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_RIGHT_SEAT_PERCENTAGE_POSITION_1)
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_RIGHT_SEAT_PERCENTAGE_POSITION_2)
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_THIRD_RIGHT_SEAT_PERCENTAGE_POSITION)
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_THIRD_LEFT_SEAT_PERCENTAGE_POSITION)
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_DRIVER_SEAT_CONFIGURATION)
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_PASSENGER_SEAT_CONFIGURATION)
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_LEFT_SEAT_CONFIGURATION)
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_RIGHT_SEAT_CONFIGURATION)
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_THIRD_LEFT_SEAT_CONFIGURATION)
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_THIRD_RIGHT_SEAT_CONFIGURATION)
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_MODE_STATUS)
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_PASSENGER_COMPARTMENT_MODE_SERVICE_AVAILABILITY_STATUS)
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_PASSENGER_COMPARTMENT_MODE_SERVICE_RESPONSE_STATUS)
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_PASSENGER_COMPARTMENT_MODE_SERVICE_RESPONSE_STATUS_2)
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_MODE_STATUS_2)
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_PASSENGER_COMPARTMENT_MODE_AVAILABILITY_AND_NOTIFICATION_STATUS_6)
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_DRIVER_SEAT_RECOVERY_POSITION)
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_DRIVER_SEAT_RECOVERY_POSITION_2)
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_ROW_COMFORT_MODE_REQUEST_INFO)
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_ROW_LEFT_COMFORT_MODE_REQUEST_HMI)
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_ROW_RIGHT_COMFORT_MODE_REQUEST_HMI)

            isServerAvailable = true
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_SERVICE_1_NOT_AVAILABLE) {
            Log.i(TAG, "SERVICE_NOT_AVAILABLE")
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_DRIVER_SEAT_PERCENTAGE_POSITION_1)
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_DRIVER_SEAT_PERCENTAGE_POSITION_2)
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_DRIVER_SEAT_PERCENTAGE_POSITION_3)
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_PASSENGER_SEAT_PERCENTAGE_POSITION_1)
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_PASSENGER_SEAT_PERCENTAGE_POSITION_2)
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_LEFT_SEAT_PERCENTAGE_POSITION_1)
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_LEFT_SEAT_PERCENTAGE_POSITION_2)
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_RIGHT_SEAT_PERCENTAGE_POSITION_1)
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_RIGHT_SEAT_PERCENTAGE_POSITION_2)
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_THIRD_RIGHT_SEAT_PERCENTAGE_POSITION)
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_THIRD_LEFT_SEAT_PERCENTAGE_POSITION)
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_DRIVER_SEAT_CONFIGURATION)
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_PASSENGER_SEAT_CONFIGURATION)
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_LEFT_SEAT_CONFIGURATION)
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_RIGHT_SEAT_CONFIGURATION)
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_THIRD_LEFT_SEAT_CONFIGURATION)
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_THIRD_RIGHT_SEAT_CONFIGURATION)
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_MODE_STATUS)
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_PASSENGER_COMPARTMENT_MODE_SERVICE_AVAILABILITY_STATUS)
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_PASSENGER_COMPARTMENT_MODE_SERVICE_RESPONSE_STATUS)
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_PASSENGER_COMPARTMENT_MODE_SERVICE_RESPONSE_STATUS_2)
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_MODE_STATUS_2)
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_PASSENGER_COMPARTMENT_MODE_AVAILABILITY_AND_NOTIFICATION_STATUS_6)
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_DRIVER_SEAT_RECOVERY_POSITION)
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_DRIVER_SEAT_RECOVERY_POSITION_2)
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_ROW_COMFORT_MODE_REQUEST_INFO)
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_ROW_LEFT_COMFORT_MODE_REQUEST_HMI)
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_ROW_RIGHT_COMFORT_MODE_REQUEST_HMI)
            isServerAvailable = false
        }

        //region driver seat position notification
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_DRIVER_SEAT_PERCENTAGE_POSITION_1) {
            Log.i(TAG, "SUCCESS: NOTIFY_DRIVER_SEAT_PERCENTAGE_POSITION_1")
            val resp = SomeipS2SManagementInterface.Driver_Seat_Percentage_Position_1Field.parseFrom(data.payload)

            val seatReclPosition = resp.outPut.drvStBkReclnUpwdDnwdPos *0.025
            val seatPosition = resp.outPut.drvStFrwdBkwdPos*0.025

            Log.d(TAG, "onChangeEvent, Recline position:"+seatReclPosition+"SeatPosition"+seatPosition)
            val seatReclPos: SeatComponentPosition = SeatComponentPosition.newBuilder()
                .setComponent(SeatComponent.SC_BACK)
                .setPosition(seatReclPosition.toInt())
                .setDirection(UpdateSeatPositionRequest.Direction.D_UP)
                .build();

            val seatPos: SeatComponentPosition = SeatComponentPosition.newBuilder()
                .setComponent(SeatComponent.SC_CUSHION)
                .setPosition(seatPosition.toInt())
                .setDirection(UpdateSeatPositionRequest.Direction.D_FORWARD)
                .build();

            val seatReq: SeatPosition = SeatPosition.newBuilder()
                .setName("row1_left")
                .setSeatComponentPositions(0, seatReclPos)
                .setSeatComponentPositions(1, seatPos)
                .build();

            Log.i(TAG, "Publishing the cloud events to Bus")

            val topic = ResourceMappingConstants.DRIVER_SEAT_SOMEIP
            val uResource = UResource(topic, "", SeatPosition::class.java.simpleName)

            SomeIpUtil.pubEvent(seatReq, ServiceConstant.SEATING_SERVICE, uResource, SeatingService.mLaunchManager.getmUltifiLinkMonitor())

        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_DRIVER_SEAT_PERCENTAGE_POSITION_2) {
            Log.i(TAG, "SUCCESS: NOTIFY_DRIVER_SEAT_PERCENTAGE_POSITION_2")
            val resp = SomeipS2SManagementInterface.Driver_Seat_Percentage_Position_2Field.parseFrom(data.payload)

            val seatCushionPosition = resp.outPut.drvStCshnFrntUpwdDnwdPos
            val seatCushionRearPosition = resp.outPut.drvStCshnRrUpwdDnwdPos

            Log.d(TAG, "onChangeEvent, Cushion position:"+seatCushionPosition+".CushionRear:"+seatCushionRearPosition)
            val seatCushFrontPos: SeatComponentPosition = SeatComponentPosition.newBuilder()
                .setComponent(SeatComponent.SC_CUSHION_FRONT)
                .setPosition(seatCushionPosition)
                .setDirection(UpdateSeatPositionRequest.Direction.D_UP)
                .build();

            val seatCushRearPos: SeatComponentPosition = SeatComponentPosition.newBuilder()
                .setComponent(SeatComponent.SC_CUSHION)
                .setPosition(seatCushionRearPosition)
                .setDirection(UpdateSeatPositionRequest.Direction.D_UP)
                .build();

            val seatReq: SeatPosition = SeatPosition.newBuilder()
                .setName("row1_left")
                .setSeatComponentPositions(0, seatCushFrontPos)
                .setSeatComponentPositions(1, seatCushRearPos)
                .build();

            Log.i(TAG, "Publishing the cloud events to Bus")

            val topic = ResourceMappingConstants.DRIVER_SEAT_SOMEIP
            val uResource = UResource(topic, "", SeatPosition::class.java.simpleName)

            SomeIpUtil.pubEvent(seatReq, ServiceConstant.SEATING_SERVICE, uResource, SeatingService.mLaunchManager.getmUltifiLinkMonitor())
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_DRIVER_SEAT_PERCENTAGE_POSITION_3) {
            Log.i(TAG, "SUCCESS: NOTIFY_DRIVER_SEAT_PERCENTAGE_POSITION_3")
            val resp = SomeipS2SManagementInterface.Driver_Seat_Percentage_Position_3Field.parseFrom(data.payload)

            val seatBolsterPosition = resp.outPut.drvStBlstOtwdInwdPos
            // check if there is foot component
//            val seatFtPosition = resp.outPut.drvStFtUpwdDnwdPos
            val seatHeadRestRearPosition = resp.outPut.drvStHdrstUpwdDnwdPos
            val seatLegRestRearPosition = resp.outPut.drvStLgrstUpwdDnwdPos

            Log.d(TAG, "onChangeEvent, Bolster position:"+seatBolsterPosition+
                    ", Head Rest:"+ seatHeadRestRearPosition + ", Leg Rest:" + seatLegRestRearPosition)

            val seatBolPos: SeatComponentPosition = SeatComponentPosition.newBuilder()
                .setComponent(SeatComponent.SC_SIDE_BOLSTER_BACK)
                .setPosition(seatBolsterPosition)
                .setDirection(UpdateSeatPositionRequest.Direction.D_INFLATE)
                .build();

//            val seatFtPos: SeatComponentPosition = SeatComponentPosition.newBuilder()
//                .setComponent(SeatComponent.SC_FOOTREST)
//                .setPosition(seatFtPosition)
//                .setDirection(UpdateSeatPositionRequest.Direction.D_UP)
//                .build();

            val seatHeadPos: SeatComponentPosition = SeatComponentPosition.newBuilder()
                .setComponent(SeatComponent.SC_HEADREST)
                .setPosition(seatHeadRestRearPosition)
                .setDirection(UpdateSeatPositionRequest.Direction.D_UP)
                .build();

            val seatLegPos: SeatComponentPosition = SeatComponentPosition.newBuilder()
//                .setComponent(SeatComponent.SC_LEGREST)
                .setPosition(seatLegRestRearPosition)
                .setDirection(UpdateSeatPositionRequest.Direction.D_UP)
                .build();

            val seatReq: SeatPosition = SeatPosition.newBuilder()
                .setName("row1_left")
                .setSeatComponentPositions(0, seatBolPos)
//                .setSeatComponentPositions(1, seatFtPos)
                .setSeatComponentPositions(1, seatHeadPos)
                .setSeatComponentPositions(2, seatLegPos)
                .build();

            Log.i(TAG, "Publishing the cloud events to Bus")

            val topic = ResourceMappingConstants.DRIVER_SEAT_SOMEIP
            val uResource = UResource(topic, "", SeatPosition::class.java.simpleName)

            SomeIpUtil.pubEvent(seatReq, ServiceConstant.SEATING_SERVICE, uResource, SeatingService.mLaunchManager.getmUltifiLinkMonitor())
        }
        //endregion

        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_PASSENGER_SEAT_PERCENTAGE_POSITION_1) {
            Log.i(TAG,"PASSENGER_SEAT_PERCENTAGE_POSITION_1: Success")
            val resp = SomeipS2SManagementInterface.Passenger_Seat_Percentage_Position_1Field.parseFrom(data.payload)
            val bolsterPosition = resp.outPut.passStBlstOtwdInwdPos
            val seatPosition = resp.outPut.passStFrwdBkwdPos
            val footPosition = resp.outPut.passStFtUpwdDnwdPos
            val reclinePosition = resp.outPut.passStBkReclnUpwdDnwdPos

        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_PASSENGER_SEAT_PERCENTAGE_POSITION_2) {
            Log.i(TAG,"PASSENGER_SEAT_PERCENTAGE_POSITION_2: Success")

        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_LEFT_SEAT_PERCENTAGE_POSITION_1) {
            Log.i(TAG,"SECOND_LEFT_SEAT_PERCENTAGE_POSITION_1: Success")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_LEFT_SEAT_PERCENTAGE_POSITION_2) {
            Log.i(TAG,"SECOND_LEFT_SEAT_PERCENTAGE_POSITION_2: Success")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_RIGHT_SEAT_PERCENTAGE_POSITION_1) {
            Log.i(
                TAG,
                "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_RIGHT_SEAT_PERCENTAGE_POSITION_1: Success"
            )
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_RIGHT_SEAT_PERCENTAGE_POSITION_2) {
            Log.i(
                TAG,
                "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_RIGHT_SEAT_PERCENTAGE_POSITION_2: Success"
            )
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_THIRD_RIGHT_SEAT_PERCENTAGE_POSITION) {
            Log.i(
                TAG,
                "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_THIRD_RIGHT_SEAT_PERCENTAGE_POSITION: Success"
            )
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_THIRD_LEFT_SEAT_PERCENTAGE_POSITION) {
            Log.i(
                TAG,
                "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_THIRD_LEFT_SEAT_PERCENTAGE_POSITION: Success"
            )
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_DRIVER_SEAT_CONFIGURATION) {
            Log.i(TAG, "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_DRIVER_SEAT_CONFIGURATION: Success")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_PASSENGER_SEAT_CONFIGURATION) {
            Log.i(TAG, "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_PASSENGER_SEAT_CONFIGURATION: Success")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_LEFT_SEAT_CONFIGURATION) {
            Log.i(TAG, "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_LEFT_SEAT_CONFIGURATION: Success")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_RIGHT_SEAT_CONFIGURATION) {
            Log.i(TAG, "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_RIGHT_SEAT_CONFIGURATION: Success")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_THIRD_LEFT_SEAT_CONFIGURATION) {
            Log.i(TAG, "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_THIRD_LEFT_SEAT_CONFIGURATION: Success")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_THIRD_RIGHT_SEAT_CONFIGURATION) {
            Log.i(TAG, "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_THIRD_RIGHT_SEAT_CONFIGURATION: Success")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_MODE_STATUS) {
            Log.i(TAG, "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_MODE_STATUS: Success")
//            val resp = SomeipS2SManagementInterface.Seat_Mode_Status_2Field.parseFrom(data.payload)
//            val seatMessage = resp.outPut.
//
//
//
//            val resp = SomeipS2SManagementInterface.Sunroof_StatusField.parseFrom(data.payload)
//            val sunroofPercentagePositionStatus = resp.outPut.snrfPctPosSts
//            val booParam = resp.outPut.snrfConfig
//            Log.d(
//                TAG,
//                "onChangeEvent, PropName: sunroofPercentagePositionStatus, " +
//                        "newPropValue:$sunroofPercentagePositionStatus, " +
//                        "sunroofConf:$booParam"
//            )
//
//
//            Log.i(TAG, "Publishing the cloud events to Bus")
//
//            // no field mask in the resp, should set all the fields to msg obj
//            val sunroof: Sunroof = Sunroof.newBuilder()
//                .setPosition(sunroofPercentagePositionStatus)
//                .build()
//
//            val topic = ResourceMappingConstants.SUNROOF_FRONT + ".someip"
//            val uResource = UResource(topic, "", Sunroof::class.java.simpleName)
//
//            SomeIpUtil.pubEvent(sunroof, ServiceConstant.ACCESS_SERVICE, uResource, AccessService.mLaunchManager.getmUltifiLinkMonitor())
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_PASSENGER_COMPARTMENT_MODE_SERVICE_AVAILABILITY_STATUS) {
            Log.i(
                TAG,
                "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_PASSENGER_COMPARTMENT_MODE_SERVICE_AVAILABILITY_STATUS: Success"
            )
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_PASSENGER_COMPARTMENT_MODE_SERVICE_RESPONSE_STATUS) {
            Log.i(
                TAG,
                "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_PASSENGER_COMPARTMENT_MODE_SERVICE_RESPONSE_STATUS: Success"
            )
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_PASSENGER_COMPARTMENT_MODE_SERVICE_RESPONSE_STATUS_2) {
            Log.i(
                TAG,
                "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_PASSENGER_COMPARTMENT_MODE_SERVICE_RESPONSE_STATUS_2: Success"
            )
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_MODE_STATUS_2) {
            Log.i(TAG, "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_MODE_STATUS_2: Success")

//            val resp = SomeipS2SManagementInterface.Seat_Mode_Status_2Field.parseFrom(data.payload)
//            val seatMes = resp.outPut.
//
//
//
//            val resp = SomeipS2SManagementInterface.Sunroof_StatusField.parseFrom(data.payload)
//            val sunroofPercentagePositionStatus = resp.outPut.snrfPctPosSts
//            val booParam = resp.outPut.snrfConfig
//            Log.d(
//                TAG,
//                "onChangeEvent, PropName: sunroofPercentagePositionStatus, " +
//                        "newPropValue:$sunroofPercentagePositionStatus, " +
//                        "sunroofConf:$booParam"
//            )
//
//
//            Log.i(TAG, "Publishing the cloud events to Bus")
//
//            // no field mask in the resp, should set all the fields to msg obj
//            val sunroof: Sunroof = Sunroof.newBuilder()
//                .setPosition(sunroofPercentagePositionStatus)
//                .build()
//
//            val topic = ResourceMappingConstants.SUNROOF_FRONT + ".someip"
//            val uResource = UResource(topic, "", Sunroof::class.java.simpleName)
//
//            SomeIpUtil.pubEvent(sunroof, ServiceConstant.ACCESS_SERVICE, uResource, AccessService.mLaunchManager.getmUltifiLinkMonitor())


        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_PASSENGER_COMPARTMENT_MODE_AVAILABILITY_AND_NOTIFICATION_STATUS_6) {
            Log.i(
                TAG,
                "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_PASSENGER_COMPARTMENT_MODE_AVAILABILITY_AND_NOTIFICATION_STATUS_6: Success"
            )
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_DRIVER_SEAT_RECOVERY_POSITION) {
            Log.i(TAG, "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_DRIVER_SEAT_RECOVERY_POSITION: Success")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_DRIVER_SEAT_RECOVERY_POSITION_2) {
            Log.i(TAG, "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_DRIVER_SEAT_RECOVERY_POSITION_2: Success")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_ROW_COMFORT_MODE_REQUEST_INFO) {
            Log.i(
                TAG,
                "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_ROW_COMFORT_MODE_REQUEST_INFO: Success"
            )
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_ROW_LEFT_COMFORT_MODE_REQUEST_HMI) {
            Log.i(
                TAG,
                "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_ROW_LEFT_COMFORT_MODE_REQUEST_HMI: Success"
            )
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_ROW_RIGHT_COMFORT_MODE_REQUEST_HMI) {
            Log.i(
                TAG,
                "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_ROW_RIGHT_COMFORT_MODE_REQUEST_HMI: Success"
            )
        }
    }


    //#region driver seat set requests
    fun setDriverSeatRecallReq(enable:Boolean) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Driver_Seat_Recall_Request_Service_37C_M1: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Driver_Seat_Recall_Request_Service_37C_M1Field.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Driver_Seat_Recall_Request_Service_37C_M1.newBuilder()
                    .setDrvStRclReqSrv(enable)).build()

        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_DRIVER_SEAT_RECALL_REQUEST_SERVICE_37C_M1)
    }

    fun setDriverBolsterReq(pos: Int) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Driver_Seat_Recall_Request_Service_37C_M1: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Driver_Seat_Recall_Request_Service_37C_M1Field.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Driver_Seat_Recall_Request_Service_37C_M1.newBuilder()
                    .setDrvStBlstOtwdInwdTrgtPosReqSrv(pos)).build()

        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_DRIVER_SEAT_RECALL_REQUEST_SERVICE_37C_M1)
    }

    fun setDriverHeadRestReq(pos: Int): Boolean {
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Driver Seat head rest: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Driver_Seat_Recall_Request_Service_37C_M2Field.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Driver_Seat_Recall_Request_Service_37C_M2.newBuilder()
                    .setDrvStHdrstUpwdDnwdTrgtPosReqSrv(pos)).build()

        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_DRIVER_SEAT_RECALL_REQUEST_SERVICE_37C_M2)
    }

    fun setDriverLegRestReq( pos: Int): Boolean {
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Driver seat leg rest: failed, server is not available or client is not ready")
            return false
        }
//        var newRecall: GeneratedMessageV3= SomeipS2SManagementInterface.Driver_Seat_Recall_Request_Service_37C_M2Field.newBuilder().build()
//        } else if (component == SeatComponent.SC_SIDE_BOLSTER_BACK) {
        val newRecall = SomeipS2SManagementInterface.Driver_Seat_Recall_Request_Service_37C_M2Field.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Driver_Seat_Recall_Request_Service_37C_M2.newBuilder()
                    .setDrvStLgrstUpwdDnwdTrgtPosReqSrv(pos)).build()

        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_DRIVER_SEAT_RECALL_REQUEST_SERVICE_37C_M2)
    }

    fun setDriverSeatPosReq( pos: Int):Boolean {
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Driver seat: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Driver_Seat_Recall_Request_Service_3AB_M3Field.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Driver_Seat_Recall_Request_Service_3AB_M3.newBuilder()
                    .setDrvStFrwdBkwdTrgtPosReqSrv(pos)).build()

        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_DRIVER_SEAT_RECALL_REQUEST_SERVICE_3AB_M3)
    }

    fun setDriverReclinesReq(pos: Int):Boolean {
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Driver seat recline: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Driver_Seat_Recall_Request_Service_3AB_M3Field.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Driver_Seat_Recall_Request_Service_3AB_M3.newBuilder()
                    .setDrvStBkReclnUpwdDnwdTrgtPosReqSrv(pos)).build()

        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_DRIVER_SEAT_RECALL_REQUEST_SERVICE_3AB_M3)
    }

    fun setDriverCushionFrontReq(pos: Int):Boolean {
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Driver seat Cushion Front : failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Driver_Seat_Recall_Request_Service_3AC_M4Field.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Driver_Seat_Recall_Request_Service_3AC_M4.newBuilder()
                    .setDrvStCshnFrntUpwdDnwdTrgtPosReqSrv(pos)).build()

        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_DRIVER_SEAT_RECALL_REQUEST_SERVICE_3AC_M4)
    }


    fun setDriverCushionRearReq(pos: Int):Boolean {
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Driver seat cushion Rear: failed, server is not available or client is not ready")
            return false
        }
//        if (component == SeatComponent.SC_CUSHION) {
        val newRecall = SomeipS2SManagementInterface
            .Driver_Seat_Recall_Request_Service_3AC_M4Field.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Driver_Seat_Recall_Request_Service_3AC_M4.newBuilder()
                    .setDrvStCshnRrUpwdDnwdTrgtPosReqSrv(pos)).build()
        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_DRIVER_SEAT_RECALL_REQUEST_SERVICE_3AC_M4)
    }
    //endregion

    fun generateSendStatus(newRecall:GeneratedMessageV3, setTopic: Long):Boolean{
        val req = SomeIpData(setTopic, System.currentTimeMillis(), newRecall.toByteArray())
        val resp = SomeIpData()
        val res = someIpClientProxy?.setAttribute(req, resp)
        if (res == ResultValue.OK) {
            Log.i(TAG, "Response: OK")
            return true
        }
        Log.i(TAG, "Response: FAILED")
        return false
    }


    //region get driver seat configuration/status
    fun getDriverSeatConfig():SomeipS2SManagementInterface.Driver_Seat_ConfigurationField?{
        val resp = SomeIpData()
        val res = someIpClientProxy?.getAttribute(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_DRIVER_SEAT_CONFIGURATION, resp)
        if(res == ResultValue.OK){
            return SomeipS2SManagementInterface.Driver_Seat_ConfigurationField.parseFrom(resp.payload)
        }
        return null
    }

    fun getDriverRecline(): Boolean{
        val config = getDriverSeatConfig()
        if(config != null){
            return config.outPut.drvStBkReclnUpwdDnwdMovConfig
        }
        return false
    }

    fun getDriverBolster(): Boolean{
        val config = getDriverSeatConfig()
        if(config != null){
            return config.outPut.drvStBlstOtwdInwdMovConfig
        }
        return false
    }

    fun getDriverCushionFront(): Boolean{
        val config = getDriverSeatConfig()
        if(config != null){
            return config.outPut.drvStCshnFrntUpwdDnwdMovConfig
        }
        return false
    }

    fun getDriverCushionRear(): Boolean{
        val config = getDriverSeatConfig()
        if(config != null){
            return config.outPut.drvStCshnRrUpwdDnwdMovConfig
        }
        return false
    }

    fun getDriverSeat(): Boolean{
        val config = getDriverSeatConfig()
        if(config != null){
            return config.outPut.drvStFrwdBkwdMovConfig
        }
        return false
    }

    fun getDriverFoot(): Boolean{
        val config = getDriverSeatConfig()
        if(config != null){
            return config.outPut.drvStFtUpwdDnwdMovConfig
        }
        return false
    }

    fun getDriverHead(): Boolean{
        val config = getDriverSeatConfig()
        if(config != null){
            return config.outPut.drvStHdrstUpwdDnwdMovConfig

        }
        return false
    }

    fun getDriverLeg(): Boolean{
        val config = getDriverSeatConfig()
        if(config != null){
            return config.outPut.drvStLgrstUpwdMovConfig
        }
        return false
    }

    fun getDriverSeatStatus():SomeipS2SManagementInterface.Driver_Seat_Virtual_Control_Availability_And_Notification_StatusField?{
        val resp = SomeIpData()
        val res = someIpClientProxy?.getAttribute(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_DRIVER_SEAT_VIRTUAL_CONTROL_AVAILABILITY_AND_NOTIFICATION_STATUS, resp)
        if(res == ResultValue.OK){
            return SomeipS2SManagementInterface.Driver_Seat_Virtual_Control_Availability_And_Notification_StatusField.parseFrom(resp.payload)
        }
        return null
    }

    fun getDriverCushionFrontStatus():Boolean{
        val available = getDriverSeatStatus()
        if(available!=null){
            return available.outPut.drvStCushnFrtUpwdDnwdMovmtVirtCtrlAvl
        }
        return false
    }

    fun getDriverCushionRearStatus():Boolean{
        val available = getDriverSeatStatus()
        if(available!=null){
            return available.outPut.drvStCushnRrUpwdDnwdMovmtVirtCtrlAvl
        }
        return false
    }

    fun getDriverFootStatus():Boolean{
        val available = getDriverSeatStatus()
        if(available!=null){
            return available.outPut.drvStFtrstUpwdDnwdMovmtVirtCtrlAvl
        }
        return false
    }

    fun getDriverForwardStatus():Boolean{
        val available = getDriverSeatStatus()
        if(available!=null){
            return available.outPut.drvStFwdBkwdMovmtVirtCtrlAvl
        }
        return false
    }

    fun getDriverHeadForwardStatus():Boolean{
        val available = getDriverSeatStatus()
        if(available!=null){
            return available.outPut.drvStHdrstFwdBkwdMovmtVirtCtrlAvl
        }
        return false
    }

    fun getDriverHeadUpStatus():Boolean{
        val available = getDriverSeatStatus()
        if(available!=null){
            return available.outPut.drvStHdrstUpwdDnwdMovmtVirtCtrlAvl
        }
        return false
    }

    fun getDriverReclineStatus():Boolean{
        val available = getDriverSeatStatus()
        if(available!=null){
            return available.outPut.drvStRclUpwdDnwdMovmtVirtCtrlAvl
        }
        return false
    }

//            endregion


    fun getPositionMessage(topic: Long): SomeIpData?{
        if (!isServerAvailable || !isReady) {
            Log.i( TAG, "get Driver Seat Position: failed, server is not available or client is not ready")
            return null;
        }
        val resp = SomeIpData()
        val res = someIpClientProxy?.getAttribute(topic, resp)
        if (res == ResultValue.OK) {
            return resp
        }
        return null;
    }

    
    //region Driver seat get position

    fun getDriverSeatPosition(): Int?{
        val resp = getPositionMessage(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_DRIVER_SEAT_PERCENTAGE_POSITION_1)
        if(resp != null){
            val tmp =  SomeipS2SManagementInterface.Driver_Seat_Percentage_Position_1Field.parseFrom(resp.payload)
            return tmp.outPut.drvStFrwdBkwdPos
        }
        return null
    }

    fun getDriverReclinePosition(): Int?{
        val resp = getPositionMessage(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_DRIVER_SEAT_PERCENTAGE_POSITION_1)
        if(resp != null){
            val tmp =  SomeipS2SManagementInterface.Driver_Seat_Percentage_Position_1Field.parseFrom(resp.payload)
            return tmp.outPut.drvStBkReclnUpwdDnwdPos
        }
        return null
    }

    fun getDriverCushionRearPosition(): Int?{
        val resp = getPositionMessage(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_DRIVER_SEAT_PERCENTAGE_POSITION_2)
        if(resp != null){
            val tmp =  SomeipS2SManagementInterface.Driver_Seat_Percentage_Position_2Field.parseFrom(resp.payload)
            return tmp.outPut.drvStCshnRrUpwdDnwdPos
        }
        return null
    }

    fun getDriverCushionFrontPosition(): Int?{
        val resp = getPositionMessage(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_DRIVER_SEAT_PERCENTAGE_POSITION_2)
        if(resp != null){
            val tmp =  SomeipS2SManagementInterface.Driver_Seat_Percentage_Position_2Field.parseFrom(resp.payload)
            return tmp.outPut.drvStCshnFrntUpwdDnwdPos
        }
        return null
    }

    fun getDriverBolsterPosition(): Int?{
        val resp = getPositionMessage(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_DRIVER_SEAT_PERCENTAGE_POSITION_3)
        if(resp != null){
            val tmp =  SomeipS2SManagementInterface.Driver_Seat_Percentage_Position_3Field.parseFrom(resp.payload)
            return tmp.outPut.drvStBlstOtwdInwdPos
        }
        return null
    }

    fun getDriverFootPosition(): Int?{
        val resp = getPositionMessage(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_DRIVER_SEAT_PERCENTAGE_POSITION_3)
        if(resp != null){
            val tmp =  SomeipS2SManagementInterface.Driver_Seat_Percentage_Position_3Field.parseFrom(resp.payload)
            return tmp.outPut.drvStFtUpwdDnwdPos
        }
        return null
    }

    fun getDriverHeadPosition(): Int?{
        val resp = getPositionMessage(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_DRIVER_SEAT_PERCENTAGE_POSITION_3)
        if(resp != null){
            val tmp =  SomeipS2SManagementInterface.Driver_Seat_Percentage_Position_3Field.parseFrom(resp.payload)
            return tmp.outPut.drvStHdrstUpwdDnwdPos
        }
        return null
    }

    fun getDriverLegPosition(): Int?{
        val resp = getPositionMessage(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_DRIVER_SEAT_PERCENTAGE_POSITION_3)
        if(resp != null){
            val tmp =  SomeipS2SManagementInterface.Driver_Seat_Percentage_Position_3Field.parseFrom(resp.payload)
            return tmp.outPut.drvStLgrstUpwdDnwdPos
//            return tmp.outPut.drvStPosRclRspStat
        }
        return null
    }

    // what is this property?
    fun getDriverReclinePositionStat(): Int?{
        val resp = getPositionMessage(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_DRIVER_SEAT_PERCENTAGE_POSITION_3)
        if(resp != null){
            val tmp =  SomeipS2SManagementInterface.Driver_Seat_Percentage_Position_3Field.parseFrom(resp.payload)
            return tmp.outPut.drvStPosRclRspStat
        }
        return null
    }

//    endregion


    //region Passenger seat set requests
    fun setPassSeatRecallReq(enable:Boolean) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Passenger seat service enable: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Passenger_Seat_Recall_Request_Service1Field.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Passenger_Seat_Recall_Request_Service1.newBuilder()
                .setPassStRclReqSrv(enable)).build()

        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_PASSENGER_SEAT_RECALL_REQUEST_SERVICE1)
    }

    fun setPassReclineRecallReq(pos:Int) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Passenger seat recline: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Passenger_Seat_Recall_Request_Service1Field.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Passenger_Seat_Recall_Request_Service1.newBuilder()
                .setPassStBkReclnUpwdDnwdTrgtPosReqSrv(pos)).build()

        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_PASSENGER_SEAT_RECALL_REQUEST_SERVICE1)
    }

    fun setPassCushionFrontRecallReq(pos:Int) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Passenger seat cushion front: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Passenger_Seat_Recall_Request_Service1Field.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Passenger_Seat_Recall_Request_Service1.newBuilder()
                .setPassStCshnFrntUpwdDnwdTrgtPosReqSrv(pos)).build()

        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_PASSENGER_SEAT_RECALL_REQUEST_SERVICE1)
    }

    fun setPassSeatFrontRecallReq(pos:Int) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Passenger seat front: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Passenger_Seat_Recall_Request_Service1Field.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Passenger_Seat_Recall_Request_Service1.newBuilder()
                .setPassStFrwdBkwdTrgtPosReqSrv(pos)).build()

        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_PASSENGER_SEAT_RECALL_REQUEST_SERVICE1)
    }

    fun setPassCushionRearRecallReq(pos:Int) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Passenger seat cushion rear: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Passenger_Seat_Recall_Request_Service1Field.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Passenger_Seat_Recall_Request_Service1.newBuilder()
                .setPassStCshnRrUpwdDnwdTrgtPosReqSrv(pos)).build()

        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_PASSENGER_SEAT_RECALL_REQUEST_SERVICE1)
    }

    fun setPassFootRecallReq(pos:Int) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Passenger seat foot rest: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Passenger_Seat_Recall_Request_Service1Field.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Passenger_Seat_Recall_Request_Service1.newBuilder()
                .setPassStFtrstUpwdDnwdTrgtPosReqSrv(pos)).build()

        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_PASSENGER_SEAT_RECALL_REQUEST_SERVICE1)
    }

    fun setPassHeadBackRecallReq(pos:Int) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Passenger seat head rest: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Passenger_Seat_Recall_Request_Service2Field.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Passenger_Seat_Recall_Request_Service2.newBuilder()
                .setPassStHdrstFwdBkwdTrgtPosReqSrv(pos)).build()

        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_PASSENGER_SEAT_RECALL_REQUEST_SERVICE2)
    }

    fun setPassHeadUpRecallReq(pos:Int) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Passenger seat head rest: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Passenger_Seat_Recall_Request_Service2Field.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Passenger_Seat_Recall_Request_Service2.newBuilder()
                .setPassStHdrstUpwdDnwdTrgtPosReqSrv(pos)).build()

        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_PASSENGER_SEAT_RECALL_REQUEST_SERVICE2)
    }
    //endregion


    //region Passenger seat configuration/statue
    fun getPassSeatConfig():SomeipS2SManagementInterface.Passenger_Seat_ConfigurationField?{
        val resp = SomeIpData()
        val res = someIpClientProxy?.getAttribute(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_PASSENGER_SEAT_CONFIGURATION, resp)
        if(res == ResultValue.OK){
            return SomeipS2SManagementInterface.Passenger_Seat_ConfigurationField.parseFrom(resp.payload)
        }
        return null
    }

    fun getPassRecline(): Boolean{
        val config = getPassSeatConfig()
        if(config != null){
            return config.outPut.passStBkReclnUpwdDnwdMovConfig
        }
        return false
    }

    fun getPassBolster(): Boolean{
        val config = getPassSeatConfig()
        if(config != null){
            return config.outPut.passStBlstOtwdInwdMovConfig
        }
        return false
    }

    fun getPassCushionFront(): Boolean{
        val config = getPassSeatConfig()
        if(config != null){
            return config.outPut.passStCshnFrntUpwdDnwdMovConfig
        }
        return false
    }

    fun getPassCushionRear(): Boolean{
        val config = getPassSeatConfig()
        if(config != null){
            return config.outPut.passStCshnRrUpwdDnwdMovConfig
        }
        return false
    }

    fun getPassForward(): Boolean{
        val config = getPassSeatConfig()
        if(config != null){
            return config.outPut.passStFrwdBkwdMovConfig
        }
        return false
    }
    fun getPassFootUpward(): Boolean{
        val config = getPassSeatConfig()
        if(config != null){
            return config.outPut.passStFtUpwdDnwdMovConfig
        }
        return false
    }
    fun getPassLegUpward(): Boolean{
        val config = getPassSeatConfig()
        if(config != null){
            return config.outPut.passStLgrstUpwdMovConfig
        }
        return false
    }
    fun getPassHeadUpward(): Boolean{
        val config = getPassSeatConfig()
        if(config != null){
            return config.outPut.passStHdrstUpwdDnwdMovConfig
        }
        return false
    }


    fun getPassSeatStatus():SomeipS2SManagementInterface.Passenger_Seat_Virtual_Control_Availability_And_Notification_StatusField?{
        val resp = SomeIpData()
        val res = someIpClientProxy?.getAttribute(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_PASSENGER_SEAT_VIRTUAL_CONTROL_AVAILABILITY_AND_NOTIFICATION_STATUS, resp)
        if(res == ResultValue.OK){
            return SomeipS2SManagementInterface.Passenger_Seat_Virtual_Control_Availability_And_Notification_StatusField.parseFrom(resp.payload)
        }
        return null
    }

    fun getPassCushionFrontStatus():Boolean{
        val available = getPassSeatStatus()
        if(available!=null){
            return available.outPut.passStCushnFrtUpwdDnwdMovmtVirtCtrlAvl
        }
        return false
    }

    fun getPassCushionRearStatus():Boolean{
        val available = getPassSeatStatus()
        if(available!=null){
            return available.outPut.passStCushnRrUpwdDnwdMovmtVirtCtrlAvl
        }
        return false
    }

    fun getPassSeatForwardStatus():Boolean{
        val available = getPassSeatStatus()
        if(available!=null){
            return available.outPut.passStFwdBkwdMovmtVirtCtrlAvl
        }
        return false
    }

    fun getPassLamberStatus():Boolean{
        val available = getPassSeatStatus()
        if(available!=null){
            return available.outPut.passStLmbrVirtCtrlAvl
        }
        return false
    }

    fun getPassFootStatus():Boolean{
        val available = getPassSeatStatus()
        if(available!=null){
            return available.outPut.passStFtrstUpwdDnwdMovmtVirtCtrlAvl
        }
        return false
    }

    fun getPassHeadForwardStatus():Boolean{
        val available = getPassSeatStatus()
        if(available!=null){
            return available.outPut.passStHdrstFwdBkwdMovmtVirtCtrlAvl
        }
        return false
    }

    fun getPassHeadUpwardStatus():Boolean{
        val available = getPassSeatStatus()
        if(available!=null){
            return available.outPut.passStHdrstUpwdDnwdMovmtVirtCtrlAvl
        }
        return false
    }

    fun getPassReclineStatus():Boolean{
        val available = getPassSeatStatus()
        if(available!=null){
            return available.outPut.passStRclUpwdDnwdMovmtVirtCtrlAvl
        }
        return false
    }
    //endregion


    //region Passenger get position
    fun getPassReclinePosition(): Int?{
        val resp = getPositionMessage(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_PASSENGER_SEAT_PERCENTAGE_POSITION_1)
        if(resp != null){
            val tmp =  SomeipS2SManagementInterface.Passenger_Seat_Percentage_Position_1Field.parseFrom(resp.payload)
            return tmp.outPut.passStBkReclnUpwdDnwdPos
        }
        return null
    }

    fun getPassBolsterPosition(): Int?{
        val resp = getPositionMessage(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_PASSENGER_SEAT_PERCENTAGE_POSITION_1)
        if(resp != null){
            val tmp =  SomeipS2SManagementInterface.Passenger_Seat_Percentage_Position_1Field.parseFrom(resp.payload)
            return tmp.outPut.passStBlstOtwdInwdPos
        }
        return null
    }

    fun getPassForwardPosition(): Int?{
        val resp = getPositionMessage(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_PASSENGER_SEAT_PERCENTAGE_POSITION_1)
        if(resp != null){
            val tmp =  SomeipS2SManagementInterface.Passenger_Seat_Percentage_Position_1Field.parseFrom(resp.payload)
            return tmp.outPut.passStFrwdBkwdPos
        }
        return null
    }

    fun getPassFootPosition(): Int?{
        val resp = getPositionMessage(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_PASSENGER_SEAT_PERCENTAGE_POSITION_1)
        if(resp != null){
            val tmp =  SomeipS2SManagementInterface.Passenger_Seat_Percentage_Position_1Field.parseFrom(resp.payload)
            return tmp.outPut.passStFtUpwdDnwdPos
        }
        return null
    }

    fun getPassCushionPosition(): Int?{
        val resp = getPositionMessage(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_PASSENGER_SEAT_PERCENTAGE_POSITION_2)
        if(resp != null){
            val tmp =  SomeipS2SManagementInterface.Passenger_Seat_Percentage_Position_2Field.parseFrom(resp.payload)
            return tmp.outPut.passStCshnFrntUpwdDnwdPos
        }
        return null
    }

    fun getPassCushionRearPosition(): Int?{
        val resp = getPositionMessage(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_PASSENGER_SEAT_PERCENTAGE_POSITION_2)
        if(resp != null){
            val tmp =  SomeipS2SManagementInterface.Passenger_Seat_Percentage_Position_2Field.parseFrom(resp.payload)
            return tmp.outPut.passStCshnRrUpwdDnwdPos
        }
        return null
    }

    fun getPassHeadPosition(): Int?{
        val resp = getPositionMessage(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_PASSENGER_SEAT_PERCENTAGE_POSITION_2)
        if(resp != null){
            val tmp =  SomeipS2SManagementInterface.Passenger_Seat_Percentage_Position_2Field.parseFrom(resp.payload)
            return tmp.outPut.passStHdrstUpwdDnwdPos
        }
        return null
    }

    fun getPassLegPosition(): Int?{
        val resp = getPositionMessage(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_PASSENGER_SEAT_PERCENTAGE_POSITION_2)
        if(resp != null){
            val tmp =  SomeipS2SManagementInterface.Passenger_Seat_Percentage_Position_2Field.parseFrom(resp.payload)
            return tmp.outPut.passStLgrstUpwdDnwdPos
        }
        return null
    }

    //endregion


    //region SecondLeft seat set requests
    fun setSecLeftRecallReq(enable:Boolean) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Second left seat service enable: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Second_Row_Left_Seat_Recall_Request_Service1Field.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Second_Row_Left_Seat_Recall_Request_Service1.newBuilder()
                .setSecRwLtStRclReqSrv(enable)).build()
        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_SECOND_ROW_LEFT_SEAT_RECALL_REQUEST_SERVICE1)
    }

    fun setSecLeftCushionFrontPos(pos: Int) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Second left seat service enable: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Second_Row_Left_Seat_Recall_Request_Service1Field.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Second_Row_Left_Seat_Recall_Request_Service1.newBuilder()
                .setSecRwLtStCushnFrtUpwdDnwdTrgtPosReqSrv(pos)).build()
        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_SECOND_ROW_LEFT_SEAT_RECALL_REQUEST_SERVICE1)
    }

    fun setSecLeftCushionRearPos(pos: Int) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Second left seat service enable: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Second_Row_Left_Seat_Recall_Request_Service1Field.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Second_Row_Left_Seat_Recall_Request_Service1.newBuilder()
                .setSecRwLtStCushnRrUpwdDnwdTrgtPosReqSrv(pos)).build()
        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_SECOND_ROW_LEFT_SEAT_RECALL_REQUEST_SERVICE1)
    }

    fun setSecLeftSeatForwardPos(pos: Int) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Second left seat service enable: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Second_Row_Left_Seat_Recall_Request_Service1Field.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Second_Row_Left_Seat_Recall_Request_Service1.newBuilder()
                .setSecRwLtStFwdBkwdTrgtPosReqSrv(pos)).build()
        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_SECOND_ROW_LEFT_SEAT_RECALL_REQUEST_SERVICE1)
    }

    fun setSecLeftSeatLeftwardPos(pos: Int) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Second left seat service enable: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Second_Row_Left_Seat_Recall_Request_Service1Field.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Second_Row_Left_Seat_Recall_Request_Service1.newBuilder()
                .setSecRwLtStLtwdRtwdTrgtPosReqSrv(pos)).build()
        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_SECOND_ROW_LEFT_SEAT_RECALL_REQUEST_SERVICE1)
    }

    fun setSecLeftReclinePos(pos: Int) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Second left seat service enable: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Second_Row_Left_Seat_Recall_Request_Service1Field.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Second_Row_Left_Seat_Recall_Request_Service1.newBuilder()
                .setSecRwLtStRclUpwdDnwdTrgtPosReqSrv(pos)).build()
        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_SECOND_ROW_LEFT_SEAT_RECALL_REQUEST_SERVICE1)
    }

    fun setSecLeftFootPos(pos: Int) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Second left seat service enable: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Second_Row_Left_Seat_Recall_Request_Service2Field.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Second_Row_Left_Seat_Recall_Request_Service2.newBuilder()
                .setSecRwLtStFtrstUpwdDnwdTrgtPosReqSrv(pos)).build()
        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_SECOND_ROW_LEFT_SEAT_RECALL_REQUEST_SERVICE2)
    }

    fun setSecLeftHeadForwardPos(pos: Int) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Second left seat service enable: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Second_Row_Left_Seat_Recall_Request_Service2Field.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Second_Row_Left_Seat_Recall_Request_Service2.newBuilder()
                .setSecRwLtStHdrstFwdBkwdTrgtPosReqSrv(pos)).build()
        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_SECOND_ROW_LEFT_SEAT_RECALL_REQUEST_SERVICE2)
    }
    fun setSecLeftHeadUpwardPos(pos: Int) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Second left seat service enable: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Second_Row_Left_Seat_Recall_Request_Service2Field.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Second_Row_Left_Seat_Recall_Request_Service2.newBuilder()
                .setSecRwLtStHdrstUpwdDnwdTrgtPosReqSrv(pos)).build()
        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_SECOND_ROW_LEFT_SEAT_RECALL_REQUEST_SERVICE2)
    }

    fun setSecLeftLegOutwardPos(pos: Int) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Second left seat service enable: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Second_Row_Left_Seat_Recall_Request_Service2Field.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Second_Row_Left_Seat_Recall_Request_Service2.newBuilder()
                .setSecRwLtStLgrstOtwdInwdTrgtPosReqSrv(pos)).build()
        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_SECOND_ROW_LEFT_SEAT_RECALL_REQUEST_SERVICE2)
    }

    fun setSecLeftLegUpwardPos(pos: Int) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Second left seat service enable: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Second_Row_Left_Seat_Recall_Request_Service2Field.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Second_Row_Left_Seat_Recall_Request_Service2.newBuilder()
                .setSecRwLtStLgrstUpwdDnwdTrgtPosReqSrv(pos)).build()
        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_SECOND_ROW_LEFT_SEAT_RECALL_REQUEST_SERVICE2)
    }
    fun setSecLeftArmPos(pos: Int) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Second left seat service enable: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Second_Row_Left_Seat_Recall_Request_Service3Field.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Second_Row_Left_Seat_Recall_Request_Service3.newBuilder()
                .setSecRwLtStArmScrnUpwdDnwdTrgtPosReqSrv(pos)).build()
        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_SECOND_ROW_LEFT_SEAT_RECALL_REQUEST_SERVICE3)
    }

    //endregion


    //region Second Left seat configuration/status
    fun getSecondLeftSeatConfig():SomeipS2SManagementInterface.Second_Left_Seat_ConfigurationField?{
        val resp = SomeIpData()
        val res = someIpClientProxy?.getAttribute(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SECOND_LEFT_SEAT_CONFIGURATION, resp)
        if(res == ResultValue.OK){
            return SomeipS2SManagementInterface.Second_Left_Seat_ConfigurationField.parseFrom(resp.payload)
        }
        return null
    }

    fun getSecondLeftRecline(): Boolean{
        val config = getSecondLeftSeatConfig()
        if(config != null){
            return config.outPut.secRwLtStBkReclnUpwdDnwdMovConfig
        }
        return false
    }

    fun getSecondLeftBolster(): Boolean{
        val config = getSecondLeftSeatConfig()
        if(config != null){
            return config.outPut.secRwLtStBlstOtwdInwdMovConfig
        }
        return false
    }

    fun getSecondLeftCushionFront(): Boolean{
        val config = getSecondLeftSeatConfig()
        if(config != null){
            return config.outPut.secRwLtStCshnFrntUpwdDnwdMovConfig
        }
        return false
    }

    fun getSecondLeftCushionRear(): Boolean{
        val config = getSecondLeftSeatConfig()
        if(config != null){
            return config.outPut.secRwLtStCshnRrUpwdDnwdMemConfig
        }
        return false
    }

    fun getSecondLeftForward(): Boolean{
        val config = getSecondLeftSeatConfig()
        if(config != null){
            return config.outPut.secRwLtStFrwdBkwdMovConfig
        }
        return false
    }

    fun getSecondLeftHead(): Boolean{
        val config = getSecondLeftSeatConfig()
        if(config != null){
            return config.outPut.secRwLtStHdrstUpwdDnwdMovConfig
        }
        return false
    }

    fun getSecondLeftLeg(): Boolean{
        val config = getSecondLeftSeatConfig()
        if(config != null){
            return config.outPut.secRwLtStLgrstUpwdMovConfig
        }
        return false
    }

    fun getSecondLeftLeftward(): Boolean{
        val config = getSecondLeftSeatConfig()
        if(config != null){
            return config.outPut.secRwLtStLtwdRtwdMovConfig
        }
        return false
    }

    fun getSecondLeftSeatStatus():SomeipS2SManagementInterface.Second_Row_Left_Seat_Virtual_Control_Availability_And_Notification_StatusField?{
        val resp = SomeIpData()
        val res = someIpClientProxy?.getAttribute(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SECOND_ROW_LEFT_SEAT_VIRTUAL_CONTROL_AVAILABILITY_AND_NOTIFICATION_STATUS, resp)
        if(res == ResultValue.OK){
            return SomeipS2SManagementInterface.Second_Row_Left_Seat_Virtual_Control_Availability_And_Notification_StatusField.parseFrom(resp.payload)
        }
        return null
    }

    fun getSecondLeftArmStatus():Boolean{
        val available = getSecondLeftSeatStatus()
        if(available!=null){
            return available.outPut.secRwLtStArmScrnUpwdDnwdMovmtVirtCtrlAvl
        }
        return false
    }

    fun getSecondLeftCushionFrontStatus():Boolean{
        val available = getSecondLeftSeatStatus()
        if(available!=null){
            return available.outPut.secRwLtStCushnFrtUpwdDnwdMovmtVirtCtrlAvl
        }
        return false
    }
    fun getSecondLeftCushionRearStatus():Boolean{
        val available = getSecondLeftSeatStatus()
        if(available!=null){
            return available.outPut.secRwLtStCushnRrUpwdDnwdMovmtVirtCtrlAvl
        }
        return false
    }
    fun getSecondLeftFootRestStatus():Boolean{
        val available = getSecondLeftSeatStatus()
        if(available!=null){
            return available.outPut.secRwLtStFtrstUpwdDnwdMovmtVirtCtrlAvl
        }
        return false
    }
    fun getSecondLeftHeadForwardStatus():Boolean{
        val available = getSecondLeftSeatStatus()
        if(available!=null){
            return available.outPut.secRwLtStHdrstFwdBkwdMovmtVirtCtrlAvl
        }
        return false
    }
    fun getSecondLeftHeadUpwardStatus():Boolean{
        val available = getSecondLeftSeatStatus()
        if(available!=null){
            return available.outPut.secRwLtStHdrstUpwdDnwdMovmtVirtCtrlAvl
        }
        return false
    }
    fun getSecondLeftLegOutwardStatus():Boolean{
        val available = getSecondLeftSeatStatus()
        if(available!=null){
            return available.outPut.secRwLtStLgrstOtwdInwdMovmtVirtCtrlAvl
        }
        return false
    }
    fun getSecondLeftLegUpwardStatus():Boolean{
        val available = getSecondLeftSeatStatus()
        if(available!=null){
            return available.outPut.secRwLtStLgrstUpwdDnwdMovmtVirtCtrlAvl
        }
        return false
    }
    fun getSecondLeftLeftwardStatus():Boolean{
        val available = getSecondLeftSeatStatus()
        if(available!=null){
            return available.outPut.secRwLtStLtwdRtwdMovmtVirtCtrlAvl
        }
        return false
    }
    fun getSecondLeftNeckStatus():Boolean{
        val available = getSecondLeftSeatStatus()
        if(available!=null){
            return available.outPut.secRwLtStNckrstFrwdBkwdMovmtVirtCtrlAvl
        }
        return false
    }
    fun getSecondLeftReclineStatus():Boolean{
        val available = getSecondLeftSeatStatus()
        if(available!=null){
            return available.outPut.secRwLtStRclUpwdDnwdMovmtVirtCtrlAvl
        }
        return false
    }
    fun getSecondLeftForwardStatus():Boolean{
        val available = getSecondLeftSeatStatus()
        if(available!=null){
            return available.outPut.secRwLtStFwdBkwdMovmtVirtCtrlAvl
        }
        return false
    }
    //endregion


    //region Second Left seat get position
    fun getSecondLeftReclinePos(): Int?{
        val resp = getPositionMessage(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SECOND_LEFT_SEAT_PERCENTAGE_POSITION_1)
        if(resp != null){
            val tmp =  SomeipS2SManagementInterface.Second_Left_Seat_Percentage_Position_1Field.parseFrom(resp.payload)
            return tmp.outPut.secRwLtStBkReclnUpwdDnwdPos
        }
        return null
    }
    fun getSecondLeftBolsterPos(): Int?{
        val resp = getPositionMessage(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SECOND_LEFT_SEAT_PERCENTAGE_POSITION_1)
        if(resp != null){
            val tmp =  SomeipS2SManagementInterface.Second_Left_Seat_Percentage_Position_1Field.parseFrom(resp.payload)
            return tmp.outPut.secRwLtStBlstOtwdInwdPos
        }
        return null
    }
    fun getSecondLeftForwardPos(): Int?{
        val resp = getPositionMessage(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SECOND_LEFT_SEAT_PERCENTAGE_POSITION_1)
        if(resp != null){
            val tmp =  SomeipS2SManagementInterface.Second_Left_Seat_Percentage_Position_1Field.parseFrom(resp.payload)
            return tmp.outPut.secRwLtStFrwdBkwdPos
        }
        return null
    }
    fun getSecondLeftLeftwardPos(): Int?{
        val resp = getPositionMessage(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SECOND_LEFT_SEAT_PERCENTAGE_POSITION_1)
        if(resp != null){
            val tmp =  SomeipS2SManagementInterface.Second_Left_Seat_Percentage_Position_1Field.parseFrom(resp.payload)
            return tmp.outPut.secRwLtStLtwdRtwdPos
        }
        return null
    }
    fun getSecondLeftCushionFrontPos(): Int?{
        val resp = getPositionMessage(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SECOND_LEFT_SEAT_PERCENTAGE_POSITION_2)
        if(resp != null){
            val tmp =  SomeipS2SManagementInterface.Second_Left_Seat_Percentage_Position_2Field.parseFrom(resp.payload)
            return tmp.outPut.secRwLtStCshnFrntUpwdDnwdPos
        }
        return null
    }
    fun getSecondLeftCushionRearPos(): Int?{
        val resp = getPositionMessage(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SECOND_LEFT_SEAT_PERCENTAGE_POSITION_2)
        if(resp != null){
            val tmp =  SomeipS2SManagementInterface.Second_Left_Seat_Percentage_Position_2Field.parseFrom(resp.payload)
            return tmp.outPut.secRwLtStCshnRrUpwdDnwdPos
        }
        return null
    }
    fun getSecondLeftHeadUpwardPos(): Int?{
        val resp = getPositionMessage(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SECOND_LEFT_SEAT_PERCENTAGE_POSITION_2)
        if(resp != null){
            val tmp =  SomeipS2SManagementInterface.Second_Left_Seat_Percentage_Position_2Field.parseFrom(resp.payload)
            return tmp.outPut.secRwLtStHdrstStUpwdDnwdPos
        }
        return null
    }
    fun getSecondLeftLegUpwardPos(): Int?{
        val resp = getPositionMessage(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SECOND_LEFT_SEAT_PERCENTAGE_POSITION_2)
        if(resp != null){
            val tmp =  SomeipS2SManagementInterface.Second_Left_Seat_Percentage_Position_2Field.parseFrom(resp.payload)
            return tmp.outPut.secRwLtStLgrstUpwdDnwdPos
        }
        return null
    }

    //endregion


    //region Second right seat set requests
    fun setSecRightRecallReq(enable:Boolean) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Second left seat service enable: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Second_Row_Right_Seat_Recall_Request_Service1Field.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Second_Row_Right_Seat_Recall_Request_Service1.newBuilder()
                .setSecRwRtStRclReqSrv(enable)).build()
        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_SECOND_ROW_RIGHT_SEAT_RECALL_REQUEST_SERVICE1)
    }
    fun setSecRightCushionFrontPos(pos: Int) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Second left seat service enable: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Second_Row_Right_Seat_Recall_Request_Service1Field.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Second_Row_Right_Seat_Recall_Request_Service1.newBuilder()
                .setSecRwRtStCushnFrtUpwdDnwdTrgtPosReqSrv(pos)).build()
        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_SECOND_ROW_RIGHT_SEAT_RECALL_REQUEST_SERVICE1)
    }
    fun setSecRightCushionRearPos(pos: Int) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Second left seat service enable: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Second_Row_Right_Seat_Recall_Request_Service1Field.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Second_Row_Right_Seat_Recall_Request_Service1.newBuilder()
                .setSecRwRtStCushnRrUpwdDnwdTrgtPosReqSrv(pos)).build()
        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_SECOND_ROW_RIGHT_SEAT_RECALL_REQUEST_SERVICE1)
    }
    fun setSecRightForwardPos(pos: Int) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Second left seat service enable: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Second_Row_Right_Seat_Recall_Request_Service1Field.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Second_Row_Right_Seat_Recall_Request_Service1.newBuilder()
                .setSecRwRtStFwdBkwdTrgtPosReqSrv(pos)).build()
        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_SECOND_ROW_RIGHT_SEAT_RECALL_REQUEST_SERVICE1)
    }
    fun setSecRightLeftwardPos(pos: Int) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Second left seat service enable: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Second_Row_Right_Seat_Recall_Request_Service1Field.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Second_Row_Right_Seat_Recall_Request_Service1.newBuilder()
                .setSecRwRtStLtwdRtwdTrgtPosReqSrv(pos)).build()
        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_SECOND_ROW_RIGHT_SEAT_RECALL_REQUEST_SERVICE1)
    }
    fun setSecRightReclinePos(pos: Int) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Second left seat service enable: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Second_Row_Right_Seat_Recall_Request_Service1Field.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Second_Row_Right_Seat_Recall_Request_Service1.newBuilder()
                .setSecRwRtStRclUpwdDnwdTrgtPosReqSrv(pos)).build()
        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_SECOND_ROW_RIGHT_SEAT_RECALL_REQUEST_SERVICE1)
    }
    fun setSecRightFootPos(pos: Int) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Second left seat service enable: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Second_Row_Right_Seat_Recall_Request_Service2Field.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Second_Row_Right_Seat_Recall_Request_Service2.newBuilder()
                .setSecRwRtStFtrstUpwdDnwdTrgtPosReqSrv(pos)).build()
        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_SECOND_ROW_RIGHT_SEAT_RECALL_REQUEST_SERVICE2)
    }
    fun setSecRightHeadForwardPos(pos: Int) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Second left seat service enable: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Second_Row_Right_Seat_Recall_Request_Service2Field.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Second_Row_Right_Seat_Recall_Request_Service2.newBuilder()
                .setSecRwRtStHdrstFwdBkwdTrgtPosReqSrv(pos)).build()
        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_SECOND_ROW_RIGHT_SEAT_RECALL_REQUEST_SERVICE2)
    }
    fun setSecRightHeadUpwardPos(pos: Int) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Second left seat service enable: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Second_Row_Right_Seat_Recall_Request_Service2Field.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Second_Row_Right_Seat_Recall_Request_Service2.newBuilder()
                .setSecRwRtStHdrstUpwdDnwdTrgtPosReqSrv(pos)).build()
        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_SECOND_ROW_RIGHT_SEAT_RECALL_REQUEST_SERVICE2)
    }
    fun setSecRightLegOutwardPos(pos: Int) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Second left seat service enable: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Second_Row_Right_Seat_Recall_Request_Service2Field.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Second_Row_Right_Seat_Recall_Request_Service2.newBuilder()
                .setSecRwRtStLgrstOtwdInwdTrgtPosReqSrv(pos)).build()
        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_SECOND_ROW_RIGHT_SEAT_RECALL_REQUEST_SERVICE2)
    }
    fun setSecRightLegUpwardPos(pos: Int) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Second left seat service enable: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Second_Row_Right_Seat_Recall_Request_Service2Field.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Second_Row_Right_Seat_Recall_Request_Service2.newBuilder()
                .setSecRwRtStLgrstUpwdDnwdTrgtPosReqSrv(pos)).build()
        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_SECOND_ROW_RIGHT_SEAT_RECALL_REQUEST_SERVICE2)
    }
    fun setSecRightArmPos(pos: Int) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Second left seat service enable: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Second_Row_Right_Seat_Recall_Request_Service3Field.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Second_Row_Right_Seat_Recall_Request_Service3.newBuilder()
                .setSecRwRtStArmScrnUpwdDnwdTrgtPosReqSrv(pos)).build()
        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_SECOND_ROW_RIGHT_SEAT_RECALL_REQUEST_SERVICE3)
    }

    //endregion


    //region Second right seat configuration/status
    fun getSecRightSeatConfig():SomeipS2SManagementInterface.Second_Right_Seat_ConfigurationField?{
        val resp = SomeIpData()
        val res = someIpClientProxy?.getAttribute(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SECOND_RIGHT_SEAT_CONFIGURATION, resp)
        if(res == ResultValue.OK){
            return SomeipS2SManagementInterface.Second_Right_Seat_ConfigurationField.parseFrom(resp.payload)
        }
        return null
    }

    fun getSecRightLeftward(): Boolean{
        val config = getSecRightSeatConfig()
        if(config != null){
            return config.outPut.secRwRtStLtwdRtwdMovConfig
        }
        return false
    }
    fun getSecRightLegUpward(): Boolean{
        val config = getSecRightSeatConfig()
        if(config != null){
            return config.outPut.secRwRtStLgrstUpwdMovConfig
        }
        return false
    }
    fun getSecRightForward(): Boolean{
        val config = getSecRightSeatConfig()
        if(config != null){
            return config.outPut.secRwRtStFrwdBkwdMovConfig
        }
        return false
    }
    fun getSecRightRecline(): Boolean{
        val config = getSecRightSeatConfig()
        if(config != null){
            return config.outPut.secRwRtStBkReclnUpwdDnwdMovConfig
        }
        return false
    }
    fun getSecRightHeadUpward(): Boolean{
        val config = getSecRightSeatConfig()
        if(config != null){
            return config.outPut.secRwRtStHdrstUpwdDnwdMovConfig
        }
        return false
    }
    fun getSecRightBolster(): Boolean{
        val config = getSecRightSeatConfig()
        if(config != null){
            return config.outPut.secRwRtStBlstOtwdInwdMovConfig
        }
        return false
    }
    fun getSecRightCushionFront(): Boolean{
        val config = getSecRightSeatConfig()
        if(config != null){
            return config.outPut.secRwRtStCshnFrntUpwdDnwdMovConfig
        }
        return false
    }
    fun getSecRightCushionRear(): Boolean{
        val config = getSecRightSeatConfig()
        if(config != null){
            return config.outPut.secRwRtStCshnRrUpwdDnwdMovConfig
        }
        return false
    }

    fun getSecRightSeatStatus():SomeipS2SManagementInterface.Second_Row_Right_Seat_Virtual_Control_Availability_And_Notification_StatusField?{
        val resp = SomeIpData()
        val res = someIpClientProxy?.getAttribute(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SECOND_ROW_RIGHT_SEAT_VIRTUAL_CONTROL_AVAILABILITY_AND_NOTIFICATION_STATUS, resp)
        if(res == ResultValue.OK){
            return SomeipS2SManagementInterface.Second_Row_Right_Seat_Virtual_Control_Availability_And_Notification_StatusField.parseFrom(resp.payload)
        }
        return null
    }
    fun getSecRightForwardStatus():Boolean{
        val available = getSecRightSeatStatus()
        if(available!=null){
            return available.outPut.secRwRtStFwdBkwdMovmtVirtCtrlAvl
        }
        return false
    }
    fun getSecRightLeftwardStatus():Boolean{
        val available = getSecRightSeatStatus()
        if(available!=null){
            return available.outPut.secRwRtStLtwdRtwdMovmtVirtCtrlAvl
        }
        return false
    }
    fun getSecRightUpwardStatus():Boolean{
        val available = getSecRightSeatStatus()
        if(available!=null){
            return available.outPut.secRwRtStFtrstUpwdDnwdMovmtVirtCtrlAvl
        }
        return false
    }
    fun getSecRightArmStatus():Boolean{
        val available = getSecRightSeatStatus()
        if(available!=null){
            return available.outPut.secRwRtStArmScrnUpwdDnwdMovmtVirtCtrlAvl
        }
        return false
    }
    fun getSecRightHeadForwardStatus():Boolean{
        val available = getSecRightSeatStatus()
        if(available!=null){
            return available.outPut.secRwRtStHdrstFwdBkwdMovmtVirtCtrlAvl
        }
        return false
    }
    fun getSecRightHeadUpwardStatus():Boolean{
        val available = getSecRightSeatStatus()
        if(available!=null){
            return available.outPut.secRwRtStHdrstUpwdDnwdMovmtVirtCtrlAvl
        }
        return false
    }
    fun getSecRightCushionFrontStatus():Boolean{
        val available = getSecRightSeatStatus()
        if(available!=null){
            return available.outPut.secRwRtStCushnFrtUpwdDnwdCtrlAvl
        }
        return false
    }
    fun getSecRightCushionRearStatus():Boolean{
        val available = getSecRightSeatStatus()
        if(available!=null){
            return available.outPut.secRwRtStCushnRrUpwdDnwdCtrlAvl
        }
        return false
    }
    fun getSecRightLegOutwardStatus():Boolean{
        val available = getSecRightSeatStatus()
        if(available!=null){
            return available.outPut.secRwRtStLgrstOtwdInwdMovmtVirtCtrlAvl
        }
        return false
    }
    fun getSecRightLegUpwardStatus():Boolean{
        val available = getSecRightSeatStatus()
        if(available!=null){
            return available.outPut.secRwRtStLgrstUpwdDnwdMovmtVirtCtrlAvl
        }
        return false
    }
    fun getSecRightNeckStatus():Boolean{
        val available = getSecRightSeatStatus()
        if(available!=null){
            return available.outPut.secRwRtStNckrstFrwdBkwdMovmtVirtCtrlAvl
        }
        return false
    }
    fun getSecRightReclineStatus():Boolean{
        val available = getSecRightSeatStatus()
        if(available!=null){
            return available.outPut.secRwRtStRclUpwdDnwdMovmtVirtCtrlAvl
        }
        return false
    }

    //endregion


    //region Second right seat get position
    fun getSecRightReclinePos(): Int?{
        val resp = getPositionMessage(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SECOND_RIGHT_SEAT_PERCENTAGE_POSITION_1)
        if(resp != null){
            val tmp =  SomeipS2SManagementInterface.Second_Right_Seat_Percentage_Position_1Field.parseFrom(resp.payload)
            return tmp.outPut.secRwRtStBkReclnUpwdDnwdPos
        }
        return null
    }
    fun getSecRightBolsterPos(): Int?{
        val resp = getPositionMessage(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SECOND_RIGHT_SEAT_PERCENTAGE_POSITION_1)
        if(resp != null){
            val tmp =  SomeipS2SManagementInterface.Second_Right_Seat_Percentage_Position_1Field.parseFrom(resp.payload)
            return tmp.outPut.secRwRtStBlstOtwdInwdPos
        }
        return null
    }
    fun getSecRightForwardPos(): Int?{
        val resp = getPositionMessage(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SECOND_RIGHT_SEAT_PERCENTAGE_POSITION_1)
        if(resp != null){
            val tmp =  SomeipS2SManagementInterface.Second_Right_Seat_Percentage_Position_1Field.parseFrom(resp.payload)
            return tmp.outPut.secRwRtStFrwdBkwdPos
        }
        return null
    }
    fun getSecRightLeftwardPos(): Int?{
        val resp = getPositionMessage(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SECOND_RIGHT_SEAT_PERCENTAGE_POSITION_1)
        if(resp != null){
            val tmp =  SomeipS2SManagementInterface.Second_Right_Seat_Percentage_Position_1Field.parseFrom(resp.payload)
            return tmp.outPut.secRwRtStLtwdRtwdPos
        }
        return null
    }
    fun getSecRightCushionFrontPos(): Int?{
        val resp = getPositionMessage(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SECOND_RIGHT_SEAT_PERCENTAGE_POSITION_2)
        if(resp != null){
            val tmp =  SomeipS2SManagementInterface.Second_Right_Seat_Percentage_Position_2Field.parseFrom(resp.payload)
            return tmp.outPut.secRwRtStCshnFrntUpwdDnwdPos
        }
        return null
    }
    fun getSecRightCushionRearPos(): Int?{
        val resp = getPositionMessage(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SECOND_RIGHT_SEAT_PERCENTAGE_POSITION_2)
        if(resp != null){
            val tmp =  SomeipS2SManagementInterface.Second_Right_Seat_Percentage_Position_2Field.parseFrom(resp.payload)
            return tmp.outPut.secRwRtStCshnRrUpwdDnwdPos
        }
        return null
    }
    fun getSecRightHeadPos(): Int?{
        val resp = getPositionMessage(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SECOND_RIGHT_SEAT_PERCENTAGE_POSITION_2)
        if(resp != null){
            val tmp =  SomeipS2SManagementInterface.Second_Right_Seat_Percentage_Position_2Field.parseFrom(resp.payload)
            return tmp.outPut.secRwRtStHdrstUpwdDnwdPos
        }
        return null
    }
    fun getSecRightLegRearPos(): Int?{
        val resp = getPositionMessage(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SECOND_RIGHT_SEAT_PERCENTAGE_POSITION_2)
        if(resp != null){
            val tmp =  SomeipS2SManagementInterface.Second_Right_Seat_Percentage_Position_2Field.parseFrom(resp.payload)
            return tmp.outPut.secRwRtStLgrstUpwdDnwdPos
        }
        return null
    }

    //endregion


    //region Third left seat set requests
    fun setThirdLeftRecallReq(enable:Boolean) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Second left seat service enable: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Third_Row_Left_Seat_Virtual_Control_Request_ServiceField.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Third_Row_Left_Seat_Virtual_Control_Request_Service.newBuilder()
                .setThdRwLtStRclReqSrv(enable)).build()
        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_THIRD_ROW_LEFT_SEAT_VIRTUAL_CONTROL_REQUEST_SERVICE)
    }
    fun setThirdLeftReclineReq(pos:Int) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Second left seat service enable: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Third_Row_Left_Seat_Virtual_Control_Request_ServiceField.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Third_Row_Left_Seat_Virtual_Control_Request_Service.newBuilder()
                .setThdRwLtStRclUpwdDnwdTrgtPosReqSrv(pos)).build()
        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_THIRD_ROW_LEFT_SEAT_VIRTUAL_CONTROL_REQUEST_SERVICE)
    }
    fun setThirdLeftCushionFoldReq(pos:Int) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Second left seat service enable: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Third_Row_Left_Seat_Virtual_Control_Request_ServiceField.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Third_Row_Left_Seat_Virtual_Control_Request_Service.newBuilder()
                .setThdRwLtStCushnFldTrgtPosReqSrv(pos)).build()
        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_THIRD_ROW_LEFT_SEAT_VIRTUAL_CONTROL_REQUEST_SERVICE)
    }

    fun setThirdLeftForwardReq(pos:Int) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Second left seat service enable: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Third_Row_Left_Seat_Virtual_Control_Request_ServiceField.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Third_Row_Left_Seat_Virtual_Control_Request_Service.newBuilder()
                .setThdRwLtStFwdBkwdTrgtPosReqSrv(pos)).build()
        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_THIRD_ROW_LEFT_SEAT_VIRTUAL_CONTROL_REQUEST_SERVICE)
    }

    //endregion


    //region Third left seat configuration/status
    fun getThirdLeftSeatConfig():SomeipS2SManagementInterface.Third_Left_Seat_ConfigurationField?{
        val resp = SomeIpData()
        val res = someIpClientProxy?.getAttribute(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_THIRD_LEFT_SEAT_CONFIGURATION, resp)
        if(res == ResultValue.OK){
            return SomeipS2SManagementInterface.Third_Left_Seat_ConfigurationField.parseFrom(resp.payload)
        }
        return null
    }
    fun getThdLeftCushionFoldConf(): Boolean{
        val config = getThirdLeftSeatConfig()
        if(config != null){
            return config.outPut.thdRwLtStCshnFldUpwdDnwdMovConfig
        }
        return false
    }
    fun getThdLeftReclineUpwardConf(): Boolean{
        val config = getThirdLeftSeatConfig()
        if(config != null){
            return config.outPut.thdRwLtStBkReclnUpwdDnwdMovConfig
        }
        return false
    }
    fun getThdLeftForwardConf(): Boolean{
        val config = getThirdLeftSeatConfig()
        if(config != null){
            return config.outPut.thdRwLtStFrwdBkwdMovConfig
        }
        return false
    }

    fun getThirdSeatStatus():SomeipS2SManagementInterface.Third_Row_Left_Seat_Virtual_Control_Availability_And_Notification_StatusField?{
        val resp = SomeIpData()
        val res = someIpClientProxy?.getAttribute(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_THIRD_ROW_LEFT_SEAT_VIRTUAL_CONTROL_AVAILABILITY_AND_NOTIFICATION_STATUS, resp)
        if(res == ResultValue.OK){
            return SomeipS2SManagementInterface.Third_Row_Left_Seat_Virtual_Control_Availability_And_Notification_StatusField.parseFrom(resp.payload)
        }
        return null
    }
    fun getThdLeftCushionFoldStatus():Boolean{
        val available = getThirdSeatStatus()
        if(available!=null){
            return available.outPut.thdRwLtStCushnFldMovmtVirtCtrlAvl
        }
        return false
    }
    fun getThdLeftForwardStatus():Boolean{
        val available = getThirdSeatStatus()
        if(available!=null){
            return available.outPut.thdRwLtStFwdBkwdMovmtVirtCtrlAvl
        }
        return false
    }
    fun getThdLeftReclineStatus():Boolean{
        val available = getThirdSeatStatus()
        if(available!=null){
            return available.outPut.thdRwLtStRclUpwdDnwdMovmtVirtCtrlAvl
        }
        return false
    }

    //endregion


    //region Third left seat get position
    fun getThdLeftReclinePos(): Int?{
        val resp = getPositionMessage(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_THIRD_LEFT_SEAT_PERCENTAGE_POSITION)
        if(resp != null){
            val tmp =  SomeipS2SManagementInterface.Third_Left_Seat_Percentage_PositionField.parseFrom(resp.payload)
            return tmp.outPut.thdRwLtStBkReclnUpwdDnwdPos
        }
        return null
    }
    fun getThdLeftForwardPos(): Int?{
        val resp = getPositionMessage(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_THIRD_LEFT_SEAT_PERCENTAGE_POSITION)
        if(resp != null){
            val tmp =  SomeipS2SManagementInterface.Third_Left_Seat_Percentage_PositionField.parseFrom(resp.payload)
            return tmp.outPut.thdRwLtStFrwdBkwdPos
        }
        return null
    }
    fun getThdLeftCushionFoldPos(): Int?{
        val resp = getPositionMessage(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_THIRD_LEFT_SEAT_PERCENTAGE_POSITION)
        if(resp != null){
            val tmp =  SomeipS2SManagementInterface.Third_Left_Seat_Percentage_PositionField.parseFrom(resp.payload)
            return tmp.outPut.thdRwLtStCshnFldUpwdDnwdPos
        }
        return null
    }

    //endregion


    //region Third right seat set requests
    fun setThirdRightRecallReq(enable:Boolean) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Second left seat service enable: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Third_Row_Right_Seat_Virtual_Control_Request_ServiceField.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Third_Row_Right_Seat_Virtual_Control_Request_Service.newBuilder()
                .setThdRwRtStRclReqSrv(enable)).build()
        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_THIRD_ROW_RIGHT_SEAT_VIRTUAL_CONTROL_REQUEST_SERVICE)
    }
    fun setThirdRightReclineReq(pos:Int) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Second left seat service enable: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Third_Row_Right_Seat_Virtual_Control_Request_ServiceField.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Third_Row_Right_Seat_Virtual_Control_Request_Service.newBuilder()
                .setThdRwRtStRclUpwdDnwdTrgtPosReqSrv(pos)).build()
        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_THIRD_ROW_RIGHT_SEAT_VIRTUAL_CONTROL_REQUEST_SERVICE)
    }
    fun setThirdRightCushionFoldReq(pos:Int) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Second left seat service enable: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Third_Row_Right_Seat_Virtual_Control_Request_ServiceField.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Third_Row_Right_Seat_Virtual_Control_Request_Service.newBuilder()
                .setThdRwRtStCushnFldTrgtPosReqSrv(pos)).build()
        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_THIRD_ROW_RIGHT_SEAT_VIRTUAL_CONTROL_REQUEST_SERVICE)
    }

    fun setThirdRightForwardReq(pos:Int) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Second left seat service enable: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Third_Row_Right_Seat_Virtual_Control_Request_ServiceField.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Third_Row_Right_Seat_Virtual_Control_Request_Service.newBuilder()
                .setThdRwRtStFwdBkwdTrgtPosReqSrv(pos)).build()
        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_THIRD_ROW_RIGHT_SEAT_VIRTUAL_CONTROL_REQUEST_SERVICE)
    }

    //endregion


    //region Third right seat configuration/status
    fun getThirdRightSeatConfig():SomeipS2SManagementInterface.Third_Right_Seat_ConfigurationField?{
        val resp = SomeIpData()
        val res = someIpClientProxy?.getAttribute(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_THIRD_RIGHT_SEAT_CONFIGURATION, resp)
        if(res == ResultValue.OK){
            return SomeipS2SManagementInterface.Third_Right_Seat_ConfigurationField.parseFrom(resp.payload)
        }
        return null
    }
    fun getThirdRightCushionFoldConf(): Boolean{
        val config = getThirdRightSeatConfig()
        if(config != null){
            return config.outPut.thdRwRtStCshnFldUpwdDnwdMovConfig
        }
        return false
    }
    fun getThirdRightReclineUpwardConf(): Boolean{
        val config = getThirdRightSeatConfig()
        if(config != null){
            return config.outPut.thdRwRtStBkReclnUpwdDnwdMovConfig
        }
        return false
    }
    fun getThirdRightForwardConf(): Boolean{
        val config = getThirdRightSeatConfig()
        if(config != null){
            return config.outPut.thdRwRtStFrwdBkwdMovConfig
        }
        return false
    }

    fun getThirdRightSeatStatus():SomeipS2SManagementInterface.Third_Row_Right_Seat_Virtual_Control_Availability_And_Notification_StatusField?{
        val resp = SomeIpData()
        val res = someIpClientProxy?.getAttribute(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_THIRD_ROW_RIGHT_SEAT_VIRTUAL_CONTROL_AVAILABILITY_AND_NOTIFICATION_STATUS, resp)
        if(res == ResultValue.OK){
            return SomeipS2SManagementInterface.Third_Row_Right_Seat_Virtual_Control_Availability_And_Notification_StatusField.parseFrom(resp.payload)
        }
        return null
    }
    fun getThdRightCushionFoldStatus():Boolean{
        val available = getThirdRightSeatStatus()
        if(available!=null){
            return available.outPut.thdRwRtStCushnFldMovmtVirtCtrlAvl
        }
        return false
    }
    fun getThdRightForwardStatus():Boolean{
        val available = getThirdRightSeatStatus()
        if(available!=null){
            return available.outPut.thdRwRtStFwdBkwdMovmtVirtCtrlAvl
        }
        return false
    }
    fun getThdRightReclineStatus():Boolean{
        val available = getThirdRightSeatStatus()
        if(available!=null){
            return available.outPut.thdRwRtStRclUpwdDnwdMovmtVirtCtrlAvl
        }
        return false
    }

    //endregion


    //region Third right seat get position
    fun getThdRightReclinePos(): Int?{
        val resp = getPositionMessage(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_THIRD_RIGHT_SEAT_PERCENTAGE_POSITION)
        if(resp != null){
            val tmp =  SomeipS2SManagementInterface.Third_Right_Seat_Percentage_PositionField.parseFrom(resp.payload)
            return tmp.outPut.thdRwRtStBkReclnUpwdDnwdPos
        }
        return null
    }
    fun getThdRightForwardPos(): Int?{
        val resp = getPositionMessage(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_THIRD_RIGHT_SEAT_PERCENTAGE_POSITION)
        if(resp != null){
            val tmp =  SomeipS2SManagementInterface.Third_Right_Seat_Percentage_PositionField.parseFrom(resp.payload)
            return tmp.outPut.thdRwRtStFrwdBkwdPos
        }
        return null
    }
    fun getThdRightCushionFoldPos(): Int?{
        val resp = getPositionMessage(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_THIRD_RIGHT_SEAT_PERCENTAGE_POSITION)
        if(resp != null){
            val tmp =  SomeipS2SManagementInterface.Third_Right_Seat_Percentage_PositionField.parseFrom(resp.payload)
            return tmp.outPut.thdRwRtStCshnFldUpwdDnwdPos
        }
        return null
    }

    //endregion



    fun getSeatMode(): SomeipS2SManagementInterface.Seat_Mode_StatusField? {
        if (!isServerAvailable || !isReady) {
            Log.i(
                TAG,
                "get Seat_Mode_StatusField: failed, server is not available or client is not ready"
            )
            return null
        }

        val resp = SomeIpData()

        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SEAT_MODE_STATUS,
            resp
        )

        Log.i(TAG, "sendReq2Server: get Seat_Mode_StatusField")
        if (res == ResultValue.OK) {
            Log.i(TAG, "sendReq2Server: Seat_Mode_StatusField OK")
//            val temp = SomeipS2SManagementInterface.Driver_Seat_ConfigurationField.parseFrom(resp.payload)
//            Log.i(TAG, "getSunroofPosition: " + temp.outPut.drvStBkReclnUpwdDnwdMemConfig)
//            return temp
        }

        Log.i(TAG, "FAILED: Seat_Mode_StatusField")
        return null;
    }

    fun getSeatPassenger_Com_Mode_Ser(): SomeipS2SManagementInterface.Seat_Passenger_Compartment_Mode_Service_Availability_StatusField? {
        if (!isServerAvailable || !isReady) {
            Log.i(
                TAG,
                "get Seat_Passenger_Compartment_Mode_Service_Availability_StatusField: failed, server is not available or client is not ready"
            )
            return null
        }

        val resp = SomeIpData()

        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SEAT_MODE_STATUS,
            resp
        )

        Log.i(
            TAG,
            "sendReq2Server: get Seat_Passenger_Compartment_Mode_Service_Availability_StatusField"
        )
        if (res == ResultValue.OK) {
            Log.i(
                TAG,
                "sendReq2Server: Seat_Passenger_Compartment_Mode_Service_Availability_StatusField OK"
            )
//            val temp = SomeipS2SManagementInterface.Driver_Seat_ConfigurationField.parseFrom(resp.payload)
//            Log.i(TAG, "getSunroofPosition: " + temp.outPut.drvStBkReclnUpwdDnwdMemConfig)
//            return temp
        }

        Log.i(TAG, "FAILED: Seat_Passenger_Compartment_Mode_Service_Availability_StatusField")
        return null;
    }

    fun getSeatPassenger_Com_Mode_Ser_Resp(): SomeipS2SManagementInterface.Seat_Passenger_Compartment_Mode_Service_Response_StatusField? {
        if (!isServerAvailable || !isReady) {
            Log.i(
                TAG,
                "get Seat_Passenger_Compartment_Mode_Service_Response_StatusField: failed, server is not available or client is not ready"
            )
            return null
        }

        val resp = SomeIpData()

        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SEAT_MODE_STATUS,
            resp
        )

        Log.i(
            TAG,
            "sendReq2Server: get Seat_Passenger_Compartment_Mode_Service_Response_StatusField"
        )
        if (res == ResultValue.OK) {
            Log.i(
                TAG,
                "sendReq2Server: Seat_Passenger_Compartment_Mode_Service_Response_StatusField OK"
            )
//            val temp = SomeipS2SManagementInterface.Driver_Seat_ConfigurationField.parseFrom(resp.payload)
//            Log.i(TAG, "getSunroofPosition: " + temp.outPut.drvStBkReclnUpwdDnwdMemConfig)
//            return temp
        }

        Log.i(TAG, "FAILED: Seat_Passenger_Compartment_Mode_Service_Response_StatusField")
        return null;
    }

    fun getSeatPassenger_Com_Mode_Ser_Resp2(): SomeipS2SManagementInterface.Seat_Passenger_Compartment_Mode_Service_Response_Status_2Field? {
        if (!isServerAvailable || !isReady) {
            Log.i(
                TAG,
                "get Seat_Passenger_Compartment_Mode_Service_Response_Status_2Field: failed, server is not available or client is not ready"
            )
            return null
        }

        val resp = SomeIpData()

        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SEAT_MODE_STATUS,
            resp
        )

        Log.i(
            TAG,
            "sendReq2Server: get Seat_Passenger_Compartment_Mode_Service_Response_Status_2Field"
        )
        if (res == ResultValue.OK) {
            Log.i(
                TAG,
                "sendReq2Server: Seat_Passenger_Compartment_Mode_Service_Response_Status_2Field OK"
            )
//            val temp = SomeipS2SManagementInterface.Driver_Seat_ConfigurationField.parseFrom(resp.payload)
//            Log.i(TAG, "getSunroofPosition: " + temp.outPut.drvStBkReclnUpwdDnwdMemConfig)
//            return temp
        }

        Log.i(TAG, "FAILED: Seat_Passenger_Compartment_Mode_Service_Response_Status_2Field")
        return null;
    }

    fun setSeatModeReq() {
        if (!isServerAvailable || !isReady) {
            Log.i(
                TAG,
                "Seat_Mode_Control_Request: failed, server is not available or client is not ready"
            )
            return
        }

        val newSeatMode = SomeipS2SManagementInterface
            .Seat_Mode_Control_RequestField.newBuilder()
            .setOutPut(
                SomeipS2SManagementInterface.Seat_Mode_Control_Request.newBuilder()
                    .setStSrvReqStMd1(isBoolean)
                    .setStSrvReqStMd2(isBoolean)
                    .setStSrvReqStMd3(isBoolean)
                    .setStSrvReqStMd4(isBoolean)
                    .setStSrvReqStMd5(isBoolean)
                    .setStSrvReqStMd6(isBoolean)
                    .setStSrvReqStMd7(isBoolean)
                    .setStSrvReqStMd8(isBoolean)
                    .setStSrvReqStMd9(isBoolean)
                    .setStSrvReqStMd10(isBoolean)
                    .setStSrvReqStMd11(isBoolean)
                    .setStSrvReqStMd12(isBoolean)
                    .setStSrvReqStMd13(isBoolean)
                    .setStSrvReqStMd14(isBoolean)
                    .setStSrvReqStMd15(isBoolean)
                    .setStSrvReqStMd16(isBoolean)
                    .setStSrvReqStMd17(isBoolean)
                    .setStSrvReqStMd18(isBoolean)
                    .setStSrvReqStMd19(isBoolean)
                    .setStSrvReqStMd20(isBoolean)
                    .setStSrvReqStMd21(isBoolean)
                    .setStSrvReqStMd22(isBoolean)
                    .setStSrvReqStMd23(isBoolean)
                    .setStSrvReqStMd24(isBoolean)
                    .setStSrvReqStMd25(isBoolean)
                    .setStSrvReqStMd26(isBoolean)
                    .setStSrvReqStMd27(isBoolean)
                    .setStSrvReqStMd28(isBoolean)
                    .setStSrvReqStMd29(isBoolean)
                    .setStSrvReqStMd30(isBoolean)
                    .setStSrvReqStMd31(isBoolean)
                    .setStSrvReqStMd32(isBoolean)
            )
            .build()

        val req = SomeIpData(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_SEAT_MODE_CONTROL_REQUEST,
            System.currentTimeMillis(),
            newSeatMode.toByteArray()
        )

        val resp = SomeIpData()

        val res = someIpClientProxy?.setAttribute(req, resp)
        Log.i(TAG, "sendReq2Server: Seat_Mode_Control_Request.")

        if (res == ResultValue.OK) {
            Log.i(TAG, "Seat_Mode_Control_Request: Response OK")
            return
        }

        Log.i(TAG, "Seat_Mode_Control_Request: FAILED")
    }

    fun getSeatMode2(): SomeipS2SManagementInterface.Seat_Mode_Status_2Field? {
        if (!isServerAvailable || !isReady) {
            Log.i(
                TAG,
                "get Seat_Mode_Status_2Field: failed, server is not available or client is not ready"
            )
            return null
        }

        val resp = SomeIpData()

        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SEAT_MODE_STATUS,
            resp
        )

        Log.i(TAG, "sendReq2Server: get Seat_Mode_Status_2Field")
        if (res == ResultValue.OK) {
            Log.i(TAG, "sendReq2Server: Seat_Mode_Status_2Field OK")
//            val temp = SomeipS2SManagementInterface.Driver_Seat_ConfigurationField.parseFrom(resp.payload)
//            Log.i(TAG, "getSunroofPosition: " + temp.outPut.drvStBkReclnUpwdDnwdMemConfig)
//            return temp
        }

        Log.i(TAG, "FAILED: Seat_Mode_Status_2Field")
        return null;
    }

    fun getSeatPassenger_Com_Mode_Ava_Notifi6(): SomeipS2SManagementInterface.Seat_Passenger_Compartment_Mode_Availability_and_Notification_Status_6Field? {
        if (!isServerAvailable || !isReady) {
            Log.i(
                TAG,
                "get Seat_Passenger_Compartment_Mode_Availability_and_Notification_Status_6Field: failed, server is not available or client is not ready"
            )
            return null
        }

        val resp = SomeIpData()

        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SEAT_MODE_STATUS,
            resp
        )

        Log.i(
            TAG,
            "sendReq2Server: get Seat_Passenger_Compartment_Mode_Availability_and_Notification_Status_6Field"
        )
        if (res == ResultValue.OK) {
            Log.i(
                TAG,
                "sendReq2Server: Seat_Passenger_Compartment_Mode_Availability_and_Notification_Status_6Field OK"
            )
//            val temp = SomeipS2SManagementInterface.Driver_Seat_ConfigurationField.parseFrom(resp.payload)
//            Log.i(TAG, "getSunroofPosition: " + temp.outPut.drvStBkReclnUpwdDnwdMemConfig)
//            return temp
        }

        Log.i(
            TAG,
            "FAILED: Seat_Passenger_Compartment_Mode_Availability_and_Notification_Status_6Field"
        )
        return null;
    }

    fun getDriverSeatRecoveryPosition(): SomeipS2SManagementInterface.Driver_Seat_Recovery_PositionField? {
        if (!isServerAvailable || !isReady) {
            Log.i(
                TAG,
                "get Driver_Seat_Recovery_PositionField: failed, server is not available or client is not ready"
            )
            return null
        }

        val resp = SomeIpData()

        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SEAT_MODE_STATUS,
            resp
        )

        Log.i(TAG, "sendReq2Server: get Driver_Seat_Recovery_PositionField")
        if (res == ResultValue.OK) {
            Log.i(TAG, "sendReq2Server: Driver_Seat_Recovery_PositionField OK")
//            val temp = SomeipS2SManagementInterface.Driver_Seat_ConfigurationField.parseFrom(resp.payload)
//            Log.i(TAG, "getSunroofPosition: " + temp.outPut.drvStBkReclnUpwdDnwdMemConfig)
//            return temp
        }

        Log.i(TAG, "FAILED: Driver_Seat_Recovery_PositionField")
        return null;
    }

    fun getDriverSeatRecoveryPosition2(): SomeipS2SManagementInterface.Driver_Seat_Recovery_Position_2Field? {
        if (!isServerAvailable || !isReady) {
            Log.i(
                TAG,
                "get Driver_Seat_Recovery_Position_2Field: failed, server is not available or client is not ready"
            )
            return null
        }

        val resp = SomeIpData()

        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SEAT_MODE_STATUS,
            resp
        )

        Log.i(TAG, "sendReq2Server: get Driver_Seat_Recovery_Position_2Field")
        if (res == ResultValue.OK) {
            Log.i(TAG, "sendReq2Server: Driver_Seat_Recovery_Position_2Field OK")
//            val temp = SomeipS2SManagementInterface.Driver_Seat_ConfigurationField.parseFrom(resp.payload)
//            Log.i(TAG, "getSunroofPosition: " + temp.outPut.drvStBkReclnUpwdDnwdMemConfig)
//            return temp
        }

        Log.i(TAG, "FAILED: Driver_Seat_Recovery_Position_2Field")
        return null;
    }

    fun getSec_RowLeft_Com_Mode_Req(): SomeipS2SManagementInterface.Second_Row_Left_ComfortMode_Request_HMIField? {
        if (!isServerAvailable || !isReady) {
            Log.i(
                TAG,
                "get Second_Row_Left_ComfortMode_Request_HMIField: failed, server is not available or client is not ready"
            )
            return null
        }

        val resp = SomeIpData()

        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SEAT_MODE_STATUS,
            resp
        )

        Log.i(TAG, "sendReq2Server: get Second_Row_Left_ComfortMode_Request_HMIField")
        if (res == ResultValue.OK) {
            Log.i(TAG, "sendReq2Server: Second_Row_Left_ComfortMode_Request_HMIField OK")
//            val temp = SomeipS2SManagementInterface.Driver_Seat_ConfigurationField.parseFrom(resp.payload)
//            Log.i(TAG, "getSunroofPosition: " + temp.outPut.drvStBkReclnUpwdDnwdMemConfig)
//            return temp
        }

        Log.i(TAG, "FAILED: Second_Row_Left_ComfortMode_Request_HMIField")
        return null;
    }

    fun getSec_RowRight_Com_Mode_Req(): SomeipS2SManagementInterface.Second_Row_Right_ComfortMode_Request_HMIField? {
        if (!isServerAvailable || !isReady) {
            Log.i(
                TAG,
                "get Second_Row_Right_ComfortMode_Request_HMIField: failed, server is not available or client is not ready"
            )
            return null
        }

        val resp = SomeIpData()

        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SEAT_MODE_STATUS,
            resp
        )

        Log.i(TAG, "sendReq2Server: get Second_Row_Right_ComfortMode_Request_HMIField")
        if (res == ResultValue.OK) {
            Log.i(TAG, "sendReq2Server: Second_Row_Right_ComfortMode_Request_HMIField OK")
//            val temp = SomeipS2SManagementInterface.Driver_Seat_ConfigurationField.parseFrom(resp.payload)
//            Log.i(TAG, "getSunroofPosition: " + temp.outPut.drvStBkReclnUpwdDnwdMemConfig)
//            return temp
        }

        Log.i(TAG, "FAILED: Second_Row_Right_ComfortMode_Request_HMIField")
        return null;
    }


}