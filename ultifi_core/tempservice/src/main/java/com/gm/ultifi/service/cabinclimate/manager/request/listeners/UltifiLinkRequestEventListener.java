package com.gm.ultifi.service.cabinclimate.manager.request.listeners;

import androidx.annotation.NonNull;

import com.gm.ultifi.service.cabinclimate.manager.CarPropertyManagerMonitor;
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
        //Todo should unpack CloudEvent to Request, and deliver to BaseRequestProcessor
    }
}