package com.gm.ultifi.service.seating.request;

import android.util.Log;

import com.gm.ultifi.base.request.BaseRequestProcessor;
import com.google.rpc.Code;
import com.google.rpc.Status;
import com.ultifi.core.common.util.StatusUtils;

public class SeatModeControlRequestProcessor extends BaseRequestProcessor {

    private static final String TAG = SeatModeControlRequestProcessor.class.getSimpleName();
    @Override
    public Status processRequest() {
        Log.i(TAG, "processRequest: Seat Mode Control Request request");

//        UpdateSeatModeRequest req = this.mRequest.unpack(UpdateSeatModeRequest.class).orElse(null);
//        if (req ==null){
//            return StatusUtils.buildStatus(Code.UNKNOWN);
//        }
//        Mode mode = req.getMode();

        return StatusUtils.buildStatus(Code.UNKNOWN, "fail to update field");
    }

//    protected Boolean checkModeControlStatus(Mode mode){
//        CarPropertyExtensionManager carPropertyManager = getCarPropertyExtensionManager();
////        Update property id
////        Boolean seatModeControlAvailable = carPropertyManager.isPropertyAvailable(557847224, GLOBAL_AREA_ID);
//        Log.i(TAG, "seatModeControlAvailable: " + seatModeControlAvailable);
//
////        Update property id
////        Boolean seatModeControlControlAllowed = carPropertyManager.getBooleanProperty(555750073, GLOBAL_AREA_ID);
//        Log.i(TAG, "seatModeControlControlAllowed: " + seatModeControlControlAllowed);
//
//        Mode curSeatMode = carPropertyManager.getProperty(557856808, GLOBAL_AREA_ID);
//
//        Boolean isSeatModeChanged = !mode.equals(curSeatMode);
//
//        return seatModeControlAvailable && seatModeControlControlAllowed && isSeatModeChanged;
//    }
}
