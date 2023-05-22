package com.ultifi.core.common;

import androidx.annotation.NonNull;

import com.google.rpc.Code;
import com.google.rpc.Status;
import com.ultifi.core.common.util.StatusUtils;

public class StatusException extends RuntimeException {

    private final Status mStatus;

    public StatusException(Code code, String message) {
        this(StatusUtils.buildStatus(code, message), null);
    }

    public StatusException(Code code, String message, Throwable throwable) {
        this(StatusUtils.buildStatus(code, message), throwable);
    }

    public StatusException(Status status) {
        this(status, null);
    }

    public StatusException(Status status, Throwable throwable) {
        super(status == null ? null : status.getMessage(), throwable);
        if (status == null) {
            status = StatusUtils.buildStatus(Code.UNKNOWN);
        }

        mStatus = status;
    }

    @NonNull
    public Status getStatus() {
        return mStatus;
    }

    @NonNull
    public Code getCode() {
        Code code;
        if ((code = Code.forNumber(mStatus.getCode())) == null) {
            code = Code.UNKNOWN;
        }
        return code;
    }
}