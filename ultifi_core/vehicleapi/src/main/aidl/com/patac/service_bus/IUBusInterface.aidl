package com.patac.service_bus;

import com.patac.service_bus.IEventListener;
import com.patac.service_bus.StatusProto;
import com.patac.service_bus.CloudEventProto;


interface IUBusInterface {

    StatusProto registerClient(String paramString, IBinder paramIBinder, in IEventListener paramIEventListener);
    StatusProto unregisterClient(IBinder paramIBinder);
    StatusProto send(in CloudEventProto paramCloudEventProto, IBinder paramIBinder);
    StatusProto enableEventsDispatching(String paramString, in Bundle paramBundle, IBinder paramIBinder);
    StatusProto disableEventsDispatching(String paramString, in Bundle paramBundle, IBinder paramIBinder);
    CloudEventProto[] pull(String paramString, int paramInt, in Bundle paramBundle, IBinder paramIBinder);

}