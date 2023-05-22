package com.patac.service_bus;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gm.ultifi.sdk.uprotocol.cloudevent.serialize.CloudEventSerializers;
import com.google.rpc.Code;
import com.ultifi.core.common.StatusException;

import java.util.Arrays;

import io.cloudevents.CloudEvent;

public class CloudEventProto implements Parcelable {

    private final byte[] mPayload;

    private CloudEventProto(@NonNull byte[] payload) {
        mPayload = payload;
    }

    private CloudEventProto(@NonNull CloudEvent cloudEvent) {
        mPayload = CloudEventSerializers.PROTOBUF.serializer().serialize(cloudEvent);
    }

    public static CloudEventProto from(@NonNull CloudEvent cloudEvent) {
        return new CloudEventProto(cloudEvent);
    }

    @Nullable
    public static CloudEvent parseOrNull(@Nullable CloudEventProto cloudEventProto) {
        if (cloudEventProto == null) {
            return null;
        }

        try {
            return CloudEventSerializers.PROTOBUF.serializer().deserialize(cloudEventProto.mPayload);
        } catch (Exception exception) {
            return null;
        }
    }

    @NonNull
    public static CloudEvent parseOrThrow(@Nullable CloudEventProto cloudEventProto) {
        if (cloudEventProto != null) {
            try {
                return CloudEventSerializers.PROTOBUF.serializer().deserialize(cloudEventProto.mPayload);
            } catch (Exception exception) {
                throw new StatusException(Code.INTERNAL, "Failed to deserialize event", exception);
            }
        }
        throw new StatusException(Code.INTERNAL, "Data is null");
    }

    public static final Creator<CloudEventProto> CREATOR = new Creator<>() {
        @Override
        public CloudEventProto createFromParcel(Parcel in) {
            byte[] payload = new byte[in.readInt()];
            in.readByteArray(payload);
            return new CloudEventProto(payload);
        }

        @Override
        public CloudEventProto[] newArray(int size) {
            return new CloudEventProto[size];
        }
    };

    @NonNull
    public CloudEvent parse() {
        return parseOrThrow(this);
    }

    @NonNull
    public byte[] getData() {
        return mPayload;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mPayload.length);
        dest.writeByteArray(mPayload);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(mPayload);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CloudEventProto)) {
            return false;
        }

        return Arrays.equals(mPayload, ((CloudEventProto) obj).mPayload);
    }
}