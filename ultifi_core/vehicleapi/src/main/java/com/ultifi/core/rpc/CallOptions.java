package com.ultifi.core.rpc;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gm.ultifi.sdk.uprotocol.cloudevent.factory.UCloudEvent;
import com.ultifi.core.common.util.StatusUtils;

import java.util.Optional;

import io.cloudevents.CloudEvent;

public class CallOptions {

    // 10 seconds
    public static final int TIMEOUT_DEFAULT = 10000;

    public static final CallOptions DEFAULT = new CallOptions(TIMEOUT_DEFAULT, "");

    private final int mTimeout;

    private final String mToken;

    @NonNull
    public static Optional<CallOptions> from(@NonNull CloudEvent paramCloudEvent) {
        try {
            return Optional.of(new CallOptions(
                    UCloudEvent.getTtl(paramCloudEvent).orElseThrow(() -> new IllegalArgumentException("Timeout is unknown")),
                    UCloudEvent.getToken(paramCloudEvent).orElse("")));
        } catch (Exception exception) {
            return Optional.empty();
        }
    }

    private CallOptions(int paramInt, @Nullable String token) {
        this.mTimeout = StatusUtils.checkArgumentPositive(paramInt, "Timeout is zero or negative");
        if (token == null) {
            token = "";
        }
        mToken = token;
    }

    @NonNull
    public static Builder newBuilder() {
        return new Builder();
    }

    public int timeout() {
        return mTimeout;
    }

    @NonNull
    public Optional<String> token() {
        if (TextUtils.isEmpty(mToken)) {
            return Optional.empty();
        }
        return Optional.of(mToken);
    }

    public static final class Builder {

        private int mTimeout = TIMEOUT_DEFAULT;
        private String mToken = "";

        @NonNull
        public Builder withTimeout(int timeout) {
            mTimeout = timeout;
            return this;
        }

        @NonNull
        public Builder withToken(String token) {
            if (token == null) {
                token = "";
            }
            mToken = token;
            return this;
        }

        @NonNull
        public CallOptions build() {
            return new CallOptions(mTimeout, mToken);
        }
    }
}