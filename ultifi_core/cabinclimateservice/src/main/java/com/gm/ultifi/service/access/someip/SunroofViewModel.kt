package com.gm.ultifi.service.access.someip

import android.util.Log
import plugin.SomeIpTopic
import plugin.SomeipS2SManagementInterface
import ts.car.someip.sdk.common.ResultValue
import ts.car.someip.sdk.common.SomeIpData

open class SunroofViewModel : BaseAppViewModel() {
    private val param = 1
    private val param1 = 1
    private val param2 = false
    override fun doOnRequest(data: SomeIpData): SomeIpData? {
        // method for server, no need to implement in client
        return null;
    }

    override fun doOnSomeIpEvent(data: SomeIpData) {
        // deal with the data from server after client's callback being invoked
        Log.i(TAG, "onSomeIpEvent: " + data.topic)

        // record the server's status
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_SERVICE_1_AVAILABLE) {
            Log.i(TAG, "SERVICE_AVAILABLE")
            isServerAvailable = true
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SUNROOF_STATUS)
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_WINDOW_STATUS)
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SUNBLIND_STATUS)
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SUNBLIND_CONFIGURATION_AVAILABILITY)
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_WINDOW_CONTROL_2L)
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_WINDOW_CONTROL_2R)
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_PERCENTAGE_WINDOW_CONTROL_2L)
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_PERCENTAGE_WINDOW_CONTROL_2R)
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_WINDOW_STATUS_INFORMATION)
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_WINDOW_CONTROL_RSTP)
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_PERCENTAGE_WINDOW_CONTROL_RSTP)
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_FRONT_SUNSHADE_STATUS)
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_REAR_SUNSHADE_STATUS)
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_HMI_2L_SUNROOF_SYSTEM_CONTROL)
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_HMI_2R_SUNROOF_SYSTEM_CONTROL)
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_SERVICE_1_NOT_AVAILABLE) {
            Log.i(TAG, "SERVICE_NOT_AVAILABLE")
            isServerAvailable = false
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SUNROOF_STATUS)
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_WINDOW_STATUS)
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SUNBLIND_STATUS)
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SUNBLIND_CONFIGURATION_AVAILABILITY)
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_WINDOW_CONTROL_2L)
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_WINDOW_CONTROL_2R)
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_PERCENTAGE_WINDOW_CONTROL_2L)
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_PERCENTAGE_WINDOW_CONTROL_2R)
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_WINDOW_STATUS_INFORMATION)
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_WINDOW_CONTROL_RSTP)
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_PERCENTAGE_WINDOW_CONTROL_RSTP)
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_FRONT_SUNSHADE_STATUS)
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_REAR_SUNSHADE_STATUS)
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_HMI_2L_SUNROOF_SYSTEM_CONTROL)
            someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_HMI_2R_SUNROOF_SYSTEM_CONTROL)
        }

        // notify subscriber the change of sunroof status
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SUNROOF_STATUS) {
            Log.i(TAG, "SUCCESS: NOTIFY_SUNROOF_STATUS")

            // TODO: 测试完后恢复

            val resp = SomeipS2SManagementInterface.Sunroof_StatusField.parseFrom(data.payload)
            val sunroofPercentagePositionStatus = resp.outPut.snrfPctPosSts
            val booParam = resp.outPut.snrfConfig
            Log.d(
                TAG,
                "onChangeEvent, PropName: sunroofPercentagePositionStatus, " +
                        "newPropValue:$sunroofPercentagePositionStatus, " +
                        "sunroofConf:$booParam"
            )


            Log.i(TAG, "Publishing the cloud events to Bus")

            // no field mask in the resp, should set all the fields to msg obj
            val sunroof: Sunroof = Sunroof.newBuilder()
                .setPosition(sunroofPercentagePositionStatus)
                .build()

            val topic = ResourceMappingConstants.SUNROOF_FRONT + ".someip"
            val uResource = UResource(topic, "", Sunroof::class.java.simpleName)
            val topicUri = UltifiUriFactory.buildUProtocolUri(
                UAuthority.local(),
                BaseMapper.SERVICE,
                uResource
            )

            val cloudEvent = CloudEventFactory.publish(
                topicUri,
                Any.pack(sunroof),
                UCloudEventAttributes.empty()
            );

            AccessService.mLaunchManager.getmUltifiLinkMonitor().publish(cloudEvent)
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_WINDOW_STATUS) {
            Log.i(TAG, "SUCCESS: NOTIFY_WINDOW_STATUS")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SUNBLIND_STATUS) {
            Log.i(TAG, "SUCCESS: NOTIFY_SUNBLIND_STATUS")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SUNBLIND_CONFIGURATION_AVAILABILITY) {
            Log.i(TAG, "SUCCESS: NOTIFY_SUNBLIND_CONFIGURATION_AVAILABILITY")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_WINDOW_CONTROL_2L) {
            Log.i(TAG, "SUCCESS: NOTIFY_WINDOW_CONTROL_2L")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_WINDOW_CONTROL_2R) {
            Log.i(TAG, "SUCCESS: NOTIFY_WINDOW_CONTROL_2R")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_PERCENTAGE_WINDOW_CONTROL_2L) {
            Log.i(TAG, "SUCCESS: NOTIFY_PERCENTAGE_WINDOW_CONTROL_2L")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_PERCENTAGE_WINDOW_CONTROL_2R) {
            Log.i(TAG, "SUCCESS: NOTIFY_PERCENTAGE_WINDOW_CONTROL_2R")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_WINDOW_STATUS_INFORMATION) {
            Log.i(TAG, "SUCCESS: NOTIFY_WINDOW_STATUS_INFORMATION")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_WINDOW_CONTROL_RSTP) {
            Log.i(TAG, "SUCCESS: NOTIFY_WINDOW_CONTROL_RSTP")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_PERCENTAGE_WINDOW_CONTROL_RSTP) {
            Log.i(TAG, "SUCCESS: NOTIFY_PERCENTAGE_WINDOW_CONTROL_RSTP")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_FRONT_SUNSHADE_STATUS) {
            Log.i(TAG, "SUCCESS: NOTIFY_FRONT_SUNSHADE_STATUS")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_REAR_SUNSHADE_STATUS) {
            Log.i(TAG, "SUCCESS: NOTIFY_REAR_SUNSHADE_STATUS")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_HMI_2L_SUNROOF_SYSTEM_CONTROL) {
            Log.i(TAG, "SUCCESS: NOTIFY_REAR_SUNSHADE_STATUS")
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_HMI_2R_SUNROOF_SYSTEM_CONTROL) {
            Log.i(TAG, "SUCCESS: NOTIFY_REAR_SUNSHADE_STATUS")
        }
    }

    override fun doClientOk() {
        // check if the service is available
        someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_SERVICE_1_AVAILABLE)
        someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_SERVICE_1_NOT_AVAILABLE)
    }

    override fun doStartClient(): Int? {
        // discover server's service
        return startClient(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_SERVICE_1_ID)
    }

    override fun doStopClient() {
        stopClient(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_SERVICE_1_ID)
    }

    // region ===== set funtion =====

    fun setSunroofSunshadeControlRequest() {
        // check the connection of someip
        if (!isServerAvailable || !isReady) {
            Log.i(TAG, "setSunroofPosition: failed, server is not available or client is not ready")
            return
        }

        val controlRequest = SomeipS2SManagementInterface
            .Sunroof_Sunshade_Control_RequestField.newBuilder()
            .setOutPut(
                SomeipS2SManagementInterface.Sunroof_Sunshade_Control_Request.newBuilder()
                    .setSnrfPctCtrlReqSrv(param)
                    .setSnrfCtrlReqSrv(param)
                    .setFrtSnshdCtrlReqSrv(param)
                    .setRrSnshdCtrlReqSrv(param)
                    .setFrtSnshdPctCtrlReqSrv(param)
                    .setRrSnshdPctCtrlReqSrv(param)
            )
            .build()

        val req = SomeIpData(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_SUNROOF_SUNSHADE_CONTROL_REQUEST,
            System.currentTimeMillis(),
            controlRequest.toByteArray()
        )

        val resp = SomeIpData()

        val res = someIpClientProxy?.setAttribute(req, resp)
        Log.i(TAG, "sendReq2Server: SET_SUNROOF_SUNSHADE_CONTROL_REQUEST")

        if (res == ResultValue.OK) {
            Log.i(TAG, "SUCCESS: SET_SUNROOF_SUNSHADE_CONTROL_REQUEST")
            return
        }

        Log.i(TAG, "FAILED: SET_SUNROOF_SUNSHADE_CONTROL_REQUEST")
    }

    fun setWindowServiceControlRequest() {
        // check the connection of someip
        if (!isServerAvailable || !isReady) {
            Log.i(
                TAG,
                "setWindowServiceControlRequest: failed, server is not available or client is not ready"
            )
            return
        }

        val controlRequest = SomeipS2SManagementInterface
            .Window_Service_Control_RequestField.newBuilder()
            .setOutPut(
                SomeipS2SManagementInterface.Window_Service_Control_Request.newBuilder()
                    .setDrvWndCtrlCmfReqSrv(param)
                    .setPsWndCtrlCmfReqSrv(param)
                    .setRLWndCtrloCmfReqSrv(param)
                    .setRRWndCtrlCmfReqSrv(param)
            )
            .build()

        val req = SomeIpData(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_WINDOW_SERVICE_CONTROL_REQUEST,
            System.currentTimeMillis(),
            controlRequest.toByteArray()
        )

        val resp = SomeIpData()

        val res = someIpClientProxy?.setAttribute(req, resp)
        Log.i(TAG, "sendReq2Server: SET_WINDOW_SERVICE_CONTROL_REQUEST")

        if (res == ResultValue.OK) {
            Log.i(TAG, "SUCCESS: SET_WINDOW_SERVICE_CONTROL_REQUEST")
            return
        }

        Log.i(TAG, "FAILED: SET_WINDOW_SERVICE_CONTROL_REQUEST")

    }

    fun setWindowsPercentageControlRequest() {
        // check the connection of someip
        if (!isServerAvailable || !isReady) {
            Log.i(
                TAG,
                "setWindowsPercentageControlRequest: failed, server is not available or client is not ready"
            )
            return
        }

        val controlRequest = SomeipS2SManagementInterface
            .Window_Percentage_Control_RequestField.newBuilder()
            .setOutPut(
                SomeipS2SManagementInterface.Window_Percentage_Control_Request.newBuilder()
                    .setDrWndCtrlPosReqSrv(param1)
                    .setDrWndCtrlPosReqEnblSrv(param2)
                    .setPsWndCtrlPosReqSrv(param1)
                    .setPsWndCtrlPosReqEnblSrv(param2)
                    .setRLWndCtrlPosReqSrv(param1)
                    .setRLWndCtrlPosReqEnblSrv(param2)
                    .setRRWndCtrlPosReqSrv(param1)
                    .setRRWndCtrlPosReqEnblSrv(param2)
            )
            .build()

        val req = SomeIpData(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_WINDOW_PERCENTAGE_CONTROL_REQUEST,
            System.currentTimeMillis(),
            controlRequest.toByteArray()
        )

        val resp = SomeIpData()

        val res = someIpClientProxy?.setAttribute(req, resp)
        Log.i(TAG, "sendReq2Server: SET_WINDOW_PERCENTAGE_CONTROL_REQUEST")

        if (res == ResultValue.OK) {
            Log.i(TAG, "SUCCESS: SET_WINDOW_PERCENTAGE_CONTROL_REQUEST")
            return
        }

        Log.i(TAG, "FAILED: SET_WINDOW_PERCENTAGE_CONTROL_REQUEST")
    }

    fun setSideSunblindControlRequest() {
        // check the connection of someip
        if (!isServerAvailable || !isReady) {
            Log.i(
                TAG,
                "setSideSunblindControlRequest: failed, server is not available or client is not ready"
            )
            return
        }

        val controlRequest = SomeipS2SManagementInterface
            .Side_Sunblind_Control_RequestField.newBuilder()
            .setOutPut(
                SomeipS2SManagementInterface.Side_Sunblind_Control_Request.newBuilder()
                    .setRLSSBICmfReq(param)
                    .setRRSSBICmfReq(param)
            )
            .build()

        val req = SomeIpData(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_SIDE_SUNBLIND_CONTROL_REQUEST,
            System.currentTimeMillis(),
            controlRequest.toByteArray()
        )

        val resp = SomeIpData()

        val res = someIpClientProxy?.setAttribute(req, resp)
        Log.i(TAG, "sendReq2Server: SET_SIDE_SUNBLIND_CONTROL_REQUEST")

        if (res == ResultValue.OK) {
            Log.i(TAG, "SUCCESS: SET_SIDE_SUNBLIND_CONTROL_REQUEST")
            return
        }

        Log.i(TAG, "FAILED: SET_SIDE_SUNBLIND_CONTROL_REQUEST")
    }

    // endregion ===== set function =====

    // region ===== get function =====

    fun getSunroofStatus(): SomeipS2SManagementInterface.Sunroof_StatusField? {
        // check the connection of someip
        if (!isServerAvailable || !isReady) {
            Log.i(TAG, "getSunroofStatus: failed, server is not available or client is not ready")
            return null
        }

        val resp = SomeIpData()

        Log.i(TAG, "sendReq2Server: GET_SUNROOF_STATUS")
        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SUNROOF_STATUS,
            resp
        )

        if (res == ResultValue.OK) {
            Log.i(TAG, "SUCCESS: GET_SUNROOF_STATUS")
//            val temp = SomeipS2SManagementInterface.Sunroof_StatusField.parseFrom(resp.payload)
//            Log.i(TAG, "getSunroofPosition: " + temp.outPut.snrfPctPosSts)
            return null
        }

        Log.i(TAG, "FAILED: GET_SUNROOF_STATUS")
        return null;
    }

    fun getWindowStatus() {
        // check the connection of someip
        if (!isServerAvailable || !isReady) {
            Log.i(TAG, "getWindowStatus: failed, server is not available or client is not ready")
            return
        }

        val resp = SomeIpData()

        Log.i(TAG, "sendReq2Server: GET_WINDOW_STATUS")
        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_WINDOW_STATUS,
            resp
        )

        if (res == ResultValue.OK) {
            Log.i(TAG, "SUCCESS: GET_WINDOW_STATUS")
            return
        }

        Log.i(TAG, "FAILED: GET_WINDOW_STATUS")
    }

    fun getSunblindStatus() {
        // check the connection of someip
        if (!isServerAvailable || !isReady) {
            Log.i(TAG, "getSunblindStatus: failed, server is not available or client is not ready")
            return
        }

        val resp = SomeIpData()

        Log.i(TAG, "sendReq2Server: GET_SUNBLIND_STATUS")
        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SUNBLIND_STATUS,
            resp
        )

        if (res == ResultValue.OK) {
            Log.i(TAG, "SUCCESS: GET_SUNBLIND_STATUS")
            return
        }

        Log.i(TAG, "FAILED: GET_SUNBLIND_STATUS")
    }

    fun getSunblindConfigurationAvailability() {
        // check the connection of someip
        if (!isServerAvailable || !isReady) {
            Log.i(
                TAG,
                "getSunblindConfigurationAvailability: failed, server is not available or client is not ready"
            )
            return
        }

        val resp = SomeIpData()

        Log.i(TAG, "sendReq2Server: GET_SUNBLIND_CONFIGURATION_AVAILABILITY")
        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SUNBLIND_CONFIGURATION_AVAILABILITY,
            resp
        )

        if (res == ResultValue.OK) {
            Log.i(TAG, "SUCCESS: GET_SUNBLIND_CONFIGURATION_AVAILABILITY")
            return
        }

        Log.i(TAG, "FAILED: GET_SUNBLIND_CONFIGURATION_AVAILABILITY")
    }

    fun getWindowControl2L() {
        // check the connection of someip
        if (!isServerAvailable || !isReady) {
            Log.i(
                TAG,
                "getWindowControl2L: failed, server is not available or client is not ready"
            )
            return
        }

        val resp = SomeIpData()

        Log.i(TAG, "sendReq2Server: GET_WINDOW_CONTROL_2L")
        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_WINDOW_CONTROL_2L,
            resp
        )

        if (res == ResultValue.OK) {
            Log.i(TAG, "SUCCESS: GET_WINDOW_CONTROL_2L")
            return
        }

        Log.i(TAG, "FAILED: GET_WINDOW_CONTROL_2L")
    }

    fun getWindowControl2R() {
        // check the connection of someip
        if (!isServerAvailable || !isReady) {
            Log.i(
                TAG,
                "getWindowControl2R: failed, server is not available or client is not ready"
            )
            return
        }

        val resp = SomeIpData()

        Log.i(TAG, "sendReq2Server: GET_WINDOW_CONTROL_2R")
        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_WINDOW_CONTROL_2R,
            resp
        )

        if (res == ResultValue.OK) {
            Log.i(TAG, "SUCCESS: GET_WINDOW_CONTROL_2R")
            return
        }

        Log.i(TAG, "FAILED: GET_WINDOW_CONTROL_2R")
    }

    fun getPercentageWindowControl2L() {
        // check the connection of someip
        if (!isServerAvailable || !isReady) {
            Log.i(
                TAG,
                "getPercentageWindowControl2L: failed, server is not available or client is not ready"
            )
            return
        }

        val resp = SomeIpData()

        Log.i(TAG, "sendReq2Server: GET_PERCENTAGE_WINDOW_CONTROL_2L")
        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_PERCENTAGE_WINDOW_CONTROL_2L,
            resp
        )

        if (res == ResultValue.OK) {
            Log.i(TAG, "SUCCESS: GET_PERCENTAGE_WINDOW_CONTROL_2L")
            return
        }

        Log.i(TAG, "FAILED: GET_PERCENTAGE_WINDOW_CONTROL_2L")
    }

    fun getPercentageWindowControl2R() {
        // check the connection of someip
        if (!isServerAvailable || !isReady) {
            Log.i(
                TAG,
                "getPercentageWindowControl2R: failed, server is not available or client is not ready"
            )
            return
        }

        val resp = SomeIpData()

        Log.i(TAG, "sendReq2Server: GET_PERCENTAGE_WINDOW_CONTROL_2R")
        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_PERCENTAGE_WINDOW_CONTROL_2R,
            resp
        )

        if (res == ResultValue.OK) {
            Log.i(TAG, "SUCCESS: GET_PERCENTAGE_WINDOW_CONTROL_2R")
            return
        }

        Log.i(TAG, "FAILED: GET_PERCENTAGE_WINDOW_CONTROL_2R")
    }

    fun getWindowStatusInformation() {
        // check the connection of someip
        if (!isServerAvailable || !isReady) {
            Log.i(
                TAG,
                "getWindowStatusInformation: failed, server is not available or client is not ready"
            )
            return
        }

        val resp = SomeIpData()

        Log.i(TAG, "sendReq2Server: GET_WINDOW_STATUS_INFORMATION")
        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_WINDOW_STATUS_INFORMATION,
            resp
        )

        if (res == ResultValue.OK) {
            Log.i(TAG, "SUCCESS: GET_WINDOW_STATUS_INFORMATION")
            return
        }

        Log.i(TAG, "FAILED: GET_WINDOW_STATUS_INFORMATION")
    }

    fun getWindowControlRSTP() {
        // check the connection of someip
        if (!isServerAvailable || !isReady) {
            Log.i(
                TAG,
                "getWindowControlRSTP: failed, server is not available or client is not ready"
            )
            return
        }

        val resp = SomeIpData()

        Log.i(TAG, "sendReq2Server: GET_WINDOW_CONTROL_RSTP")
        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_WINDOW_CONTROL_RSTP,
            resp
        )

        if (res == ResultValue.OK) {
            Log.i(TAG, "SUCCESS: GET_WINDOW_CONTROL_RSTP")
            return
        }

        Log.i(TAG, "FAILED: GET_WINDOW_CONTROL_RSTP")
    }

    fun getPercentageWindowControlRSTP() {
        // check the connection of someip
        if (!isServerAvailable || !isReady) {
            Log.i(
                TAG,
                "getPercentageWindowControlRSTP: failed, server is not available or client is not ready"
            )
            return
        }

        val resp = SomeIpData()

        Log.i(TAG, "sendReq2Server: GET_PERCENTAGE_WINDOW_CONTROL_RSTP")
        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_PERCENTAGE_WINDOW_CONTROL_RSTP,
            resp
        )

        if (res == ResultValue.OK) {
            Log.i(TAG, "SUCCESS: GET_PERCENTAGE_WINDOW_CONTROL_RSTP")
            return
        }

        Log.i(TAG, "FAILED: GET_PERCENTAGE_WINDOW_CONTROL_RSTP")
    }

    fun getFrontSunshadeStatus() {
        // check the connection of someip
        if (!isServerAvailable || !isReady) {
            Log.i(
                TAG,
                "getFrontSunshadeStatus: failed, server is not available or client is not ready"
            )
            return
        }

        val resp = SomeIpData()

        Log.i(TAG, "sendReq2Server: GET_FRONT_SUNSHADE_STATUS")
        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_FRONT_SUNSHADE_STATUS,
            resp
        )

        if (res == ResultValue.OK) {
            Log.i(TAG, "SUCCESS: GET_FRONT_SUNSHADE_STATUS")
            return
        }

        Log.i(TAG, "FAILED: GET_FRONT_SUNSHADE_STATUS")
    }

    fun getRearSunshadeStatus() {
        // check the connection of someip
        if (!isServerAvailable || !isReady) {
            Log.i(
                TAG,
                "getRearSunshadeStatus: failed, server is not available or client is not ready"
            )
            return
        }

        val resp = SomeIpData()

        Log.i(TAG, "sendReq2Server: GET_REAR_SUNSHADE_STATUS")
        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_REAR_SUNSHADE_STATUS,
            resp
        )

        if (res == ResultValue.OK) {
            Log.i(TAG, "SUCCESS: GET_REAR_SUNSHADE_STATUS")
            return
        }

        Log.i(TAG, "FAILED: GET_REAR_SUNSHADE_STATUS")
    }

    fun getHMI2LSunroofSystemControl() {
        // check the connection of someip
        if (!isServerAvailable || !isReady) {
            Log.i(
                TAG,
                "getHMI2LSunroofSystemControl: failed, server is not available or client is not ready"
            )
            return
        }

        val resp = SomeIpData()

        Log.i(TAG, "sendReq2Server: GET_HMI_2L_SUNROOF_SYSTEM_CONTROL")
        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_HMI_2L_SUNROOF_SYSTEM_CONTROL,
            resp
        )

        if (res == ResultValue.OK) {
            Log.i(TAG, "SUCCESS: GET_HMI_2L_SUNROOF_SYSTEM_CONTROL")
            return
        }

        Log.i(TAG, "FAILED: GET_HMI_2L_SUNROOF_SYSTEM_CONTROL")
    }

    fun getHMI2RSunroofSystemControl() {
        // check the connection of someip
        if (!isServerAvailable || !isReady) {
            Log.i(
                TAG,
                "getHMI2RSunroofSystemControl: failed, server is not available or client is not ready"
            )
            return
        }

        val resp = SomeIpData()

        Log.i(TAG, "sendReq2Server: GET_HMI_2R_SUNROOF_SYSTEM_CONTROL")
        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_HMI_2R_SUNROOF_SYSTEM_CONTROL,
            resp
        )

        if (res == ResultValue.OK) {
            Log.i(TAG, "SUCCESS: GET_HMI_2R_SUNROOF_SYSTEM_CONTROL")
            return
        }

        Log.i(TAG, "FAILED: GET_HMI_2R_SUNROOF_SYSTEM_CONTROL")
    }

    // endregion ===== get function =====

}