package com.gm.ultifi.base.request.listeners;

import android.util.Log;

import androidx.annotation.NonNull;

import com.gm.ultifi.base.monitor.CarPropertyManagerMonitor;
import com.google.protobuf.Any;
import com.ultifi.core.UltifiLink;

import java.util.concurrent.CompletableFuture;

import io.cloudevents.CloudEvent;

public class UltifiLinkRequestEventListener implements UltifiLink.RequestEventListener {

    private static final String TAG = UltifiLinkRequestEventListener.class.getSimpleName();

    private final CarPropertyManagerMonitor mCarPropertyMgrMonitor;

    public UltifiLinkRequestEventListener(CarPropertyManagerMonitor carPropertyMgrMonitor) {
        mCarPropertyMgrMonitor = carPropertyMgrMonitor;
    }

    @Override
    public void onEvent(@NonNull CloudEvent cloudEvent, @NonNull CompletableFuture<Any> completableFuture) {
        // TODO: 2023/4/26 should unpack CloudEvent to Request, and deliver to BaseRequestProcessor
        //  transfer CloudEvent to Request
        cloudEvent.getData().toBytes();
        Log.i(TAG,"TODO...");
//        String methodUri = request.methodUri();
//        Log.d(TAG, "requestOrigin: " + methodUri);
//        RequestProcessorFactory requestProcessorFactory = RequestProcessorFactory.getInstance();
//        BaseRequestProcessor baseRequestProcessor =
//                (BaseRequestProcessor) requestProcessorFactory.getRequestProcessor(methodUri.trim());
//        if (baseRequestProcessor == null) {
//            Status status = StatusUtils.buildStatus(Code.UNKNOWN, methodUri + " not found.");
//            completableFuture.complete(Any.pack((Message) status));
//            return;
//        }
//
//        baseRequestProcessor.setCarPropertyManagerMonitor(mCarPropertyMgrMonitor);
//        baseRequestProcessor.setRequestMessage(request);
//        if (baseRequestProcessor.isCarPropertyExtensionManagerNull()) {
//            Log.i(TAG, "CarPropertyExtensionManager: is null");
//            Status status = StatusUtils.buildStatus(Code.UNKNOWN, "CarPropertyExtensionManager is null");
//            completableFuture.complete(Any.pack((Message) status));
//            return;
//        }
//        Status status = baseRequestProcessor.processRequest();
//        Log.d(TAG, "Status: " + StatusUtils.toShortString(status));
//        completableFuture.complete(Any.pack((Message) status));
    }
}