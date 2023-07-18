/*
 * Copyright (C) GM Global Technology Operations LLC 2022.
 * All Rights Reserved.
 * GM Confidential Restricted.
 */

package com.patac.tester

import android.util.Log
import com.google.rpc.Code
import com.google.rpc.Status
import com.patac.tester.util.extractCode
import com.patac.tester.util.subscribeToTopic
import com.patac.tester.util.unsubscribeFromTopic
import com.ultifi.core.UltifiLink
import com.ultifi.core.common.util.StatusUtils.buildStatus
import com.ultifi.core.usubscription.v1.Subscription
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.ConcurrentHashMap

class SubscriptionRegistry(
    private val ultifiLink: UltifiLink
) {
    private val subscribedTopics = ConcurrentHashMap<String, SubscriptionCounter>()
    private val mutexPerTopic = ConcurrentHashMap<String, Mutex>()

    suspend fun subscribe(topic: String): Status = withTopicLock(topic) {
        val sub = subscribedTopics[topic]?.let {
            it.count++
            it.subscription
        } ?: run {
            val newSub = ultifiLink.subscribeToTopic(ultifiLink.getClientUri(), topic)
            if (newSub.status.code == Code.OK) {
                subscribedTopics[topic] = SubscriptionCounter(newSub, 1)
            }
            newSub
        }
        buildStatus(sub.status.code)
    }

    suspend fun unsubscribe(topic: String): Boolean = withTopicLock(topic) {
        val newCount = subscribedTopics[topic]?.let { counter ->
            --counter.count
        }
        if (newCount == 0) {
            val status = unsubscribeAndRemoveTopicFromRegistry(topic)
            status.extractCode() == Code.OK
        } else {
            Log.v("SubscriptionRegistry","Called unsubscribed but this subscription is still being used")
            false
        }
    }

    private suspend inline fun <T> withTopicLock(topic: String, action: () -> T): T {
        val mutex = mutexPerTopic.computeIfAbsent(topic) { Mutex() }
        return try {
            mutex.withLock(action = action)
        } finally {
            if (!mutex.isLocked) {
                mutexPerTopic.remove(topic)
            }
        }
    }

    private suspend fun unsubscribeAndRemoveTopicFromRegistry(topic: String): Status {
        return subscribedTopics[topic]?.let {
            val status = ultifiLink.unsubscribeFromTopic(it.subscription)
            if (status.extractCode() == Code.OK) {
                subscribedTopics.remove(topic)
            }
            status
        } ?: buildStatus(Code.NOT_FOUND)
    }

    private data class SubscriptionCounter(val subscription: Subscription, var count: Int)
}