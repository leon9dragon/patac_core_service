package com.gm.ultifi.service.seating.request;

import android.util.Log;

import com.gm.ultifi.base.servicemanager.ServiceLaunchManager;
import com.gm.ultifi.service.seating.someip.SeatViewModel;
import com.google.rpc.Code;
import com.google.rpc.Status;
import com.ultifi.core.common.util.StatusUtils;
import com.ultifi.vehicle.body.seating.v1.UpdateSeatPositionGroupRequest;
import com.ultifi.vehicle.body.seating.v1.UpdateSeatPositionRequest;

import java.util.List;


public class SeatPositionGroupSomeIpRequestProcessor extends SeatPositionSomeIpRequestProcessor{
    private static final String TAG = SeatPositionGroupSomeIpRequestProcessor.class.getSimpleName();

    private static SeatViewModel seatViewModel = ServiceLaunchManager.seatViewModel;

    @Override
    public Status processRequest() {

        Log.i(TAG, "SeatPositionGroup Request: process seat position some ip request.");

        UpdateSeatPositionGroupRequest req = this.mRequest.unpack(UpdateSeatPositionGroupRequest.class).orElse(null);
        List<UpdateSeatPositionRequest> reqList = req.getPositionList();

        return StatusUtils.buildStatus(Code.UNKNOWN, "fail to update field");
    }



}
