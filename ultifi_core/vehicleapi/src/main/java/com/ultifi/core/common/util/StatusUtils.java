package com.ultifi.core.common.util;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.rpc.Code;
import com.google.rpc.Status;
import com.ultifi.core.common.StatusException;

public class StatusUtils {

    public static final Status STATUS_OK = buildStatus(Code.OK);

    public static final Status STATUS_UNEXPECTED_RESPONSE = buildStatus(Code.UNKNOWN, "Unexpected response");

    @Deprecated(since = "1.0.2")
    public static boolean isStatusOk(@Nullable Status status) {
        return isOk(status);
    }

    @Deprecated(since = "1.0.2")
    public static boolean isStatusCode(@Nullable Status status, int code) {
        return hasCode(status, code);
    }

    public static boolean isOk(@Nullable Status status) {
        return (status != null && status.getCode() == Code.OK.getNumber());
    }

    public static boolean hasCode(@Nullable Status status, int code) {
        return (status != null && status.getCode() == code);
    }

    @NonNull
    public static Code getCode(@Nullable Status status, @NonNull Code paramCode) {
        Code code;
        if (status != null) {
            code = Code.forNumber(status.getCode());
        } else {
            code = paramCode;
        }
        return code;
    }

    @NonNull
    public static Status.Builder newStatusBuilder(@NonNull Code code) {
        return Status.newBuilder().setCode(code.getNumber());
    }

    @NonNull
    public static Status buildStatus(@NonNull Code code) {
        return newStatusBuilder(code).build();
    }

    @NonNull
    public static Status throwableToStatus(Throwable throwable) {
        Code code;
        if (throwable instanceof StatusException) {
            return ((StatusException) throwable).getStatus();
        }
        if (throwable instanceof SecurityException) {
            code = Code.PERMISSION_DENIED;
        } else if (throwable instanceof com.google.protobuf.InvalidProtocolBufferException) {
            code = Code.INVALID_ARGUMENT;
        } else if (throwable instanceof IllegalArgumentException) {
            code = Code.INVALID_ARGUMENT;
        } else if (throwable instanceof NullPointerException) {
            code = Code.INVALID_ARGUMENT;
        } else if (throwable instanceof IllegalStateException) {
            code = Code.UNAVAILABLE;
        } else if (throwable instanceof android.os.RemoteException) {
            code = Code.UNAVAILABLE;
        } else if (throwable instanceof UnsupportedOperationException) {
            code = Code.UNIMPLEMENTED;
        } else if (throwable instanceof java.util.concurrent.ExecutionException) {
            code = Code.INTERNAL;
        } else if (throwable instanceof InterruptedException) {
            code = Code.CANCELLED;
        } else if (throwable instanceof java.util.concurrent.TimeoutException) {
            code = Code.DEADLINE_EXCEEDED;
        } else {
            code = Code.UNKNOWN;
        }
        return buildStatus(code, throwable == null ? "" : throwable.getMessage());
    }

    public static void checkArgument(boolean value, @Nullable String message) {
        if (value) {
            return;
        }
        throw new StatusException(Code.INVALID_ARGUMENT, message);
    }

    public static int checkArgumentPositive(int value, @Nullable String message) {
        if (value > 0) {
            return value;
        }
        throw new StatusException(Code.INVALID_ARGUMENT, message);
    }

    public static int checkArgumentNonNegative(int value, @Nullable String message) {
        if (value >= 0) {
            return value;
        }
        throw new StatusException(Code.INVALID_ARGUMENT, message);
    }

    @NonNull
    public static <T extends CharSequence> T checkStringNotEmpty(T value, @Nullable String message) {
        if (!TextUtils.isEmpty(value)) {
            return value;
        }
        throw new StatusException(Code.INVALID_ARGUMENT, message);
    }

    @NonNull
    public static <T extends CharSequence> T checkStringEquals(T value1, @NonNull T value2, @NonNull Code code, @Nullable String message) {
        if (TextUtils.equals(value1, value2)) {
            return value1;
        }
        throw new StatusException(code, message);
    }

    @NonNull
    public static <T> T checkNotNull(@Nullable T obj, @Nullable String message) {
        if (obj != null) {
            return obj;
        }
        throw new StatusException(Code.INVALID_ARGUMENT, message);
    }

    public static void checkState(boolean value, @Nullable String message) {
        if (value) {
            return;
        }
        throw new StatusException(Code.FAILED_PRECONDITION, message);
    }

    @NonNull
    public static <C extends java.util.Collection<T>, T> T checkCollectionContains(@NonNull C collection, @NonNull T value, @NonNull String message) {
        checkNotNull(collection, message + " '" + value + "' is not supported");
        checkNotNull(value, message + " is null");
        if (collection.contains(value)) {
            return value;
        }
        throw new StatusException(Code.INVALID_ARGUMENT, message + " '" + value + "' is not supported");
    }

    @NonNull
    public static String toShortString(@Nullable Status status) {
        return (status != null) ? ("Status{code=" + Code.forNumber(status.getCode()) + ", message='" + status.getMessage() + "')") : "null";
    }

    public static boolean hasCode(@Nullable Status status, @NonNull Code code) {
        return (status != null && status.getCode() == code.getNumber());
    }

    @NonNull
    public static Code getCode(@Nullable Status status) {
        return getCode(status, Code.UNKNOWN);
    }

    @NonNull
    public static Status.Builder newStatusBuilder(@NonNull Code code, @Nullable String message) {
        Status.Builder builder = newStatusBuilder(code);
        if (message == null) {
            message = "";
        }
        return builder.setMessage(message);
    }

    @NonNull
    public static Status buildStatus(@NonNull Code code, @Nullable String message) {
        return newStatusBuilder(code, message).build();
    }

    public static void checkArgument(boolean value, @NonNull Code code, @Nullable String message) {
        if (value) {
            return;
        }
        throw new StatusException(code, message);
    }

    public static int checkArgumentPositive(int value, @NonNull Code code, @Nullable String message) {
        if (value > 0) {
            return value;
        }
        throw new StatusException(code, message);
    }

    public static int checkArgumentNonNegative(int value, @NonNull Code code, @Nullable String message) {
        if (value >= 0) {
            return value;
        }
        throw new StatusException(code, message);
    }

    @NonNull
    public static <T> T checkNotNull(@Nullable T object, @NonNull Code code, @Nullable String message) {
        if (object != null) {
            return object;
        }
        throw new StatusException(code, message);
    }

    public static void checkState(boolean value, @NonNull Code code, @Nullable String message) {
        if (value) {
            return;
        }
        throw new StatusException(code, message);
    }

    @NonNull
    public static <T extends CharSequence> T checkStringNotEmpty(T value, @NonNull Code code, @Nullable String message) {
        if (!TextUtils.isEmpty(value)) {
            return value;
        }
        throw new StatusException(code, message);
    }

    @NonNull
    public static <T extends CharSequence> T checkStringEquals(T value1, T value2, @Nullable String message) {
        if (TextUtils.equals(value1, value2)) {
            return value1;
        }
        throw new StatusException(Code.INVALID_ARGUMENT, message);
    }
}