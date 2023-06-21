package com.gm.ultifi.service.access;

import com.gm.ultifi.base.monitor.CanManagerMonitor;
import com.gm.ultifi.base.monitor.CarPropertyManagerMonitor;
import com.gm.ultifi.base.monitor.UltifiLinkMonitor;
import com.gm.ultifi.base.response.mapper.BaseMapper;
import com.gm.ultifi.base.servicemanager.ServiceLaunchManager;
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UEntity;
import com.gm.ultifi.service.access.manager.request.helper.SunroofSomeIpRequestProcessor;
import com.gm.ultifi.service.access.response.mapper.ResourceMappingConstants;
import com.gm.ultifi.service.access.response.mapper.SunroofMapper;
import com.gm.ultifi.base.utils.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccessLaunchManger extends ServiceLaunchManager {

    public AccessLaunchManger(UltifiLinkMonitor ultifiLinkMonitor, CanManagerMonitor canManagerMonitor, CarPropertyManagerMonitor carPropertyManagerMonitor) {
        super(ultifiLinkMonitor, canManagerMonitor, carPropertyManagerMonitor);
    }

    public void registerTopicMethod() {
        Map<String, List<String>> topicMapper = new HashMap<>();
        List<String> resource = Arrays.asList(ResourceMappingConstants.SUNROOF_FRONT, ResourceMappingConstants.SUNROOF_FRONT+".someip");
        topicMapper.put("Sunroof", resource);
        mUltifiLinkMonitor.registerRPCMethod(new String[]{
                SunroofMapper.SUNROOF_RPC_METHOD,
                SunroofSomeIpRequestProcessor.SUNROOF_RPC_METHOD_SOME_IP,
        });

        UEntity uEntity = new UEntity(BaseMapper.BASE_URI_SERVICE, BaseMapper.VERSION);
        // create all topics to uBus


        for (String topic : Utility.buildTopicsList(topicMapper, uEntity)) {
            createTopic(topic);
        }
    }
}
