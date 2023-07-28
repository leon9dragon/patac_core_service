package com.gm.ultifi.service.access.someip

import android.util.Log
import plugin.SomeIpTopic
import plugin.SomeipS2SManagementInterface
import ts.car.someip.sdk.common.ResultValue
import ts.car.someip.sdk.common.SomeIpData

open class AmbientLightViewModel : BaseAppViewModel() {
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
            someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_ILS_AMBIENT_LIGHT_SEVICE_CONTROL)
            isServerAvailable = true
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_SERVICE_1_NOT_AVAILABLE) {
            Log.i(TAG, "SERVICE_NOT_AVAILABLE")
            unsub()
            isServerAvailable = false
        }

        // notify subscriber the change of sunroof status
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_ILS_AMBIENT_LIGHT_SEVICE_CONTROL) {
            val resp =
                SomeipS2SManagementInterface.ILS_Ambient_Light_Sevice_Control.parseFrom(data.payload)
            val led1 = resp.ilsAmbLgtBluColrValSetReqLED1

            Log.d(
                TAG, "onChangeEvent, PropName: led1, " + "led1:$led1, "
            )
//            Log.i(TAG, "Publishing the cloud events to Bus")
//
//            // no field mask in the resp, should set all the fields to msg obj
//            val sunroof: Sunroof = Sunroof.newBuilder()
//                .setPosition(sunroofPercentagePositionStatus)
//                .build()
//
//            val topic = ResourceMappingConstants.SUNROOF_FRONT + ".someip"
//            val uResource = UResource(topic, "", Sunroof::class.java.simpleName)
//            val topicUri = UltifiUriFactory.buildUProtocolUri(
//                UAuthority.local(),
//                BaseMapper.SERVICE,
//                uResource
//            )
//
//            val cloudEvent = CloudEventFactory.publish(
//                topicUri,
//                Any.pack(sunroof),
//                UCloudEventAttributes.empty()
//            )
            // TODO: 测试完后恢复
            // AccessService.mLaunchManager.getmUltifiLinkMonitor().publish(cloudEvent)
        }
    }

    fun unsub() {
        someIpClientProxy?.unsubscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_ILS_AMBIENT_LIGHT_SEVICE_CONTROL)
    }

    override fun doClientOk() {
        // check if the service is available
        someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_SERVICE_1_AVAILABLE)
        someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_SERVICE_1_NOT_AVAILABLE)
    }

    override fun doStartClient(): Int? {
        // subscribe server's service
        return startClient(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_SERVICE_1_ID)
    }

    override fun doStopClient() {
        stopClient(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_SERVICE_1_ID)
    }

    fun setAmbientLight() {
        // check the connection of someip
        if (!isServerAvailable || !isReady) {
            Log.i(TAG, "setSunroofPosition: failed, server is not available or client is not ready")
            return
        }

        val ambientLightControl =
            SomeipS2SManagementInterface.ILS_Ambient_Light_Sevice_ControlField.newBuilder()
                .setOutPut(
                    SomeipS2SManagementInterface.ILS_Ambient_Light_Sevice_Control.newBuilder()
                        .setILSAmbLgtOnOffCtrlReqLED1(1)//控制指令
                        .setILSAmbLgtBritLvlSetReqLED1(1)//亮度 0-100
                        .setILSAmbLgtBritLvlSetReqEnblLED1(1)//亮度使能
                        .setILSAmbLgtRedColrValSetReqLED1(1)//红色 0-255
                        .setILSAmbLgtGrenColrValSetReqLED1(1)//绿色
                        .setILSAmbLgtBluColrValSetReqLED1(1)//蓝色
                        .setILSAmbLgtRGBColrValSetReqEnblLED1(1)//颜色使能

                        .setILSAmbLgtOnOffCtrlReqLED2(1)//控制指令
                        .setILSAmbLgtBritLvlSetReqLED2(1)//亮度
                        .setILSAmbLgtBritLvlSetReqEnblLED2(1)//亮度使能
                        .setILSAmbLgtRedColrValSetReqLED2(1)//红色
                        .setILSAmbLgtGrenColrValSetReqLED2(1)//绿色
                        .setILSAmbLgtBluColrValSetReqLED2(1)//蓝色
                        .setILSAmbLgtRGBColrValSetReqEnblLED2(1)//颜色使能

                        .setILSAmbLgtOnOffCtrlReqLED3(1)//控制指令
                        .setILSAmbLgtBritLvlSetReqLED3(1)//亮度
                        .setILSAmbLgtBritLvlSetReqEnblLED3(1)//亮度使能
                        .setILSAmbLgtRedColrValSetReqLED3(1)//红色
                        .setILSAmbLgtGrenColrValSetReqLED3(1)//绿色
                        .setILSAmbLgtBluColrValSetReqLED3(1)//蓝色
                        .setILSAmbLgtRGBColrValSetReqEnblLED3(1)//颜色使能

                        .setILSAmbLgtOnOffCtrlReqLED4(1)//控制指令
                        .setILSAmbLgtBritLvlSetReqLED4(1)//亮度
                        .setILSAmbLgtBritLvlSetReqEnblLED4(1)//亮度使能
                        .setILSAmbLgtRedColrValSetReqLED5(1)//红色
                        .setILSAmbLgtGrenColrValSetReqLED5(1)//绿色
                        .setILSAmbLgtBluColrValSetReqLED5(1)//蓝色
                        .setILSAmbLgtRGBColrValSetReqEnblLED5(1)//颜色使能

                        .setILSAmbLgtOnOffCtrlReqLED6(1)//控制指令
                        .setILSAmbLgtBritLvlSetReqLED6(1)//亮度
                        .setILSAmbLgtBritLvlSetReqEnblLED6(1)//亮度使能
                        .setILSAmbLgtRedColrValSetReqLED6(1)//红色
                        .setILSAmbLgtGrenColrValSetReqLED6(1)//绿色
                        .setILSAmbLgtBluColrValSetReqLED6(1)//蓝色
                        .setILSAmbLgtRGBColrValSetReqEnblLED6(1)//颜色使能

                        .setILSAmbLgtOnOffCtrlReqLED7(1)//控制指令
                        .setILSAmbLgtBritLvlSetReqLED7(1)//亮度
                        .setILSAmbLgtBritLvlSetReqEnblLED7(1)//亮度使能
                        .setILSAmbLgtRedColrValSetReqLED7(1)//红色
                        .setILSAmbLgtGrenColrValSetReqLED7(1)//绿色
                        .setILSAmbLgtBluColrValSetReqLED7(1)//蓝色
                        .setILSAmbLgtRGBColrValSetReqEnblLED7(1)//颜色使能

                        .setILSAmbLgtOnOffCtrlReqLED8(1)//控制指令
                        .setILSAmbLgtBritLvlSetReqLED8(1)//亮度
                        .setILSAmbLgtBritLvlSetReqEnblLED8(1)//亮度使能
                        .setILSAmbLgtRedColrValSetReqLED8(1)//红色
                        .setILSAmbLgtGrenColrValSetReqLED8(1)//绿色
                        .setILSAmbLgtBluColrValSetReqLED8(1)//蓝色
                        .setILSAmbLgtRGBColrValSetReqEnblLED8(1)//颜色使能

                        .setILSAmbLgtOnOffCtrlReqLED9(1)//控制指令
                        .setILSAmbLgtBritLvlSetReqLED9(1)//亮度
                        .setILSAmbLgtBritLvlSetReqEnblLED9(1)//亮度使能
                        .setILSAmbLgtRedColrValSetReqLED9(1)//红色
                        .setILSAmbLgtGrenColrValSetReqLED9(1)//绿色
                        .setILSAmbLgtBluColrValSetReqLED9(1)//蓝色
                        .setILSAmbLgtRGBColrValSetReqEnblLED9(1)//颜色使能

                        .setILSAmbLgtOnOffCtrlReqLED10(1)//控制指令
                        .setILSAmbLgtBritLvlSetReqLED10(1)//亮度
                        .setILSAmbLgtBritLvlSetReqEnblLED10(1)//亮度使能
                        .setILSAmbLgtRedColrValSetReqLED10(1)//红色
                        .setILSAmbLgtGrenColrValSetReqLED10(1)//绿色
                        .setILSAmbLgtBluColrValSetReqLED10(1)//蓝色
                        .setILSAmbLgtRGBColrValSetReqEnblLED10(1)//颜色使能

                        .setILSAmbLgtOnOffCtrlReqLED11(1)//控制指令
                        .setILSAmbLgtBritLvlSetReqLED11(1)//亮度
                        .setILSAmbLgtBritLvlSetReqEnblLED11(1)//亮度使能
                        .setILSAmbLgtRedColrValSetReqLED11(1)//红色
                        .setILSAmbLgtGrenColrValSetReqLED11(1)//绿色
                        .setILSAmbLgtBluColrValSetReqLED11(1)//蓝色
                        .setILSAmbLgtRGBColrValSetReqEnblLED11(1)//颜色使能

                ).build()

        val req = SomeIpData(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_ILS_AMBIENT_LIGHT_SEVICE_CONTROL,
            System.currentTimeMillis(),
            ambientLightControl.toByteArray()
        )

        val resp = SomeIpData()

        val res = someIpClientProxy?.setAttribute(req, resp)
        Log.i(TAG, "sendReq2Server: lightControl")

        if (res == ResultValue.OK) {
            Log.i(TAG, "setLightControl: OK")
            return
        }

        Log.i(TAG, "setLightControl: FAILED")
    }

    fun getAmbientLedControlAvailable() {
        if (!isServerAvailable || !isReady) {
            Log.i(
                TAG,
                "getAmbientLedControlAvailable: failed, server is not available or client is not ready"
            )
            return
        }

        val resp = SomeIpData()
        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_LED_CONTROL_AVAILABILITY, resp
        )

        Log.i(TAG, "sendReq2Server: getAmbientLedControlAvailable")
        if (res == ResultValue.OK) {
            val result =
                SomeipS2SManagementInterface.LED_Control_AvailabilityField.parseFrom(resp.payload)
            Log.i(
                TAG,
                "ilsAmbLgtLED1AvbtyBCM: " + result.outPut.ilsAmbLgtLED1AvbtyBCM + "ilsAmbLgtLED2AvbtyBCM: " + result.outPut.ilsAmbLgtLED2AvbtyBCM + "ilsAmbLgtLED3AvbtyBCM: " + result.outPut.ilsAmbLgtLED3AvbtyBCM + "ilsAmbLgtLED4AvbtyBCM: " + result.outPut.ilsAmbLgtLED4AvbtyBCM + "ilsAmbLgtLED5AvbtyBCM: " + result.outPut.ilsAmbLgtLED5AvbtyBCM
            )
            return
        }

        Log.i(TAG, "FAILED: getAmbientLedControlAvailable callback")
        return
    }

    fun getLed1AndLed2Status() {
        if (!isServerAvailable || !isReady) {
            Log.i(
                TAG, "getLed1AndLed2Status: failed, server is not available or client is not ready"
            )
            return
        }

        val resp = SomeIpData()
        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_LED1_LED2_STATUS, resp
        )

        Log.i(TAG, "sendReq2Server: getLed1AndLed2Status")
        if (res == ResultValue.OK) {
            val result = SomeipS2SManagementInterface.LED1_LED2_StatusField.parseFrom(resp.payload)
            Log.i(TAG, "SUCCESS: getLed1AndLed2Status callback")
            return
        }

        Log.i(TAG, "FAILED: getLed1AndLed2Status callback")
        return
    }

    fun getLed3AndLed4Status() {
        if (!isServerAvailable || !isReady) {
            Log.i(
                TAG, "getLed3AndLed4Status: failed, server is not available or client is not ready"
            )
            return
        }

        val resp = SomeIpData()
        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_LED3_LED4_STATUS, resp
        )

        Log.i(TAG, "sendReq2Server: getLed3AndLed4Status")
        if (res == ResultValue.OK) {
            val result = SomeipS2SManagementInterface.LED3_LED4_StatusField.parseFrom(resp.payload)
            Log.i(TAG, "SUCCESS: getLed3AndLed4Status callback")
            return
        }

        Log.i(TAG, "FAILED: getLed3AndLed4Status callback")
        return
    }

    fun getLed5AndLed6Status() {
        if (!isServerAvailable || !isReady) {
            Log.i(
                TAG, "getLed5AndLed6Status: failed, server is not available or client is not ready"
            )
            return
        }

        val resp = SomeIpData()
        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_LED5_LED6_STATUS, resp
        )

        Log.i(TAG, "sendReq2Server: getLed5AndLed6Status")
        if (res == ResultValue.OK) {
            val result = SomeipS2SManagementInterface.LED5_LED6_StatusField.parseFrom(resp.payload)
            Log.i(TAG, "SUCCESS: getLed5AndLed6Status callback")
            return
        }

        Log.i(TAG, "FAILED: getLed5AndLed6Status callback")
        return
    }

    fun getLed7AndLed8Status() {
        if (!isServerAvailable || !isReady) {
            Log.i(
                TAG, "getLed7AndLed8Status: failed, server is not available or client is not ready"
            )
            return
        }

        val resp = SomeIpData()
        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_LED7_LED8_STATUS, resp
        )

        Log.i(TAG, "sendReq2Server: getLed7AndLed8Status")
        if (res == ResultValue.OK) {
            val result = SomeipS2SManagementInterface.LED7_LED8_StatusField.parseFrom(resp.payload)
            Log.i(TAG, "SUCCESS: getLed7AndLed8Status callback")
            return
        }

        Log.i(TAG, "FAILED: getLed7AndLed8Status callback")
        return
    }

    fun getLed9AndLed10Status() {
        if (!isServerAvailable || !isReady) {
            Log.i(
                TAG, "getLed9AndLed10Status: failed, server is not available or client is not ready"
            )
            return
        }

        val resp = SomeIpData()
        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_LED9_LED10_STATUS, resp
        )

        Log.i(TAG, "sendReq2Server: getLed9AndLed10Status")
        if (res == ResultValue.OK) {
            val result = SomeipS2SManagementInterface.LED9_LED10_StatusField.parseFrom(resp.payload)
            Log.i(TAG, "SUCCESS: getLed9AndLed10Status callback")
            return
        }

        Log.i(TAG, "FAILED: getLed9AndLed10Status callback")
        return
    }

    fun getLed11Status() {
        if (!isServerAvailable || !isReady) {
            Log.i(TAG, "getLed11Status: failed, server is not available or client is not ready")
            return
        }

        val resp = SomeIpData()
        val res = someIpClientProxy?.getAttribute(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_LED11_STATUS, resp
        )

        Log.i(TAG, "sendReq2Server: getLed11Status")
        if (res == ResultValue.OK) {
            val result = SomeipS2SManagementInterface.LED11_StatusField.parseFrom(resp.payload)
            Log.i(TAG, "SUCCESS: getLed11Status callback")
            return
        }

        Log.i(TAG, "FAILED: getLed11Status callback")
        return
    }


}