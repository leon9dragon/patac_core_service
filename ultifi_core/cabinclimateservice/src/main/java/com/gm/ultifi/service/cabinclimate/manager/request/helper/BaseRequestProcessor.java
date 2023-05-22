package com.gm.ultifi.service.cabinclimate.manager.request.helper;

import com.gm.ultifi.service.cabinclimate.manager.CarPropertyManagerMonitor;
import com.gm.ultifi.service.cabinclimate.manager.propertymanager.AreaPropertyMapper;
import com.gm.ultifi.service.cabinclimate.manager.propertymanager.CarPropertyExtensionManager;
import com.gm.ultifi.service.cabinclimate.manager.propertymanager.ProtobufMessageIds;
import com.gm.ultifi.service.cabinclimate.manager.request.rpc.RequestProcessorFactory;
import com.google.rpc.Code;
import com.google.rpc.Status;
import com.ultifi.core.common.util.StatusUtils;
import com.ultifi.core.rpc.Request;

public abstract class BaseRequestProcessor implements RequestProcessorFactory.RequestProcessor {

    private CarPropertyManagerMonitor mCarPropertyManager;

    protected Request mRequest;

    public CarPropertyExtensionManager getCarPropertyExtensionManager() {
        return mCarPropertyManager.getCarPropertyExtensionManager();
    }

    public boolean isCarPropertyExtensionManagerNull() {
        return (getCarPropertyExtensionManager() == null);
    }

    public void setCarPropertyManagerMonitor(CarPropertyManagerMonitor carPropertyManagerMonitor) {
        mCarPropertyManager = carPropertyManagerMonitor;
    }

    public void setRequestMessage(Request request) {
        mRequest = request;
    }

    protected String setCarProperty(Class<?> type, int propertyId, int areaId, Object value) {
        return getCarPropertyExtensionManager().setProperty(type, propertyId, areaId, value);
    }

    protected Status checkPreCondition(String path, String zoneName) {
        if (!path.equals("zone.power_on")) {
            CarPropertyExtensionManager carPropertyExtensionManager = getCarPropertyExtensionManager();
            //Todo should add use zone.power_on's actual car propertyId
            int propertyId = 0;// ZoneEnum.HVAC_POWER_ON.getPropertyId().intValue();
            int areaId = AreaPropertyMapper.getZoneByZoneName(zoneName, ProtobufMessageIds.POWER_ON);
            Boolean powerOn = carPropertyExtensionManager.getBooleanProperty(propertyId, areaId);
            if (powerOn != null) {
                Status status = null;
                if (powerOn) {
                    status = StatusUtils.buildStatus(Code.FAILED_PRECONDITION, "HVAC Power State: " + powerOn);
                }
                return status;
            }
        }
        return null;
    }
}