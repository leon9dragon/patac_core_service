package com.gm.ultifi.service.seating.someip

import android.util.Log
import com.gm.ultifi.base.someip.BaseAppViewModel
import com.gm.ultifi.base.utils.SomeIpUtil
import com.gm.ultifi.service.seating.SeatingBeanFactory
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UResource
import com.gm.ultifi.service.SeatingService
import com.gm.ultifi.service.constant.ResourceMappingConstants
import com.gm.ultifi.service.constant.ServiceConstant
import com.gm.ultifi.vehicle.body.seating.v1.SeatComponent
import com.google.protobuf.GeneratedMessageV3
import com.ultifi.vehicle.body.seating.v1.SeatPosition
import com.ultifi.vehicle.body.seating.v1.SeatPosition.SeatComponentPosition
import com.ultifi.vehicle.body.seating.v1.SeatPosition.newBuilder
import com.ultifi.vehicle.body.seating.v1.UpdateSeatPositionRequest
import plugin.SomeipS2SManagementInterface
import ts.car.someip.plugin.SomeIpTopic
import ts.car.someip.sdk.common.ResultValue
import ts.car.someip.sdk.common.SomeIpData

class SeatViewModel : BaseAppViewModel() {
    val globalMap: MutableMap<String, Any> = SeatingBeanFactory.beanMap;

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
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_PASSENGER_SEAT_VIRTUAL_CONTROL_AVAILABILITY_AND_NOTIFICATION_STATUS)
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_ROW_LEFT_SEAT_VIRTUAL_CONTROL_AVAILABILITY_AND_NOTIFICATION_STATUS)
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_ROW_RIGHT_SEAT_VIRTUAL_CONTROL_AVAILABILITY_AND_NOTIFICATION_STATUS)
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_THIRD_ROW_LEFT_SEAT_VIRTUAL_CONTROL_AVAILABILITY_AND_NOTIFICATION_STATUS)
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_THIRD_ROW_RIGHT_SEAT_VIRTUAL_CONTROL_AVAILABILITY_AND_NOTIFICATION_STATUS)
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_DRIVER_SEAT_VIRTUAL_CONTROL_AVAILABILITY_AND_NOTIFICATION_STATUS)

//            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_MODE_STATUS)
//            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_MODE_STATUS_2)
//            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_PASSENGER_COMPARTMENT_MODE_SERVICE_AVAILABILITY_STATUS)
//            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_PASSENGER_COMPARTMENT_MODE_SERVICE_RESPONSE_STATUS)
//            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_PASSENGER_COMPARTMENT_MODE_SERVICE_RESPONSE_STATUS_2)
//            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_PASSENGER_COMPARTMENT_MODE_AVAILABILITY_AND_NOTIFICATION_STATUS_6)
//            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_DRIVER_SEAT_RECOVERY_POSITION)
//            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_DRIVER_SEAT_RECOVERY_POSITION_2)

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
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_DRIVER_SEAT_VIRTUAL_CONTROL_AVAILABILITY_AND_NOTIFICATION_STATUS)
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_PASSENGER_SEAT_VIRTUAL_CONTROL_AVAILABILITY_AND_NOTIFICATION_STATUS)
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_ROW_LEFT_SEAT_VIRTUAL_CONTROL_AVAILABILITY_AND_NOTIFICATION_STATUS)
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_ROW_RIGHT_SEAT_VIRTUAL_CONTROL_AVAILABILITY_AND_NOTIFICATION_STATUS)
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_THIRD_ROW_LEFT_SEAT_VIRTUAL_CONTROL_AVAILABILITY_AND_NOTIFICATION_STATUS)
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_THIRD_ROW_RIGHT_SEAT_VIRTUAL_CONTROL_AVAILABILITY_AND_NOTIFICATION_STATUS)

