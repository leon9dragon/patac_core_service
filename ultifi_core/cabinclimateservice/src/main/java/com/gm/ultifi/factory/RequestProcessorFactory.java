package com.gm.ultifi.factory;

import android.text.TextUtils;
import android.util.Log;

import com.gm.ultifi.base.monitor.CarPropertyManagerMonitor;
import com.gm.ultifi.service.access.request.SunroofRequestProcessor;
import com.gm.ultifi.service.access.request.SunroofSomeIpRequestProcessor;
import com.gm.ultifi.service.seating.request.SeatPositionSomeIpRequestProcessor;
import com.gm.ultifi.service.constant.ServiceConstant;
import com.gm.ultifi.service.seating.request.SeatTemperatureRequestProcessor;
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

    /**
     * mapping the target processor to handle the request
     */
    public RequestProcessor getRequestProcessor(String processor) {
        if (TextUtils.isEmpty(processor)) {
            Log.i(TAG, "processor string is empty");
        } else {
            Log.i(TAG, "TRY TO GET PROCESSOR: " + processor);
            if (processor.equals(ServiceConstant.SUNROOF_RPC_METHOD_URI)) {
                return new SunroofRequestProcessor();
            }
            if(processor.equals(ServiceConstant.SUNROOF_RPC_METHOD_URI_SOME_IP)) {
                return new SunroofSomeIpRequestProcessor();
            }
            if(processor.equals(ServiceConstant.SEATING_RPC_POSITION_METHOD_URI_SOME_IP)){
                return new SeatPositionSomeIpRequestProcessor();
            }
            if(processor.equals(ServiceConstant.SEATING_RPC_TEMPERATURE_METHOD_URI_SOME_IP)){
                return new SeatTemperatureRequestProcessor();
            }
//            if(processor.equals(ServiceConstant.SEAT_RPC_POSITION_GROUP_METHOD)){
//                return new SeatPositionGroupSomeIpRequestProcessor();
//            }
        }
        return null;
    }

    public interface RequestProcessor {
        Status processRequest();

        void setCarPropertyManagerMonitor(CarPropertyManagerMonitor carPropertyManagerMonitor);

        void setRequestMessage(Request request);
    }
}