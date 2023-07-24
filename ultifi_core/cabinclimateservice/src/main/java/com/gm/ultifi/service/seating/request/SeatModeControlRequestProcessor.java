package com.gm.ultifi.service.seating.request;

import static com.gm.ultifi.base.utils.SeatAreaIdConst.GLOBAL_AREA_ID;

import android.util.Log;

import com.gm.ultifi.base.propertymanager.CarPropertyExtensionManager;
import com.gm.ultifi.base.request.BaseRequestProcessor;
import com.google.rpc.Code;
import com.google.rpc.Status;
import com.ultifi.core.common.util.StatusUtils;
import com.ultifi.vehicle.body.seating.v1.UpdateSeatModeRequest;

import java.util.ArrayList;
import java.util.Arrays;


public class SeatModeControlRequestProcessor extends BaseRequestProcessor {

    private static final String TAG = SeatModeControlRequestProcessor.class.getSimpleName();
    @Override
    public Status processRequest() {
        Log.i(TAG, "processRequest: Seat Mode Control Request request");
        UpdateSeatModeRequest req = this.mRequest.unpack(UpdateSeatModeRequest.class).orElse(null);
        if (req == null) {
            return StatusUtils.buildStatus(Code.UNKNOWN);
        }
        Integer modeVal = req.getModeValue();
        Log.d(TAG, "Testing start to get condition.");
        if(isAvailable(modeVal)&&isSupported(modeVal)){
            this.setCarProperty(Integer.class, 557856838, GLOBAL_AREA_ID, modeVal);
            return StatusUtils.buildStatus(Code.OK);
        }
        return StatusUtils.buildStatus(Code.UNKNOWN, "fail to update field");
    }

    protected Boolean isAvailable(Integer val){
//        557928531
        return checkCondition(val, 557928531);

    }

    protected Boolean isSupported(Integer val){
        //557928530
        return checkCondition(val, 557928530);
    }

    protected Boolean checkCondition(Integer inx, Integer propertyId){
        CarPropertyExtensionManager carPropertyManager = getCarPropertyExtensionManager();
        Integer[] valList = carPropertyManager.getListProperty(propertyId, GLOBAL_AREA_ID);
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(valList));
        // the list includes a serial of value, and the index corresponding with mode type
        // todo double check the logic
        int value = list.get(inx);
        return value ==2 || value ==3;
    }
}
