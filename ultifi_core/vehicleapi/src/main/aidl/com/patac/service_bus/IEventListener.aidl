package com.patac.service_bus;

import com.patac.service_bus.CloudEventProto;


interface IEventListener {

    void onEvent(in CloudEventProto paramCloudEventProto);
}