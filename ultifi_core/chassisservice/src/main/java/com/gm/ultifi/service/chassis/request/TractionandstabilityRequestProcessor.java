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

        UpdateTractionandStabilitySystemRequest.TractionandStabilitySystemRequest updateTractionandStabilitySystemRequest=req.getTractionandstabilitysystemrequest();
        this.setCarProperty(Integer.class,557857069,GLOBAL_AREA_ID,updateTractionandStabilitySystemRequest.getNumber());
        Log.i(TAG,"processRequest: finish set updateTractionandStabilitySystemRequest = " + updateTractionandStabilitySystemRequest.getNumber());
        return StatusUtils.buildStatus(Code.OK);

//       以下代码为原先带field mask时所写
//        TractionControlSystem tractioncontrolsystem = req.getTractionControlSystem();
//        ElectronicStabilityControlSystem electronicstabilitycontrolsystem = req.getElectronicStabilityControlSystem();
//        boolean tractionSystemControlConvert = false;
//        boolean electronicStabilityConvert = false;

//        int count = req.getUpdateMask().getPathsCount();
//        for (int i = 0; i < count; i++) {
//            String path = req.getUpdateMask().getPaths(i);//TractionControlSystem.isEnabled, Electronicstabilitycontrolsystem.isEnabled
//
//            if("TractionControlSystem.isEnabled".equals(path)){
//                tractionSystemControlConvert = tractioncontrolsystem.getIsEnabled();
//            }
//
//            if("Electronicstabilitycontrolsystem.isEnabled".equals(path)){
//                electronicStabilityConvert = electronicstabilitycontrolsystem.getIsEnabled();
//            }
//        }
//
//        //Log.i(TAG, "target field: " + fieldName + ", update value: " + tractioncontrolsystem.getIsEnabled());
//
//        if (tractionSystemControlConvert && electronicStabilityConvert) {
//            this.setCarProperty(Integer.class, 557857069, GLOBAL_AREA_ID, 1);
//            return StatusUtils.buildStatus(Code.OK);
//        } else if (!tractionSystemControlConvert && electronicStabilityConvert) {
//            this.setCarProperty(Integer.class, 557857069, GLOBAL_AREA_ID, 3);
//            return StatusUtils.buildStatus(Code.OK);
//        } else if (tractionSystemControlConvert) {
//            return StatusUtils.buildStatus(Code.UNKNOWN, "fail to update field");
//        } else {
//            this.setCarProperty(Integer.class, 557857069, GLOBAL_AREA_ID, 2);
//            return StatusUtils.buildStatus(Code.OK);
//        }


    }
}
