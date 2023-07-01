package com.gm.ultifi.service.access;

import com.gm.ultifi.base.monitor.CanManagerMonitor;
import com.gm.ultifi.base.monitor.CarPropertyManagerMonitor;
import com.gm.ultifi.base.monitor.UltifiLinkMonitor;
import com.gm.ultifi.base.response.mapper.BaseMapper;
import com.gm.ultifi.base.servicemanager.ServiceLaunchManager;
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UEntity;
import com.gm.ultifi.service.access.request.SunroofSomeIpRequestProcessor;
import com.gm.ultifi.service.constant.ResourceMappingConstants;
import com.gm.ultifi.base.utils.Utility;
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
                SunroofSomeIpRequestProcessor.SUNROOF_RPC_METHOD_SOME_IP,
                //todo next add seat method...
        }, Arrays.asList(ServiceConstant.ACCESS_SERVICE));


        UEntity uEntity = new UEntity(ServiceConstant.ACCESS_URI, ServiceConstant.VERSION);
        // create all topics to uBus
        Map<String, List<String>> topicMapper = new HashMap<>();
        List<String> resource = Arrays.asList(ResourceMappingConstants.SUNROOF_FRONT, ResourceMappingConstants.SUNROOF_FRONT+".someip");
        // todo next add seat topic
        topicMapper.put("Sunroof", resource);
        for (String topic : Utility.buildTopicsList(topicMapper, uEntity)) {
            createTopic(topic);
        }
    }
}
