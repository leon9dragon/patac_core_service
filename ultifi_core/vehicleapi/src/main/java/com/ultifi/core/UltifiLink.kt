package com.ultifi.core

import android.content.*
import android.os.*
import android.util.ArraySet
import android.util.Log
import androidx.annotation.GuardedBy
import androidx.annotation.NonNull
import com.gm.ultifi.sdk.uprotocol.cloudevent.datamodel.UCloudEventAttributes
import com.gm.ultifi.sdk.uprotocol.cloudevent.datamodel.UCloudEventAttributes.UCloudEventAttributesBuilder
import com.gm.ultifi.sdk.uprotocol.cloudevent.datamodel.UCloudEventType
import com.gm.ultifi.sdk.uprotocol.cloudevent.factory.CloudEventFactory
import com.gm.ultifi.sdk.uprotocol.cloudevent.factory.UCloudEvent
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UAuthority
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UEntity
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UResource
import com.gm.ultifi.sdk.uprotocol.uri.factory.UltifiUriFactory
import com.google.protobuf.Any
import com.google.protobuf.Message
import com.google.rpc.Code
import com.google.rpc.Status
import com.patac.service_bus.CloudEventProto
import com.patac.service_bus.IEventListener
import com.patac.service_bus.IUBusInterface
import com.patac.service_bus.StatusProto
import com.ultifi.core.common.StatusException
import com.ultifi.core.common.util.StatusUtils
import com.ultifi.core.rpc.Request
import com.ultifi.core.usubscription.v1.Subscription
import com.ultifi.core.usubscription.v1.USubscription
import io.cloudevents.CloudEvent
import io.cloudevents.core.provider.EventFormatProvider
import java.lang.ref.WeakReference
import java.util.concurrent.*
import java.util.function.Consumer