//            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_MODE_STATUS)
//            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_MODE_STATUS_2)
//            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_PASSENGER_COMPARTMENT_MODE_SERVICE_AVAILABILITY_STATUS)
//            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_PASSENGER_COMPARTMENT_MODE_SERVICE_RESPONSE_STATUS)
//            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_PASSENGER_COMPARTMENT_MODE_SERVICE_RESPONSE_STATUS_2)
//            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_PASSENGER_COMPARTMENT_MODE_AVAILABILITY_AND_NOTIFICATION_STATUS_6)
//            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_DRIVER_SEAT_RECOVERY_POSITION)
//            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_DRIVER_SEAT_RECOVERY_POSITION_2)

            isServerAvailable = false
        }

        //region driver seat position notification
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_DRIVER_SEAT_PERCENTAGE_POSITION_1) {
            Log.i(TAG, "SUCCESS: NOTIFY_DRIVER_SEAT_PERCENTAGE_POSITION_1")
            val resp = SomeipS2SManagementInterface.Driver_Seat_Percentage_Position_1Field.parseFrom(data.payload)

            val seatReclPosition = resp.outPut.drvStBkReclnUpwdDnwdPos *0.025
            val seatPosition = resp.outPut.drvStFrwdBkwdPos*0.025

            Log.d(TAG, "onChangeEvent, Recline position:"+seatReclPosition+"SeatPosition"+seatPosition)

            val dirPosRecl = buildDirectionPos(UpdateSeatPositionRequest.Direction.D_UP, seatReclPosition.toInt())
            val seatReclPos: SeatComponentPosition = SeatComponentPosition.newBuilder()
                .setComponent(SeatComponent.SC_BACK)
                .setDirectionPositions(0, dirPosRecl)
                .build();

            val dirPosSeat = buildDirectionPos(UpdateSeatPositionRequest.Direction.D_FORWARD, seatPosition.toInt())
            val seatPos: SeatComponentPosition = SeatComponentPosition.newBuilder()
                .setComponent(SeatComponent.SC_CUSHION)
                .setDirectionPositions(0, dirPosSeat)
                .build();

            val seatReq: SeatPosition = SeatPosition.newBuilder()
                .setName("row1_left")
                .setSeatComponentPositions(0, seatReclPos)
                .setSeatComponentPositions(1, seatPos)
                .build();

            Log.i(TAG, "Publishing the cloud events to Bus")

            val topic = ResourceMappingConstants.DRIVER_SEAT
            val uResource = UResource(topic, "", SeatPosition::class.java.simpleName)

            SomeIpUtil.pubEvent(seatReq, ServiceConstant.SEATING_SERVICE, uResource, SeatingService.mLaunchManager.getmUltifiLinkMonitor())

        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_DRIVER_SEAT_PERCENTAGE_POSITION_2) {
            Log.i(TAG, "SUCCESS: NOTIFY_DRIVER_SEAT_PERCENTAGE_POSITION_2")
            val resp = SomeipS2SManagementInterface.Driver_Seat_Percentage_Position_2Field.parseFrom(data.payload)
            val seatCushPos = resp.outPut.drvStCshnFrntUpwdDnwdPos*0.025
            val seatCushRearPos = resp.outPut.drvStCshnRrUpwdDnwdPos*0.025

            Log.d(TAG,"onChangeEvent, Cushion position:"+seatCushPos+".CushionRear:"+seatCushRearPos)

            val dirPosCush = buildDirectionPos(UpdateSeatPositionRequest.Direction.D_UP, seatCushPos.toInt())
            val seatCushFront: SeatComponentPosition = SeatComponentPosition.newBuilder()
                .setComponent(SeatComponent.SC_CUSHION_FRONT)
                .setDirectionPositions(0, dirPosCush)
                .build();

            val dirPosCushRear = buildDirectionPos(UpdateSeatPositionRequest.Direction.D_UP, seatCushRearPos.toInt())
            val seatCushRear: SeatComponentPosition = SeatComponentPosition.newBuilder()
                .setComponent(SeatComponent.SC_CUSHION)
                .setDirectionPositions(0, dirPosCushRear)
                .build();

            val seatReq: SeatPosition = newBuilder()
                .setName("row1_left")
                .setSeatComponentPositions(0, seatCushFront)
                .setSeatComponentPositions(1, seatCushRear)
                .build();

            Log.i(TAG, "Publishing the cloud events to Bus")
            val topic = ResourceMappingConstants.DRIVER_SEAT
            val uResource = UResource(topic, "", SeatPosition::class.java.simpleName)

            SomeIpUtil.pubEvent(seatReq, ServiceConstant.SEATING_SERVICE, uResource, SeatingService.mLaunchManager.getmUltifiLinkMonitor())
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_DRIVER_SEAT_PERCENTAGE_POSITION_3) {
            Log.i(TAG, "SUCCESS: NOTIFY_DRIVER_SEAT_PERCENTAGE_POSITION_3")
            val resp = SomeipS2SManagementInterface.Driver_Seat_Percentage_Position_3Field.parseFrom(data.payload)

            val seatBolstPos = resp.outPut.drvStBlstOtwdInwdPos*0.025
            // check if there is foot component
            val seatFtPos = resp.outPut.drvStFtUpwdDnwdPos*0.025
            val seatHdRestRearPos = resp.outPut.drvStHdrstUpwdDnwdPos*0.025
            val seatLegRearPos = resp.outPut.drvStLgrstUpwdDnwdPos*0.025

            Log.d(TAG, "onChangeEvent, Bolster position:"+seatBolstPos+ ",Foot rest:"+seatFtPos+
                    ", Head Rest:"+ seatHdRestRearPos + ", Leg Rest:" + seatLegRearPos)

            val dirPosBolst = buildDirectionPos(UpdateSeatPositionRequest.Direction.D_INFLATE, seatBolstPos.toInt())
            val seatBolst: SeatComponentPosition = SeatComponentPosition.newBuilder()
                .setComponent(SeatComponent.SC_SIDE_BOLSTER_BACK)
                .setDirectionPositions(0, dirPosBolst)
                .build();

            val dirPosHdRear = buildDirectionPos(UpdateSeatPositionRequest.Direction.D_UP, seatHdRestRearPos.toInt())
            val seatHead: SeatComponentPosition = SeatComponentPosition.newBuilder()
                .setComponent(SeatComponent.SC_HEADREST)
                .setDirectionPositions(0, dirPosHdRear)
                .build();

            val dirPosLegRear = buildDirectionPos(UpdateSeatPositionRequest.Direction.D_UP, seatLegRearPos.toInt())
            val seatLeg: SeatComponentPosition = SeatComponentPosition.newBuilder()
                .setComponent(SeatComponent.SC_LEGREST)
                .setDirectionPositions(0, dirPosLegRear)
                .build();

            val seatReq: SeatPosition = newBuilder()
                .setName("row1_left")
                .setSeatComponentPositions(0, seatBolst)
//                .setSeatComponentPositions(1, seatFoot)
                .setSeatComponentPositions(1, seatHead)
                .setSeatComponentPositions(2, seatLeg)
                .build();

            Log.i(TAG, "Publishing the cloud events to Bus")

            // todo need to confirm SomeIp topic name?
            val topic = ResourceMappingConstants.DRIVER_SEAT
            val uResource = UResource(topic, "", SeatPosition::class.java.simpleName)
            SomeIpUtil.pubEvent(seatReq, ServiceConstant.SEATING_SERVICE, uResource, SeatingService.mLaunchManager.getmUltifiLinkMonitor())

            if(getDriverFoot()) {
                val dirPosFoot = buildDirectionPos(UpdateSeatPositionRequest.Direction.D_UP, seatFtPos.toInt())
                val seatFoot: SeatComponentPosition = SeatComponentPosition.newBuilder()
                    .setComponent(SeatComponent.SC_FOOTREST)
                    .setDirectionPositions(0, dirPosFoot)
                    .build();

                val seatReqFoot: SeatPosition = newBuilder()
                    .setName("row2_left")
                    .setSeatComponentPositions(0, seatFoot)
                    .build();
                val topicSec = ResourceMappingConstants.SECOND_LEFT_SEAT
                val uResourceSec = UResource(topicSec, "", SeatPosition::class.java.simpleName)
                SomeIpUtil.pubEvent(seatReqFoot, ServiceConstant.SEATING_SERVICE, uResourceSec, SeatingService.mLaunchManager.getmUltifiLinkMonitor())
            }
        }
        //endregion

        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_PASSENGER_SEAT_PERCENTAGE_POSITION_1) {
            Log.i(TAG,"PASSENGER_SEAT_PERCENTAGE_POSITION_1: Success")
            val resp = SomeipS2SManagementInterface.Passenger_Seat_Percentage_Position_1Field.parseFrom(data.payload)
            val bolstPos = resp.outPut.passStBlstOtwdInwdPos*0.025
            val seatPos = resp.outPut.passStFrwdBkwdPos*0.025
            val footPos = resp.outPut.passStFtUpwdDnwdPos*0.025
            val seatReclPos = resp.outPut.passStBkReclnUpwdDnwdPos*0.025
            Log.d(TAG, "onChangeEvent, Bolster position:"+bolstPos+
                    ", Seat forward backward:"+ seatPos + ", Foot:" + footPos +
                    ", Recline:" + seatReclPos);

            val dirPosBolst = buildDirectionPos(UpdateSeatPositionRequest.Direction.D_INFLATE, bolstPos.toInt())
            val seatBolst: SeatComponentPosition = SeatComponentPosition.newBuilder()
                .setComponent(SeatComponent.SC_SIDE_BOLSTER_BACK)
                .setDirectionPositions(0, dirPosBolst)
                .build();

            val dirPosSeat = buildDirectionPos(UpdateSeatPositionRequest.Direction.D_FORWARD, seatPos.toInt())
            val seat: SeatComponentPosition = SeatComponentPosition.newBuilder()
                .setComponent(SeatComponent.SC_CUSHION)
                .setDirectionPositions(0, dirPosSeat)
                .build();

            val dirPosRecl = buildDirectionPos(UpdateSeatPositionRequest.Direction.D_UP, seatReclPos.toInt())
            val seatRecl: SeatComponentPosition = SeatComponentPosition.newBuilder()
                .setComponent(SeatComponent.SC_BACK)
                .setDirectionPositions(0, dirPosRecl)
                .build();

            val seatReq: SeatPosition = SeatPosition.newBuilder()
                .setName("row1_right")
                .setSeatComponentPositions(0, seatBolst)
                .setSeatComponentPositions(1, seat)
                .setSeatComponentPositions(2, seatRecl)
                .build();

            Log.i(TAG, "Publishing the cloud events to Bus")
            // todo need to confirm SomeIp topic name?
            val topic = ResourceMappingConstants.PASSENGER_SEAT
            val uResource = UResource(topic, "", SeatPosition::class.java.simpleName)
            SomeIpUtil.pubEvent(seatReq, ServiceConstant.SEATING_SERVICE, uResource, SeatingService.mLaunchManager.getmUltifiLinkMonitor())

            if(getDriverFoot()) {
                val dirPosFoot = buildDirectionPos(UpdateSeatPositionRequest.Direction.D_UP, footPos.toInt())
                val seatFoot: SeatComponentPosition = SeatComponentPosition.newBuilder()
                    .setComponent(SeatComponent.SC_FOOTREST)
                    .setDirectionPositions(0, dirPosFoot)
                    .build();
                val seatReqFoot: SeatPosition = SeatPosition.newBuilder()
                    .setName("row2_right")
                    .setSeatComponentPositions(0, seatFoot)
                    .build();
                val topicSec = ResourceMappingConstants.SECOND_RIGHT_SEAT
                val uResourceSec = UResource(topicSec, "", SeatPosition::class.java.simpleName)
                SomeIpUtil.pubEvent(seatReqFoot, ServiceConstant.SEATING_SERVICE, uResourceSec, SeatingService.mLaunchManager.getmUltifiLinkMonitor())
            }
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_PASSENGER_SEAT_PERCENTAGE_POSITION_2) {
            Log.i(TAG,"PASSENGER_SEAT_PERCENTAGE_POSITION_2: Success")
            val resp = SomeipS2SManagementInterface.Passenger_Seat_Percentage_Position_2Field.parseFrom(data.payload)
            val cushFrontPos = resp.outPut.passStCshnFrntUpwdDnwdPos*0.025
            val cushRearPos = resp.outPut.passStCshnRrUpwdDnwdPos*0.025
            val headRestPos = resp.outPut.passStHdrstUpwdDnwdPos*0.025
            val legRestPos = resp.outPut.passStLgrstUpwdDnwdPos*0.025
            Log.d(TAG, "onChangeEvent, Cushion front position:"+cushFrontPos+
                    ", cushion rear:"+ cushRearPos + ", head rest:" + headRestPos +
                    ", leg rest:" + legRestPos)

            val dirPosCushFront = buildDirectionPos(UpdateSeatPositionRequest.Direction.D_UP, cushFrontPos.toInt())
            val seatCushFront: SeatComponentPosition = SeatComponentPosition.newBuilder()
                .setComponent(SeatComponent.SC_CUSHION_FRONT)
                .setDirectionPositions(0, dirPosCushFront)
                .build();

            val dirPosCushRear = buildDirectionPos(UpdateSeatPositionRequest.Direction.D_UP, cushRearPos.toInt())
            val seatCushRear: SeatComponentPosition = SeatComponentPosition.newBuilder()
                .setComponent(SeatComponent.SC_CUSHION)
                .setDirectionPositions(0, dirPosCushRear)
                .build();

            val dirPosHead = buildDirectionPos(UpdateSeatPositionRequest.Direction.D_UP, headRestPos.toInt())
            val seatHead: SeatComponentPosition = SeatComponentPosition.newBuilder()
                .setComponent(SeatComponent.SC_HEADREST)
                .setDirectionPositions(0, dirPosHead)
                .build();

            val dirPosLeg = buildDirectionPos(UpdateSeatPositionRequest.Direction.D_UP, legRestPos.toInt())
            val seatLeg: SeatComponentPosition = SeatComponentPosition.newBuilder()
                .setComponent(SeatComponent.SC_LEGREST)
                .setDirectionPositions(0, dirPosLeg)
                .build();

            val seatReq: SeatPosition = newBuilder()
                .setName("row1_right")
                .setSeatComponentPositions(0, seatCushFront)
                .setSeatComponentPositions(1, seatCushRear)
                .setSeatComponentPositions(2, seatLeg)
                .setSeatComponentPositions(3, seatHead)
                .build();

            Log.i(TAG, "Publishing the cloud events to Bus")
            // todo need to confirm SomeIp topic name?
            val topic = ResourceMappingConstants.PASSENGER_SEAT
            val uResource = UResource(topic, "", SeatPosition::class.java.simpleName)

            SomeIpUtil.pubEvent(seatReq, ServiceConstant.SEATING_SERVICE, uResource, SeatingService.mLaunchManager.getmUltifiLinkMonitor())

        }

        // todo second row foot rest component(check driver seat position; if not getDriverFoot(), it is 4 seat modes)
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_LEFT_SEAT_PERCENTAGE_POSITION_1) {
            Log.i(TAG,"SECOND_LEFT_SEAT_PERCENTAGE_POSITION_1: Success")
            val resp = SomeipS2SManagementInterface.Second_Left_Seat_Percentage_Position_1Field.parseFrom(data.payload)
            val reclPos = resp.outPut.secRwLtStBkReclnUpwdDnwdPos*0.025
            val bolstPos = resp.outPut.secRwLtStBlstOtwdInwdPos*0.025
            val seatFrontPos = resp.outPut.secRwLtStFrwdBkwdPos*0.025
            val seatLeftPos = resp.outPut.secRwLtStLtwdRtwdPos*0.025
            Log.d(TAG, "onChangeEvent, Bolster position:"+reclPos+
                    ", recline:"+ bolstPos + ", seat front:" + seatFrontPos +
                    ", seat left:" + seatLeftPos);

            val dirPosRecl = buildDirectionPos(UpdateSeatPositionRequest.Direction.D_UP, reclPos.toInt())
            val seatRecl: SeatComponentPosition = SeatComponentPosition.newBuilder()
                .setComponent(SeatComponent.SC_BACK)
                .setDirectionPositions(0, dirPosRecl)
                .build();

            val dirPosBolst = buildDirectionPos(UpdateSeatPositionRequest.Direction.D_INFLATE, bolstPos.toInt())
            val seatBolst: SeatComponentPosition = SeatComponentPosition.newBuilder()
                .setComponent(SeatComponent.SC_SIDE_BOLSTER_BACK)
                .setDirectionPositions(0, dirPosBolst)
                .build();

            val dirPosFront = buildDirectionPos(UpdateSeatPositionRequest.Direction.D_FORWARD, seatFrontPos.toInt())
            val dirPosLeft = buildDirectionPos(UpdateSeatPositionRequest.Direction.D_LEFTWARD, seatLeftPos.toInt())
            val seatLeft: SeatComponentPosition = SeatComponentPosition.newBuilder()
                .setComponent(SeatComponent.SC_CUSHION)
                .setDirectionPositions(0, dirPosLeft)
                .setDirectionPositions(1, dirPosFront)
                .build();

            val seatReq: SeatPosition = SeatPosition.newBuilder()
                .setName("row2_left")
                .setSeatComponentPositions(0, seatRecl)
                .setSeatComponentPositions(1, seatBolst)
                .setSeatComponentPositions(2, seatLeft)
                .build();

            Log.i(TAG, "Publishing the cloud events to Bus")
            // todo need to confirm SomeIp topic name?
            val topic = ResourceMappingConstants.SECOND_LEFT_SEAT
            val uResource = UResource(topic, "", SeatPosition::class.java.simpleName)
            SomeIpUtil.pubEvent(seatReq, ServiceConstant.SEATING_SERVICE, uResource, SeatingService.mLaunchManager.getmUltifiLinkMonitor())
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_LEFT_SEAT_PERCENTAGE_POSITION_2) {
            Log.i(TAG,"SECOND_LEFT_SEAT_PERCENTAGE_POSITION_2: Success")
            val resp = SomeipS2SManagementInterface.Second_Left_Seat_Percentage_Position_2Field.parseFrom(data.payload)
            val cushFrontPos = resp.outPut.secRwLtStCshnFrntUpwdDnwdPos*0.025
            val cushRearPos = resp.outPut.secRwLtStCshnRrUpwdDnwdPos*0.025
            val headRestPos = resp.outPut.secRwLtStHdrstStUpwdDnwdPos*0.025
            val legRestPos = resp.outPut.secRwLtStLgrstUpwdDnwdPos*0.025

            Log.d(TAG, "onChangeEvent, Cushion front position:"+cushFrontPos+
                    ", cushion rear:"+ cushRearPos + ", head rest:" + headRestPos +
                    ", leg rest:" + legRestPos)

            val dirPosCushFront = buildDirectionPos(UpdateSeatPositionRequest.Direction.D_UP, cushFrontPos.toInt())
            val seatCushFront: SeatComponentPosition = SeatComponentPosition.newBuilder()
                .setComponent(SeatComponent.SC_CUSHION_FRONT)
                .setDirectionPositions(0, dirPosCushFront)
                .build();

            val dirPosCushRear = buildDirectionPos(UpdateSeatPositionRequest.Direction.D_UP, cushRearPos.toInt())
            val seatCushRear: SeatComponentPosition = SeatComponentPosition.newBuilder()
                .setComponent(SeatComponent.SC_CUSHION)
                .setDirectionPositions(0, dirPosCushRear)
                .build();

            val dirPosHead = buildDirectionPos(UpdateSeatPositionRequest.Direction.D_UP, headRestPos.toInt())
            val seatHead: SeatComponentPosition = SeatComponentPosition.newBuilder()
                .setComponent(SeatComponent.SC_HEADREST)
                .setDirectionPositions(0, dirPosHead)
                .build();

            val dirPosLeg = buildDirectionPos(UpdateSeatPositionRequest.Direction.D_UP, legRestPos.toInt())
            val seatLeg: SeatComponentPosition = SeatComponentPosition.newBuilder()
                .setComponent(SeatComponent.SC_LEGREST)
                .setDirectionPositions(0, dirPosLeg)
                .build();

            val seatReq: SeatPosition = SeatPosition.newBuilder()
                .setName("row2_left")
                .setSeatComponentPositions(0, seatCushFront)
                .setSeatComponentPositions(1, seatCushRear)
                .setSeatComponentPositions(2, seatHead)
                .setSeatComponentPositions(3, seatLeg)
                .build();

            Log.i(TAG, "Publishing the cloud events to Bus")
            // todo need to confirm SomeIp topic name?
            val topic = ResourceMappingConstants.SECOND_LEFT_SEAT
            val uResource = UResource(topic, "", SeatPosition::class.java.simpleName)

            SomeIpUtil.pubEvent(seatReq, ServiceConstant.SEATING_SERVICE, uResource, SeatingService.mLaunchManager.getmUltifiLinkMonitor())
        }

        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_RIGHT_SEAT_PERCENTAGE_POSITION_1) {
            Log.i( TAG, "SECOND_RIGHT_SEAT_PERCENTAGE_POSITION_1: Success" )
            val resp = SomeipS2SManagementInterface.Second_Right_Seat_Percentage_Position_1Field.parseFrom(data.payload)
            val reclPos = resp.outPut.secRwRtStBkReclnUpwdDnwdPos*0.025
            val bolstPos = resp.outPut.secRwRtStBlstOtwdInwdPos*0.025
            val seatForwardPos = resp.outPut.secRwRtStFrwdBkwdPos*0.025
            val seatLeftPos = resp.outPut.secRwRtStLtwdRtwdPos*0.025
            Log.d(TAG, "onChangeEvent, Bolster position:"+bolstPos+
                    ", recline:"+ reclPos + ", seat front:" + seatForwardPos +
                    ", seat left:" + seatLeftPos);

            val dirPosRecl = buildDirectionPos(UpdateSeatPositionRequest.Direction.D_UP, reclPos.toInt())
            val seatRecl: SeatComponentPosition = SeatComponentPosition.newBuilder()
                .setComponent(SeatComponent.SC_BACK)
                .setDirectionPositions(0, dirPosRecl)
                .build();

            val dirPosBolst = buildDirectionPos(UpdateSeatPositionRequest.Direction.D_INFLATE, bolstPos.toInt())
            val seatBol: SeatComponentPosition = SeatComponentPosition.newBuilder()
                .setComponent(SeatComponent.SC_SIDE_BOLSTER_BACK)
                .setDirectionPositions(0, dirPosBolst)
                .build();

            val dirPosForward = buildDirectionPos(UpdateSeatPositionRequest.Direction.D_FORWARD, seatForwardPos.toInt())
            val dirPosLeft = buildDirectionPos(UpdateSeatPositionRequest.Direction.D_LEFTWARD, seatLeftPos.toInt())
            val seatLeft: SeatComponentPosition = SeatComponentPosition.newBuilder()
                .setComponent(SeatComponent.SC_CUSHION)
                .setDirectionPositions(0, dirPosForward)
                .setDirectionPositions(1, dirPosLeft)
                .build();

            val seatReq: SeatPosition = SeatPosition.newBuilder()
                .setName("row2_right")
                .setSeatComponentPositions(0, seatRecl)
                .setSeatComponentPositions(1, seatBol)
                .setSeatComponentPositions(2, seatLeft)
//                .setSeatComponentPositions(3, seatLeft)
                .build();

            Log.i(TAG, "Publishing the cloud events to Bus")
            // todo need to confirm SomeIp topic name?
            val topic = ResourceMappingConstants.SECOND_RIGHT_SEAT
            val uResource = UResource(topic, "", SeatPosition::class.java.simpleName)
            SomeIpUtil.pubEvent(seatReq, ServiceConstant.SEATING_SERVICE, uResource, SeatingService.mLaunchManager.getmUltifiLinkMonitor())
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_RIGHT_SEAT_PERCENTAGE_POSITION_2) {
            Log.i(TAG,"SECOND_RIGHT_SEAT_PERCENTAGE_POSITION_2: Success")
            val resp = SomeipS2SManagementInterface.Second_Right_Seat_Percentage_Position_2Field.parseFrom(data.payload)
            val cushFrontPos = resp.outPut.secRwRtStCshnFrntUpwdDnwdPos*0.025
            val cushRearPos = resp.outPut.secRwRtStCshnRrUpwdDnwdPos*0.025
            val headRestPos = resp.outPut.secRwRtStHdrstUpwdDnwdPos*0.025
            val legRestPos = resp.outPut.secRwRtStLgrstUpwdDnwdPos*0.025
            Log.d(TAG, "onChangeEvent, Cushion front position:"+cushFrontPos+
                    ", cushion rear:"+ cushRearPos + ", head rest:" + headRestPos +
                    ", leg rest:" + legRestPos)

            val dirPosLeft = buildDirectionPos(UpdateSeatPositionRequest.Direction.D_UP, cushFrontPos.toInt())
            val seatCushFront: SeatComponentPosition = SeatComponentPosition.newBuilder()
                .setComponent(SeatComponent.SC_CUSHION_FRONT)
                .setDirectionPositions(0, dirPosLeft)
                .build();

            val dirPosCushRear = buildDirectionPos(UpdateSeatPositionRequest.Direction.D_UP, cushRearPos.toInt())
            val seatCushRear: SeatComponentPosition = SeatComponentPosition.newBuilder()
                .setComponent(SeatComponent.SC_CUSHION)
                .setDirectionPositions(0, dirPosCushRear)
                .build();

            val dirPosHead = buildDirectionPos(UpdateSeatPositionRequest.Direction.D_UP, headRestPos.toInt())
            val seatHead: SeatComponentPosition = SeatComponentPosition.newBuilder()
                .setComponent(SeatComponent.SC_HEADREST)
                .setDirectionPositions(0, dirPosHead)
                .build();

            val dirPosLeg = buildDirectionPos(UpdateSeatPositionRequest.Direction.D_UP, legRestPos.toInt())
            val seatLegPos: SeatComponentPosition = SeatComponentPosition.newBuilder()
                .setComponent(SeatComponent.SC_LEGREST)
                .setDirectionPositions(0, dirPosLeg)
                .build();

            val seatReq: SeatPosition = SeatPosition.newBuilder()
                .setName("row2_right")
                .setSeatComponentPositions(0, seatCushFront)
                .setSeatComponentPositions(1, seatCushRear)
                .setSeatComponentPositions(2, seatHead)
                .setSeatComponentPositions(3, seatLegPos)
                .build();

            Log.i(TAG, "Publishing the cloud events to Bus")
            // todo need to confirm SomeIp topic name?
            val topic = ResourceMappingConstants.SECOND_RIGHT_SEAT
            val uResource = UResource(topic, "", SeatPosition::class.java.simpleName)

            SomeIpUtil.pubEvent(seatReq, ServiceConstant.SEATING_SERVICE, uResource, SeatingService.mLaunchManager.getmUltifiLinkMonitor())
        }

        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_THIRD_LEFT_SEAT_PERCENTAGE_POSITION) {
            Log.i(TAG,"THIRD_LEFT_SEAT_PERCENTAGE_POSITION: Success")
            val resp = SomeipS2SManagementInterface.Third_Left_Seat_Percentage_PositionField.parseFrom(data.payload)
            val reclPos = resp.outPut.thdRwLtStBkReclnUpwdDnwdPos*0.025
            val cushFoldPos = resp.outPut.thdRwLtStCshnFldUpwdDnwdPos*0.025
            val forwardPos = resp.outPut.thdRwLtStFrwdBkwdPos*0.025
            Log.d(TAG, "onChangeEvent, recline position:"+reclPos+
                    ", cushion fold:"+ cushFoldPos + ", seat forward:" + forwardPos)

            val dirPosRecl = buildDirectionPos(UpdateSeatPositionRequest.Direction.D_UP, reclPos.toInt())
            val seatCushFront: SeatComponentPosition = SeatComponentPosition.newBuilder()
                .setComponent(SeatComponent.SC_BACK)
                .setDirectionPositions(0, dirPosRecl)
                .build();

            val dirPosCushFold = buildDirectionPos(UpdateSeatPositionRequest.Direction.D_FOLD, cushFoldPos.toInt())
//            val seatCushFold: SeatComponentPosition = SeatComponentPosition.newBuilder()
//                .setComponent(SeatComponent.SC_CUSHION)
//                .setDirectionPositions(0, dirPosCushFold)
//                .build();

            val dirPosForwad = buildDirectionPos(UpdateSeatPositionRequest.Direction.D_FORWARD, forwardPos.toInt())
            val seat: SeatComponentPosition = SeatComponentPosition.newBuilder()
                .setComponent(SeatComponent.SC_CUSHION)
                .setDirectionPositions(0, dirPosForwad)
                .setDirectionPositions(1, dirPosCushFold)
                .build();

            val seatReq: SeatPosition = newBuilder()
                .setName("row3_left")
                .setSeatComponentPositions(0, seatCushFront)
                .setSeatComponentPositions(1, seat)
                .build();

            Log.i(TAG, "Publishing the cloud events to Bus")
            // todo need to confirm SomeIp topic name?
            val topic = ResourceMappingConstants.THIRD_LEFT_SEAT
            val uResource = UResource(topic, "", SeatPosition::class.java.simpleName)
            SomeIpUtil.pubEvent(seatReq, ServiceConstant.SEATING_SERVICE, uResource, SeatingService.mLaunchManager.getmUltifiLinkMonitor())

        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_THIRD_RIGHT_SEAT_PERCENTAGE_POSITION) {
            Log.i(TAG,"THIRD_RIGHT_SEAT_PERCENTAGE_POSITION: Success")
            val resp = SomeipS2SManagementInterface.Third_Right_Seat_Percentage_PositionField.parseFrom(data.payload)
            val reclPos = resp.outPut.thdRwRtStBkReclnUpwdDnwdPos*0.025
            val cushFoldPos = resp.outPut.thdRwRtStCshnFldUpwdDnwdPos*0.025
            val forwardPos = resp.outPut.thdRwRtStFrwdBkwdPos*0.025
            Log.d(TAG, "onChangeEvent, recline position:"+reclPos+
                    ", cushion fold:"+ cushFoldPos + ", seat forward:" + forwardPos.toInt())

            val dirPosRecl = buildDirectionPos(UpdateSeatPositionRequest.Direction.D_UP, reclPos.toInt())
            val seatCushFront: SeatComponentPosition = SeatComponentPosition.newBuilder()
                .setComponent(SeatComponent.SC_BACK)
                .setDirectionPositions(0, dirPosRecl)
                .build();

            val dirPosCushFold = buildDirectionPos(UpdateSeatPositionRequest.Direction.D_FOLD, cushFoldPos.toInt())
            val dirPosForward = buildDirectionPos(UpdateSeatPositionRequest.Direction.D_FORWARD, forwardPos.toInt())
            val seat: SeatComponentPosition = SeatComponentPosition.newBuilder()
                .setComponent(SeatComponent.SC_CUSHION)
                .setDirectionPositions(0, dirPosForward)
                .setDirectionPositions(1, dirPosCushFold)
                .build();

            val seatReq: SeatPosition = SeatPosition.newBuilder()
                .setName("row3_right")
                .setSeatComponentPositions(0, seatCushFront)
                .setSeatComponentPositions(1, seat)
//                .setSeatComponentPositions(3, seatLeftPos)
                .build();

            Log.i(TAG, "Publishing the cloud events to Bus")
            // todo need to confirm SomeIp topic name?
            val topic = ResourceMappingConstants.THIRD_RIGHT_SEAT
            val uResource = UResource(topic, "", SeatPosition::class.java.simpleName)
            SomeIpUtil.pubEvent(seatReq, ServiceConstant.SEATING_SERVICE, uResource, SeatingService.mLaunchManager.getmUltifiLinkMonitor())

        }

        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_DRIVER_SEAT_CONFIGURATION) {
            Log.i(TAG, "DRIVER_SEAT_CONFIGURATION: Success")
            val resource = ResourceMappingConstants.DRIVER_SEAT
            val resp = SomeipS2SManagementInterface.Driver_Seat_ConfigurationField.parseFrom(data.payload)

            val driverStFootConf = resp.outPut.drvStFtUpwdDnwdMovConfig
            val cacheFootConf = globalMap["drvStFtUpwdDnwdMovConfig"]
            if(driverStFootConf != cacheFootConf){
                val cacheFootPos = globalMap["drvStFtUpwdDnwdPos"] as Int
                val status = globalMap["drvStFtrstUpwdDnwdMovmtVirtCtrlAvl"] as Boolean
                buildMessageAndPublish(cacheFootPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_FOOTREST, driverStFootConf, status)
                globalMap["drvStFtUpwdDnwdMovConfig"] = driverStFootConf
            }

            val driverStForwardConf = resp.outPut.drvStFrwdBkwdMovConfig
            val cacheStForwardConf = globalMap["drvStFrwdBkwdMovConfig"]
            if(driverStForwardConf != cacheStForwardConf){
                val cacheStPos = globalMap["drvStFrwdBkwdPos"] as Int
                val status = globalMap["drvStFwdBkwdMovmtVirtCtrlAvl"] as Boolean
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_FORWARD, SeatComponent.SC_CUSHION, driverStForwardConf, status)
                globalMap["drvStFrwdBkwdMovConfig"] = driverStForwardConf
            }

            val driverStLegConf = resp.outPut.drvStLgrstUpwdMovConfig
            val cacheLgConf = globalMap["drvStLgrstUpwdMovConfig"]
            if(driverStLegConf !=cacheLgConf){
                val cacheStPos = globalMap["drvStLgrstUpwdDnwdPos"] as Int
                val status = true
//                val status = globalMap["drvStLg"] as Boolean
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_LEGREST, driverStLegConf, status)
                globalMap["drvStLgrstUpwdMovConfig"] = driverStLegConf
            }

            val driverStHeadConf = resp.outPut.drvStHdrstUpwdDnwdMovConfig
            val cacheHdConf = globalMap["drvStHdrstUpwdDnwdMovConfig"]
            if(cacheHdConf != driverStHeadConf){
                val cacheStPos = globalMap["drvStHdrstUpwdDnwdPos"] as Int
                val status = globalMap["drvStHdrstUpwdDnwdMovmtVirtCtrlAvl"] as Boolean    //drvStHdrstFwdBkwdMovmtVirtCtrlAvl
                //todo need to check "drvStHdrstFwdBkwdMovmtVirtCtrlAvl" and to set repeated direction
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_HEADREST, driverStHeadConf, status)
                globalMap["drvStHdrstUpwdDnwdMovConfig"] = driverStHeadConf
            }

            val driverStReclConf = resp.outPut.drvStBkReclnUpwdDnwdMovConfig
            val cacheReclConf = globalMap["drvStBkReclnUpwdDnwdMovConfig"]
            if(driverStReclConf != cacheReclConf){
                val cacheStPos = globalMap["drvStBkReclnUpwdDnwdPos"] as Int
                val status = globalMap["drvStRclUpwdDnwdMovmtVirtCtrlAvl"] as Boolean
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_BACK, driverStReclConf, status)
                globalMap["drvStBkReclnUpwdDnwdMovConfig"] = driverStReclConf
            }

            val driverStCushRearConf = resp.outPut.drvStCshnRrUpwdDnwdMovConfig
            val cacheCushRearConf = globalMap["drvStCshnRrUpwdDnwdMovConfig"]
            if(driverStCushRearConf != cacheCushRearConf){
                val cacheStPos = globalMap["drvStCshnRrUpwdDnwdPos"] as Int
                val status = globalMap["drvStCushnRrUpwdDnwdMovmtVirtCtrlAvl"] as Boolean
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_CUSHION, driverStCushRearConf, status)
                globalMap["drvStCshnRrUpwdDnwdMovConfig"] = driverStCushRearConf
            }

            val driverStCushFrontConf = resp.outPut.drvStCshnFrntUpwdDnwdMovConfig
            val cacheCushFrontConf = globalMap["drvStCshnFrntUpwdDnwdMovConfig"]
            if(driverStCushFrontConf != cacheCushFrontConf){
                val cacheStPos = globalMap["drvStCshnFrntUpwdDnwdPos"] as Int
                val status = globalMap["drvStCushnFrtUpwdDnwdMovmtVirtCtrlAvl"] as Boolean
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_CUSHION_FRONT, driverStCushFrontConf, status)
                globalMap["drvStCshnFrntUpwdDnwdMovConfig"] = driverStCushFrontConf
            }

            val driverStBolsterConf = resp.outPut.drvStBlstOtwdInwdMovConfig
            val cacheBolsterConf = globalMap["drvStBlstOtwdInwdMovConfig"]
            if(cacheBolsterConf != driverStBolsterConf){
                val cacheStPos = globalMap["drvStBlstOtwdInwdPos"] as Int
                val status = true
//                val status = globalMap["drvStBolsterVirtCtrlAvl"] as Boolean   currently, not existing status
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_INFLATE, SeatComponent.SC_SIDE_BOLSTER_BACK, driverStBolsterConf, status)
                globalMap["drvStBlstOtwdInwdMovConfig"] = driverStBolsterConf
            }
        }

        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_PASSENGER_SEAT_CONFIGURATION) {
            Log.i(TAG, "PASSENGER_SEAT_CONFIGURATION: Success")
            val resp = SomeipS2SManagementInterface.Passenger_Seat_ConfigurationField.parseFrom(data.payload)
            val passStFootConf = resp.outPut.passStFtUpwdDnwdMovConfig
            val cacheFootConf = globalMap["passStFtUpwdDnwdMovConfig"]
            val resource = ResourceMappingConstants.PASSENGER_SEAT
            if(passStFootConf != cacheFootConf){
                val cacheFootPos = globalMap["passStFtUpwdDnwdPos"] as Int
                val status = globalMap["passStFtrstUpwdDnwdMovmtVirtCtrlAvl"] as Boolean
                buildMessageAndPublish(cacheFootPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_FOOTREST, passStFootConf, status)
                globalMap["passStFtUpwdDnwdMovConfig"] = passStFootConf
            }

            val passStForwardConf = resp.outPut.passStFrwdBkwdMovConfig
            val cacheStForwardConf = globalMap["passStFrwdBkwdMovConfig"]
            if(passStForwardConf != cacheStForwardConf){
                val cacheStPos = globalMap["passStFrwdBkwdPos"] as Int
                val status = globalMap["passStFwdBkwdMovmtVirtCtrlAvl"] as Boolean         //passStFwdBkwdMovmtVirtCtrlAvl
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_FORWARD, SeatComponent.SC_CUSHION, passStForwardConf, status)
                globalMap["passStFrwdBkwdMovConfig"] = passStForwardConf
            }

            val passStLegConf = resp.outPut.passStLgrstUpwdMovConfig
            val cacheLgConf = globalMap["passStLgrstUpwdMovConfig"]
            if(passStLegConf != cacheLgConf){
                val cacheStPos = globalMap["passStLgrstUpwdDnwdPos"] as Int
                val status = true
//                val status = globalMap["passStLg"] as Boolean             currently, not existing status
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_LEGREST, passStLegConf, status)
                globalMap["passStLgrstUpwdMovConfig"] = passStLegConf
            }

            val passStHeadConf = resp.outPut.passStHdrstUpwdDnwdMovConfig
            val cacheHdConf = globalMap["passStHdrstUpwdDnwdMovConfig"]
            if(cacheHdConf != passStHeadConf){
                val cacheStPos = globalMap["passStHdrstUpwdDnwdPos"] as Int
                val status = globalMap["passStHdrstUpwdDnwdMovmtVirtCtrlAvl"] as Boolean
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_HEADREST, passStHeadConf, status)
                globalMap["passStHdrstUpwdDnwdMovConfig"] = passStHeadConf
            }

            val passStReclConf = resp.outPut.passStBkReclnUpwdDnwdMovConfig
            val cacheReclConf = globalMap["passStBkReclnUpwdDnwdMovConfig"]
            if(passStReclConf != cacheReclConf){
                val cacheStPos = globalMap["passStBkReclnUpwdDnwdPos"] as Int
                val status = globalMap["passStRclUpwdDnwdMovmtVirtCtrlAvl"] as Boolean
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_BACK, passStReclConf, status)
                globalMap["passStBkReclnUpwdDnwdMovConfig"] = passStReclConf
            }

            val passStCushRearConf = resp.outPut.passStCshnRrUpwdDnwdMovConfig
            val cacheCushRearConf = globalMap["passStCshnRrUpwdDnwdMovConfig"]
            if(passStCushRearConf != cacheCushRearConf){
                val cacheStPos = globalMap["passStCshnRrUpwdDnwdPos"] as Int
                val status = globalMap["passStCushnRrUpwdDnwdMovmtVirtCtrlAvl"] as Boolean
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_CUSHION, passStCushRearConf, status)
                globalMap["passStCshnRrUpwdDnwdMovConfig"] = passStCushRearConf
            }

            val passStCushFrontConf = resp.outPut.passStCshnFrntUpwdDnwdMovConfig
            val cacheCushFrontConf = globalMap["passStCshnFrntUpwdDnwdMovConfig"]
            if(passStCushFrontConf != cacheCushFrontConf){
                val cacheStPos = globalMap["passStCshnFrntUpwdDnwdPos"] as Int
                val status = globalMap["passStCushnFrtUpwdDnwdMovmtVirtCtrlAvl"] as Boolean
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_CUSHION_FRONT, passStCushFrontConf, status)
                globalMap["passStCshnFrntUpwdDnwdMovConfig"] = passStCushFrontConf
            }

            val passStBolsterConf = resp.outPut.passStBlstOtwdInwdMovConfig
            val cacheBolsterConf = globalMap["passStBlstOtwdInwdMovConfig"]
            if(cacheBolsterConf != passStBolsterConf){
                val cacheStPos = globalMap["passStBlstOtwdInwdPos"] as Int
                val status = true
//                val status = globalMap["passStBl"] as Boolean            //       currently, not existing status
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_INFLATE, SeatComponent.SC_SIDE_BOLSTER_BACK, passStBolsterConf, status)
                globalMap["passStBlstOtwdInwdMovConfig"] = passStBolsterConf
            }

        }

        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_LEFT_SEAT_CONFIGURATION) {
            Log.i(TAG, "SECOND_LEFT_SEAT_CONFIGURATION: Success")
            val resp = SomeipS2SManagementInterface.Second_Left_Seat_ConfigurationField.parseFrom(data.payload)
            val resource = ResourceMappingConstants.SECOND_LEFT_SEAT

            val secLtStLtConf = resp.outPut.secRwLtStLtwdRtwdMovConfig
            val cacheFootConf = globalMap["secRwLtStLtwdRtwdMovConfig"]
            if(secLtStLtConf != cacheFootConf){
                val cacheFootPos = globalMap["secRwLtStLtwdRtwdPos"] as Int
                val status = globalMap["secRwLtStLtwdRtwdMovmtVirtCtrlAvl"] as Boolean              // todo two directions
                buildMessageAndPublish(cacheFootPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_FOOTREST, secLtStLtConf, status)
                globalMap["secRwLtStLtwdRtwdMovConfig"] = secLtStLtConf
            }

            val secLtStForwardConf = resp.outPut.secRwLtStFrwdBkwdMovConfig
            val cacheStForwardConf = globalMap["secRwLtStFrwdBkwdMovConfig"]
            if(secLtStForwardConf != cacheStForwardConf){
                val cacheStPos = globalMap["secRwLtStFrwdBkwdPos"] as Int
                val status = globalMap["secRwLtStFwdBkwdMovmtVirtCtrlAvl"] as Boolean               // todo two directions
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_FORWARD, SeatComponent.SC_CUSHION, secLtStForwardConf, status)
                globalMap["secRwLtStFrwdBkwdMovConfig"] = secLtStForwardConf
            }

            val secLfStLegConf = resp.outPut.secRwLtStLgrstUpwdMovConfig
            val cacheLgConf = globalMap["secRwLtStLgrstUpwdMovConfig"]
            if(secLfStLegConf != cacheLgConf){
                val cacheStPos = globalMap["secRwLtStLgrstUpwdDnwdPos"] as Int
                val status = globalMap["secRwLtStLgrstUpwdDnwdMovmtVirtCtrlAvl"] as Boolean          // todo two directions
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_LEGREST, secLfStLegConf, status)
                globalMap["secRwLtStLgrstUpwdMovConfig"] = secLfStLegConf
            }

            val secLfStHeadConf = resp.outPut.secRwLtStHdrstUpwdDnwdMovConfig
            val cacheHdConf = globalMap["secRwLtStHdrstUpwdDnwdMovConfig"]
            if(cacheHdConf != secLfStHeadConf){
                val cacheStPos = globalMap["secRwLtStHdrstStUpwdDnwdPos"] as Int
                val status = globalMap["secRwLtStHdrstUpwdDnwdMovmtVirtCtrlAvl"] as Boolean             // todo two directions
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_HEADREST, secLfStHeadConf, status)
                globalMap["secRwLtStHdrstUpwdDnwdMovConfig"] = secLfStHeadConf
            }

            val secLfStReclConf = resp.outPut.secRwLtStBkReclnUpwdDnwdMovConfig
            val cacheReclConf = globalMap["secRwLtStBkReclnUpwdDnwdMovConfig"]
            if(secLfStReclConf != cacheReclConf){
                val cacheStPos = globalMap["secRwLtStBkReclnUpwdDnwdPos"] as Int
                val status = globalMap["secRwLtStRclUpwdDnwdMovmtVirtCtrlAvl"] as Boolean
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_BACK, secLfStReclConf, status)
                globalMap["secRwLtStBkReclnUpwdDnwdMovConfig"] = secLfStReclConf
            }

            val secLtStCushRearConf = resp.outPut.secRwLtStCshnRrUpwdDnwdMovConfig
            val cacheCushRearConf = globalMap["secRwLtStCshnRrUpwdDnwdMovConfig"]
            if(secLtStCushRearConf != cacheCushRearConf){
                val cacheStPos = globalMap["secRwLtStCshnRrUpwdDnwdPos"] as Int
                val status = globalMap["secRwLtStCushnRrUpwdDnwdMovmtVirtCtrlAvl"] as Boolean
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_CUSHION, secLtStCushRearConf, status)
                globalMap["secRwLtStCshnRrUpwdDnwdMovConfig"] = secLtStCushRearConf
            }

            val secLtStCushFrontConf = resp.outPut.secRwLtStCshnFrntUpwdDnwdMovConfig
            val cacheCushFrontConf = globalMap["secRwLtStCshnFrntUpwdDnwdMovConfig"]
            if(secLtStCushFrontConf != cacheCushFrontConf){
                val cacheStPos = globalMap["secRwLtStCshnFrntUpwdDnwdPos"] as Int
                val status = globalMap["secRwLtStCushnFrtUpwdDnwdMovmtVirtCtrlAvl"] as Boolean
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_CUSHION_FRONT, secLtStCushFrontConf, status)
                globalMap["secRwLtStCshnFrntUpwdDnwdMovConfig"] = secLtStCushFrontConf
            }

            val secLtStBolsterConf = resp.outPut.secRwLtStBlstOtwdInwdMovConfig
            val cacheBolsterConf = globalMap["secRwLtStBlstOtwdInwdMovConfig"]
            if(cacheBolsterConf != secLtStBolsterConf){
                val cacheStPos = globalMap["secRwLtStBlstOtwdInwdPos"] as Int
                val status = true
//                val status = globalMap["secRwLtStBlst"] as Boolean     currently, not existing status
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_INFLATE, SeatComponent.SC_SIDE_BOLSTER_BACK, secLtStBolsterConf, status)
                globalMap["secRwLtStBlstOtwdInwdMovConfig"] = secLtStBolsterConf
            }
        }

        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_RIGHT_SEAT_CONFIGURATION) {
            Log.i(TAG, "SECOND_RIGHT_SEAT_CONFIGURATION: Success")
            val resp = SomeipS2SManagementInterface.Second_Right_Seat_ConfigurationField.parseFrom(data.payload)
            val resource = ResourceMappingConstants.SECOND_RIGHT_SEAT

            val secRtStLtConf = resp.outPut.secRwRtStLtwdRtwdMovConfig
            val cacheFootConf = globalMap["secRwRtStLtwdRtwdMovConfig"]
            if(secRtStLtConf != cacheFootConf){
                val cacheFootPos = globalMap["secRwRtStLtwdRtwdPos"] as Int
                val status = globalMap["secRwRtStLtwdRtwdMovmtVirtCtrlAvl"] as Boolean              // todo two directions
                buildMessageAndPublish(cacheFootPos, resource, UpdateSeatPositionRequest.Direction.D_LEFTWARD, SeatComponent.SC_BACK, secRtStLtConf, status)
                globalMap["secRwRtStLtwdRtwdMovConfig"] = secRtStLtConf
            }

            val secRtStForwardConf = resp.outPut.secRwRtStFrwdBkwdMovConfig
            val cacheStForwardConf = globalMap["secRwRtStFrwdBkwdMovConfig"]
            if(secRtStForwardConf != cacheStForwardConf){
                val cacheStPos = globalMap["secRwLtStFrwdBkwdPos"] as Int

                val status = globalMap["secRwRtStFwdBkwdMovmtVirtCtrlAvl"] as Boolean               // todo two directions
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_FORWARD, SeatComponent.SC_CUSHION, secRtStForwardConf, status)
                globalMap["secRwRtStFrwdBkwdMovConfig"] = secRtStForwardConf
            }

            val secRfStLegConf = resp.outPut.secRwRtStLgrstUpwdMovConfig
            val cacheLgConf = globalMap["secRwRtStLgrstUpwdMovConfig"]
            if(secRfStLegConf != cacheLgConf){
                val cacheStPos = globalMap["secRwRtStLgrstUpwdDnwdPos"] as Int
                val status = globalMap["secRwRtStLgrstUpwdDnwdMovmtVirtCtrlAvl"] as Boolean          // todo two directions
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_LEGREST, secRfStLegConf, status)
                globalMap["secRwRtStLgrstUpwdMovConfig"] = secRfStLegConf
            }

            val secRfStHeadConf = resp.outPut.secRwRtStHdrstUpwdDnwdMovConfig
            val cacheHdConf = globalMap["secRwRtStHdrstUpwdDnwdMovConfig"]
            if(cacheHdConf != secRfStHeadConf){
                val cacheStPos = globalMap["secRwRtStHdrstStUpwdDnwdPos"] as Int
                val status = globalMap["secRwRtStHdrstUpwdDnwdMovmtVirtCtrlAvl"] as Boolean             // todo two directions
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_HEADREST, secRfStHeadConf, status)
                globalMap["secRwRtStHdrstUpwdDnwdMovConfig"] = secRfStHeadConf
            }

            val secRfStReclConf = resp.outPut.secRwRtStBkReclnUpwdDnwdMovConfig
            val cacheReclConf = globalMap["secRwRtStBkReclnUpwdDnwdMovConfig"]
            if(secRfStReclConf != cacheReclConf){
                val cacheStPos = globalMap["secRwRtStBkReclnUpwdDnwdPos"] as Int
                val status = globalMap["secRwRtStRclUpwdDnwdMovmtVirtCtrlAvl"] as Boolean
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_BACK, secRfStReclConf, status)
                globalMap["secRwRtStBkReclnUpwdDnwdMovConfig"] = secRfStReclConf
            }

            val secRtStCushRearConf = resp.outPut.secRwRtStCshnRrUpwdDnwdMovConfig
            val cacheCushRearConf = globalMap["secRwRtStCshnRrUpwdDnwdMovConfig"]
            if(secRtStCushRearConf != cacheCushRearConf){
                val cacheStPos = globalMap["secRwLtStCshnRrUpwdDnwdPos"] as Int
                val status = globalMap["secRwRtStCushnRrUpwdDnwdMovmtVirtCtrlAvl"] as Boolean
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_CUSHION, secRtStCushRearConf, status)
                globalMap["secRwRtStCshnRrUpwdDnwdMovConfig"] = secRtStCushRearConf
            }

            val secRtStCushFrontConf = resp.outPut.secRwRtStCshnFrntUpwdDnwdMovConfig
            val cacheCushFrontConf = globalMap["secRwRtStCshnFrntUpwdDnwdMovConfig"]
            if(secRtStCushFrontConf != cacheCushFrontConf){
                val cacheStPos = globalMap["secRwRtStCshnFrntUpwdDnwdPos"] as Int
                val status = globalMap["secRwRtStCushnFrtUpwdDnwdMovmtVirtCtrlAvl"] as Boolean
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_CUSHION_FRONT, secRtStCushFrontConf, status)
                globalMap["secRwRtStCshnFrntUpwdDnwdMovConfig"] = secRtStCushFrontConf
            }

            val secRtStBolsterConf = resp.outPut.secRwRtStBlstOtwdInwdMovConfig
            val cacheBolsterConf = globalMap["secRwRtStBlstOtwdInwdMovConfig"]
            if(cacheBolsterConf != secRtStBolsterConf){
                val cacheStPos = globalMap["secRwLtStBlstOtwdInwdPos"] as Int
                val status = true
//                val status = globalMap["secRwLtStBlst"] as Boolean     currently, not existing status
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_INFLATE, SeatComponent.SC_SIDE_BOLSTER_BACK, secRtStBolsterConf, status)
                globalMap["secRwRtStBlstOtwdInwdMovConfig"] = secRtStBolsterConf
            }
        }

        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_THIRD_LEFT_SEAT_CONFIGURATION) {
            Log.i(TAG, "THIRD_LEFT_SEAT_CONFIGURATION: Success")
            val resp = SomeipS2SManagementInterface.Third_Left_Seat_ConfigurationField.parseFrom(data.payload)
            val resource = ResourceMappingConstants.THIRD_LEFT_SEAT

            val thdLtStLtConf = resp.outPut.thdRwLtStLtwdRtwdMovConfig
            val cacheFootConf = globalMap["thdRwLtStLtwdRtwdMovConfig"]                 // current, no existing config

            if(thdLtStLtConf != cacheFootConf){
                val cacheFootPos = globalMap["thdRwLtStLtwdRtwdPos"] as Int
                val status = true
//                val status = globalMap["thdRwLtStLtwdRtwdMovmtVirtCtrlAvl"] as Boolean      // current, no existing config
                buildMessageAndPublish(cacheFootPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_FOOTREST, thdLtStLtConf, status)
                globalMap["thdRwLtStLtwdRtwdMovConfig"] = thdLtStLtConf
            }

            val thdLtStForwardConf = resp.outPut.thdRwLtStFrwdBkwdMovConfig
            val cacheStForwardConf = globalMap["thdRwLtStFrwdBkwdMovConfig"]
            if(thdLtStForwardConf != cacheStForwardConf){
                val cacheStPos = globalMap["thdRwLtStFrwdBkwdPos"] as Int
                val status = globalMap["thdRwLtStFwdBkwdMovmtVirtCtrlAvl"] as Boolean
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_FORWARD, SeatComponent.SC_CUSHION, thdLtStForwardConf, status)
                globalMap["thdRwLtStFrwdBkwdMovConfig"] = thdLtStForwardConf
            }

            val thdLfStLegConf = resp.outPut.thdRwLtStLgrstUpwdMovConfig                    // current, no existing config
            val cacheLgConf = globalMap["thdRwLtStLgrstUpwdMovConfig"]
            if(thdLfStLegConf != cacheLgConf){
                val cacheStPos = globalMap["thdRwLtStLgrstUpwdDnwdPos"] as Int
                val status = true
//                val status = globalMap["thdRwLtStLgrstUpwdMovmtVirtCtrlAvl"] as Boolean         // current, no existing config
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_LEGREST, thdLfStLegConf, status)
                globalMap["thdRwLtStLgrstUpwdMovConfig"] = thdLfStLegConf
            }

            val thdLfStHeadConf = resp.outPut.thdRwLtStHdrstUpwdMovConfig                           // current, no existing config
            val cacheHdConf = globalMap["thdRwLtStHdrstUpwdMovConfig"]
            if(cacheHdConf != thdLfStHeadConf){
                val cacheStPos = globalMap["thdRwLtStHdrstUpwdPos"] as Int
                val status = true
//                val status = globalMap["thdRwLtStHdrstUpwdMovmtVirtCtrlAvl"] as Boolean         // current, no existing config
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_HEADREST, thdLfStHeadConf, status)
                globalMap["thdRwLtStHdrstUpwdMovConfig"] = thdLfStHeadConf
            }

            val thdLfStReclConf = resp.outPut.thdRwLtStBkReclnUpwdDnwdMovConfig
            val cacheReclConf = globalMap["thdRwLtStBkReclnUpwdDnwdMovConfig"]
            if(thdLfStReclConf != cacheReclConf){
                val cacheStPos = globalMap["thdRwLtStBkReclnUpwdDnwdPos"] as Int
                val status = globalMap["thdRwLtStRclUpwdDnwdMovmtVirtCtrlAvl"] as Boolean
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_BACK, thdLfStReclConf, status)
                globalMap["thdRwLtStBkReclnUpwdDnwdMovConfig"] = thdLfStReclConf
            }

            val thdLtStCushRearConf = resp.outPut.thdRwLtStCshnRrUpwdDnwdMovConfig
            val cacheCushRearConf = globalMap["thdRwLtStCshnRrUpwdDnwdMovConfig"]                   // current, no existing config
            if(thdLtStCushRearConf != cacheCushRearConf){
                val cacheStPos = globalMap["thdRwLtStCshnRrUpwdDnwdPos"] as Int
                val status = true
//                val status = globalMap["thdRwLtStCshnRrUpwdDnwdMovmtVirtCtrlAvl"] as Boolean           // current, no existing config
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_CUSHION, thdLtStCushRearConf, status)
                globalMap["thdRwLtStCshnRrUpwdDnwdMovConfig"] = thdLtStCushRearConf
            }

            val thdLtStCushFoldConf = resp.outPut.thdRwLtStCshnFldUpwdDnwdMovConfig
            val cacheCushFrontConf = globalMap["thdRwLtStCshnFldUpwdDnwdMovConfig"]
            if(thdLtStCushFoldConf != cacheCushFrontConf){
                val cacheStPos = globalMap["thdRwLtStCshnFldUpwdDnwdPos"] as Int
                val status = true
//                val status = globalMap["thdRwLtStCshnFldUpwdDnwdMovmtVirtCtrlAvl"] as Boolean           // todo checking cushion fold? (current, no existing config)
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_CUSHION_FRONT, thdLtStCushFoldConf, status)
                globalMap["thdRwLtStCshnFldUpwdDnwdMovConfig"] = thdLtStCushFoldConf
            }

            val thdLtStBolsterConf = resp.outPut.thdRwLtStBlstOtwdInwdMovConfig
            val cacheBolsterConf = globalMap["thdRwLtStBlstOtwdInwdMovConfig"]                      //current, no existing config
            if(cacheBolsterConf != thdLtStBolsterConf){
                val cacheStPos = globalMap["thdRwLtStBlstOtwdInwdPos"] as Int
                val status = true
//                val status = globalMap["thdRwLtStBlstOtwdInwdwdMovmtVirtCtrlAvl"] as Boolean      // current, no existing status
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_INFLATE, SeatComponent.SC_SIDE_BOLSTER_BACK, thdLtStBolsterConf, status)
                globalMap["thdRwLtStBlstOtwdInwdMovConfig"] = thdLtStBolsterConf
            }
        }

        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_THIRD_RIGHT_SEAT_CONFIGURATION) {
            Log.i(TAG, "THIRD_RIGHT_SEAT_CONFIGURATION: Success")
            val resp = SomeipS2SManagementInterface.Third_Right_Seat_ConfigurationField.parseFrom(data.payload)
            val resource = ResourceMappingConstants.THIRD_RIGHT_SEAT

            val thdRtStLtConf = resp.outPut.thdRwRtStLtwdRtwdMovConfig
            val cacheFootConf = globalMap["thdRwRtStLtwdRtwdMovConfig"]
            if(thdRtStLtConf != cacheFootConf){
                val cacheFootPos = globalMap["thdRwRtStLtwdRtwdPos"] as Int
                val status = true
//                val status = globalMap["thdRwLtStLtwdRtwdMovmtVirtCtrlAvl"] as Boolean      // current, no existing config
                buildMessageAndPublish(cacheFootPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_FOOTREST, thdRtStLtConf, status)
                globalMap["thdRwRtStLtwdRtwdMovConfig"] = thdRtStLtConf
            }

            val thdRtStForwardConf = resp.outPut.thdRwRtStFrwdBkwdMovConfig
            val cacheStForwardConf = globalMap["thdRwRtStFrwdBkwdMovConfig"]
            if(thdRtStForwardConf != cacheStForwardConf){
                val cacheStPos = globalMap["thdRwRtStFrwdBkwdPos"] as Int
                val status = globalMap["thdRwRtStFwdBkwdMovmtVirtCtrlAvl"] as Boolean
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_FORWARD, SeatComponent.SC_CUSHION, thdRtStForwardConf, status)
                globalMap["thdRwRtStFrwdBkwdMovConfig"] = thdRtStForwardConf
            }

            val thdLfStLegConf = resp.outPut.thdRwRtStLgrstUpwdMovConfig
            val cacheLgConf = globalMap["thdRwRtStLgrstUpwdMovConfig"]                              // current, no existing config
            if(thdLfStLegConf != cacheLgConf){
                val cacheStPos = globalMap["thdRwRtStLgrstUpwdDnwdPos"] as Int
                val status = true
//                val status = globalMap["thdRwRtStLgrstUpwdMovmtVirtCtrlAvl"] as Boolean         // current, no existing config
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_LEGREST, thdLfStLegConf, status)
                globalMap["thdRwRtStLgrstUpwdMovConfig"] = thdLfStLegConf
            }

            val thdLfStHeadConf = resp.outPut.thdRwRtStHdrstUpwdMovConfig
            val cacheHdConf = globalMap["thdRwRtStHdrstUpwdMovConfig"]                              // current, no existing config
            if(cacheHdConf != thdLfStHeadConf){
                val cacheStPos = globalMap["thdRwRtStHdrstUpwdPos"] as Int
                val status = true
//                val status = globalMap["thdRwLtStHdrstUpwdMovmtVirtCtrlAvl"] as Boolean         // current, no existing config
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_HEADREST, thdLfStHeadConf, status)
                globalMap["thdRwRtStHdrstUpwdMovConfig"] = thdLfStHeadConf
            }

            val thdLfStReclConf = resp.outPut.thdRwRtStBkReclnUpwdDnwdMovConfig
            val cacheReclConf = globalMap["thdRwRtStBkReclnUpwdDnwdMovConfig"]
            if(thdLfStReclConf != cacheReclConf){
                val cacheStPos = globalMap["thdRwRtStBkReclnUpwdDnwdPos"] as Int
                val status = globalMap["thdRwRtStRclUpwdDnwdMovmtVirtCtrlAvl"] as Boolean
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_BACK, thdLfStReclConf, status)
                globalMap["thdRwRtStBkReclnUpwdDnwdMovConfig"] = thdLfStReclConf
            }

            val thdRtStCushRearConf = resp.outPut.thdRwRtStCshnRrUpwdDnwdMovConfig
            val cacheCushRearConf = globalMap["thdRwRtStCshnRrUpwdDnwdMovConfig"]                   // current, no existing config
            if(thdRtStCushRearConf != cacheCushRearConf){
                val cacheStPos = globalMap["thdRwRtStCshnRrUpwdDnwdPos"] as Int
                val status = true
//                val status = globalMap["thdRwRtStCshnRrUpwdDnwdMovmtVirtCtrlAvl"] as Boolean           // current, no existing config
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_CUSHION, thdRtStCushRearConf, status)
                globalMap["thdRwRtStCshnRrUpwdDnwdMovConfig"] = thdRtStCushRearConf
            }

            val thdRtStCushFoldConf = resp.outPut.thdRwRtStCshnFldUpwdDnwdMovConfig
            val cacheCushFrontConf = globalMap["thdRwRtStCshnFldUpwdDnwdMovConfig"]
            if(thdRtStCushFoldConf != cacheCushFrontConf){
                val cacheStPos = globalMap["thdRwRtStCshnFltUpwdDnwdPos"] as Int
                val status = true
//                val status = globalMap["thdRwRtStCshnFldUpwdDnwdMovmtVirtCtrlAvl"] as Boolean           // todo checking cushion fold? (current, no existing config)
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_CUSHION_FRONT, thdRtStCushFoldConf, status)
                globalMap["thdRwRtStCshnFldUpwdDnwdMovConfig"] = thdRtStCushFoldConf
            }

            val thdRtStBolsterConf = resp.outPut.thdRwRtStBlstOtwdInwdMovConfig
            val cacheBolsterConf = globalMap["thdRwRtStBlstOtwdInwdMovConfig"]
            if(cacheBolsterConf != thdRtStBolsterConf){
                val cacheStPos = globalMap["thdRwRtStBlstOtwdInwdPos"] as Int
                val status = true
//                val status = globalMap["thdRwRtStBlstOtwdInwdwdMovmtVirtCtrlAvl"] as Boolean      // current, no existing status
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_INFLATE, SeatComponent.SC_SIDE_BOLSTER_BACK, thdRtStBolsterConf, status)
                globalMap["thdRwRtStBlstOtwdInwdMovConfig"] = thdRtStBolsterConf
            }
        }

        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_DRIVER_SEAT_VIRTUAL_CONTROL_AVAILABILITY_AND_NOTIFICATION_STATUS) {
            Log.i(TAG, "DRIVER_SEAT_VIRTUAL_CONTROL_AVAILABILITY_AND_NOTIFICATION_STATUS: Success")
            val resource = ResourceMappingConstants.DRIVER_SEAT
            val resp = SomeipS2SManagementInterface.Driver_Seat_Virtual_Control_Availability_And_Notification_StatusField.parseFrom(data.payload)

            val cushionFStatus = resp.outPut.drvStCushnFrtUpwdDnwdMovmtVirtCtrlAvl
            val cacheCushionFStatus = globalMap["drvStCushnFrtUpwdDnwdMovmtVirtCtrlAvl"]
            if(cushionFStatus != cacheCushionFStatus){
                val cacheCushFrontConf = globalMap["drvStCshnFrntUpwdDnwdMovConfig"] as Boolean
                val cacheStPos = globalMap["drvStCshnFrntUpwdDnwdPos"] as Int
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_CUSHION_FRONT, cacheCushFrontConf, cushionFStatus)
                globalMap["drvStCushnFrtUpwdDnwdMovmtVirtCtrlAvl"] = cushionFStatus
            }

            val lumbarStatus = resp.outPut.drvStLmbrVirtCtrlAvl
            val cacheLumStatus = globalMap["drvStLmbrVirtCtrlAvl"]
            if(lumbarStatus != cacheCushionFStatus){
                Log.i(TAG, "Driver lumbar status is changed.")
            }

            val seatStatus = resp.outPut.drvStFwdBkwdMovmtVirtCtrlAvl
            val cacheSeatStatus = globalMap["drvStFwdBkwdMovmtVirtCtrlAvl"]
            if(seatStatus != cacheSeatStatus){
                val cacheStForwardConf = globalMap["drvStFrwdBkwdMovConfig"] as Boolean
                val cacheStPos = globalMap["drvStFrwdBkwdPos"] as Int
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_FORWARD, SeatComponent.SC_CUSHION, cacheStForwardConf, seatStatus)
                globalMap["drvStFwdBkwdMovmtVirtCtrlAvl"] = seatStatus
            }

            val massStatus = resp.outPut.drvStMassVirtCtrlAvl
            val cacheMassStatus = globalMap["drvStMassVirtCtrlAvl"]
            if(massStatus != cacheMassStatus){
                Log.i(TAG, "Driver massage status is changed.")
            }

            val hdRestStatus = resp.outPut.drvStHdrstUpwdDnwdMovmtVirtCtrlAvl
            val cacheHdRestStatus = globalMap["drvStHdrstUpwdDnwdMovmtVirtCtrlAvl"]
            if(hdRestStatus != cacheHdRestStatus){
                val cacheHdConf = globalMap["drvStHdrstUpwdDnwdMovConfig"] as Boolean    //drvStHdrstFwdBkwdMovmtConf
                val cacheStPos = globalMap["drvStHdrstUpwdDnwdPos"] as Int
                //todo need to check "drvStHdrstFwdBkwdMovmtVirtCtrlAvl" and to set repeated direction
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_HEADREST, cacheHdConf, hdRestStatus)
                globalMap["drvStHdrstUpwdDnwdMovmtVirtCtrlAvl"] = hdRestStatus
            }

            val ftRestStatus = resp.outPut.drvStFtrstUpwdDnwdMovmtVirtCtrlAvl
            val cacheFtRestStatus = globalMap["drvStFtrstUpwdDnwdMovmtVirtCtrlAvl"]
            if(ftRestStatus != cacheFtRestStatus){
                val cacheFootConf = globalMap["drvStFtUpwdDnwdMovConfig"] as Boolean
                val cacheFootPos = globalMap["drvStFtUpwdDnwdPos"] as Int
                buildMessageAndPublish(cacheFootPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_FOOTREST, cacheFootConf, ftRestStatus)
                globalMap["drvStFtrstUpwdDnwdMovmtVirtCtrlAvl"] = ftRestStatus
            }

            val cushionRStatus = resp.outPut.drvStCushnRrUpwdDnwdMovmtVirtCtrlAvl
            val cacheCushionRStatus = globalMap["drvStCushnRrUpwdDnwdMovmtVirtCtrlAvl"]
            if(cushionRStatus != cacheCushionRStatus){
                val cacheCushRearConf = globalMap["drvStCshnRrUpwdDnwdMovConfig"] as Boolean
                val cacheStPos = globalMap["drvStCshnRrUpwdDnwdPos"] as Int
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_CUSHION, cacheCushRearConf, cushionRStatus)
                globalMap["drvStCushnRrUpwdDnwdMovmtVirtCtrlAvl"] = cushionRStatus
            }

            val reclineStatus = resp.outPut.drvStRclUpwdDnwdMovmtVirtCtrlAvl
            val cacheReclineStatus = globalMap["drvStRclUpwdDnwdMovmtVirtCtrlAvl"]
            if(reclineStatus != cacheReclineStatus){
                val cacheReclConf = globalMap["drvStBkReclnUpwdDnwdMovConfig"] as Boolean
                val cacheStPos = globalMap["drvStBkReclnUpwdDnwdPos"] as Int
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_BACK, cacheReclConf, reclineStatus)
                globalMap["drvStRclUpwdDnwdMovmtVirtCtrlAvl"] = reclineStatus
            }

            val hdRestFStatus = resp.outPut.drvStHdrstFwdBkwdMovmtVirtCtrlAvl
            val cacheHdRestFStatus = globalMap["drvStHdrstFwdBkwdMovmtVirtCtrlAvl"]
            if(hdRestStatus != cacheHdRestFStatus){
                Log.i(TAG, "Driver Seat status is changed.")
//                val cacheReclConf = globalMap["drvStHdrstFwdBkwdMovConfig"] as Boolean            //currently, no existing conf
//                val cacheStPos = globalMap["drvStHdrstFwdBkwdPos"] as Int                         //currently, no existing pos
//                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_FORWARD, SeatComponent.SC_HEADREST, cacheReclConf, hdRestFStatus)
//                globalMap["drvStHdrstFwdBkwdMovmtVirtCtrlAvl"] = hdRestFStatus
            }
        }

        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_PASSENGER_SEAT_VIRTUAL_CONTROL_AVAILABILITY_AND_NOTIFICATION_STATUS) {
            Log.i(TAG, "PASSENGER_SEAT_VIRTUAL_CONTROL_AVAILABILITY_AND_NOTIFICATION_STATUS: Success")
            val resp = SomeipS2SManagementInterface.Passenger_Seat_Virtual_Control_Availability_And_Notification_StatusField.parseFrom(data.payload)
            val resource = ResourceMappingConstants.THIRD_LEFT_SEAT

            val cacheMassStatus = globalMap["passStMassVirtCtrlAvl"]
            val passMassStatus = resp.outPut.passStMassVirtCtrlAvl
            if(passMassStatus != cacheMassStatus){
                Log.i(TAG, "Massage status is changed.")
                // todo massage
            }

            val passSeatStatus = resp.outPut.passStFwdBkwdMovmtVirtCtrlAvl
            val cacheSeatStatus = globalMap["passStFwdBkwdMovmtVirtCtrlAvl"]
            if(passSeatStatus != cacheSeatStatus){
                Log.i(TAG, "Passenger Seat status is changed.")
                val cacheStForwardConf = globalMap["passStFrwdBkwdMovConfig"] as Boolean
                val cacheStPos = globalMap["passStFrwdBkwdPos"] as Int
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_FORWARD, SeatComponent.SC_CUSHION, cacheStForwardConf, passSeatStatus)
                globalMap["passStFwdBkwdMovmtVirtCtrlAvl"] = passSeatStatus

            }

            val lmbStatus = resp.outPut.passStLmbrVirtCtrlAvl
            val cacheLmbStatus = globalMap["passStLmbrVirtCtrlAvl"]
            if(lmbStatus != cacheLmbStatus){
                Log.i(TAG, "Massage status is changed.")
                // todo lumbar
            }
            val footStatus = resp.outPut.passStFtrstUpwdDnwdMovmtVirtCtrlAvl
            val cacheFootStatus = globalMap["passStFtrstUpwdDnwdMovmtVirtCtrlAvl"]
            if(footStatus != cacheFootStatus){
                val cacheFootConf = globalMap["passStFtUpwdDnwdMovConfig"] as Boolean
                val cacheFootPos = globalMap["passStFtUpwdDnwdPos"] as Int
                buildMessageAndPublish(cacheFootPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_FOOTREST, cacheFootConf, footStatus)
                globalMap["passStFtrstUpwdDnwdMovmtVirtCtrlAvl"] = footStatus
            }

            val hdRestUpStatus = resp.outPut.passStHdrstUpwdDnwdMovmtVirtCtrlAvl
            val cacheHdUpRestStatus = globalMap["passStHdrstUpwdDnwdMovmtVirtCtrlAvl"]
            if(hdRestUpStatus != cacheHdUpRestStatus){
                val cacheHdConf = globalMap["passStHdrstUpwdDnwdMovConfig"] as Boolean
                val cacheStPos = globalMap["passStHdrstUpwdDnwdPos"] as Int
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_HEADREST, cacheHdConf, hdRestUpStatus)
                globalMap["passStHdrstUpwdDnwdMovmtVirtCtrlAvl"] = hdRestUpStatus
            }

            val hdRestStatus = resp.outPut.passStHdrstFwdBkwdMovmtVirtCtrlAvl
            val cacheHdRestStatus = globalMap["passStHdrstFwdBkwdMovmtVirtCtrlAvl"]
            if(hdRestStatus != cacheHdRestStatus){
                Log.i(TAG, "Headrest forward status is changed.")
                //todo Head rest forward
//                val cacheHdConf = globalMap["passStHdrstfwdBkwdMovConfig"] as Boolean               // currently, no existing
//                val cacheStPos = globalMap["passStHdrstFwdBkwdPos"] as Int                          // currently, no existing
//                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_HEADREST, cacheHdConf, hdRestUpStatus)
//                globalMap["passStHdrstFwdBkwdMovmtVirtCtrlAvl"] = hdRestUpStatus
            }
            val reclineStatus = resp.outPut.passStRclUpwdDnwdMovmtVirtCtrlAvl
            val cacheReclineStatus = globalMap["passStRclUpwdDnwdMovmtVirtCtrlAvl"]
            if(reclineStatus != cacheReclineStatus){
                val cacheStPos = globalMap["passStBkReclnUpwdDnwdPos"] as Int
                val cacheReclConf = globalMap["passStBkReclnUpwdDnwdMovConfig"] as Boolean
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_BACK, cacheReclConf, reclineStatus)
                globalMap["passStRclUpwdDnwdMovmtVirtCtrlAvl"] = reclineStatus
            }

            val cushionFrontStatus = resp.outPut.passStCushnFrtUpwdDnwdMovmtVirtCtrlAvl
            val cacheCushionFConf = globalMap["passStCushnFrtUpwdDnwdMovmtVirtCtrlAvl"]
            if(cushionFrontStatus != cacheCushionFConf){
                val cacheCushFrontConf = globalMap["passStCshnFrntUpwdDnwdMovConfig"] as Boolean
                val cacheStPos = globalMap["passStCshnFrntUpwdDnwdPos"] as Int
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_CUSHION_FRONT, cacheCushFrontConf, cushionFrontStatus)
                globalMap["passStCushnFrtUpwdDnwdMovmtVirtCtrlAvl"] = cushionFrontStatus
            }

            val cushionRStatus = resp.outPut.passStCushnRrUpwdDnwdMovmtVirtCtrlAvl
            val cacheCushionRStatus = globalMap["passStCushnRrUpwdDnwdMovmtVirtCtrlAvl"]
            if(cushionRStatus != cacheCushionRStatus){
                val cacheCushRearConf = globalMap["passStCshnRrUpwdDnwdMovConfig"] as Boolean
                val cacheStPos = globalMap["passStCshnRrUpwdDnwdPos"] as Int
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_CUSHION, cacheCushRearConf, cushionRStatus)
                globalMap["passStCushnRrUpwdDnwdMovmtVirtCtrlAvl"] = cushionRStatus
            }

        }

        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_ROW_LEFT_SEAT_VIRTUAL_CONTROL_AVAILABILITY_AND_NOTIFICATION_STATUS) {
            Log.i(TAG,"SECOND_ROW_LEFT_SEAT_VIRTUAL_CONTROL_AVAILABILITY_AND_NOTIFICATION_STATUS: Success")
            val resp = SomeipS2SManagementInterface.Second_Row_Left_Seat_Virtual_Control_Availability_And_Notification_StatusField.parseFrom(data.payload)
            val resource = ResourceMappingConstants.SECOND_LEFT_SEAT

            val cushionFStatus = resp.outPut.secRwLtStCushnFrtUpwdDnwdMovmtVirtCtrlAvl
            val cacheCushionFStatus = globalMap["secRwLtStCushnFrtUpwdDnwdMovmtVirtCtrlAvl"]
            if(cushionFStatus != cacheCushionFStatus){
                val cacheCushFrontConf = globalMap["secRwLtStCshnFrntUpwdDnwdMovConfig"] as Boolean
                val cacheStPos = globalMap["secRwLtStCshnFrntUpwdDnwdPos"] as Int
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_CUSHION_FRONT, cacheCushFrontConf, cushionFStatus)
                globalMap["secRwLtStCushnFrtUpwdDnwdMovmtVirtCtrlAvl"] = cushionFStatus
            }

            val seatStatus = resp.outPut.secRwLtStFwdBkwdMovmtVirtCtrlAvl
            val cacheSeatStatus = globalMap["secRwLtStFwdBkwdMovmtVirtCtrlAvl"]
            if(seatStatus != cacheSeatStatus){
                val cacheStForwardConf = globalMap["secRwLtStFrwdBkwdMovConfig"] as Boolean         // todo two directions
                    val cacheStPos = globalMap["secRwLtStFrwdBkwdPos"] as Int
                    buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_FORWARD, SeatComponent.SC_CUSHION, cacheStForwardConf, seatStatus)
                    globalMap["secRwLtStFwdBkwdMovmtVirtCtrlAvl"] = seatStatus
            }

            val seatLStatus = resp.outPut.secRwLtStLtwdRtwdMovmtVirtCtrlAvl
            val cacheSeatLStatus = globalMap["secRwLtStLtwdRtwdMovmtVirtCtrlAvl"]
            if(seatLStatus != cacheSeatLStatus){
                val cacheFootConf = globalMap["secRwLtStLtwdRtwdMovConfig"] as Boolean              // todo two directions
                val cacheFootPos = globalMap["secRwLtStLtwdRtwdPos"] as Int
                buildMessageAndPublish(cacheFootPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_FOOTREST, cacheFootConf, seatLStatus)
                globalMap["secRwLtStLtwdRtwdMovmtVirtCtrlAvl"] = seatLStatus
            }

            val hdRestUStatus = resp.outPut.secRwLtStHdrstUpwdDnwdMovmtVirtCtrlAvl
            val cacheHdRestStatus = globalMap["secRwLtStHdrstUpwdDnwdMovmtVirtCtrlAvl"]
            if(hdRestUStatus != cacheHdRestStatus){
                val cacheHdConf = globalMap["secRwLtStHdrstUpwdDnwdMovConfig"] as Boolean             // todo two directions
                val cacheStPos = globalMap["secRwLtStHdrstStUpwdDnwdPos"] as Int
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_HEADREST, cacheHdConf, hdRestUStatus)
                globalMap["secRwLtStHdrstUpwdDnwdMovmtVirtCtrlAvl"] = hdRestUStatus
            }
            val legRestUStatus = resp.outPut.secRwLtStLgrstUpwdDnwdMovmtVirtCtrlAvl
            val cacheLegRestUStatus = globalMap["secRwLtStLgrstUpwdDnwdMovmtVirtCtrlAvl"]
            if(legRestUStatus !=cacheLegRestUStatus){
                val cacheLgConf = globalMap["secRwLtStLgrstUpwdMovConfig"] as Boolean          // todo two directions
                val cacheStPos = globalMap["secRwLtStLgrstUpwdDnwdPos"] as Int
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_LEGREST, cacheLgConf, legRestUStatus)
                globalMap["secRwLtStLgrstUpwdDnwdMovmtVirtCtrlAvl"] = legRestUStatus
            }

            val ftRestUStatus = resp.outPut.secRwLtStFtrstUpwdDnwdMovmtVirtCtrlAvl
            val cacheFtRestUStatus = globalMap["secRwLtStFtrstUpwdDnwdMovmtVirtCtrlAvl"]
            if(ftRestUStatus != cacheFtRestUStatus){
                Log.i(TAG, "Second left foot rest upward status is changed.")
                val cacheLgConf = true
//                maybe the configuration is in driver component or second row component
//                val cacheLgConf = globalMap["secRwLtStLgrstUpwdMovConfig"] as Boolean          // check if the conf is in driver seat(todo two directions)
                val cacheStPos = globalMap["secRwLtStLgrstUpwdDnwdPos"] as Int
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_LEGREST, cacheLgConf, ftRestUStatus)
                globalMap["secRwLtStFtrstUpwdDnwdMovmtVirtCtrlAvl"] = ftRestUStatus
            }

            val hdRestStatus = resp.outPut.secRwLtStHdrstFwdBkwdMovmtVirtCtrlAvl
            val cacheHdRestFStatus = globalMap["secRwLtStHdrstFwdBkwdMovmtVirtCtrlAvl"]
            if(hdRestStatus != cacheHdRestFStatus){
                Log.i(TAG, "Second head rest forward status is changed.")
            }
            val lgRestStatus = resp.outPut.secRwLtStLgrstOtwdInwdMovmtVirtCtrlAvl
            val cacheLgRestStatus = globalMap["secRwLtStLgrstOtwdInwdMovmtVirtCtrlAvl"]
            if(lgRestStatus != cacheLgRestStatus){
                Log.i(TAG, "Second leg rest outward status is changed.")
            }
            val neckRestStatus = resp.outPut.secRwLtStNckrstFrwdBkwdMovmtVirtCtrlAvl
            val cacheNeckRestStatus = globalMap["secRwLtStNckrstFrwdBkwdMovmtVirtCtrlAvl"]
            if(neckRestStatus != cacheNeckRestStatus){
                Log.i(TAG, "Second leg rest outward status is changed.")
            }
            val reclineStatus = resp.outPut.secRwLtStRclUpwdDnwdMovmtVirtCtrlAvl
            val cacheReclineStatus = globalMap["secRwLtStRclUpwdDnwdMovmtVirtCtrlAvl"]
            if(reclineStatus != cacheReclineStatus){
                val cacheReclConf = globalMap["secRwLtStBkReclnUpwdDnwdMovConfig"] as Boolean
                val cacheStPos = globalMap["secRwLtStBkReclnUpwdDnwdPos"] as Int
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_BACK, cacheReclConf, reclineStatus)
                globalMap["secRwLtStRclUpwdDnwdMovmtVirtCtrlAvl"] = reclineStatus
            }

            val armStatus = resp.outPut.secRwLtStArmScrnUpwdDnwdMovmtVirtCtrlAvl
            val cacheArmStatus = globalMap["secRwLtStArmScrnUpwdDnwdMovmtVirtCtrlAvl"]
            if(armStatus != cacheArmStatus){
                Log.i(TAG, "Second arm rest upward status is changed.")
            }

            val cushionRStatus = resp.outPut.secRwLtStCushnRrUpwdDnwdMovmtVirtCtrlAvl
            val cacheCushionRStatus = globalMap["secRwLtStCushnRrUpwdDnwdMovmtVirtCtrlAvl"]
            if(cushionRStatus != cacheCushionRStatus){
                val cacheCushRearConf = globalMap["secRwLtStCshnRrUpwdDnwdMovConfig"] as Boolean
                val cacheStPos = globalMap["secRwLtStCshnRrUpwdDnwdPos"] as Int
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_CUSHION, cacheCushRearConf, cushionRStatus)
                globalMap["secRwLtStCushnRrUpwdDnwdMovmtVirtCtrlAvl"] = cushionRStatus
            }
        }

        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_ROW_RIGHT_SEAT_VIRTUAL_CONTROL_AVAILABILITY_AND_NOTIFICATION_STATUS) {
            Log.i(TAG,"SECOND_ROW_RIGHT_SEAT_VIRTUAL_CONTROL_AVAILABILITY_AND_NOTIFICATION_STATUS: Success")
            val resp = SomeipS2SManagementInterface.Second_Row_Right_Seat_Virtual_Control_Availability_And_Notification_StatusField.parseFrom(data.payload)
            val resource = ResourceMappingConstants.SECOND_RIGHT_SEAT

            val cushionFStatus = resp.outPut.secRwRtStCushnFrtUpwdDnwdMovmtVirtCtrlAvl
            val cacheCushionFStatus = globalMap["secRwRtStCushnFrtUpwdDnwdMovmtVirtCtrlAvl"]
            if(cushionFStatus != cacheCushionFStatus){
                val cacheCushFrontConf = globalMap["secRwRtStCshnFrntUpwdDnwdMovConfig"] as Boolean
                val cacheStPos = globalMap["secRwRtStCshnFrntUpwdDnwdPos"] as Int
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_CUSHION_FRONT, cacheCushFrontConf, cushionFStatus)
                globalMap["secRwRtStCushnFrtUpwdDnwdMovmtVirtCtrlAvl"] = cushionFStatus
            }

            val seatStatus = resp.outPut.secRwRtStFwdBkwdMovmtVirtCtrlAvl
            val cacheSeatStatus = globalMap["secRwRtStFwdBkwdMovmtVirtCtrlAvl"]
            if(seatStatus != cacheSeatStatus){
                val cacheStForwardConf = globalMap["secRwRtStFrwdBkwdMovConfig"] as Boolean         // todo two directions
                val cacheStPos = globalMap["secRwRtStFrwdBkwdPos"] as Int
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_FORWARD, SeatComponent.SC_CUSHION, cacheStForwardConf, seatStatus)
                globalMap["secRwRtStFwdBkwdMovmtVirtCtrlAvl"] = seatStatus
            }

            val seatLStatus = resp.outPut.secRwRtStLtwdRtwdMovmtVirtCtrlAvl
            val cacheSeatLStatus = globalMap["secRwRtStLtwdRtwdMovmtVirtCtrlAvl"]
            if(seatLStatus != cacheSeatLStatus){
                val cacheFootConf = globalMap["secRwRtStLtwdRtwdMovConfig"] as Boolean              // todo two directions
                val cacheFootPos = globalMap["secRwRtStLtwdRtwdPos"] as Int
                buildMessageAndPublish(cacheFootPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_FOOTREST, cacheFootConf, seatLStatus)
                globalMap["secRwRtStLtwdRtwdMovmtVirtCtrlAvl"] = seatLStatus
            }

            val hdRestUStatus = resp.outPut.secRwRtStHdrstUpwdDnwdMovmtVirtCtrlAvl
            val cacheHdRestStatus = globalMap["secRwRtStHdrstUpwdDnwdMovmtVirtCtrlAvl"]
            if(hdRestUStatus != cacheHdRestStatus){
                val cacheHdConf = globalMap["secRwRtStHdrstUpwdDnwdMovConfig"] as Boolean             // todo two directions
                val cacheStPos = globalMap["secRwRtStHdrstStUpwdDnwdPos"] as Int
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_HEADREST, cacheHdConf, hdRestUStatus)
                globalMap["secRwRtStHdrstUpwdDnwdMovmtVirtCtrlAvl"] = hdRestUStatus
            }
            val legRestUStatus = resp.outPut.secRwRtStLgrstUpwdDnwdMovmtVirtCtrlAvl
            val cacheLegRestUStatus = globalMap["secRwRtStLgrstUpwdDnwdMovmtVirtCtrlAvl"]
            if(legRestUStatus !=cacheLegRestUStatus){
                val cacheLgConf = globalMap["secRwRtStLgrstUpwdMovConfig"] as Boolean          // todo two directions
                val cacheStPos = globalMap["secRwRtStLgrstUpwdDnwdPos"] as Int
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_LEGREST, cacheLgConf, legRestUStatus)
                globalMap["secRwRtStLgrstUpwdDnwdMovmtVirtCtrlAvl"] = legRestUStatus
            }

            val ftRestUStatus = resp.outPut.secRwRtStFtrstUpwdDnwdMovmtVirtCtrlAvl
            val cacheFtRestUStatus = globalMap["secRwRtStFtrstUpwdDnwdMovmtVirtCtrlAvl"]
            if(ftRestUStatus != cacheFtRestUStatus){
                Log.i(TAG, "Second left foot rest upward status is changed.")
                val cacheLgConf = true
//                maybe the configuration is in driver component or second row component
//                val cacheLgConf = globalMap["secRwRtStLgrstUpwdMovConfig"] as Boolean          // check if the conf is in driver seat(todo two directions)
                val cacheStPos = globalMap["secRwRtStLgrstUpwdDnwdPos"] as Int
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_LEGREST, cacheLgConf, ftRestUStatus)
                globalMap["secRwRtStFtrstUpwdDnwdMovmtVirtCtrlAvl"] = ftRestUStatus
            }

            val hdRestStatus = resp.outPut.secRwRtStHdrstFwdBkwdMovmtVirtCtrlAvl
            val cacheHdRestFStatus = globalMap["secRwRtStHdrstFwdBkwdMovmtVirtCtrlAvl"]
            if(hdRestStatus != cacheHdRestFStatus){
                Log.i(TAG, "Second head rest forward status is changed.")
            }
            val lgRestStatus = resp.outPut.secRwRtStLgrstOtwdInwdMovmtVirtCtrlAvl
            val cacheLgRestStatus = globalMap["secRwRtStLgrstOtwdInwdMovmtVirtCtrlAvl"]
            if(lgRestStatus != cacheLgRestStatus){
                Log.i(TAG, "Second leg rest outward status is changed.")
            }
            val neckRestStatus = resp.outPut.secRwRtStNckrstFrwdBkwdMovmtVirtCtrlAvl
            val cacheNeckRestStatus = globalMap["secRwRtStNckrstFrwdBkwdMovmtVirtCtrlAvl"]
            if(neckRestStatus != cacheNeckRestStatus){
                Log.i(TAG, "Second leg rest outward status is changed.")
            }
            val reclineStatus = resp.outPut.secRwRtStRclUpwdDnwdMovmtVirtCtrlAvl
            val cacheReclineStatus = globalMap["secRwRtStRclUpwdDnwdMovmtVirtCtrlAvl"]
            if(reclineStatus != cacheReclineStatus){
                val cacheReclConf = globalMap["secRwRtStBkReclnUpwdDnwdMovConfig"] as Boolean
                val cacheStPos = globalMap["secRwRtStBkReclnUpwdDnwdPos"] as Int
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_BACK, cacheReclConf, reclineStatus)
                globalMap["secRwRtStRclUpwdDnwdMovmtVirtCtrlAvl"] = reclineStatus
            }

            val armStatus = resp.outPut.secRwRtStArmScrnUpwdDnwdMovmtVirtCtrlAvl
            val cacheArmStatus = globalMap["secRwRtStArmScrnUpwdDnwdMovmtVirtCtrlAvl"]
            if(armStatus != cacheArmStatus){
                Log.i(TAG, "Second arm rest upward status is changed.")
            }

            val cushionRStatus = resp.outPut.secRwRtStCushnRrUpwdDnwdMovmtVirtCtrlAvl
            val cacheCushionRStatus = globalMap["secRwRtStCushnRrUpwdDnwdMovmtVirtCtrlAvl"]
            if(cushionRStatus != cacheCushionRStatus){
                val cacheCushRearConf = globalMap["secRwRtStCshnRrUpwdDnwdMovConfig"] as Boolean
                val cacheStPos = globalMap["secRwRtStCshnRrUpwdDnwdPos"] as Int
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_CUSHION, cacheCushRearConf, cushionRStatus)
                globalMap["secRwRtStCushnRrUpwdDnwdMovmtVirtCtrlAvl"] = cushionRStatus
            }
        }

        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_THIRD_ROW_LEFT_SEAT_VIRTUAL_CONTROL_AVAILABILITY_AND_NOTIFICATION_STATUS) {
            Log.i(TAG,"THIRD_ROW_LEFT_SEAT_VIRTUAL_CONTROL_AVAILABILITY_AND_NOTIFICATION_STATUS: Success")
            val resp = SomeipS2SManagementInterface.Third_Row_Left_Seat_Virtual_Control_Availability_And_Notification_StatusField.parseFrom(data.payload)
            val resource = ResourceMappingConstants.THIRD_LEFT_SEAT

            val seatForwardStatus = resp.outPut.thdRwLtStFwdBkwdMovmtVirtCtrlAvl
            val cacheStForwardStatus = globalMap["thdRwLtStFwdBkwdMovmtVirtCtrlAvl"]
            if(seatForwardStatus != cacheStForwardStatus){
                val cacheStForwardConf = globalMap["thdRwLtStFrwdBkwdMovConfig"] as Boolean
                val cacheStPos = globalMap["thdRwLtStFrwdBkwdPos"] as Int
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_FORWARD, SeatComponent.SC_CUSHION, cacheStForwardConf, seatForwardStatus)
                globalMap["thdRwLtStFwdBkwdMovmtVirtCtrlAvl"] = seatForwardStatus
            }
            val cushionFldStatus = resp.outPut.thdRwLtStCushnFldMovmtVirtCtrlAvl
            val cacheCushionFldStatus = globalMap["thdRwLtStCushnFldMovmtVirtCtrlAvl"]
            if(cushionFldStatus != cacheCushionFldStatus){
                Log.i(TAG,"Third left cushion fold status is changed")
            }

            val seatLStatus = resp.outPut.thdRwLtStLtwdRtwdMovmtVirtCtrlAvl
            val cacheStLStatus = globalMap["thdRwLtStLtwdRtwdMovmtVirtCtrlAvl"]
            if(seatLStatus != cacheStLStatus){
                val cacheFootConf = globalMap["thdRwRtStLtwdRtwdMovConfig"] as Boolean
                val cacheFootPos = globalMap["thdRwRtStLtwdRtwdPos"] as Int
                buildMessageAndPublish(cacheFootPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_FOOTREST, cacheFootConf, seatLStatus)
                globalMap["thdRwLtStLtwdRtwdMovmtVirtCtrlAvl"] = cacheFootConf
            }

            val reclineStatus = resp.outPut.thdRwLtStRclUpwdDnwdMovmtVirtCtrlAvl
            val cacheReclineStatus = globalMap["thdRwLtStRclUpwdDnwdMovmtVirtCtrlAvl"]
            if(reclineStatus != cacheReclineStatus){
                val cacheReclConf = globalMap["thdRwLtStBkReclnUpwdDnwdMovConfig"] as Boolean
                val cacheStPos = globalMap["thdRwLtStBkReclnUpwdDnwdPos"] as Int
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_BACK, cacheReclConf, reclineStatus)
                globalMap["thdRwLtStRclUpwdDnwdMovmtVirtCtrlAvl"] = reclineStatus
            }
            val cushionFStatus = resp.outPut.thdRwLtStCushnFrtUpwdDnwdMovmtVirtCtrlAvl
            val cacheCushionFStatus = globalMap["thdRwLtStCushnFrtUpwdDnwdMovmtVirtCtrlAvl"]
            if(cushionFStatus != cacheCushionFStatus){
                Log.i(TAG,"Third left cushion front status is changed")
            }

            val cushionRStatus = resp.outPut.thdRwLtStCushnRrUpwdDnwdMovmtVirtCtrlAvl
            val cacheCushionRStatus = globalMap["thdRwLtStCushnRrUpwdDnwdMovmtVirtCtrlAvl"]
            if(cushionRStatus != cacheCushionRStatus){
                Log.i(TAG,"Third left cushion front status is changed")
            }

        }

        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_THIRD_ROW_RIGHT_SEAT_VIRTUAL_CONTROL_AVAILABILITY_AND_NOTIFICATION_STATUS) {
            Log.i(TAG,"THIRD_ROW_RIGHT_SEAT_VIRTUAL_CONTROL_AVAILABILITY_AND_NOTIFICATION_STATUS: Success")
            val resp = SomeipS2SManagementInterface.Third_Row_Right_Seat_Virtual_Control_Availability_And_Notification_StatusField.parseFrom(data.payload)
            val resource = ResourceMappingConstants.THIRD_RIGHT_SEAT

            val seatForwardStatus = resp.outPut.thdRwRtStFwdBkwdMovmtVirtCtrlAvl
            val cacheStForwardStatus = globalMap["thdRwRtStFwdBkwdMovmtVirtCtrlAvl"]
            if(seatForwardStatus != cacheStForwardStatus){
                val cacheStForwardConf = globalMap["thdRwRtStFrwdBkwdMovConfig"] as Boolean
                val cacheStPos = globalMap["thdRwRtStFrwdBkwdPos"] as Int
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_FORWARD, SeatComponent.SC_CUSHION, cacheStForwardConf, seatForwardStatus)
                globalMap["thdRwRtStFwdBkwdMovmtVirtCtrlAvl"] = seatForwardStatus
            }
            val cushionFldStatus = resp.outPut.thdRwRtStCushnFldMovmtVirtCtrlAvl
            val cacheCushionFldStatus = globalMap["thdRwRtStCushnFldMovmtVirtCtrlAvl"]
            if(cushionFldStatus != cacheCushionFldStatus){
                Log.i(TAG,"Third left cushion fold status is changed")
            }

            val seatLStatus = resp.outPut.thdRwRtStLtwdRtwdMovmtVirtCtrlAvl
            val cacheStLStatus = globalMap["thdRwRtStLtwdRtwdMovmtVirtCtrlAvl"]
            if(seatLStatus != cacheStLStatus){
                val cacheFootConf = globalMap["thdRwRtStLtwdRtwdMovConfig"] as Boolean
                val cacheFootPos = globalMap["thdRwRtStLtwdRtwdPos"] as Int
                buildMessageAndPublish(cacheFootPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_FOOTREST, cacheFootConf, seatLStatus)
                globalMap["thdRwRtStLtwdRtwdMovmtVirtCtrlAvl"] = cacheFootConf
            }

            val reclineStatus = resp.outPut.thdRwRtStRclUpwdDnwdMovmtVirtCtrlAvl
            val cacheReclineStatus = globalMap["thdRwRtStRclUpwdDnwdMovmtVirtCtrlAvl"]
            if(reclineStatus != cacheReclineStatus){
                val cacheReclConf = globalMap["thdRwRtStBkReclnUpwdDnwdMovConfig"] as Boolean
                val cacheStPos = globalMap["thdRwRtStBkReclnUpwdDnwdPos"] as Int
                buildMessageAndPublish(cacheStPos, resource, UpdateSeatPositionRequest.Direction.D_UP, SeatComponent.SC_BACK, cacheReclConf, reclineStatus)
                globalMap["thdRwRtStRclUpwdDnwdMovmtVirtCtrlAvl"] = reclineStatus
            }
            val cushionFStatus = resp.outPut.thdRwRtStCushnFrtUpwdDnwdMovmtVirtCtrlAvl
            val cacheCushionFStatus = globalMap["thdRwRtStCushnFrtUpwdDnwdMovmtVirtCtrlAvl"]
            if(cushionFStatus != cacheCushionFStatus){
                Log.i(TAG,"Third left cushion front status is changed")
            }

            val cushionRStatus = resp.outPut.thdRwRtStCushnRrUpwdDnwdMovmtVirtCtrlAvl
            val cacheCushionRStatus = globalMap["thdRwRtStCushnRrUpwdDnwdMovmtVirtCtrlAvl"]
            if(cushionRStatus != cacheCushionRStatus){
                Log.i(TAG,"Third left cushion front status is changed")
            }
        }

