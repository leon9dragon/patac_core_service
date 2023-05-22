package com.gm.ultifi.service.cabinclimate.response.config;

import androidx.annotation.NonNull;

public class PropertyConfig {
    private final boolean mIsReadOnly;

    private final String mProtobufField;

    private final float mRate;

    private final String mResName;

    private final Class<?> mType;

    private final String mSignalName;

    public PropertyConfig(Builder builder) {
        mProtobufField = builder.protobufField;
        mRate = builder.rate;
        mType = builder.type;
        mSignalName = builder.signalName;
        mResName = builder.resName;
        mIsReadOnly = builder.isReadOnly;
    }

    public Class<?> getClassType() {
        return mType;
    }

    public boolean getIsReadOnly() {
        return mIsReadOnly;
    }

    public String getProtobufField() {
        return mProtobufField;
    }

    public float getRate() {
        return mRate;
    }

    public String getResName() {
        return mResName;
    }

    public String getSignalName() {
        return mSignalName;
    }

    @NonNull
    @Override
    public String toString() {
        return "PropertyConfig{mProtobufField='" + mProtobufField
                + '\'' + ", mRate=" + mRate + ", mType=" + mType + '}';
    }

    public static class Builder {
        private boolean isReadOnly = true;

        private String resName;

        private String protobufField;

        private float rate = 0.0F;

        private String signalName;

        private Class<?> type;

        public Builder() {
        }

        public PropertyConfig build() {
            return new PropertyConfig(this);
        }

        public Builder setClassType(Class<?> type) {
            this.type = type;
            return this;
        }

        public Builder setProtobufField(String fieldName) {
            this.protobufField = fieldName;
            return this;
        }

        public Builder setRate(float rate) {
            this.rate = rate;
            return this;
        }

        public Builder setReadOnly(boolean readOnly) {
            this.isReadOnly = readOnly;
            return this;
        }

        public Builder setResName(String resName) {
            this.resName = resName;
            return this;
        }

        public Builder setSignalName(String signalName) {
            this.signalName = signalName;
            return this;
        }
    }
}