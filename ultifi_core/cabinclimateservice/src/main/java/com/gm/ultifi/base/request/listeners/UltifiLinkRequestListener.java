package com.gm.ultifi.base.request.listeners;

import android.util.Log;

import androidx.annotation.NonNull;

import com.gm.ultifi.base.monitor.CarPropertyManagerMonitor;
import com.gm.ultifi.base.request.BaseRequestProcessor;
import com.gm.ultifi.base.request.rpc.RequestProcessorFactory;
import com.google.protobuf.Any;
import com.google.protobuf.Message;
import com.google.rpc.Code;
import com.google.rpc.Status;
import com.ultifi.core.UltifiLink;
import com.ultifi.core.common.util.StatusUtils;
import com.ultifi.core.rpc.Request;
import com.ultifi.vehicle.body.access.v1.SunroofCommand;

import java.util.concurrent.CompletableFuture;

public class UltifiLinkRequestListener implements UltifiLink.RequestListener {

    private static final String TAG = UltifiLinkRequestListener.class.getSimpleName();

    private final CarPropertyManagerMonitor mCarPropertyMgrMonitor;

    public UltifiLinkRequestListener(CarPropertyManagerMonitor carPropertyMgrMonitor) {
        mCarPropertyMgrMonitor = carPropertyMgrMonitor;
    }

    @Override
    public void onRequest(@NonNull Request request,
                          @NonNull CompletableFuture<Any> completableFuture) {
        String methodUri = request.methodUri();
        Log.d(TAG, "requestOrigin: " + methodUri);
        SunroofCommand sunroofCommand = request.unpack(SunroofCommand.class).orElse(null);

        assert sunroofCommand != null;
        Log.d(TAG, "update mask: " + sunroofCommand.getUpdateMask().getPaths(0));
        RequestProcessorFactory requestProcessorFactory = RequestProcessorFactory.getInstance();

        // mapping processor by methodUri
        BaseRequestProcessor baseRequestProcessor =
                (BaseRequestProcessor) requestProcessorFactory.getRequestProcessor(methodUri.trim());
        if (baseRequestProcessor == null) {
            Status status = StatusUtils.buildStatus(Code.UNKNOWN, methodUri + " not found.");
            completableFuture.complete(Any.pack((Message) status));
            return;
        }
        // this setting is only for getting CarPropertyExtensionManager
        baseRequestProcessor.setCarPropertyManagerMonitor(mCarPropertyMgrMonitor);
        baseRequestProcessor.setRequestMessage(request);
        if (baseRequestProcessor.isCarPropertyExtensionManagerNull()) {
            Log.i(TAG, "CarPropertyExtensionManager: is null");
            Status status = StatusUtils.buildStatus(Code.UNKNOWN, "CarPropertyExtensionManager is null");
            completableFuture.complete(Any.pack((Message) status));
            return;
        }
        Status status = baseRequestProcessor.processRequest();
        Log.d(TAG, "Status: " + StatusUtils.toShortString(status));
        completableFuture.complete(Any.pack((Message) status));
    }
}