//        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_ROW_COMFORT_MODE_REQUEST_INFO) {
//            Log.i(TAG,"S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_ROW_COMFORT_MODE_REQUEST_INFO: Success")
//        }
//
//        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_ROW_LEFT_COMFORT_MODE_REQUEST_HMI) {
//            Log.i(TAG,"S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_ROW_LEFT_COMFORT_MODE_REQUEST_HMI: Success")
//        }
//
//        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_ROW_RIGHT_COMFORT_MODE_REQUEST_HMI) {
//            Log.i(TAG,"SECOND_ROW_RIGHT_COMFORT_MODE_REQUEST_HMI: Success")
//        }
    }

    private fun buildMessageAndPublish(cacheStPos: Int, resource: String, direction: UpdateSeatPositionRequest.Direction, comp: SeatComponent, config:Boolean, status:Boolean) {
        val supported = if(config) direction else null
        val available = if(status) direction else null
        val builder = newBuilder()
        val dirPosSeat = buildDirectionPos(direction, cacheStPos)
        val comBuilder = SeatComponentPosition.newBuilder().setComponent(comp).setDirectionPositions(0, dirPosSeat)

        if (supported !=null){
            comBuilder.setSupportedSeatComponentPercentageDirections(0, supported)
        }
        if(available!=null){
            comBuilder.setAvailableSeatComponentPercentageDirections(0, available)
        }
        val component = comBuilder.build()
        val seat = builder.setName(resource)
            .setSeatComponentPositions(0, component).build()
        val uResource = UResource(resource, "", SeatPosition::class.java.simpleName)
        SomeIpUtil.pubEvent(
            seat,
            ServiceConstant.SEATING_SERVICE,
            uResource,
            SeatingService.mLaunchManager.getmUltifiLinkMonitor()
        )
    }


    fun buildDirectionPos(direction: UpdateSeatPositionRequest.Direction, pos: Int): SeatPosition.DirectionPosition{
        return SeatPosition.DirectionPosition.newBuilder()
            .setDirection(direction)
            .setPercentagePosition(pos)
            .build()
    }

    fun publishEvent(topic: String, message:GeneratedMessageV3){
        val uResource = UResource(topic, "", SeatPosition::class.java.simpleName)
        SomeIpUtil.pubEvent(message, ServiceConstant.SEATING_SERVICE, uResource, SeatingService.mLaunchManager.getmUltifiLinkMonitor())
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

    // fun getDriverFootStatus():Boolean{}

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


    fun getTest(): Boolean?{
        val resp = getPositionMessage(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_DRIVER_SEAT_CONFIGURATION)
        if(resp != null){
            val tmp =  SomeipS2SManagementInterface.Driver_Seat_ConfigurationField.parseFrom(resp.payload)
            return tmp.outPut.drvStFtUpwdDnwdMovConfig
        }
        return null
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


    //region Passenger seat configuration/status
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
            return config.outPut.secRwLtStCshnRrUpwdDnwdMovConfig
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
            return available.outPut.secRwRtStCushnFrtUpwdDnwdMovmtVirtCtrlAvl
        }
        return false
    }
    fun getSecRightCushionRearStatus():Boolean{
        val available = getSecRightSeatStatus()
        if(available!=null){
            return available.outPut.secRwRtStCushnRrUpwdDnwdMovmtVirtCtrlAvl
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


    //region Seat Massage
    //set
    fun setPassSeatMassageReq(mode:Int, intensity:Int) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Second left seat service enable: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Passenger_Seat_Virtual_Control_RequestField.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Passenger_Seat_Virtual_Control_Request.newBuilder()
                .setPassStMassMdVirtCtrlReq(mode)
                .setPassStMassStrsVirtCtrlReq(intensity))
                .build()
        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_PASSENGER_SEAT_VIRTUAL_CONTROL_REQUEST)
    }

    fun setSecLeftSeatMassageReq(mode:Int, intensity:Int) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Second left seat service enable: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Second_Row_Left_Seat_Virtual_Control_RequestField.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Second_Row_Left_Seat_Virtual_Control_Request.newBuilder()
                .setSecRwLtStMassMdVirtCtrlReq(mode)
                .setSecRwLtStMassStrsVirtCtrlReq(intensity))
            .build()
        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_SECOND_ROW_LEFT_SEAT_VIRTUAL_CONTROL_REQUEST)
    }

    fun setSecRightSeatMassageReq(mode:Int, intensity:Int) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Second left seat service enable: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Second_Row_Right_Seat_Virtual_Control_RequestField.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Second_Row_Right_Seat_Virtual_Control_Request.newBuilder()
                .setSecRwRtStMassMdVirtCtrlReq(mode)
                .setSecRwRtStMassStrsVirtCtrlReq(intensity))
            .build()
        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_SECOND_ROW_RIGHT_SEAT_VIRTUAL_CONTROL_REQUEST)
    }

    fun setThdLeftSeatMassageReq(mode:Int, intensity:Int) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Second left seat service enable: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Third_Row_Left_Seat_Virtual_Control_Request_ServiceField.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Third_Row_Left_Seat_Virtual_Control_Request_Service.newBuilder()
                .setThdRwLtStMassMdVirtCtrlReq(mode)
                .setThdRwLtStMassStrsVirtCtrlReq(intensity))
            .build()
        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_THIRD_ROW_LEFT_SEAT_VIRTUAL_CONTROL_REQUEST_SERVICE)
    }

    fun setThdRightSeatMassageReq(mode:Int, intensity:Int) :Boolean{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG,"Second left seat service enable: failed, server is not available or client is not ready")
            return false
        }
        val newRecall = SomeipS2SManagementInterface.Third_Row_Right_Seat_Virtual_Control_Request_ServiceField.newBuilder()
            .setOutPut(SomeipS2SManagementInterface.Third_Row_Right_Seat_Virtual_Control_Request_Service.newBuilder()
                .setThdRwRtStMassMdVirtCtrlReq(mode)
                .setThdRwRtStMassStrsVirtCtrlReq(intensity))
            .build()
        return generateSendStatus(newRecall, SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_THIRD_ROW_RIGHT_SEAT_VIRTUAL_CONTROL_REQUEST_SERVICE)
    }

    //get status
    fun getPassSeatMassStatus():Boolean?{
        val resp = SomeIpData()
        val res = someIpClientProxy?.getAttribute(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_PASSENGER_SEAT_VIRTUAL_CONTROL_AVAILABILITY_AND_NOTIFICATION_STATUS, resp)
        if(res == ResultValue.OK){
//            return SomeipS2SManagementInterface.Passenger_Seat_Virtual_Control_Availability_And_Notification_StatusField.parseFrom(resp.payload)
            val tmp = SomeipS2SManagementInterface.Passenger_Seat_Virtual_Control_Availability_And_Notification_StatusField.parseFrom(resp.payload)
            return tmp.outPut.passStMassVirtCtrlAvl
        }
        return null
    }

    fun getPassSeatMassConf():Boolean{
        val config = getPassSeatConfig()
        if(config != null){
            return config.outPut.passStMassConfig
        }
        return false
    }
    fun getSecLeftSeatMassStatus():Boolean?{
        val resp = SomeIpData()
        val res = someIpClientProxy?.getAttribute(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SECOND_ROW_LEFT_SEAT_VIRTUAL_CONTROL_AVAILABILITY_AND_NOTIFICATION_STATUS, resp)
        if(res == ResultValue.OK){
            val tmp = SomeipS2SManagementInterface.Second_Row_Left_Seat_Virtual_Control_Availability_And_Notification_StatusField.parseFrom(resp.payload)
            return tmp.outPut.secRwLtStMassVirtCtrlAvl
        }
        return null
    }
    fun getSecLeftSeatMassConf():Boolean{
        val config = getSecondLeftSeatConfig()
        if(config != null){
            return config.outPut.secRwLtStMassConfig
        }
        return false
    }

    fun getSecRightSeatMassStatus():Boolean?{
        val resp = SomeIpData()
        val res = someIpClientProxy?.getAttribute(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SECOND_ROW_RIGHT_SEAT_VIRTUAL_CONTROL_AVAILABILITY_AND_NOTIFICATION_STATUS, resp)
        if(res == ResultValue.OK){
            val tmp = SomeipS2SManagementInterface.Second_Row_Right_Seat_Virtual_Control_Availability_And_Notification_StatusField.parseFrom(resp.payload)
            return tmp.outPut.secRwRtStMassVirtCtrlAvl
        }
        return null
    }
    fun getSecRightSeatMassConf():Boolean{
        val config = getSecRightSeatConfig()
        if(config != null){
            return config.outPut.secRwRtStMassConfig
        }
        return false
    }

    fun getThdLeftSeatMassStatus():Boolean?{
        val resp = SomeIpData()
        val res = someIpClientProxy?.getAttribute(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_THIRD_ROW_LEFT_SEAT_VIRTUAL_CONTROL_AVAILABILITY_AND_NOTIFICATION_STATUS, resp)
        if(res == ResultValue.OK){
            val tmp = SomeipS2SManagementInterface.Third_Row_Left_Seat_Virtual_Control_Availability_And_Notification_StatusField.parseFrom(resp.payload)
            return tmp.outPut.thdRwLtStMassVirtCtrlAvl
        }
        return null
    }

    fun getThdLeftSeatMassConf():Boolean{
        val config = getThirdLeftSeatConfig()
        if(config != null){
            return config.outPut.thdRwLtStMassConfig
        }
        return false
    }

    fun getThdRightSeatMassStatus():Boolean?{
        val resp = SomeIpData()
        val res = someIpClientProxy?.getAttribute(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_THIRD_ROW_RIGHT_SEAT_VIRTUAL_CONTROL_AVAILABILITY_AND_NOTIFICATION_STATUS, resp)
        if(res == ResultValue.OK){
            val tmp = SomeipS2SManagementInterface.Third_Row_Right_Seat_Virtual_Control_Availability_And_Notification_StatusField.parseFrom(resp.payload)
            return tmp.outPut.thdRwRtStMassVirtCtrlAvl
        }
        return null
    }
    fun getThdRightSeatMassConf():Boolean{
        val config = getThirdRightSeatConfig()
        if(config != null){
            return config.outPut.thdRwRtStMassConfig
        }
        return false
    }

    //endregion


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