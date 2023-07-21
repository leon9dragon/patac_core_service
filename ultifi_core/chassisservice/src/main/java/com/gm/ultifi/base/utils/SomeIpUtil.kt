package com.gm.ultifi.base.utils

import com.gm.ultifi.base.monitor.UltifiLinkMonitor
import com.gm.ultifi.sdk.uprotocol.cloudevent.datamodel.UCloudEventAttributes
import com.gm.ultifi.sdk.uprotocol.cloudevent.factory.CloudEventFactory
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UAuthority
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UEntity
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UResource
import com.gm.ultifi.sdk.uprotocol.uri.factory.UltifiUriFactory
import com.google.protobuf.Any
import com.google.protobuf.Message

class SomeIpUtil {
    companion object {
        fun pubEvent(
            message: Message,
            uEntity: UEntity,
            uResource: UResource,
            ultifiLinkMonitor: UltifiLinkMonitor
        ) {
            val topicUri = UltifiUriFactory.buildUProtocolUri(
                UAuthority.local(),
                uEntity,
                uResource
            )

            val cloudEvent = CloudEventFactory.publish(
                topicUri,
                Any.pack(message),
                UCloudEventAttributes.empty()
            );

            ultifiLinkMonitor.publish(cloudEvent)
        }
    }
}