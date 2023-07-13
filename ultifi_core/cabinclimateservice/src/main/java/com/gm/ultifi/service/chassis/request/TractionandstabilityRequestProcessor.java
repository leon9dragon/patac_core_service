package com.gm.ultifi.service.chassis.request;

import static com.gm.ultifi.base.utils.SeatAreaIdConst.GLOBAL_AREA_ID;

import android.util.Log;

import com.gm.ultifi.base.request.BaseRequestProcessor;
import com.google.rpc.Code;
import com.google.rpc.Status;
import com.ultifi.core.common.util.StatusUtils;

import com.ultifi.vehicle.chassis.v1.ElectronicStabilityControlSystem;
import com.ultifi.vehicle.chassis.v1.TractionControlSystem;
import com.ultifi.vehicle.chassis.v1.UpdateTractionandStabilitySystemRequest;

public class TractionandstabilityRequestProcessor extends BaseRequestProcessor {
    private static final String TAG = TractionandstabilityRequestProcessor.class.getSimpleName();
    @Override
    public Status processRequest() {
        Log.i(TAG, "processRequest: process chassis request");
        UpdateTractionandStabilitySystemRequest req = this.mRequest.unpack(UpdateTractionandStabilitySystemRequest.class).orElse(null);
        if (req == null) {
            return StatusUtils.buildStatus(Code.UNKNOWN);
        }
       String path = req.getUpdateMask().getPaths(3);
       String fieldName = path.substring(path.indexOf("."+1));

       TractionControlSystem tractioncontrolsystem=req.getTractionControlSystem();
       ElectronicStabilityControlSystem electronicstabilitycontrolsystem=req.getElectronicStabilityControlSystem();

        //Log.i(TAG, "target field: " + fieldName + ", update value: " + tractioncontrolsystem.getIsEnabled());

        boolean tractionSystemControlConvert = tractioncontrolsystem.getIsEnabled();
        boolean electronicStabilityConvert = electronicstabilitycontrolsystem.getIsEnabled();

        if (tractionSystemControlConvert && electronicStabilityConvert)
        {
            this.setCarProperty(Integer.class, 557857069 , GLOBAL_AREA_ID , 1);
            return StatusUtils.buildStatus(Code.OK);
            }
        else if(!tractionSystemControlConvert && electronicStabilityConvert) {
            this.setCarProperty(Integer.class, 557857069 , GLOBAL_AREA_ID , 3);
            return StatusUtils.buildStatus(Code.OK);
            }
        else if(tractionSystemControlConvert) {
            return StatusUtils.buildStatus(Code.UNKNOWN, "fail to update field");
        }
        else {
            this.setCarProperty(Integer.class, 557857069 , GLOBAL_AREA_ID , 2);
            return StatusUtils.buildStatus(Code.OK);
        }


    }
}
