package com.gm.ultifi.service.access.someip

import android.util.Log
import com.gm.ultifi.sdk.uprotocol.cloudevent.datamodel.UCloudEventAttributes
import com.gm.ultifi.sdk.uprotocol.cloudevent.factory.CloudEventFactory
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UAuthority
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UResource
import com.gm.ultifi.sdk.uprotocol.uri.factory.UltifiUriFactory
import com.gm.ultifi.service.AccessService
import com.gm.ultifi.service.access.response.mapper.BaseMapper
import com.gm.ultifi.service.access.utils.ResourceMappingConstants
import com.google.protobuf.Any
import com.ultifi.vehicle.body.access.v1.Sunroof
import plugin.SomeIpTopic
import plugin.SomeipS2SManagementInterface
import ts.car.someip.sdk.common.ResultValue
import ts.car.someip.sdk.common.SomeIpData

open class SunroofViewModel : BaseAppViewModel() {
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
        }
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_SERVICE_1_NOT_AVAILABLE) {
            Log.i(TAG, "SERVICE_NOT_AVAILABLE")
            isServerAvailable = false
        }

        // notify subscriber the change of sunroof status
        if (data.topic == SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SUNROOF_STATUS) {
            val resp = SomeipS2SManagementInterface.Sunroof_Status.parseFrom(data.payload)
            val sunroofPercentagePositionStatus = resp.snrfPctPosSts

            Log.d(
                TAG,
                "onChangeEvent, PropName: sunroofPercentagePositionStatus, newPropValue:$sunroofPercentagePositionStatus"
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
    }

    override fun doClientOk() {
        // check if the service is available
        someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_SERVICE_1_AVAILABLE)
        someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_SERVICE_1_NOT_AVAILABLE)

        someIpClientProxy?.subscribe(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_NOTIFY_SUNROOF_STATUS)
    }

    override fun doStartClient(): Int? {
        // subscribe server's service
        return startClient(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_SERVICE_1_ID)
    }

    override fun doStopClient() {
        stopClient(SomeIpTopic.S2S_MANAGEMENT_INTERFACE_SERVICE_1_ID)
    }

    fun setSunroofPosition(newPosition: Int) {
        // check the connection of someip
        if (!isServerAvailable || !isReady) {
            Log.i(TAG, "setSunroofPosition: failed, server is not available or client is not ready")
            return
        }

        val newSunroofPosition = SomeipS2SManagementInterface
            .Sunroof_Sunshade_Control_Request.newBuilder()
            .setSnrfPctCtrlReqSrv(newPosition)
            .build()

        val req = SomeIpData(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_SET_SUNROOF_SUNSHADE_CONTROL_REQUEST,
            System.currentTimeMillis(),
            newSunroofPosition.toByteArray()
        )

        val resp = SomeIpData()

        val res = someIpClientProxy?.request(req, resp)
        Log.i(TAG, "sendReq2Server: new position $newPosition")

        if (res == ResultValue.OK) {
            Log.i(TAG, "setSunroofPosition callback: " + resp.payload[0])
        }
    }

    fun getSunroofStatus(): SomeipS2SManagementInterface.Sunroof_Status? {
        // check the connection of someip
        if (!isServerAvailable || !isReady) {
            Log.i(TAG, "getSunroofStatus: failed, server is not available or client is not ready")
            return null
        }

        val req = SomeIpData(
            SomeIpTopic.S2S_MANAGEMENT_INTERFACE_1_GET_SUNROOF_STATUS,
            System.currentTimeMillis(),
            ByteArray(0)
        )

        val resp = SomeIpData()

        val res = someIpClientProxy?.request(req, resp)

        Log.i(TAG, "sendReq2Server: getSunroofStatus")
        if (res == ResultValue.OK) {
            Log.i(TAG, "setSunroofPosition callback: " + resp.payload[0])
            return SomeipS2SManagementInterface.Sunroof_Status.parseFrom(resp.payload)
        }

        Log.i(TAG, "setSunroofPosition callback: FAILED")
        return null;
    }

}