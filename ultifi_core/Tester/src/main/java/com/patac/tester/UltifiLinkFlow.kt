/*
 * Copyright (C) GM Global Technology Operations LLC 2022.
 * All Rights Reserved.
 * GM Confidential Restricted.
 */

package com.patac.tester

import android.content.Context
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import com.gm.ultifi.sdk.uprotocol.cloudevent.factory.UCloudEvent.getPayload
import com.google.protobuf.InvalidProtocolBufferException
import com.google.protobuf.Message
import com.google.rpc.Code
import com.patac.tester.util.extractCode
import com.ultifi.core.UltifiLink
import com.ultifi.core.common.util.StatusUtils
import io.cloudevents.CloudEvent
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow.DROP_OLDEST
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.flow.*
import javax.inject.Singleton

@Singleton
class UltifiLinkFlow constructor(private val context: Context) {
    var subscriptionRegistry: SubscriptionRegistry
    private val ultifiLinkConnectionFlow = MutableSharedFlow<UltifiLinkConnection>(
        replay = 1,
        onBufferOverflow = DROP_OLDEST
    )
    private val ultifiLink: UltifiLink

    val TAG = "TAG"

    init {
        val handlerThread = HandlerThread("UltifiBusConnectionThread")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        Log.v(TAG, "Creating the bus")
        ultifiLink =
            UltifiLink.create(context, handler, object : UltifiLink.ServiceLifecycleListener {
                override fun onLifecycleChanged(link: UltifiLink?, ready: Boolean) {
                    Log.d(TAG, "receiving link connection lifecycle event. IsReady: $ready")
                    link?.let {
                        ultifiLinkConnectionFlow.tryEmit(UltifiLinkConnection(link, ready))
                    }
                }

            })
        subscriptionRegistry = SubscriptionRegistry(ultifiLink)
        ultifiLinkConnectionFlow.subscriptionCount
            .filter { nbSubscribers ->
                nbSubscribers <= 1
            }.distinctUntilChanged()
            .onEach { nbSubscribers ->
                if (nbSubscribers == 0 && ultifiLink.isConnected()) {
                    Log.d(TAG, "Calling UltifiLink disconnect. Nb of Subscribers: $nbSubscribers")
                    ultifiLink.disconnect()
                } else if (nbSubscribers == 1 && !ultifiLink.isConnected() && !ultifiLink.isConnecting()) {
                    Log.d(TAG, "Calling UltifiLink connect: $nbSubscribers")
                    ultifiLink.connect()
                }
            }.onStart { Log.d(TAG, "Started to listen to subscription count") }
            .launchIn(CoroutineScope(Dispatchers.IO))
    }

    /**
     * Provides a stream of UltifiLink. When a new connection event occurs, the downstream is
     * automatically cancelled. A connection event will occur when
     * [UltifiLink.ServiceLifecycleListener] is notified of any change.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    fun asFlow(): Flow<UltifiLink> {
        return ultifiLinkConnectionFlow.asSharedFlow().flatMapLatest {
            return@flatMapLatest if (it.ultifiLink.isConnected() && it.isReady) {
                flowOf(it.ultifiLink)
            } else {
                emptyFlow()
            }
        }
    }

    suspend fun <T> invoke(block: suspend (UltifiLink) -> T): T {
        return asFlow().map {
            block(it)
        }.first()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    inline fun <reified T : Message> subscribe(
        uTopicSpec: String,
    ): Flow<Result<T>> {
        return asFlow().onEach { Log.d(TAG, "Proving an Ultifi Link") }
            .flatMapLatest { ultifiLink: UltifiLink ->
                registerToTopicFlow<T>(ultifiLink, uTopicSpec)
                    .catch { error ->
                        emit(Result.failure(error))
                    }
            }.takeWhile { it.isSuccess }
    }

    suspend inline fun <reified T : Message> registerToTopicFlow(
        ultifiLink: UltifiLink,
        topicSpec: String
    ): Flow<Result<T>> {
        return callbackFlow<Result<T>> {
            val eventListener = object : UltifiLink.EventListener {
                override fun onEvent(event: CloudEvent) {
                    Log.e(TAG, "onEvent   $event")
                    if (!ultifiLink.isConnected()) {
                        Log.e(TAG, "UltifiLink is not connected. discarding event $event")
                        return
                    }
                    Log.e(TAG, "UltifiLink   $event")

                    try {
                        val unpackedValue = getPayload(event).unpack(T::class.java)
                        trySend(Result.success(unpackedValue)).onFailure {
                            Log.e(
                                TAG,
                                "Downstream has been cancelled or failed when attempting " +
                                        "to send value",
                                it
                            )
                        }
                    } catch (e: InvalidProtocolBufferException) {
                        Log.e(TAG, "Failed to read cloud event", e)
                        trySend(
                            Result.failure(
                                CancellationException(
                                    "Failed to read cloud event",
                                    e
                                )
                            )
                        )
                    }
                }
            }

            val subscriptionStatus = subscriptionRegistry.subscribe(topicSpec)
            val subscriptionCode = subscriptionStatus.extractCode()
            if (subscriptionCode != Code.OK) {
                cancel(
                    CancellationException(
                        "Failure to subscribe to this topic ${topicSpec}: " +
                                "Code = $subscriptionCode " +
                                "Details = ${topicSpec}"
                    )
                )
            } else {
                Log.e(TAG, "Subscribed to topic ${topicSpec}")
                val status = ultifiLink.registerEventListener(topicSpec, eventListener)
                val code = StatusUtils.getCode(status, Code.UNKNOWN)
                if (code == Code.NOT_FOUND) {
                    cancel(
                        CancellationException(
                            "The specified topic (${topicSpec}) could not be found."
                        )
                    )
                } else {
                    Log.v(TAG, "Registered to topic: ${topicSpec}")
                }
            }
            awaitClose {
                ultifiLink.unregisterEventListener(topicSpec, eventListener)
                Log.v(TAG, "Unsubscribed from topic ${topicSpec}")
            }
        }.onCompletion {
            subscriptionRegistry.unsubscribe(topicSpec)
        }.flowOn(Dispatchers.IO)
    }


    class UltifiLinkConnection(val ultifiLink: UltifiLink, val isReady: Boolean)
}



