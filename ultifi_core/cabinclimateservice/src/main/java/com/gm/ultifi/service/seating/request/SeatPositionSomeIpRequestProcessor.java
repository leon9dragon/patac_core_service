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

        // if it will use direction?
        UpdateSeatPositionRequest.Direction direction = req.getDirection();
        int percentPosition = req.getPosition(); // update Position
        Log.i(TAG, "seat name: " + seatName + ", update value: " + percentPosition);

        // TODO: 2023/5/15 将参数转换成SomeIpData格式, 然后通过client来调用server提供的方法
        // if it needs to check the setDriverSeatRecallReq method to set service enabled
//        boolean serviceStatus = ServiceLaunchManager.seatViewModel.setDriverSeatRecallReq(true);

        if(seatName.equals("row1_left")) {
            boolean status = false;
            ServiceLaunchManager.seatViewModel.setDriverSeatRecallReq(true);
            if(component==SeatComponent.SC_SIDE_BOLSTER_CUSHION) {
                status = ServiceLaunchManager.seatViewModel.setDriverBolsterReq(percentPosition);
            }
            if(component==SeatComponent.SC_HEADREST){
                status = ServiceLaunchManager.seatViewModel.setDriverHeadRestReq(percentPosition);
            }
            if(component==SeatComponent.SC_SIDE_BOLSTER_BACK){
                status = ServiceLaunchManager.seatViewModel.setDriverLegRestReq(percentPosition);
            }
            if(component==SeatComponent.SC_BACK){
                status = ServiceLaunchManager.seatViewModel.setDriverSeatPosReq(percentPosition);
            }
            if(component==SeatComponent.SC_LUMBAR){
                status = ServiceLaunchManager.seatViewModel.setDriverReclinesReq(percentPosition);
            }
            if(component==SeatComponent.SC_CUSHION_FRONT){
                status = ServiceLaunchManager.seatViewModel.setDriverCushionFrontReq(percentPosition);
            }
            if(component==SeatComponent.SC_CUSHION){
                status = ServiceLaunchManager.seatViewModel.setDriverCushionRearReq(percentPosition);
            }
//            ServiceLaunchManager.seatViewModel.setDriverSeatRecallReq(false);
            return status ? StatusUtils.buildStatus(Code.OK, "success") : StatusUtils.buildStatus(Code.UNKNOWN, "fail to update field");
        }

        return StatusUtils.buildStatus(Code.UNKNOWN, "fail to update field");
    }
}
