package com.ultifi.core.usubscription.v1;

import androidx.annotation.NonNull;

import com.gm.ultifi.sdk.uprotocol.cloudevent.datamodel.UCloudEventAttributes;
import com.gm.ultifi.sdk.uprotocol.cloudevent.factory.CloudEventFactory;
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UAuthority;
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UEntity;
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UResource;
import com.gm.ultifi.sdk.uprotocol.uri.factory.UltifiUriFactory;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.rpc.Code;
import com.google.rpc.Status;
import com.ultifi.core.UltifiLink;
import com.ultifi.core.common.util.StatusUtils;
import com.ultifi.core.rpc.CallOptions;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import io.cloudevents.CloudEvent;

public class USubscription {

    private static final UEntity SERVICE = new UEntity("core.usubscription", "1");

    private static final String METHOD_SUBSCRIBE = UltifiUriFactory.buildMethodUri(UAuthority.local(), SERVICE, "Subscribe");

    private static final String METHOD_UNSUBSCRIBE = UltifiUriFactory.buildMethodUri(UAuthority.local(), SERVICE, "Unsubscribe");

    private static final String METHOD_CREATE_TOPIC = UltifiUriFactory.buildMethodUri(UAuthority.local(), SERVICE, "CreateTopic");

    private static final String METHOD_DELETE_TOPIC = UltifiUriFactory.buildMethodUri(UAuthority.local(), SERVICE, "DeleteTopic");

    private static final String METHOD_FETCH_SUBSCRIPTIONS = UltifiUriFactory.buildMethodUri(UAuthority.local(), SERVICE, "FetchSubscriptions");

    private static final String METHOD_REGISTER_FOR_SUBSCRIPTION_CHANGES = UltifiUriFactory.buildMethodUri(UAuthority.local(), SERVICE, "RegisterForSubscriptionChanges");

    private static final String METHOD_UNREGISTER_FOR_SUBSCRIPTION_CHANGES = UltifiUriFactory.buildMethodUri(UAuthority.local(), SERVICE, "UnregisterForSubscriptionChanges");

    public static final String TOPIC_SUBSCRIPTION = UltifiUriFactory.buildUProtocolUri(UAuthority.local(), SERVICE, new UResource("subscription_change", null, "Subscription"));

    private static <T extends Message> T unpackOrNull(Any payload, Class<T> clazz) {
        if (payload != null && payload.is(clazz)) {
            try {
                return payload.unpack(clazz);
            } catch (InvalidProtocolBufferException invalidProtocolBufferException) {
                return null;
            }
        }
        return null;
    }

    private static Status unpackStatus(Any payload) {
        Status status;
        if ((status = unpackOrNull(payload, Status.class)) == null) {
            status = StatusUtils.STATUS_UNEXPECTED_RESPONSE;
        }
        return status;
    }

    private static <T extends Message> Subscription coerceType(SubscriptionRequest subscriptionRequest, T payload) {
        Status status = null;
        Any any;
        if (payload instanceof Any) {
            Subscription subscription;
            if ((subscription = unpackOrNull(any = (Any)payload, Subscription.class)) != null) {
                return subscription;
            }
            status = unpackOrNull(any, Status.class);
        }
        if (status == null) {
            if (payload instanceof Status) {
                status = (Status) payload;
            } else {
                status = StatusUtils.STATUS_UNEXPECTED_RESPONSE;
            }
        }
        if (subscriptionRequest == null) {
            subscriptionRequest = SubscriptionRequest.getDefaultInstance();
        }
        return Subscription.newBuilder()
                .setTopic(subscriptionRequest.getTopic())
                .setSubscriber(subscriptionRequest.getSubscriber())
                .setAttributes(subscriptionRequest.getAttributes())
                .setStatus(SubscriptionStatus.newBuilder()
                        .setCode(Code.forNumber(status.getCode()))
                        .setMessage(status.getMessage())
                        .setState(SubscriptionStatus.State.UNSUBSCRIBED)
                        .build())
                .build();
    }


