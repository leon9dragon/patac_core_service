package com.patac.service_bus;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.rpc.Code;
import com.google.rpc.Status;
import com.ultifi.core.common.StatusException;

import java.util.Arrays;

public class StatusProto implements Parcelable {

    private final byte[] mPayload;

    private StatusProto(@NonNull byte[] payload) {
        mPayload = payload;
    }

    private StatusProto(@NonNull Status status) {
        mPayload = status.toByteArray();
    }

    public static StatusProto from(@NonNull Status status) {
        return new StatusProto(status);
    }

    @Nullable
    public static Status parseOrNull(@Nullable StatusProto statusProto) {
        if (statusProto == null) {
            return null;
        }

        try {
            return Status.parseFrom(statusProto.mPayload);
        } catch (InvalidProtocolBufferException invalidProtocolBufferException) {
            return null;
        }
    }

    @NonNull
    public static Status parseOrThrow(@Nullable StatusProto statusProto) {
        if (statusProto != null) {
            try {
                return Status.parseFrom(statusProto.mPayload);
            } catch (InvalidProtocolBufferException invalidProtocolBufferException) {
                throw new StatusException(Code.INTERNAL, "Failed to deserialize status", invalidProtocolBufferException);
            }
        }
        throw new StatusException(Code.INTERNAL, "Data is null");
    }

    public static final Creator<StatusProto> CREATOR = new Creator<>() {
        @Override
        public StatusProto createFromParcel(Parcel in) {
            byte[] payload = new byte[in.readInt()];
            in.readByteArray(payload);
            return new StatusProto(payload);
        }

        @Override
        public StatusProto[] newArray(int size) {
            return new StatusProto[size];
        }
    };

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
        if (!(obj instanceof StatusProto)) {
            return false;
        }

        return Arrays.equals(mPayload, ((StatusProto) obj).mPayload);
    }
}
