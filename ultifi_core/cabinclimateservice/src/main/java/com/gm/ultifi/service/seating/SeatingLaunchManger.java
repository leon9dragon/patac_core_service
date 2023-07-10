package com.gm.ultifi.service.seating;

import com.gm.ultifi.base.propertymanager.CarPropertyExtensionManager;
import com.gm.ultifi.service.access.response.config.enums.SunroofEnum;
import com.gm.ultifi.service.constant.ResourceMappingConstants;
import com.gm.ultifi.service.constant.ServiceConstant;

import com.gm.ultifi.base.monitor.CanManagerMonitor;
import com.gm.ultifi.base.monitor.CarPropertyManagerMonitor;
import com.gm.ultifi.base.monitor.UltifiLinkMonitor;
import com.gm.ultifi.base.servicemanager.ServiceLaunchManager;
import com.gm.ultifi.base.utils.Utility;
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UEntity;
import com.gm.ultifi.service.seating.response.config.enums.SeatEnum;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeatingLaunchManger  extends ServiceLaunchManager {

    public SeatingLaunchManger(UltifiLinkMonitor ultifiLinkMonitor, CanManagerMonitor canManagerMonitor, CarPropertyManagerMonitor carPropertyManagerMonitor) {
        super(ultifiLinkMonitor, canManagerMonitor, carPropertyManagerMonitor);
    }

    public void registerTopicMethod() {
        mUltifiLinkMonitor.registerRPCMethod(new String[]{
                ServiceConstant.SEATING_RPC_POSITION_METHOD,
                //todo next add seat method...
        }, Arrays.asList(ServiceConstant.SEATING_SERVICE));


        UEntity uEntity = new UEntity(ServiceConstant.SEATING_URI, ServiceConstant.VERSION);
        // create all topics to uBus
        Map<String, List<String>> topicMapper = new HashMap<>();
        List<String> resource = Arrays.asList(ResourceMappingConstants.DRIVER_SEAT, ResourceMappingConstants.DRIVER_SEAT+".someip");
        // todo next add seat topic
        topicMapper.put("Seating", resource);
        for (String topic : Utility.buildTopicsList(topicMapper, uEntity)) {
            createTopic(topic);
        }
    }


    // TODO: 2023/7/2 待确认
    @Override
    public void registerCarPropertyCallback() {
        CarPropertyManagerMonitor carPropertyManagerMonitor = getmCarPropertyMgrMonitor();
        CarPropertyExtensionManager.CarPropertyExtensionCallback callback = getmPropertyExtMgrCallback();

        carPropertyManagerMonitor.registerCallback(callback, SeatEnum.values());
    }


    public void unRegisterCarPropertyCallback(){
        // todo
    }
}


