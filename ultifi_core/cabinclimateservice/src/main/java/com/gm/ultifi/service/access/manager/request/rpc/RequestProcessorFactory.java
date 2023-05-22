package com.gm.ultifi.service.access.manager.request.rpc;

import android.text.TextUtils;
import android.util.Log;

import com.gm.ultifi.service.access.manager.CarPropertyManagerMonitor;
import com.gm.ultifi.service.access.manager.request.helper.SunroofRequestProcessor;
import com.gm.ultifi.service.access.response.mapper.BaseMapper;
import com.google.rpc.Status;
import com.ultifi.core.rpc.Request;

/**
 * TODO: 2023/4/26 框架代码抽离
 */
public class RequestProcessorFactory {

    private static final String TAG = RequestProcessorFactory.class.getSimpleName();

    private static class RequestProcessorFactorySingleton {
        private static final RequestProcessorFactory INSTANCE = new RequestProcessorFactory();
    }

    public static RequestProcessorFactory getInstance() {
        return RequestProcessorFactorySingleton.INSTANCE;
    }

    /**
     * TODO: 2023/5/10 业务代码抽离 增加 someip client 的 mapping
     * mapping the target processor to handle the request
     */
    public RequestProcessor getRequestProcessor(String processor) {
        if (TextUtils.isEmpty(processor)) {
            Log.i(TAG, "processor string is empty");
        } else {
            Log.i(TAG, "TRY TO GET PROCESSOR: " + processor);
            if (processor.equals(BaseMapper.SUNROOF_RESOURCE_URI)) {
                return new SunroofRequestProcessor();
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