    private static <T extends Message> FetchSubscriptionsResponse coerceType(FetchSubscriptionsRequest fetchSubscriptionsRequest, T payload) {
        Status status = null;
        if (payload instanceof Any) {
            Any any;
            FetchSubscriptionsResponse fetchSubscriptionsResponse;
            if ((fetchSubscriptionsResponse = unpackOrNull(any = (Any) payload, FetchSubscriptionsResponse.class)) != null) {
                return fetchSubscriptionsResponse;
            }
            status = unpackOrNull(any, Status.class);
        }
        if (status == null) {
            if (payload instanceof Status) {
                status = (Status) payload;
            } else {
                status = StatusUtils.STATUS_UNEXPECTED_RESPONSE;
            }
        }
        return FetchSubscriptionsResponse.newBuilder().setStatus(status).build();
    }

    public static class FutureStub extends Stub {

        private FutureStub(@NonNull UltifiLink ultifiLink, CallOptions callOptions) {
            super(ultifiLink, callOptions);
        }

        @NonNull
        public CompletableFuture<Subscription> subscribe(@NonNull SubscriptionRequest subscriptionRequest) {
            try {
                return mLink.invokeMethod(buildRequestEvent(USubscription.METHOD_SUBSCRIBE, subscriptionRequest))
                        .handle((payload, throwable) ->
                                (throwable != null) ? USubscription.coerceType(subscriptionRequest, StatusUtils.throwableToStatus(throwable))
                                        : USubscription.coerceType(subscriptionRequest, payload));
            } catch (Exception exception) {
                return CompletableFuture.completedFuture(USubscription.coerceType(subscriptionRequest, StatusUtils.throwableToStatus(null)));
            }
        }

        @NonNull
        public CompletableFuture<Status> unsubscribe(@NonNull UnsubscribeRequest unsubscribeRequest) {
            try {
                return mLink.invokeMethod(buildRequestEvent(USubscription.METHOD_UNSUBSCRIBE, unsubscribeRequest))
                        .handle((payload, throwable) ->
                                (throwable != null) ? StatusUtils.throwableToStatus(throwable)
                                        : USubscription.unpackStatus(payload));
            } catch (Exception exception) {
                return CompletableFuture.completedFuture(StatusUtils.throwableToStatus(null));
            }
        }

        @NonNull
        public CompletableFuture<Status> createTopic(@NonNull CreateTopicRequest createTopicRequest) {
            try {
                return mLink.invokeMethod(buildRequestEvent(USubscription.METHOD_CREATE_TOPIC, createTopicRequest))
                        .handle((payload, throwable) ->
                                (throwable != null) ? StatusUtils.throwableToStatus(throwable)
                                        : USubscription.unpackStatus(payload));
            } catch (Exception exception) {
                return CompletableFuture.completedFuture(StatusUtils.throwableToStatus(null));
            }
        }

        @NonNull
        public CompletableFuture<Status> deleteTopic(@NonNull DeleteTopicRequest deleteTopicRequest) {
            try {
                return mLink.invokeMethod(buildRequestEvent(USubscription.METHOD_DELETE_TOPIC, deleteTopicRequest))
                        .handle((payload, throwable) ->
                                (throwable != null) ? StatusUtils.throwableToStatus(throwable)
                                        : USubscription.unpackStatus(payload));
            } catch (Exception exception) {
                return CompletableFuture.completedFuture(StatusUtils.throwableToStatus(null));
            }
        }

        @NonNull
        public CompletableFuture<FetchSubscriptionsResponse> fetchSubscriptions(@NonNull FetchSubscriptionsRequest fetchSubscriptionsRequest) {
            try {
                return mLink.invokeMethod(buildRequestEvent(USubscription.METHOD_FETCH_SUBSCRIPTIONS, fetchSubscriptionsRequest))
                        .handle((payload, throwable) ->
                                (throwable != null) ? USubscription.coerceType(fetchSubscriptionsRequest, StatusUtils.throwableToStatus(throwable))
                                        : USubscription.coerceType(fetchSubscriptionsRequest, payload));
            } catch (Exception exception) {
                return CompletableFuture.completedFuture(USubscription.coerceType(fetchSubscriptionsRequest, StatusUtils.throwableToStatus(null)));
            }
        }
    }

    public static class BlockingStub extends Stub {

        private BlockingStub(@NonNull UltifiLink ultifiLink, CallOptions callOptions) {
            super(ultifiLink, callOptions);
        }

