package com.gm.ultifi.service.access.someip

import android.util.Log
import com.gm.ultifi.base.someip.BaseAppViewModel
import plugin.SomeIpTopic
import plugin.SomeipS2SManagementInterface
import ts.car.someip.sdk.common.ResultValue
import ts.car.someip.sdk.common.SomeIpData

class SeatingViewModel : BaseAppViewModel() {
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

        // notify subscriber the change of sunroof status
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_DRIVER_SEAT_PERCENTAGE_POSITION_1) {
            Log.i(TAG, "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_DRIVER_SEAT_PERCENTAGE_POSITION_1: Success")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_DRIVER_SEAT_PERCENTAGE_POSITION_2) {
            Log.i(TAG, "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_DRIVER_SEAT_PERCENTAGE_POSITION_2: Success")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_DRIVER_SEAT_PERCENTAGE_POSITION_3) {
            Log.i(TAG, "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_DRIVER_SEAT_PERCENTAGE_POSITION_3: Success")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_PASSENGER_SEAT_PERCENTAGE_POSITION_1) {
            Log.i(TAG, "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_PASSENGER_SEAT_PERCENTAGE_POSITION_1: Success")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_PASSENGER_SEAT_PERCENTAGE_POSITION_2) {
            Log.i(TAG, "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_PASSENGER_SEAT_PERCENTAGE_POSITION_2: Success")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_LEFT_SEAT_PERCENTAGE_POSITION_1) {
            Log.i(TAG, "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_LEFT_SEAT_PERCENTAGE_POSITION_1: Success")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_LEFT_SEAT_PERCENTAGE_POSITION_2) {
            Log.i(TAG, "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_LEFT_SEAT_PERCENTAGE_POSITION_2: Success")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_RIGHT_SEAT_PERCENTAGE_POSITION_1){
            Log.i(TAG, "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_RIGHT_SEAT_PERCENTAGE_POSITION_1: Success")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_RIGHT_SEAT_PERCENTAGE_POSITION_2){
            Log.i(TAG, "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_RIGHT_SEAT_PERCENTAGE_POSITION_2: Success")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_THIRD_RIGHT_SEAT_PERCENTAGE_POSITION){
            Log.i(TAG, "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_THIRD_RIGHT_SEAT_PERCENTAGE_POSITION: Success")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_THIRD_LEFT_SEAT_PERCENTAGE_POSITION){
            Log.i(TAG, "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_THIRD_LEFT_SEAT_PERCENTAGE_POSITION: Success")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_DRIVER_SEAT_CONFIGURATION){
            Log.i(TAG, "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_DRIVER_SEAT_CONFIGURATION: Success")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_PASSENGER_SEAT_CONFIGURATION){
            Log.i(TAG, "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_PASSENGER_SEAT_CONFIGURATION: Success")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_LEFT_SEAT_CONFIGURATION){
            Log.i(TAG, "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_LEFT_SEAT_CONFIGURATION: Success")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_RIGHT_SEAT_CONFIGURATION){
            Log.i(TAG, "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_RIGHT_SEAT_CONFIGURATION: Success")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_THIRD_LEFT_SEAT_CONFIGURATION) {
            Log.i(TAG, "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_THIRD_LEFT_SEAT_CONFIGURATION: Success")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_THIRD_RIGHT_SEAT_CONFIGURATION){
            Log.i(TAG, "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_THIRD_RIGHT_SEAT_CONFIGURATION: Success")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_MODE_STATUS){
            Log.i(TAG, "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_MODE_STATUS: Success")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_PASSENGER_COMPARTMENT_MODE_SERVICE_AVAILABILITY_STATUS){
            Log.i(TAG, "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_PASSENGER_COMPARTMENT_MODE_SERVICE_AVAILABILITY_STATUS: Success")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_PASSENGER_COMPARTMENT_MODE_SERVICE_RESPONSE_STATUS){
            Log.i(TAG, "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_PASSENGER_COMPARTMENT_MODE_SERVICE_RESPONSE_STATUS: Success")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_PASSENGER_COMPARTMENT_MODE_SERVICE_RESPONSE_STATUS_2){
            Log.i(TAG, "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_PASSENGER_COMPARTMENT_MODE_SERVICE_RESPONSE_STATUS_2: Success")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_MODE_STATUS_2){
            Log.i(TAG, "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_MODE_STATUS_2: Success")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_PASSENGER_COMPARTMENT_MODE_AVAILABILITY_AND_NOTIFICATION_STATUS_6){
            Log.i(TAG, "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SEAT_PASSENGER_COMPARTMENT_MODE_AVAILABILITY_AND_NOTIFICATION_STATUS_6: Success")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_DRIVER_SEAT_RECOVERY_POSITION){
            Log.i(TAG, "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_DRIVER_SEAT_RECOVERY_POSITION: Success")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_DRIVER_SEAT_RECOVERY_POSITION_2){
            Log.i(TAG, "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_DRIVER_SEAT_RECOVERY_POSITION_2: Success")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_ROW_COMFORT_MODE_REQUEST_INFO){
            Log.i(TAG, "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_ROW_COMFORT_MODE_REQUEST_INFO: Success")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_ROW_LEFT_COMFORT_MODE_REQUEST_HMI){
            Log.i(TAG, "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_ROW_LEFT_COMFORT_MODE_REQUEST_HMI: Success")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_ROW_RIGHT_COMFORT_MODE_REQUEST_HMI){
            Log.i(TAG, "S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SECOND_ROW_RIGHT_COMFORT_MODE_REQUEST_HMI: Success")
        }
    }

    fun setDriverSeatRecallReq_M1(){
        if (!isServerAvailable || !isReady) {
            Log.i(TAG, "Driver_Seat_Recall_Request_Service_37C_M1: failed, server is not available or client is not ready")
            return
        }
        val newRecall = SomeipS2SManagementInterface
            .Driver_Seat_Recall_Request_Service_37C_M1Field.newBuilder()
            .setOutPut(
                SomeipS2SManagementInterface.Driver_Seat_Recall_Request_Service_37C_M1.newBuilder()
                    .setDrvStRclReqSrv(isBoolean)
                    .setDrvStBlstOtwdInwdTrgtPosReqSrv(testVal)
            ).build()

        val req = SomeIpData(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_DRIVER_SEAT_RECALL_REQUEST_SERVICE_37C_M1,
            System.currentTimeMillis(),
            newRecall.toByteArray()
        )

        val resp = SomeIpData()

        val res = someIpClientProxy?.setAttribute(req, resp)
        Log.i(TAG, "sendReq2Server: Driver_Seat_Recall_Request_Service_37C_M1.")

        if (res == ResultValue.OK) {
            Log.i(TAG, "Driver_Seat_Recall_Request_Service_37C_M1: Response OK")
            return
        }

        Log.i(TAG, "Driver_Seat_Recall_Request_Service_37C_M1: FAILED")

    }
    fun setDriverSeatRecallReq_M2(){
        if (!isServerAvailable || !isReady) {
            Log.i(TAG, "Driver_Seat_Recall_Request_Service_37C_M2: failed, server is not available or client is not ready")
            return
        }
        val newRecall = SomeipS2SManagementInterface
            .Driver_Seat_Recall_Request_Service_37C_M2Field.newBuilder()
            .setOutPut(
                SomeipS2SManagementInterface.Driver_Seat_Recall_Request_Service_37C_M2.newBuilder()
                    .setDrvStHdrstUpwdDnwdTrgtPosReqSrv(testVal)
                    .setDrvStLgrstUpwdDnwdTrgtPosReqSrv(testVal)
            ).build()

        val req = SomeIpData(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_DRIVER_SEAT_RECALL_REQUEST_SERVICE_37C_M2,
            System.currentTimeMillis(),
            newRecall.toByteArray()
        )

        val resp = SomeIpData()

        val res = someIpClientProxy?.setAttribute(req, resp)
        Log.i(TAG, "sendReq2Server: Driver_Seat_Recall_Request_Service_37C_M2.")

        if (res == ResultValue.OK) {
            Log.i(TAG, "Driver_Seat_Recall_Request_Service_37C_M2: Response OK")
            return
        }

        Log.i(TAG, "Driver_Seat_Recall_Request_Service_37C_M2: FAILED")

    }
    fun setDriverSeatRecallReq_M3(){
        if (!isServerAvailable || !isReady) {
            Log.i(TAG, "Driver_Seat_Recall_Request_Service_3AB_M3Field: failed, server is not available or client is not ready")
            return
        }
        val newRecall = SomeipS2SManagementInterface
            .Driver_Seat_Recall_Request_Service_3AB_M3Field.newBuilder()
            .setOutPut(
                SomeipS2SManagementInterface.Driver_Seat_Recall_Request_Service_3AB_M3.newBuilder()
                    .setDrvStBkReclnUpwdDnwdTrgtPosReqSrv(testVal)
                    .setDrvStFrwdBkwdTrgtPosReqSrv(testVal)
            ).build()

        val req = SomeIpData(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_DRIVER_SEAT_RECALL_REQUEST_SERVICE_3AB_M3,
            System.currentTimeMillis(),
            newRecall.toByteArray()
        )

        val resp = SomeIpData()

        val res = someIpClientProxy?.setAttribute(req, resp)
        Log.i(TAG, "sendReq2Server: Driver_Seat_Recall_Request_Service_3AB_M3Field.")

        if (res == ResultValue.OK) {
            Log.i(TAG, "Driver_Seat_Recall_Request_Service_3AB_M3Field: Response OK")
            return
        }

        Log.i(TAG, "Driver_Seat_Recall_Request_Service_3AB_M3Field: FAILED")

    }
    fun setDriverSeatRecallReq_M4(){
        if (!isServerAvailable || !isReady) {
            Log.i(TAG, "Driver_Seat_Recall_Request_Service_3AC_M4Field: failed, server is not available or client is not ready")
            return
        }
        val newRecall = SomeipS2SManagementInterface
            .Driver_Seat_Recall_Request_Service_3AC_M4Field.newBuilder()
            .setOutPut(
                SomeipS2SManagementInterface.Driver_Seat_Recall_Request_Service_3AC_M4.newBuilder()
                    .setDrvStCshnFrntUpwdDnwdTrgtPosReqSrv(testVal)
                    .setDrvStCshnRrUpwdDnwdTrgtPosReqSrv(testVal)
            ).build()

        val req = SomeIpData(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_DRIVER_SEAT_RECALL_REQUEST_SERVICE_3AC_M4,
            System.currentTimeMillis(),
            newRecall.toByteArray()
        )

        val resp = SomeIpData()

        val res = someIpClientProxy?.setAttribute(req, resp)
        Log.i(TAG, "sendReq2Server: Driver_Seat_Recall_Request_Service_3AC_M4Field.")

        if (res == ResultValue.OK) {
            Log.i(TAG, "Driver_Seat_Recall_Request_Service_3AC_M4Field: Response OK")
            return
        }

        Log.i(TAG, "Driver_Seat_Recall_Request_Service_3AC_M4Field: FAILED")

    }

    fun setSeatModeReq(){
        if (!isServerAvailable || !isReady) {
            Log.i(TAG, "Seat_Mode_Control_Request: failed, server is not available or client is not ready")
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



    fun getDriverSeatPosition_1(): SomeipS2SManagementInterface.Driver_Seat_Percentage_Position_1Field?{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG, "get Driver_Seat_Percentage_Position_1Field: failed, server is not available or client is not ready")
            return null
        }

        val resp = SomeIpData()

        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SEAT_MODE_STATUS,
            resp
        )

        Log.i(TAG, "sendReq2Server: get Driver_Seat_Percentage_Position_1")
        if (res == ResultValue.OK) {
            Log.i(TAG, "sendReq2Server: Driver_Seat_Percentage_Position_1 OK")
//            val temp = SomeipS2SManagementInterface.Driver_Seat_Percentage_Position_1Field.parseFrom(resp.payload)
//            Log.i(TAG, "getSunroofPosition: " + temp.outPut.drvStFrwdBkwdPos)
//            return temp
        }

        Log.i(TAG, "FAILED: Driver_Seat_Percentage_Position_1")
        return null;
    }

    fun getDriverSeatPosition_2(): SomeipS2SManagementInterface.Driver_Seat_Percentage_Position_2Field?{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG, "get Driver_Seat_Percentage_Position_2Field: failed, server is not available or client is not ready")
            return null
        }

        val resp = SomeIpData()

        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SEAT_MODE_STATUS,
            resp
        )

        Log.i(TAG, "sendReq2Server: get Driver_Seat_Percentage_Position_2")
        if (res == ResultValue.OK) {
            Log.i(TAG, "sendReq2Server: Driver_Seat_Percentage_Position_2 OK")
//            val temp = SomeipS2SManagementInterface.Driver_Seat_Percentage_Position_2Field.parseFrom(resp.payload)
//            Log.i(TAG, "getSunroofPosition: " + temp.outPut.drvStFrwdBkwdPos)
//            return temp
        }

        Log.i(TAG, "FAILED: Driver_Seat_Percentage_Position_2")
        return null;
    }

    fun getDriverSeatPosition_3(): SomeipS2SManagementInterface.Driver_Seat_Percentage_Position_3Field?{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG, "get DriverSeatPosition: failed, server is not available or client is not ready")
            return null
        }

        val resp = SomeIpData()

        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SEAT_MODE_STATUS,
            resp
        )

        Log.i(TAG, "sendReq2Server: get Driver_Seat_Percentage_Position_3")
        if (res == ResultValue.OK) {
            Log.i(TAG, "sendReq2Server: Driver_Seat_Percentage_Position_3 OK")
//            val temp = SomeipS2SManagementInterface.Driver_Seat_Percentage_Position_3Field.parseFrom(resp.payload)
//            Log.i(TAG, "getSunroofPosition: " + temp.outPut.drvStFrwdBkwdPos)
//            return temp
        }

        Log.i(TAG, "FAILED: Driver_Seat_Percentage_Position_3")
        return null;
    }

    fun getPassenagerSeatPosition_1(): SomeipS2SManagementInterface.Passenger_Seat_Percentage_Position_1Field?{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG, "get Passenger_Seat_Percentage_Position_1Field: failed, server is not available or client is not ready")
            return null
        }

        val resp = SomeIpData()

        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SEAT_MODE_STATUS,
            resp
        )

        Log.i(TAG, "sendReq2Server: get Passenger_Seat_Percentage_Position_1Field")
        if (res == ResultValue.OK) {
            Log.i(TAG, "sendReq2Server: Passenger_Seat_Percentage_Position_1Field OK")
//            val temp = SomeipS2SManagementInterface.Driver_Seat_Percentage_Position_3Field.parseFrom(resp.payload)
//            Log.i(TAG, "getSunroofPosition: " + temp.outPut.drvStFrwdBkwdPos)
//            return temp
        }

        Log.i(TAG, "FAILED: Passenger_Seat_Percentage_Position_1Field")
        return null;
    }
    fun getPassenagerSeatPosition_2(): SomeipS2SManagementInterface.Passenger_Seat_Percentage_Position_2Field?{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG, "get Passenger_Seat_Percentage_Position_2Field: failed, server is not available or client is not ready")
            return null
        }

        val resp = SomeIpData()

        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SEAT_MODE_STATUS,
            resp
        )

        Log.i(TAG, "sendReq2Server: get Passenger_Seat_Percentage_Position_2Field")
        if (res == ResultValue.OK) {
            Log.i(TAG, "sendReq2Server: Passenger_Seat_Percentage_Position_2Field OK")
//            val temp = SomeipS2SManagementInterface.Driver_Seat_Percentage_Position_3Field.parseFrom(resp.payload)
//            Log.i(TAG, "getSunroofPosition: " + temp.outPut.drvStFrwdBkwdPos)
//            return temp
        }

        Log.i(TAG, "FAILED: Passenger_Seat_Percentage_Position_2Field")
        return null;
    }
    fun getSecondLeftSeatPosition_1(): SomeipS2SManagementInterface.Second_Left_Seat_Percentage_Position_1Field?{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG, "get Second_Left_Seat_Percentage_Position_1Field: failed, server is not available or client is not ready")
            return null
        }

        val resp = SomeIpData()

        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SEAT_MODE_STATUS,
            resp
        )

        Log.i(TAG, "sendReq2Server: get Second_Left_Seat_Percentage_Position_1Field")
        if (res == ResultValue.OK) {
            Log.i(TAG, "sendReq2Server: Second_Left_Seat_Percentage_Position_1Field OK")
//            val temp = SomeipS2SManagementInterface.Driver_Seat_Percentage_Position_3Field.parseFrom(resp.payload)
//            Log.i(TAG, "getSunroofPosition: " + temp.outPut.drvStFrwdBkwdPos)
//            return temp
        }

        Log.i(TAG, "FAILED: Second_Left_Seat_Percentage_Position_1Field")
        return null;
    }
    fun getSecondLeftSeatPosition_2(): SomeipS2SManagementInterface.Second_Left_Seat_Percentage_Position_2Field?{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG, "get Second_Left_Seat_Percentage_Position_2Field: failed, server is not available or client is not ready")
            return null
        }

        val resp = SomeIpData()

        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SEAT_MODE_STATUS,
            resp
        )

        Log.i(TAG, "sendReq2Server: get Second_Left_Seat_Percentage_Position_2Field")
        if (res == ResultValue.OK) {
            Log.i(TAG, "sendReq2Server: Second_Left_Seat_Percentage_Position_2Field OK")
//            val temp = SomeipS2SManagementInterface.Driver_Seat_Percentage_Position_3Field.parseFrom(resp.payload)
//            Log.i(TAG, "getSunroofPosition: " + temp.outPut.drvStFrwdBkwdPos)
//            return temp
        }

        Log.i(TAG, "FAILED: Second_Left_Seat_Percentage_Position_2Field")
        return null;
    }
    fun getSecondRightSeatPosition_1(): SomeipS2SManagementInterface.Second_Right_Seat_Percentage_Position_1Field?{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG, "get Second_Left_Seat_Percentage_Position_1Field: failed, server is not available or client is not ready")
            return null
        }

        val resp = SomeIpData()

        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SEAT_MODE_STATUS,
            resp
        )

        Log.i(TAG, "sendReq2Server: get Second_Left_Seat_Percentage_Position_1Field")
        if (res == ResultValue.OK) {
            Log.i(TAG, "sendReq2Server: Second_Left_Seat_Percentage_Position_1Field OK")
//            val temp = SomeipS2SManagementInterface.Driver_Seat_Percentage_Position_3Field.parseFrom(resp.payload)
//            Log.i(TAG, "getSunroofPosition: " + temp.outPut.drvStFrwdBkwdPos)
//            return temp
        }

        Log.i(TAG, "FAILED: Second_Left_Seat_Percentage_Position_1Field")
        return null;
    }
    fun getSecondRightSeatPosition_2(): SomeipS2SManagementInterface.Second_Right_Seat_Percentage_Position_2Field?{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG, "get Second_Left_Seat_Percentage_Position_2Field: failed, server is not available or client is not ready")
            return null
        }

        val resp = SomeIpData()

        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SEAT_MODE_STATUS,
            resp
        )

        Log.i(TAG, "sendReq2Server: get Second_Left_Seat_Percentage_Position_2Field")
        if (res == ResultValue.OK) {
            Log.i(TAG, "sendReq2Server: Second_Left_Seat_Percentage_Position_2Field OK")
//            val temp = SomeipS2SManagementInterface.Driver_Seat_Percentage_Position_3Field.parseFrom(resp.payload)
//            Log.i(TAG, "getSunroofPosition: " + temp.outPut.drvStFrwdBkwdPos)
//            return temp
        }

        Log.i(TAG, "FAILED: Second_Left_Seat_Percentage_Position_2Field")
        return null;
    }
    fun getThirdLeftSeatPosition(): SomeipS2SManagementInterface.Third_Left_Seat_Percentage_PositionField?{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG, "get Third_Left_Seat_Percentage_PositionField: failed, server is not available or client is not ready")
            return null
        }

        val resp = SomeIpData()

        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SEAT_MODE_STATUS,
            resp
        )

        Log.i(TAG, "sendReq2Server: get Third_Left_Seat_Percentage_PositionField")
        if (res == ResultValue.OK) {
            Log.i(TAG, "sendReq2Server: Third_Left_Seat_Percentage_PositionField OK")
//            val temp = SomeipS2SManagementInterface.Driver_Seat_Percentage_Position_3Field.parseFrom(resp.payload)
//            Log.i(TAG, "getSunroofPosition: " + temp.outPut.drvStFrwdBkwdPos)
//            return temp
        }

        Log.i(TAG, "FAILED: Third_Left_Seat_Percentage_PositionField")
        return null;
    }
    fun getThirdRightSeatPosition(): SomeipS2SManagementInterface.Third_Right_Seat_Percentage_PositionField?{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG, "get Third_Right_Seat_Percentage_PositionField: failed, server is not available or client is not ready")
            return null
        }

        val resp = SomeIpData()

        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SEAT_MODE_STATUS,
            resp
        )

        Log.i(TAG, "sendReq2Server: get Third_Right_Seat_Percentage_PositionField")
        if (res == ResultValue.OK) {
            Log.i(TAG, "sendReq2Server: Third_Right_Seat_Percentage_PositionField OK")
//            val temp = SomeipS2SManagementInterface.Driver_Seat_Percentage_Position_3Field.parseFrom(resp.payload)
//            Log.i(TAG, "getSunroofPosition: " + temp.outPut.drvStFrwdBkwdPos)
//            return temp
        }

        Log.i(TAG, "FAILED: Third_Right_Seat_Percentage_PositionField")
        return null;
    }
    fun getDriverSeatConf(): SomeipS2SManagementInterface.Driver_Seat_ConfigurationField?{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG, "get Driver_Seat_ConfigurationField: failed, server is not available or client is not ready")
            return null
        }

        val resp = SomeIpData()

        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SEAT_MODE_STATUS,
            resp
        )

        Log.i(TAG, "sendReq2Server: get Driver_Seat_ConfigurationField")
        if (res == ResultValue.OK) {
            Log.i(TAG, "sendReq2Server: Driver_Seat_ConfigurationField OK")
//            val temp = SomeipS2SManagementInterface.Driver_Seat_ConfigurationField.parseFrom(resp.payload)
//            Log.i(TAG, "getSunroofPosition: " + temp.outPut.drvStBkReclnUpwdDnwdMemConfig)
//            return temp
        }

        Log.i(TAG, "FAILED: Driver_Seat_ConfigurationField")
        return null;
    }
    fun getPassengerSeatConf(): SomeipS2SManagementInterface.Passenger_Seat_ConfigurationField?{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG, "get Passenger_Seat_ConfigurationField: failed, server is not available or client is not ready")
            return null
        }

        val resp = SomeIpData()

        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SEAT_MODE_STATUS,
            resp
        )

        Log.i(TAG, "sendReq2Server: get Passenger_Seat_ConfigurationField")
        if (res == ResultValue.OK) {
            Log.i(TAG, "sendReq2Server: Passenger_Seat_ConfigurationField OK")
//            val temp = SomeipS2SManagementInterface.Driver_Seat_ConfigurationField.parseFrom(resp.payload)
//            Log.i(TAG, "getSunroofPosition: " + temp.outPut.drvStBkReclnUpwdDnwdMemConfig)
//            return temp
        }

        Log.i(TAG, "FAILED: Passenger_Seat_ConfigurationField")
        return null;
    }
    fun getSecondLeftSeatConf(): SomeipS2SManagementInterface.Second_Left_Seat_ConfigurationField?{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG, "get Second_Left_Seat_ConfigurationField: failed, server is not available or client is not ready")
            return null
        }

        val resp = SomeIpData()

        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SEAT_MODE_STATUS,
            resp
        )

        Log.i(TAG, "sendReq2Server: get Second_Left_Seat_ConfigurationField")
        if (res == ResultValue.OK) {
            Log.i(TAG, "sendReq2Server: Second_Left_Seat_ConfigurationField OK")
//            val temp = SomeipS2SManagementInterface.Driver_Seat_ConfigurationField.parseFrom(resp.payload)
//            Log.i(TAG, "getSunroofPosition: " + temp.outPut.drvStBkReclnUpwdDnwdMemConfig)
//            return temp
        }

        Log.i(TAG, "FAILED: Second_Left_Seat_ConfigurationField")
        return null;
    }
    fun getSecondRightSeatConf(): SomeipS2SManagementInterface.Second_Right_Seat_ConfigurationField?{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG, "get Second_Right_Seat_ConfigurationField: failed, server is not available or client is not ready")
            return null
        }

        val resp = SomeIpData()

        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SEAT_MODE_STATUS,
            resp
        )

        Log.i(TAG, "sendReq2Server: get Second_Right_Seat_ConfigurationField")
        if (res == ResultValue.OK) {
            Log.i(TAG, "sendReq2Server: Second_Right_Seat_ConfigurationField OK")
//            val temp = SomeipS2SManagementInterface.Driver_Seat_ConfigurationField.parseFrom(resp.payload)
//            Log.i(TAG, "getSunroofPosition: " + temp.outPut.drvStBkReclnUpwdDnwdMemConfig)
//            return temp
        }

        Log.i(TAG, "FAILED: Second_Right_Seat_ConfigurationField")
        return null;
    }
    fun getThirdLeftSeatConf(): SomeipS2SManagementInterface.Third_Left_Seat_ConfigurationField?{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG, "get Third_Left_Seat_ConfigurationField: failed, server is not available or client is not ready")
            return null
        }

        val resp = SomeIpData()

        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SEAT_MODE_STATUS,
            resp
        )

        Log.i(TAG, "sendReq2Server: get Third_Left_Seat_ConfigurationField")
        if (res == ResultValue.OK) {
            Log.i(TAG, "sendReq2Server: Third_Left_Seat_ConfigurationField OK")
//            val temp = SomeipS2SManagementInterface.Driver_Seat_ConfigurationField.parseFrom(resp.payload)
//            Log.i(TAG, "getSunroofPosition: " + temp.outPut.drvStBkReclnUpwdDnwdMemConfig)
//            return temp
        }

        Log.i(TAG, "FAILED: Third_Left_Seat_ConfigurationField")
        return null;
    }
    fun getThirdRightSeatConf(): SomeipS2SManagementInterface.Third_Right_Seat_ConfigurationField?{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG, "get Third_Right_Seat_ConfigurationField: failed, server is not available or client is not ready")
            return null
        }

        val resp = SomeIpData()

        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SEAT_MODE_STATUS,
            resp
        )

        Log.i(TAG, "sendReq2Server: get Third_Right_Seat_ConfigurationField")
        if (res == ResultValue.OK) {
            Log.i(TAG, "sendReq2Server: Third_Right_Seat_ConfigurationField OK")
//            val temp = SomeipS2SManagementInterface.Driver_Seat_ConfigurationField.parseFrom(resp.payload)
//            Log.i(TAG, "getSunroofPosition: " + temp.outPut.drvStBkReclnUpwdDnwdMemConfig)
//            return temp
        }

        Log.i(TAG, "FAILED: Third_Right_Seat_ConfigurationField")
        return null;
    }
    fun getSeatMode(): SomeipS2SManagementInterface.Seat_Mode_StatusField?{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG, "get Seat_Mode_StatusField: failed, server is not available or client is not ready")
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
    fun getSeatPassenger_Com_Mode_Ser(): SomeipS2SManagementInterface.Seat_Passenger_Compartment_Mode_Service_Availability_StatusField?{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG, "get Seat_Passenger_Compartment_Mode_Service_Availability_StatusField: failed, server is not available or client is not ready")
            return null
        }

        val resp = SomeIpData()

        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SEAT_MODE_STATUS,
            resp
        )

        Log.i(TAG, "sendReq2Server: get Seat_Passenger_Compartment_Mode_Service_Availability_StatusField")
        if (res == ResultValue.OK) {
            Log.i(TAG, "sendReq2Server: Seat_Passenger_Compartment_Mode_Service_Availability_StatusField OK")
//            val temp = SomeipS2SManagementInterface.Driver_Seat_ConfigurationField.parseFrom(resp.payload)
//            Log.i(TAG, "getSunroofPosition: " + temp.outPut.drvStBkReclnUpwdDnwdMemConfig)
//            return temp
        }

        Log.i(TAG, "FAILED: Seat_Passenger_Compartment_Mode_Service_Availability_StatusField")
        return null;
    }
    fun getSeatPassenger_Com_Mode_Ser_Resp(): SomeipS2SManagementInterface.Seat_Passenger_Compartment_Mode_Service_Response_StatusField?{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG, "get Seat_Passenger_Compartment_Mode_Service_Response_StatusField: failed, server is not available or client is not ready")
            return null
        }

        val resp = SomeIpData()

        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SEAT_MODE_STATUS,
            resp
        )

        Log.i(TAG, "sendReq2Server: get Seat_Passenger_Compartment_Mode_Service_Response_StatusField")
        if (res == ResultValue.OK) {
            Log.i(TAG, "sendReq2Server: Seat_Passenger_Compartment_Mode_Service_Response_StatusField OK")
//            val temp = SomeipS2SManagementInterface.Driver_Seat_ConfigurationField.parseFrom(resp.payload)
//            Log.i(TAG, "getSunroofPosition: " + temp.outPut.drvStBkReclnUpwdDnwdMemConfig)
//            return temp
        }

        Log.i(TAG, "FAILED: Seat_Passenger_Compartment_Mode_Service_Response_StatusField")
        return null;
    }
    fun getSeatPassenger_Com_Mode_Ser_Resp2(): SomeipS2SManagementInterface.Seat_Passenger_Compartment_Mode_Service_Response_Status_2Field?{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG, "get Seat_Passenger_Compartment_Mode_Service_Response_Status_2Field: failed, server is not available or client is not ready")
            return null
        }

        val resp = SomeIpData()

        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SEAT_MODE_STATUS,
            resp
        )

        Log.i(TAG, "sendReq2Server: get Seat_Passenger_Compartment_Mode_Service_Response_Status_2Field")
        if (res == ResultValue.OK) {
            Log.i(TAG, "sendReq2Server: Seat_Passenger_Compartment_Mode_Service_Response_Status_2Field OK")
//            val temp = SomeipS2SManagementInterface.Driver_Seat_ConfigurationField.parseFrom(resp.payload)
//            Log.i(TAG, "getSunroofPosition: " + temp.outPut.drvStBkReclnUpwdDnwdMemConfig)
//            return temp
        }

        Log.i(TAG, "FAILED: Seat_Passenger_Compartment_Mode_Service_Response_Status_2Field")
        return null;
    }
    fun getSeatMode2(): SomeipS2SManagementInterface.Seat_Mode_Status_2Field?{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG, "get Seat_Mode_Status_2Field: failed, server is not available or client is not ready")
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
    fun getSeatPassenger_Com_Mode_Ava_Notifi6(): SomeipS2SManagementInterface.Seat_Passenger_Compartment_Mode_Availability_and_Notification_Status_6Field?{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG, "get Seat_Passenger_Compartment_Mode_Availability_and_Notification_Status_6Field: failed, server is not available or client is not ready")
            return null
        }

        val resp = SomeIpData()

        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SEAT_MODE_STATUS,
            resp
        )

        Log.i(TAG, "sendReq2Server: get Seat_Passenger_Compartment_Mode_Availability_and_Notification_Status_6Field")
        if (res == ResultValue.OK) {
            Log.i(TAG, "sendReq2Server: Seat_Passenger_Compartment_Mode_Availability_and_Notification_Status_6Field OK")
//            val temp = SomeipS2SManagementInterface.Driver_Seat_ConfigurationField.parseFrom(resp.payload)
//            Log.i(TAG, "getSunroofPosition: " + temp.outPut.drvStBkReclnUpwdDnwdMemConfig)
//            return temp
        }

        Log.i(TAG, "FAILED: Seat_Passenger_Compartment_Mode_Availability_and_Notification_Status_6Field")
        return null;
    }
    fun getDriverSeatRecoveryPosition(): SomeipS2SManagementInterface.Driver_Seat_Recovery_PositionField?{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG, "get Driver_Seat_Recovery_PositionField: failed, server is not available or client is not ready")
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
    fun getDriverSeatRecoveryPosition2(): SomeipS2SManagementInterface.Driver_Seat_Recovery_Position_2Field?{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG, "get Driver_Seat_Recovery_Position_2Field: failed, server is not available or client is not ready")
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
    fun getSec_RowLeft_Com_Mode_Req(): SomeipS2SManagementInterface.Second_Row_Left_ComfortMode_Request_HMIField?{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG, "get Second_Row_Left_ComfortMode_Request_HMIField: failed, server is not available or client is not ready")
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
    fun getSec_RowRight_Com_Mode_Req(): SomeipS2SManagementInterface.Second_Row_Right_ComfortMode_Request_HMIField?{
        if (!isServerAvailable || !isReady) {
            Log.i(TAG, "get Second_Row_Right_ComfortMode_Request_HMIField: failed, server is not available or client is not ready")
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