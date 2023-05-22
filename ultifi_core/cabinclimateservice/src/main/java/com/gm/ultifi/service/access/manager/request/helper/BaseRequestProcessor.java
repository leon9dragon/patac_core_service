package com.gm.ultifi.service.access.manager.request.helper;

import com.gm.ultifi.service.access.manager.CarPropertyManagerMonitor;
import com.gm.ultifi.service.access.manager.propertymanager.CarPropertyExtensionManager;
import com.gm.ultifi.service.access.manager.request.rpc.RequestProcessorFactory;
import com.ultifi.core.rpc.Request;

/**
 * TODO: 2023/4/26 框架代码抽离
 */
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

    // TODO: 2023/5/10 增加some/ip需要进行调用的方法, 结合sdk调用server方法, 再增加对应的processor进行业务处理
}