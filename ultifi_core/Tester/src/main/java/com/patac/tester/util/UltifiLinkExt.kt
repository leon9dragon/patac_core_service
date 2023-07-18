/*
 * Copyright (C) GM Global Technology Operations LLC 2022.
 * All Rights Reserved.
 * GM Confidential Restricted.
 */

package com.patac.tester.util

import com.google.protobuf.Message
import com.google.rpc.Status
import com.ultifi.core.Topic
import com.ultifi.core.UltifiLink
import com.ultifi.core.rpc.Request
import com.ultifi.core.usubscription.v1.*
import kotlinx.coroutines.future.await
import kotlinx.coroutines.withTimeout

suspend inline fun <reified T : Message> UltifiLink.send(
    request: Request,
    timeout: Long = request.timeout().toLong()
): T {
    return withTimeout(timeout) {
        if (!isConnected()) {
            throw IllegalStateException("The UltifiLink is not connected. Request was not sent.")
        }
        val response = invokeMethod(request).await()
        response.unpack(T::class.java)
    }
}

suspend inline fun UltifiLink.subscribeToTopic(
    clientUri: String,
    topic: String
): Subscription {
    val request = SubscriptionRequest.newBuilder()
        .setTopic(Topic.newBuilder().setUri(topic).build())
        .setSubscriber(SubscriberInfo.newBuilder().setUri(clientUri).build())
        .build()
    return USubscription.newFutureStub(this).subscribe(request).await()
}

suspend inline fun UltifiLink.unsubscribeFromTopic(
    subscription: Subscription
): Status {
    val request = UnsubscribeRequest.newBuilder()
        .setSubscription(subscription)
        .build()
    return USubscription.newFutureStub(this).unsubscribe(request).await()
}