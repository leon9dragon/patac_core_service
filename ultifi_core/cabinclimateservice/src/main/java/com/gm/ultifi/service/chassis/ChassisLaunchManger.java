package com.gm.ultifi.service.chassis;

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

public class ChassisLaunchManger extends ServiceLaunchManager {
    public ChassisLaunchManger(UltifiLinkMonitor ultifiLinkMonitor, CanManagerMonitor canManagerMonitor, CarPropertyManagerMonitor carPropertyManagerMonitor) {
        super(ultifiLinkMonitor, canManagerMonitor, carPropertyManagerMonitor);
    }

    public void registerTopicMethod() {
        mUltifiLinkMonitor.registerRPCMethod(new String[]{
                ServiceConstant.CHASSIS_RPC_TRACTION_METHOD,
                //todo next add seat method...
        }, Arrays.asList(ServiceConstant.CHASSIS_SERVICE));


        // create all topics to uBus
        Map<String, List<String>> topicMapper = new HashMap<>();
        List<String> TireTopicResource = Arrays.asList(ResourceMappingConstants.TIRE_FRONT_LEFT,ResourceMappingConstants.TIRE_FRONT_RIGHT,ResourceMappingConstants.TIRE_REAR_LEFT,ResourceMappingConstants.TIRE_REAR_RIGHT);
        List<String> SteeringAngleTopicResource = Arrays.asList(ResourceMappingConstants.STEERING_ANGLE);
        List<String> TractionControlSystemTopicResource = Arrays.asList(ResourceMappingConstants.TRACTION_CONTROL);
        List<String> ElectronicStabilityControlTopicResource = Arrays.asList(ResourceMappingConstants.ELECTRONIC_STABILITY_CONTROL);

        // first param is ultifi protobuf topic message's name
        topicMapper.put("Tire", TireTopicResource);
        topicMapper.put("SteeringAngle", SteeringAngleTopicResource);
        topicMapper.put("TractionControlSystem", TractionControlSystemTopicResource);
        topicMapper.put("ElectronicStabilityControlSystem", ElectronicStabilityControlTopicResource);
        for (String topic : Utility.buildTopicsList(topicMapper, ServiceConstant.CHASSIS_SERVICE)) {
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

    public void unRegisterCarPropertyCallback(){
    }
}
