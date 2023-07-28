package com.gm.ultifi.service.cabinclimate.manager.request.rpc;

import android.text.TextUtils;
import android.util.Log;

import com.gm.ultifi.service.cabinclimate.manager.CarPropertyManagerMonitor;
import com.gm.ultifi.service.cabinclimate.manager.request.helper.SystemSettingsRequestProcessor;
import com.gm.ultifi.service.cabinclimate.manager.request.helper.ZoneRequestProcessor;
import com.gm.ultifi.service.cabinclimate.response.mapper.BaseMapper;
import com.google.rpc.Status;
import com.ultifi.core.rpc.Request;

public class RequestProcessorFactory {

    private static final String TAG = RequestProcessorFactory.class.getSimpleName();

    private static class RequestProcessorFactorySingleton {
        private static final RequestProcessorFactory INSTANCE = new RequestProcessorFactory();
    }

    public static RequestProcessorFactory getInstance() {
        return RequestProcessorFactorySingleton.INSTANCE;
    }

    public RequestProcessor getRequestProcessor(String processor) {
        if (TextUtils.isEmpty(processor)) {
            Log.i(TAG, "processor string is empty");
        } else {
            if (processor.equals(BaseMapper.CLIMATE_ZONE_URI)) {
                return new ZoneRequestProcessor();
            }
            if (processor.equals(BaseMapper.SYSTEM_SETTINGS_URI)) {
                return new SystemSettingsRequestProcessor();
            }
        }
        return null;
    }

    public interface RequestProcessor {
        Status processRequest();

        void setCarPropertyManagerMonitor(CarPropertyManagerMonitor carPropertyManagerMonitor);

        void setRequestMessage(Request request);
    }
}