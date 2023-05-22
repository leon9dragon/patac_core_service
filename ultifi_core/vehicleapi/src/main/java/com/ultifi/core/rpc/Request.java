package com.ultifi.core.rpc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gm.ultifi.sdk.uprotocol.cloudevent.datamodel.UCloudEventType;
import com.gm.ultifi.sdk.uprotocol.cloudevent.factory.UCloudEvent;
import com.google.protobuf.Any;
import com.ultifi.core.common.util.StatusUtils;

import java.util.Optional;

import io.cloudevents.CloudEvent;

public class Request {
    private final String mId;

    private final String mMethodUri;

    private final String mResponseUri;

    private final Any mPayload;

    private final CallOptions mCallOptions;

    @NonNull
    public static Optional<Request> from(@NonNull CloudEvent event) {
        try {
            StatusUtils.checkStringEquals(event.getType(),
                    UCloudEventType.REQUEST.type(), "Event type is not '" + UCloudEventType.REQUEST.type() + "'");
            return Optional.of(new Request(event.getId(),
                    UCloudEvent.getSink(event).orElseThrow(IllegalArgumentException::new),
                    UCloudEvent.getSource(event),
                    UCloudEvent.getPayload(event),
                    CallOptions.from(event).<Throwable>orElseThrow(IllegalArgumentException::new)));
        } catch (Throwable exception) {
            return Optional.empty();
        }
    }

    private Request(@Nullable String id, @NonNull String methodId,
                    @Nullable String responseUri, @Nullable Any payload, @NonNull CallOptions callOptions) {
        if (id == null) {
            id = "";
        }
        mId = id;
        mMethodUri = StatusUtils.checkStringNotEmpty(methodId, "Method URI is empty");
        if (responseUri == null) {
            responseUri = "";
        }
        mResponseUri = responseUri;
        if (payload == null) {
            payload = Any.getDefaultInstance();
        }
        mPayload = payload;
        mCallOptions = callOptions;
    }

    @NonNull
    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder buildUpon() {
        return new Builder(this);
    }

    @NonNull
    public String id() {
        return mId;
    }

    @NonNull
    public String methodUri() {
        return mMethodUri;
    }

    @NonNull
    public String responseUri() {
        return mResponseUri;
    }

    @NonNull
    public Any payload() {
        return mPayload;
    }

    public <T extends com.google.protobuf.Message> Optional<T> unpack(Class<T> clazz) {
        try {
            return Optional.of(mPayload.unpack(clazz));
        } catch (Exception exception) {
            return Optional.empty();
        }
    }

    public int timeout() {
        return mCallOptions.timeout();
    }

    @NonNull
    public Optional<String> token() {
        return mCallOptions.token();
    }

    public static final class Builder {
        private String mMethodUri;

        private Any mPayload;

        private CallOptions mCallOptions;

        private Builder() {
            mCallOptions = CallOptions.DEFAULT;
        }

        @NonNull
        public Builder withMethodUri(String methodUri) {
            mMethodUri = methodUri;
            return this;
        }

        @NonNull
        public Builder withPayload(Any payload) {
            mPayload = payload;
            return this;
        }

        @NonNull
        public Builder withCallOptions(CallOptions callOptions) {
            if (callOptions == null) {
                callOptions = CallOptions.DEFAULT;
            }
            mCallOptions = callOptions;
            return this;
        }

        @NonNull
        public Request build() {
            return new Request(null, mMethodUri, null, mPayload, mCallOptions);
        }

        private Builder(@NonNull Request request) {
            mCallOptions = CallOptions.DEFAULT;
            mMethodUri = request.mMethodUri;
            mPayload = request.mPayload;
            mCallOptions = request.mCallOptions;
        }
    }
}