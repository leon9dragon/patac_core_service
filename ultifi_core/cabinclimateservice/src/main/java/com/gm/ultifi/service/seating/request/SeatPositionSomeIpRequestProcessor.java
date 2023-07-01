package com.gm.ultifi.service.seating.request;

import android.util.Log;

import com.gm.ultifi.base.request.BaseRequestProcessor;
import com.gm.ultifi.base.servicemanager.ServiceLaunchManager;
import com.gm.ultifi.vehicle.body.seating.v1.SeatComponent;
import com.google.rpc.Code;
import com.google.rpc.Status;
import com.ultifi.core.common.util.StatusUtils;
import com.ultifi.vehicle.body.seating.v1.UpdateSeatPositionRequest;

public class SeatPositionSomeIpRequestProcessor extends BaseRequestProcessor {

    private static final String TAG = SeatPositionSomeIpRequestProcessor.class.getSimpleName();

    @Override
    public Status processRequest() {
        Log.i(TAG, "processRequest: process seat position some ip request.");

        UpdateSeatPositionRequest req = this.mRequest.unpack(UpdateSeatPositionRequest.class).orElse(null);

        if (req == null) {
            return StatusUtils.buildStatus(Code.UNKNOWN);
        }

        String seatName = req.getName();

        SeatComponent component = req.getComponent();
        UpdateSeatPositionRequest.Direction direction = req.getDirection();
        int percentPosition = req.getPosition(); // update Position
        Log.i(TAG, "seat name: " + seatName + ", update value: " + percentPosition);

        // TODO: 2023/5/15 将参数转换成SomeIpData格式, 然后通过client来调用server提供的方法
        if(seatName.equals("row1_left")) {
            boolean status = ServiceLaunchManager.seatViewModel.setDriverSeatHeadLegRestRecallPosReq(component, percentPosition);
            return status?  StatusUtils.buildStatus(Code.OK, "success"):  StatusUtils.buildStatus(Code.UNKNOWN, "fail to update field");
        }

        return StatusUtils.buildStatus(Code.UNKNOWN, "fail to update field");
    }
}
