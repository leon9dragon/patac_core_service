package com.gm.ultifi.service.access;

import com.gm.ultifi.base.monitor.CanManagerMonitor;
import com.gm.ultifi.base.monitor.CarPropertyManagerMonitor;
import com.gm.ultifi.base.monitor.UltifiLinkMonitor;
import com.gm.ultifi.base.propertymanager.CarPropertyExtensionManager;
import com.gm.ultifi.base.servicemanager.ServiceLaunchManager;
import com.gm.ultifi.base.utils.Utility;
import com.gm.ultifi.service.access.response.config.enums.SunroofEnum;
import com.gm.ultifi.service.constant.ResourceMappingConstants;
import com.gm.ultifi.service.constant.ServiceConstant;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccessLaunchManger extends ServiceLaunchManager {

    public AccessLaunchManger(UltifiLinkMonitor ultifiLinkMonitor, CanManagerMonitor canManagerMonitor, CarPropertyManagerMonitor carPropertyManagerMonitor) {
        super(ultifiLinkMonitor, canManagerMonitor, carPropertyManagerMonitor);
    }

    public void registerTopicMethod() {
        mUltifiLinkMonitor.registerRPCMethod(new String[]{
                ServiceConstant.SUNROOF_RPC_METHOD,
                ServiceConstant.SUNROOF_RPC_METHOD_SOME_IP,
                //todo next add seat method...
        }, Arrays.asList(ServiceConstant.ACCESS_SERVICE));


        // create all topics to uBus
        Map<String, List<String>> topicMapper = new HashMap<>();
        List<String> topicResource = Arrays.asList(ResourceMappingConstants.SUNROOF_FRONT, ResourceMappingConstants.SUNROOF_FRONT+".someip");

        // first param is ultifi protobuf topic message's name
        topicMapper.put("Sunroof", topicResource);
        for (String topic : Utility.buildTopicsList(topicMapper, ServiceConstant.ACCESS_SERVICE)) {
            createTopic(topic);
        }
    }

    // TODO: 2023/7/2 这样好像没增加多少理解难度
    @Override
    public void registerCarPropertyCallback() {
        CarPropertyManagerMonitor carPropertyManagerMonitor = getmCarPropertyMgrMonitor();
        CarPropertyExtensionManager.CarPropertyExtensionCallback callback = getmPropertyExtMgrCallback();

        carPropertyManagerMonitor.registerCallback(callback, SunroofEnum.values());
    }
}