class UltifiLink private constructor(
    private var mContext: Context,
    private var mExecutor: Executor?,
    private var mLifecycleListener: ServiceLifecycleListener?
) {

    private val mBinder: Binder = Binder()

    private val mClientUri: String

    private val mResponseUri: String

    private val mConnectionLock: kotlin.Any = Any()

    @GuardedBy("mConnectionLock")
    private var mIUBus: IUBusInterface? = null

    @GuardedBy("mConnectionLock")
    private var mIsUBusConnected = false

    @GuardedBy("mConnectionLock")
    private var mConnectionFlag: Int = 0
    private val mUBusEventListener: InnerEventListener
    private val mCompletableFutures: ConcurrentHashMap<String, CompletableFuture<Any>> = ConcurrentHashMap()

    private val mRegistrationLock: kotlin.Any = Any()

    @GuardedBy("mRegistrationLock")
    private val mCachedEvents: MutableMap<String, CloudEvent> = HashMap()

    @GuardedBy("mRegistrationLock")
    private val mRequestListeners: Map<String, RequestListener> = HashMap()

    @GuardedBy("mRegistrationLock")
    private val mRequestEventListeners: Map<String, RequestEventListener> = HashMap()

    @GuardedBy("mRegistrationLock")
    private val mEventListeners: Map<String, Set<EventListener?>> = HashMap()

    private val mServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
            val iUBus: IUBusInterface? = IUBusInterface.Stub.asInterface(iBinder)
            if (iUBus != null) {
                if (mIUBus != null && mIUBus!!.asBinder() == iUBus.asBinder()) {
                    Log.d(TAG, "Service is already connected")
                    return
                }
                mConnectionFlag = 2
                mIUBus = iUBus

                registerToUBus()
                this@UltifiLink.mLifecycleListener?.onLifecycleChanged(this@UltifiLink, true)
            }
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            if (mConnectionFlag == 0) {
                return
            }
            Log.w(TAG, "Service unexpectedly disconnected")

            resetConnectionState()
            clearAllListeners()
            this@UltifiLink.mLifecycleListener?.onLifecycleChanged(this@UltifiLink, false)
        }

        override fun onNullBinding(name: ComponentName?) {
            super.onNullBinding(name)
            Log.wtf(TAG, "Null binding to service: $name", RuntimeException())
            synchronized(this@UltifiLink.mConnectionLock) {
                resetConnectionState()
                this@UltifiLink.mLifecycleListener?.onLifecycleChanged(this@UltifiLink, false)
            }
        }
    }

    init {
        val uEntity = UEntity.fromName(mContext.packageName)
        mClientUri =
            UltifiUriFactory.buildUProtocolUri(UAuthority.local(), uEntity, UResource.empty())
        mResponseUri = UltifiUriFactory.buildUriForRpc(UAuthority.local(), uEntity)
        if (mExecutor == null) {
            mExecutor = mContext.mainExecutor
        }
        if (mLifecycleListener == null) {
            mLifecycleListener = DEFAULT
        }

        mUBusEventListener = InnerEventListener(this)
        // TODO: 2023/4/26 sometimes init failed
        if (EventFormatProvider.getInstance()
                .resolveFormat("application/cloud-events+protobuf") == null) {
            Log.e(TAG, "Failed to initialize protobuf serializer")
        }
    }

    fun connect() {
        synchronized(mConnectionLock) {
            if (mConnectionFlag == 0) {
                mConnectionFlag = 1
                val intent = Intent()
                intent.component = ComponentName(UBUS_PKG_NAME, UBUS_SVC_NAME)
                val result = mContext.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
                Log.d("Ultifi", "bind service result: $result")
                mIsUBusConnected = true
            }
        }
    }

    fun disconnect() {
        clearAllListeners()
        synchronized(mConnectionLock) {
            resetConnectionState()
            if (mIsUBusConnected) {
                mContext.unbindService(mServiceConnection)
                mIsUBusConnected = false
            }
        }
    }

    fun isConnected(): Boolean {
        synchronized(mConnectionLock) {
            return mIUBus != null
        }
    }

    fun isConnecting(): Boolean {
        synchronized(mConnectionLock) {
            return mConnectionFlag == 1
        }
    }

    fun getClientUri(): String {
        return mClientUri
    }

    fun getResponseUri(): String {
        return mResponseUri
    }

    @Deprecated("since = \"1.1.0\"")
    fun invokeMethod(request: Request): CompletableFuture<Any> {
        return try {
            StatusUtils.checkNotNull(request, "Request is null")

            invokeMethod(
                CloudEventFactory.request(
                    mResponseUri, request.methodUri(), request.payload(),
                    UCloudEventAttributesBuilder()
                        .withTtl(request.timeout())
                        .withPriority(UCloudEventAttributes.Priority.REALTIME_INTERACTIVE)
                        .withToken(request.token().orElse(""))
                        .build()
                )
            )
        } catch (exception: Exception) {
            CompletableFuture.completedFuture(
                Any.pack(
                    StatusUtils.throwableToStatus(null) as Message
                )
            )
        }
    }

    fun invokeMethod(event: CloudEvent): CompletableFuture<Any> {
        return try {
            StatusUtils.checkNotNull(event, "Event is null")
            StatusUtils.checkStringEquals(
                event.type, UCloudEventType.REQUEST.type(),
                "Event type is not '" + UCloudEventType.REQUEST.type() + "'"
            )

            val ttl = StatusUtils.checkArgumentPositive(
                (UCloudEvent.getTtl(event).orElse(-1) as Int),
                "Timeout is invalid or missing"
            )

            mCompletableFutures.compute(event.id) { requestId, future ->
                StatusUtils.checkArgument(
                    (future == null),
                    Code.ABORTED,
                    "Duplicated request found"
                )
                val status: Status = send(event)
                if (!StatusUtils.isOk(status)) {
                    if (VERBOSE_LOGGABLE) {
                        Log.v(TAG,
                            "Request{id='$requestId'} failed to send: " + StatusUtils.toShortString(status))
                    }
                    throw StatusException(status)
                }
                if (VERBOSE_LOGGABLE) {
                    Log.v(TAG, "Request{id='$requestId'} sent")
                }
                createTimeoutCompletableFuture(requestId, ttl)
            }!!
        } catch (exception: Exception) {
            exception.printStackTrace()
            CompletableFuture.completedFuture(
                Any.pack(StatusUtils.throwableToStatus(null) as Message)
            )
        }
    }

    fun publish(event: CloudEvent): Status {
        return send(event)
    }

    fun send(event: CloudEvent): Status {
        return try {
            StatusUtils.checkNotNull(event, "Event is null")
            StatusProto.parseOrThrow(getIUBus().send(CloudEventProto.from(event), mBinder))
        } catch (exception: java.lang.Exception) {
            StatusUtils.throwableToStatus(null)
        }
    }

    @Deprecated("since = \"1.1.0\"")
    fun registerRequestListener(
        methodUri: String,
        listener: RequestListener
    ): Status? {
        return try {
            StatusUtils.checkStringNotEmpty(methodUri, "Method URI is empty")
            StatusUtils.checkNotNull(listener, "Listener is null")

            val iUBus: IUBusInterface = getIUBus()
            synchronized(mRegistrationLock) {
                val requestListener: RequestListener? = mRequestListeners[methodUri]
                if (requestListener == listener) {
                    return StatusUtils.STATUS_OK
                }

                StatusUtils.checkArgument(
                    requestListener == null,
                    Code.ALREADY_EXISTS,
                    "Listener is already registered"
                )

                StatusUtils.checkArgument(
                    !mRequestEventListeners.containsKey(methodUri),
                    Code.ALREADY_EXISTS, "Listener is already registered"
                )

                val status: Status = enableEventsDispatching(iUBus, methodUri)
                if (StatusUtils.isOk(status)) {
                    (mRequestListeners as HashMap)[methodUri] = listener
                }
                return status
            }
        } catch (exception: java.lang.Exception) {
            StatusUtils.throwableToStatus(null)
        }
    }

    @Deprecated("since = \"1.1.0\"")
    fun unregisterRequestListener(methodUri: String): Status {
        return try {
            StatusUtils.checkStringNotEmpty(methodUri, "Method URI is empty")

            val iUBus: IUBusInterface = getIUBus()
            synchronized(mRegistrationLock) {
                if ((mRequestListeners as HashMap).remove(methodUri) != null) {
                    disableEventsDispatching(iUBus, methodUri)
                }
                return StatusUtils.STATUS_OK
            }
        } catch (exception: java.lang.Exception) {
            StatusUtils.throwableToStatus(null)
        }
    }

    fun registerEventListener(
        methodUri: String,
        listener: RequestEventListener
    ): Status {
        return try {
            StatusUtils.checkStringNotEmpty(methodUri, "Method URI is empty")
            StatusUtils.checkNotNull(listener, "Listener is null")

            val iUBus: IUBusInterface = getIUBus()
            synchronized(mRegistrationLock) {
                val requestEventListener: RequestEventListener? = mRequestEventListeners[methodUri]
                if (requestEventListener == listener) {
                    return StatusUtils.STATUS_OK
                }

                StatusUtils.checkArgument(
                    (requestEventListener == null),
                    Code.ALREADY_EXISTS,
                    "Listener is already registered"
                )

                StatusUtils.checkArgument(
                    !mRequestListeners.containsKey(methodUri),
                    Code.ALREADY_EXISTS,
                    "Listener is already registered"
                )

                val status: Status = enableEventsDispatching(iUBus, methodUri)
                if (StatusUtils.isOk(status)) {
                    (mRequestEventListeners as HashMap)[methodUri] = listener
                }
                status
            }
        } catch (exception: java.lang.Exception) {
            StatusUtils.throwableToStatus(null)
        }
    }

    fun unregisterEventListener(
        methodUri: String,
        listener: RequestEventListener
    ): Status? {
        return try {
            StatusUtils.checkStringNotEmpty(methodUri, "Method URI is empty")
            StatusUtils.checkNotNull(listener, "Listener is null")

            val iUBus: IUBusInterface = getIUBus()
            synchronized(mRegistrationLock) {
                val removedListener = (mRequestEventListeners as HashMap).remove(methodUri)
                if (removedListener != null) {
                    disableEventsDispatching(iUBus, methodUri)
                } else {
                    StatusUtils.STATUS_OK
                }
            }
        } catch (exception: java.lang.Exception) {
            StatusUtils.throwableToStatus(null)
        }
    }

    fun unregisterEventListener(listener: RequestEventListener): Status? {
        return try {
            StatusUtils.checkNotNull(listener, "Listener is null")

            val iUBus: IUBusInterface = getIUBus()
            synchronized(mRegistrationLock) {
                (mRequestEventListeners as HashMap).keys.removeIf {
                    if (mRequestEventListeners[it] != listener) {
                        return@removeIf false
                    }

                    disableEventsDispatching(iUBus, it)
                    return@removeIf true
                }
                StatusUtils.STATUS_OK
            }
        } catch (exception: java.lang.Exception) {
            StatusUtils.throwableToStatus(null)
        }
    }

    fun registerEventListener(
        topic: String,
        listener: EventListener
    ): Status {
        return try {
            StatusUtils.checkStringNotEmpty(topic, "Topic is empty")
            StatusUtils.checkNotNull<kotlin.Any>(listener, "Listener is null")

            val iUBus: IUBusInterface = getIUBus()
            synchronized(mRegistrationLock) {
                var set = mEventListeners[topic]
                if (set == null) {
                    set = HashSet()
                }
                if (set.isEmpty()) {
                    val status: Status = enableEventsDispatching(iUBus, topic)
                    if (!StatusUtils.isOk(status)) {
                        return status
                    }
                    (mEventListeners as HashMap)[topic] = set
                } else {
                    if ((set as HashSet).add(listener)) {
                        dispatchLatestCloudEvent(mCachedEvents[topic]!!, listener)
                    }
                }
                return StatusUtils.STATUS_OK
            }
        } catch (exception: java.lang.Exception) {
            StatusUtils.throwableToStatus(null)
        }
    }

    fun unregisterEventListener(
        topic: String,
        listener: EventListener
    ): Status {
        return try {
            StatusUtils.checkStringNotEmpty(topic, "Topic is empty")
            StatusUtils.checkNotNull<kotlin.Any>(listener, "Listener is null")

            val iUBus: IUBusInterface = getIUBus()
            synchronized(mRegistrationLock) {
                if (removeEventListener(iUBus, topic, listener)) {
                    (mEventListeners as HashMap).remove(topic)
                }
                return StatusUtils.STATUS_OK
            }
        } catch (exception: java.lang.Exception) {
            StatusUtils.throwableToStatus(null)
        }
    }

    fun unregisterEventListener(listener: EventListener): Status {
        return try {
            StatusUtils.checkNotNull<kotlin.Any>(listener, "Listener is null")

            val iUBus: IUBusInterface = getIUBus()
            synchronized(mRegistrationLock) {
                (mEventListeners as HashMap).keys.removeIf {
                    removeEventListener(iUBus, it, listener)
                }
            }

            StatusUtils.STATUS_OK
        } catch (exception: java.lang.Exception) {
            StatusUtils.throwableToStatus(null)
        }
    }

    private fun registerToUBus() {
        synchronized(mConnectionLock) {
            val iUBus: IUBusInterface = mIUBus ?: return

            synchronized(mRegistrationLock) {
                val status = try {
                    StatusProto.parseOrThrow(
                        iUBus.registerClient(mClientUri, mBinder, mUBusEventListener)
                    )
                } catch (exception: java.lang.Exception) {
                    StatusUtils.throwableToStatus(exception)
                }

                if (StatusUtils.isOk(printStatus("Register client", status))) {
                    mRequestListeners.keys.forEach(Consumer {
                        enableEventsDispatching(iUBus, it)
                    })
                    mRequestEventListeners.keys.forEach(Consumer {
                        enableEventsDispatching(iUBus, it)
                    })
                }
            }
        }
    }

    private fun clearAllListeners() {
        val iUBus = getIUBus()
        synchronized(mRegistrationLock) {
            mCompletableFutures.clear()
            mCachedEvents.clear()
            mRequestListeners.keys.forEach(Consumer {
                disableEventsDispatching(iUBus, it)
            })
            (mRequestListeners as HashMap).clear()

            mRequestEventListeners.keys.forEach(Consumer {
                disableEventsDispatching(iUBus, it)
            })
            (mRequestEventListeners as HashMap).clear()

            mEventListeners.keys.forEach(Consumer {
                disableEventsDispatching(iUBus, it)
            })
            (mEventListeners as HashMap).clear()

            val status = try {
                StatusProto.parseOrThrow(iUBus.unregisterClient(mBinder))
            } catch (exception: java.lang.Exception) {
                StatusUtils.throwableToStatus(exception)
            }
            printStatus("Unregister client", status)
        }
    }

    @NonNull
    @Throws(StatusException::class)
    private fun getIUBus(): IUBusInterface {
        synchronized(mConnectionLock) {
            val iUBus: IUBusInterface? = mIUBus
            if (iUBus != null) {
                return iUBus
            }

            throw StatusException(Code.UNAVAILABLE, "Service is not connected")
        }
    }

    @NonNull
    private fun enableEventsDispatching(iUBus: IUBusInterface, uri: String): Status {
        val status: Status = try {
            StatusProto.parseOrThrow(
                iUBus.enableEventsDispatching(uri, null, mBinder)
            )
        } catch (exception: java.lang.Exception) {
            StatusUtils.throwableToStatus(null)
        }
        return printStatus("Enable events dispatching for '$uri'", status)
    }

    @NonNull
    private fun disableEventsDispatching(iUBus: IUBusInterface, uri: String): Status {
        val status: Status = try {
            StatusProto.parseOrThrow(
                iUBus.disableEventsDispatching(uri, null, mBinder)
            )
        } catch (exception: java.lang.Exception) {
            StatusUtils.throwableToStatus(null)
        }
        return printStatus("Disable events dispatching for '$uri'", status)
    }

    private fun dispatchLatestCloudEvent(
        event: CloudEvent,
        listener: EventListener
    ) {
        mExecutor!!.execute {
            val source = UCloudEvent.getSource(event)
            synchronized(mRegistrationLock) {
                if (event == mCachedEvents[source]) {
                    listener.onEvent(event)
                }
            }
        }
    }

    private fun dispatchCloudEvent(cloudEvent: CloudEvent, uCloudEventType: UCloudEventType) {
        when (UCloudEventTypeMapper.mapper[uCloudEventType.ordinal]) {
            1 -> {
                handleRequestEvent(cloudEvent)
            }
            2 -> {
                val requestId: String = UCloudEvent.getRequestId(cloudEvent).orElse("")
                val completableFuture: CompletableFuture<Any>? = mCompletableFutures[requestId]
                if (completableFuture == null) {
                    Log.v(TAG, "Request{id='$requestId'} already completed")
                } else {
                    completableFuture.complete(UCloudEvent.getPayload(cloudEvent))
                }
            }
            3 -> {
                if (USubscription.TOPIC_SUBSCRIPTION.equals(UCloudEvent.getSource(cloudEvent))) {
                    handleSubscriptionEvent(cloudEvent)
                } else {
                    handlePublishEvent(cloudEvent)
                }
            }
            else -> Log.w(TAG, "$cloudEvent skipped: Unknown type")
        }
    }

    @GuardedBy("mConnectionLock")
    private fun resetConnectionState() {
        if (mConnectionFlag == 0) {
            return
        }
        mIUBus = null
        mConnectionFlag = 0
    }

    private fun handleRequestEvent(event: CloudEvent) {
        val source: String = UCloudEvent.getSource(event)
        val sink: String = UCloudEvent.getSink(event).orElse("")
        val requestId: String = event.id
        val ttl = (UCloudEvent.getTtl(event).orElse(Integer.valueOf(-1)) as Int).toInt()

        if (source.isEmpty() || sink.isEmpty() || requestId.isEmpty() || ttl <= 0) {
            Log.w(TAG, "$event skipped: Invalid request")
            return
        }

        mExecutor!!.execute {
            if (VERBOSE_LOGGABLE) {
                Log.v(TAG, "Request{id='$requestId'} received")
            }
            synchronized(mRegistrationLock) {
                val requestEventListener = mRequestEventListeners[sink]
                if (requestEventListener == null) {
                    Log.w(TAG, "$event skipped: No registered listener")
                } else {
                    requestEventListener.onEvent(
                        event,
                        createTimeoutCompletableFuture(source, sink, requestId, ttl)
                    )
                }
                val requestListener = mRequestListeners[sink]
                if (requestListener == null) {
                    Log.w(TAG, "$event skipped: No registered listener")
                } else {
                    val request: Request? = Request.from(event).orElse(null)
                    if (request != null) {
                        requestListener.onRequest(
                            request,
                            createTimeoutCompletableFuture(request)
                        )
                    } else {
                        Log.w(TAG, "$event skipped: Failed to parse request")
                    }
                }
            }
        }
    }

    private fun createTimeoutCompletableFuture(
        request: Request
    ): CompletableFuture<Any> {
        return CompletableFuture<Any>()
            .whenComplete { any: Any, _: Throwable ->
                val attributes = UCloudEventAttributesBuilder()
                    .withPriority(UCloudEventAttributes.Priority.REALTIME_INTERACTIVE)
                    .withTtl(request.timeout())
                    .build()
                val status = send(
                    CloudEventFactory.response(
                        request.responseUri(),
                        request.methodUri(),
                        request.id(),
                        any,
                        attributes
                    )
                )
                if (VERBOSE_LOGGABLE) {
                    if (!StatusUtils.isOk(status)) {
                        Log.v(TAG,
                            "Request{id='${request.id()}'} response failed to send: " + StatusUtils.toShortString(status)
                        )
                    } else {
                        Log.v(TAG, "Request{id='$request.id()'} response sent")
                    }
                }
            }
    }

    private fun createTimeoutCompletableFuture(
        source: String,
        sink: String,
        requestId: String,
        ttl: Int
    ): CompletableFuture<Any> {
        return CompletableFuture<Any>()
            .whenComplete { any, _ ->
                val attributes = UCloudEventAttributesBuilder()
                    .withPriority(UCloudEventAttributes.Priority.REALTIME_INTERACTIVE)
                    .withTtl(ttl)
                    .build()
                val status = send(
                    CloudEventFactory.response(
                        source,
                        sink,
                        requestId,
                        any,
                        attributes
                    )
                )
                if (VERBOSE_LOGGABLE) {
                    if (!StatusUtils.isOk(status)) {
                        Log.v(TAG,
                            "Request{id='$requestId'} response failed to send: " + StatusUtils.toShortString(status))
                    } else {
                        Log.v(TAG, "Request{id='$requestId'} response sent")
                    }
                }
            }
    }

    private fun createTimeoutCompletableFuture(
        requestId: String,
        ttl: Int
    ): CompletableFuture<Any> {
        val any = Any.pack(
            StatusUtils.buildStatus(
                Code.DEADLINE_EXCEEDED,
                "Timeout while waiting for response"
            ) as Message
        )
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            return CompletableFuture<Any>()
                .whenComplete { _, throwable ->
                    if (VERBOSE_LOGGABLE) {
                        val stringBuilder = StringBuilder().append("Request{id='$requestId'} completed")
                        if (throwable != null) {
                            stringBuilder.append(" with exception: " + throwable.message)
                        }
                        Log.v(TAG, stringBuilder.toString())
                    }
                    mCompletableFutures.remove(requestId)
                }
        }
        return CompletableFuture<Any>().completeOnTimeout(any, ttl.toLong(), TimeUnit.MILLISECONDS)
            .whenComplete { _, throwable ->
                if (VERBOSE_LOGGABLE) {
                    val stringBuilder = StringBuilder().append("Request{id='$requestId'} completed")
                    if (throwable != null) {
                        stringBuilder.append(" with exception: " + throwable.message)
                    }
                    Log.v(TAG, stringBuilder.toString())
                }
                mCompletableFutures.remove(requestId)
            }
    }

    private fun handleSubscriptionEvent(event: CloudEvent) {
        val subscription = UCloudEvent.unpack(event, Subscription::class.java).orElse(null)
        if (subscription == null) {
            Log.w(TAG, "$event skipped: Failed to parse subscription")
            return
        }

        mExecutor!!.execute {
            val topic = subscription.topic.uri
            synchronized(mRegistrationLock) {
                val arraySet = ArraySet(mEventListeners[topic])
                if (arraySet.isEmpty()) {
                    Log.w(TAG, "$event skipped: No registered listener")
                } else if (subscription.status.code != Code.OK) {
                    mCachedEvents.remove(topic)
                }

                arraySet.forEach(Consumer {
                    it!!.onEvent(event)
                })
            }
        }
    }

    private fun handlePublishEvent(event: CloudEvent) {
        mExecutor!!.execute {
            val source = UCloudEvent.getSource(event)
            synchronized(mRegistrationLock) {
                val arraySet = ArraySet(mEventListeners[source])
                if (arraySet.isEmpty()) {
                    Log.w(TAG, "$event skipped: No registered listener")
                } else {
                    mCachedEvents[source] = event
                }

                arraySet.forEach(Consumer {
                    it!!.onEvent(event)
                })
            }
        }
    }

    private fun removeEventListener(
        iUBus: IUBusInterface,
        topic: String,
        listener: EventListener
    ): Boolean {
        val set = mEventListeners[topic]
        if (set != null && set.contains(listener) && (set as HashSet).remove(listener) && set.isEmpty()) {
            disableEventsDispatching(iUBus, topic)
            mCachedEvents.remove(topic)
            return true
        }
        return false
    }

    private class InnerExecutor(handler: Handler?) : Executor {
        private val mHandler: Handler

        init {
            var tmpHandler = handler
            if (tmpHandler == null) {
                tmpHandler = Handler(Looper.getMainLooper())
            }
            mHandler = tmpHandler
        }

        override fun execute(runnable: Runnable) {
            if (!mHandler.post(runnable)) {
                throw RejectedExecutionException("$mHandler is shutting down")
            }
        }
    }

    interface ServiceLifecycleListener {
        fun onLifecycleChanged(link: UltifiLink?, ready: Boolean)
    }

    interface EventListener {
        fun onEvent(event: CloudEvent)
    }

    interface RequestEventListener {
        fun onEvent(
            requestEvent: CloudEvent,
            responseFuture: CompletableFuture<Any>
        )
    }

    @Deprecated("")
    interface RequestListener {
        fun onRequest(
            request: Request,
            responseFuture: CompletableFuture<Any>
        )
    }

    private class InnerEventListener constructor(link: UltifiLink) : IEventListener.Stub() {

        private val mUltifiLinkRef: WeakReference<UltifiLink>

        init {
            mUltifiLinkRef = WeakReference(link)
        }

        @Throws(RemoteException::class)
        override fun onEvent(cloudEventProto: CloudEventProto) {
            val ultifiLink = mUltifiLinkRef.get()
            if (ultifiLink != null) {
                val cloudEvent = CloudEventProto.parseOrNull(cloudEventProto)
                if (cloudEvent != null) {
                    UCloudEventType.valueOfType(cloudEvent.type)
                        .ifPresent(EventConsumer(ultifiLink, cloudEvent))
                } else {
                    Log.e(TAG, "Failed to parse event")
                }
            }
        }
    }

    private class EventConsumer constructor(
        val link: UltifiLink,
        val event: CloudEvent
    ) : Consumer<kotlin.Any> {
        override fun accept(t: kotlin.Any) {
            link.dispatchCloudEvent(event, t as UCloudEventType)
        }
    }

    class UCloudEventTypeMapper {
        companion object {
            var mapper: Array<Int> = Array(UCloudEventType.values().size, init = {
                when (it) {
                    UCloudEventType.REQUEST.ordinal -> 1
                    UCloudEventType.RESPONSE.ordinal -> 2
                    UCloudEventType.PUBLISH.ordinal -> 3
                    else -> 0
                }
            })
        }
    }

    companion object {
        private const val TAG: String = "UltifiLink"
        private val DEBUG_LOGGABLE = true;//Log.isLoggable(TAG, Log.DEBUG)
        private val VERBOSE_LOGGABLE = true;//Log.isLoggable(TAG, Log.VERBOSE)

        private const val UBUS_PKG_NAME = "com.patac.service_bus" //"com.ultifi.core.ubus"
        private const val UBUS_SVC_NAME = "com.patac.service_bus.BusService" //"com.ultifi.core.ubus.UBusService"

        private val DEFAULT: ServiceLifecycleListener = object : ServiceLifecycleListener {
            override fun onLifecycleChanged(link: UltifiLink?, ready: Boolean) {
            }
        }

        fun create(
            context: Context,
            handler: Handler?,
            serviceLifecycleListener: ServiceLifecycleListener?
        ): UltifiLink {
            return UltifiLink(
                checkContext(context),
                InnerExecutor(handler),
                serviceLifecycleListener
            )
        }

        fun create(
            paramContext: Context,
            executor: Executor,
            serviceLifecycleListener: ServiceLifecycleListener
        ): UltifiLink {
            return UltifiLink(
                checkContext(paramContext),
                executor,
                serviceLifecycleListener
            )
        }

        private fun checkContext(context: Context): Context {
            StatusUtils.checkNotNull(context, "Context is null")
            if (context !is ContextWrapper || context.baseContext != null) {
                return context
            }
            throw StatusException(
                Code.INVALID_ARGUMENT,
                "ContextWrapper with null base passed as Context, forgot to set base Context?"
            )
        }

        @NonNull
        private fun printStatus(message: String, status: Status): Status {
            val fullMessage = message + ": " + StatusUtils.toShortString(status)
            if (!StatusUtils.isOk(status)) {
                Log.e(TAG, fullMessage)
            } else if (DEBUG_LOGGABLE) {
                Log.d(TAG, fullMessage)
            }
            return status
        }
    }
}