        @NonNull
        public Subscription subscribe(@NonNull SubscriptionRequest subscriptionRequest) {
            try {
                CloudEvent cloudEvent = buildRequestEvent(USubscription.METHOD_SUBSCRIBE, subscriptionRequest);
                return USubscription.coerceType(subscriptionRequest,
                        mLink.invokeMethod(cloudEvent).get(mCallOptions.timeout(), TimeUnit.MILLISECONDS));
            } catch (Exception exception) {
                return USubscription.coerceType(subscriptionRequest, StatusUtils.throwableToStatus(null));
            }
        }

        @NonNull
        public Status unsubscribe(@NonNull UnsubscribeRequest unsubscribeRequest) {
            try {
                CloudEvent cloudEvent = buildRequestEvent(USubscription.METHOD_UNSUBSCRIBE, unsubscribeRequest);
                return USubscription.unpackStatus(mLink.invokeMethod(cloudEvent).get(mCallOptions.timeout(), TimeUnit.MILLISECONDS));
            } catch (Exception exception) {
                return StatusUtils.throwableToStatus(null);
            }
        }

        @NonNull
        public Status createTopic(@NonNull CreateTopicRequest createTopicRequest) {
            try {
                CloudEvent cloudEvent = buildRequestEvent(USubscription.METHOD_CREATE_TOPIC, createTopicRequest);
                return USubscription.unpackStatus(mLink.invokeMethod(cloudEvent).get(mCallOptions.timeout(), TimeUnit.MILLISECONDS));
            } catch (Exception exception) {
                return StatusUtils.throwableToStatus(null);
            }
        }

        @NonNull
        public Status deleteTopic(@NonNull DeleteTopicRequest deleteTopicRequest) {
            try {
                CloudEvent cloudEvent = buildRequestEvent(USubscription.METHOD_DELETE_TOPIC, deleteTopicRequest);
                return USubscription.unpackStatus(mLink.invokeMethod(cloudEvent).get(mCallOptions.timeout(), TimeUnit.MILLISECONDS));
            } catch (Exception exception) {
                return StatusUtils.throwableToStatus(null);
            }
        }

        @NonNull
        public FetchSubscriptionsResponse fetchSubscriptions(@NonNull FetchSubscriptionsRequest fetchSubscriptionsRequest) {
            try {
                CloudEvent cloudEvent = buildRequestEvent(USubscription.METHOD_FETCH_SUBSCRIPTIONS, fetchSubscriptionsRequest);
                return USubscription.coerceType(fetchSubscriptionsRequest, mLink.invokeMethod(cloudEvent).get(mCallOptions.timeout(), TimeUnit.MILLISECONDS));
            } catch (Exception exception) {
                return USubscription.coerceType(fetchSubscriptionsRequest, StatusUtils.throwableToStatus(null));
            }
        }
    }

    private static abstract class Stub {

        protected final UltifiLink mLink;

        protected final CallOptions mCallOptions;

        private Stub(UltifiLink ultifiLink, CallOptions callOptions) {
            mLink = Objects.requireNonNull(ultifiLink, "Link is null");
            if (callOptions == null) {
                callOptions = CallOptions.DEFAULT;
            }
            mCallOptions = callOptions;
        }

        @NonNull
        protected <T extends Message> CloudEvent buildRequestEvent(@NonNull String serviceMethodUri, @NonNull T payload) {
            return CloudEventFactory.request(mLink.getResponseUri(),
                    serviceMethodUri,
                    Any.pack((Message)payload),
                    (new UCloudEventAttributes.UCloudEventAttributesBuilder()).withTtl(mCallOptions.timeout())
                            .withPriority(UCloudEventAttributes.Priority.REALTIME_INTERACTIVE)
                            .withToken(mCallOptions.token().orElse(""))
                            .build());
        }
    }

    @NonNull
    public static BlockingStub newBlockingStub(@NonNull UltifiLink ultifiLink) {
        return new BlockingStub(ultifiLink, CallOptions.DEFAULT);
    }

    @NonNull
    public static FutureStub newFutureStub(@NonNull UltifiLink newBlockingStub) {
        return new FutureStub(newBlockingStub, CallOptions.DEFAULT);
    }
}