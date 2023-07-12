package com.gm.ultifi.base.request;

import com.gm.ultifi.base.monitor.CarPropertyManagerMonitor;
import com.gm.ultifi.base.propertymanager.CarPropertyExtensionManager;
import com.gm.ultifi.factory.RequestProcessorFactory;
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

}