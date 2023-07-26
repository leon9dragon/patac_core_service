package com.gm.ultifi.service.seating.request;

import android.util.Log;

import com.gm.ultifi.base.request.BaseRequestProcessor;
import com.gm.ultifi.base.servicemanager.ServiceLaunchManager;
import com.gm.ultifi.service.seating.someip.SeatViewModel;
import com.gm.ultifi.vehicle.body.seating.v1.SeatComponent;
import com.google.rpc.Code;
import com.google.rpc.Status;
import com.ultifi.core.common.util.StatusUtils;
import com.ultifi.vehicle.body.seating.v1.UpdateSeatPositionRequest;

public class SeatPositionSomeIpRequestProcessor extends BaseRequestProcessor {

    private static final String TAG = SeatPositionSomeIpRequestProcessor.class.getSimpleName();
    
    private static SeatViewModel seatViewModel = ServiceLaunchManager.seatViewModel;

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
        int percentPosition = 0;
        boolean lumbarMov = true;                   //todo need to confirm how is the performance in app?
        if(!component.equals(SeatComponent.SC_LUMBAR)) {
            percentPosition = (int) (req.getPosition() / 0.025); // update Position
            Log.i(TAG, "seat name: " + seatName + ", update value: " + percentPosition);
        }

        // TODO: 2023/5/15 将参数转换成SomeIpData格式, 然后通过client来调用server提供的方法
        // if it needs to check the setDriverSeatRecallReq method to set service enabled
//        boolean serviceStatus = seatViewModel.setDriverSeatRecallReq(true);
        boolean status = false;
        
        if(seatName.equals("seat.row1_left")) {
            seatViewModel.setDriverSeatRecallReq(true);
            if(component==SeatComponent.SC_SIDE_BOLSTER_CUSHION) {
                // cannot found the available
                if(seatViewModel.getDriverBolster()) {
                    status = seatViewModel.setDriverBolsterReq(percentPosition);
                }
            }
            if(component==SeatComponent.SC_HEADREST){
                if(seatViewModel.getDriverHead()&&seatViewModel.getDriverHeadUpStatus()) {
                    status = seatViewModel.setDriverHeadRestReq(percentPosition);
                }
            }
            // TODO need to add enums leg rest request

//            if(component==SeatComponent.SC_SIDE_BOLSTER_BACK){
//                if(seatViewModel.getDriverBolster()&&seatViewModel.getDriverLeg()) {
//                    status = seatViewModel.setDriverLegRestReq(percentPosition);
//                }
//            }

            if(component==SeatComponent.SC_CUSHION){
                if(direction== UpdateSeatPositionRequest.Direction.D_BACKWARD ||direction== UpdateSeatPositionRequest.Direction.D_FORWARD){
                    if(seatViewModel.getDriverSeat()&&seatViewModel.getDriverForwardStatus()) {
                        status = seatViewModel.setDriverSeatPosReq(percentPosition);
                    }
                }
                else {
                    if(seatViewModel.getDriverCushionRear()&&seatViewModel.getDriverCushionRearStatus()) {
                        status = seatViewModel.setDriverCushionRearReq(percentPosition);
                    }
                }
            }
            if(component==SeatComponent.SC_BACK){
                if(seatViewModel.getDriverRecline()&&seatViewModel.getDriverReclineStatus()) {
                    status = seatViewModel.setDriverReclinesReq(percentPosition);
                }
            }
            if(component==SeatComponent.SC_CUSHION_FRONT){
                if(seatViewModel.getDriverCushionFront()&&seatViewModel.getDriverCushionFrontStatus()) {
                    status = seatViewModel.setDriverCushionFrontReq(percentPosition);
                }
            }
            seatViewModel.setDriverSeatRecallReq(false);
            return status ? StatusUtils.buildStatus(Code.OK, "success") : StatusUtils.buildStatus(Code.UNKNOWN, "fail to update field");

        }
        else if (seatName.equals("seat.row1_right")) {
            
            seatViewModel.setPassSeatRecallReq(true);
            if(component==SeatComponent.SC_BACK) {
                if(seatViewModel.getPassRecline() && seatViewModel.getPassReclineStatus()) {
                    status = seatViewModel.setPassReclineRecallReq(percentPosition);
                }
            }
            if(component==SeatComponent.SC_CUSHION_FRONT){
                if(seatViewModel.getPassCushionFront()&&seatViewModel.getPassCushionFrontStatus()) {
                    status = seatViewModel.setPassCushionFrontRecallReq(percentPosition);
                }
            }
            if(component==SeatComponent.SC_CUSHION){
                if(direction== UpdateSeatPositionRequest.Direction.D_BACKWARD ||direction== UpdateSeatPositionRequest.Direction.D_FORWARD){
                    if(seatViewModel.getPassForward()&&seatViewModel.getPassSeatForwardStatus()) {
                        status = seatViewModel.setPassSeatFrontRecallReq(percentPosition);
                    }
                }
                else {
                    if(seatViewModel.getPassCushionRear()&&seatViewModel.getPassCushionRearStatus()) {
                        status = seatViewModel.setPassCushionRearRecallReq(percentPosition);
                    }
                }
            }
            // todo need to confirm footrest and direction of headrest
            if(component==SeatComponent.SC_FOOTREST){
                if(seatViewModel.getPassFootUpward()&&seatViewModel.getPassFootStatus()) {
                    status = seatViewModel.setPassFootRecallReq(percentPosition);
                }
            }
            // todo check head rest forward/backward configuration?
            if(component==SeatComponent.SC_HEADREST){
                if(direction== UpdateSeatPositionRequest.Direction.D_BACKWARD ||direction== UpdateSeatPositionRequest.Direction.D_FORWARD) {
                    if(seatViewModel.getPassHeadForwardStatus()) {
                        status = seatViewModel.setPassHeadBackRecallReq(percentPosition);
                    }
                }
                else {
                    if(seatViewModel.getPassHeadUpward()&&seatViewModel.getPassHeadUpwardStatus()) {
                        status = seatViewModel.setPassHeadUpRecallReq(percentPosition);
                    }
                }
            }
            seatViewModel.setPassSeatRecallReq(false);
            return status ? StatusUtils.buildStatus(Code.OK, "success") : StatusUtils.buildStatus(Code.UNKNOWN, "fail to update field");
        }
        else if (seatName.equals("seat.row2_left")) {
            seatViewModel.setSecLeftRecallReq(true);
            if(component==SeatComponent.SC_ARMREST){
                //todo check arm rest configuration
                if(seatViewModel.getSecondLeftArmStatus()) {
                    status = seatViewModel.setSecLeftArmPos(percentPosition);
                }
            }
            if(component==SeatComponent.SC_CUSHION_FRONT){
                if(seatViewModel.getSecondLeftCushionFront()&&seatViewModel.getSecondLeftCushionFrontStatus()) {
                    status = seatViewModel.setSecLeftCushionFrontPos(percentPosition);
                }
            }
            if(component==SeatComponent.SC_CUSHION){
                //todo if there is left right direction
                if(direction== UpdateSeatPositionRequest.Direction.D_BACKWARD||direction== UpdateSeatPositionRequest.Direction.D_FORWARD) {
                    if(seatViewModel.getSecondLeftCushionRear()&&seatViewModel.getSecondLeftCushionRearStatus()) {
                        status = seatViewModel.setSecLeftCushionRearPos(percentPosition);
                    }
                }
                else {
                    status = seatViewModel.setSecLeftSeatForwardPos(percentPosition);
                }
            }
            if(component==SeatComponent.SC_BACK){
                if(seatViewModel.getSecondLeftRecline()&&seatViewModel.getSecondLeftReclineStatus()) {
                    status = seatViewModel.setSecLeftReclinePos(percentPosition);
                }
            }
            if(component==SeatComponent.SC_HEADREST){
                if(direction== UpdateSeatPositionRequest.Direction.D_FORWARD||direction== UpdateSeatPositionRequest.Direction.D_BACKWARD) {
                    if(seatViewModel.getSecondLeftHead()&&seatViewModel.getSecondLeftHeadForwardStatus()) {
                        status = seatViewModel.setSecLeftHeadForwardPos(percentPosition);
                    }
                }
                else {
                    if(seatViewModel.getSecondLeftHead()&&seatViewModel.getSecondLeftHeadUpwardStatus()) {
                        status = seatViewModel.setSecLeftHeadUpwardPos(percentPosition);
                    }
                }
            }
            if(component==SeatComponent.SC_LEGREST){
                status = seatViewModel.setSecLeftLegOutwardPos(percentPosition);
            }
            if(component==SeatComponent.SC_FOOTREST){
                status = seatViewModel.setSecLeftFootPos(percentPosition);
            }
            if(component==SeatComponent.SC_LUMBAR && seatViewModel.getSecondLeftLumbar() && seatViewModel.getSecondLeftLumbarStatus()){
                if(direction== UpdateSeatPositionRequest.Direction.D_FORWARD) {
                    status = seatViewModel.setSecLeftLmbrFwd(lumbarMov);
                }
                if(direction==UpdateSeatPositionRequest.Direction.D_BACKWARD){
                    status = seatViewModel.setSecLeftLmbrBkwd(lumbarMov);
                }
                if(direction==UpdateSeatPositionRequest.Direction.D_UP){
                    status = seatViewModel.setSecLeftLmbrUpwd(lumbarMov);
                }
                if(direction==UpdateSeatPositionRequest.Direction.D_DOWN){
                    status = seatViewModel.setSecLeftLmbrDnwd(lumbarMov);
                }
            }
            return status ? StatusUtils.buildStatus(Code.OK, "success") : StatusUtils.buildStatus(Code.UNKNOWN, "fail to update field");
        }
        else if (seatName.equals("seat.row2_right")) {
            seatViewModel.setSecRightRecallReq(true);
            if(component==SeatComponent.SC_ARMREST){
                //todo check arm rest configuration
                if(seatViewModel.getSecRightArmStatus()) {
                    status = seatViewModel.setSecRightArmPos(percentPosition);
                }
            }
            if(component==SeatComponent.SC_CUSHION_FRONT){
                if(seatViewModel.getSecRightCushionFront()&&seatViewModel.getSecRightCushionFrontStatus()) {
                    status = seatViewModel.setSecRightCushionFrontPos(percentPosition);
                }
            }
            if(component==SeatComponent.SC_CUSHION){
                //todo if there is left right direction
                if(direction== UpdateSeatPositionRequest.Direction.D_BACKWARD||direction== UpdateSeatPositionRequest.Direction.D_FORWARD) {
                    if(seatViewModel.getSecRightCushionRear()&&seatViewModel.getSecRightCushionRearStatus()) {
                        status = seatViewModel.setSecRightCushionRearPos(percentPosition);
                    }
                }
                else {
                    status = seatViewModel.setSecRightForwardPos(percentPosition);
                }
            }
            if(component==SeatComponent.SC_BACK){
                if(seatViewModel.getSecRightRecline()&&seatViewModel.getSecRightReclineStatus()) {
                    status = seatViewModel.setSecRightReclinePos(percentPosition);
                }
            }
            if(component==SeatComponent.SC_HEADREST){
                if(direction== UpdateSeatPositionRequest.Direction.D_FORWARD||direction== UpdateSeatPositionRequest.Direction.D_BACKWARD) {
                    if(seatViewModel.getSecRightHeadForwardStatus()) {
                        status = seatViewModel.setSecRightHeadForwardPos(percentPosition);
                    }
                }
                else {
                    if(seatViewModel.getSecRightHeadUpward()&&seatViewModel.getSecRightHeadUpwardStatus()) {
                        status = seatViewModel.setSecRightHeadUpwardPos(percentPosition);
                    }
                }
            }
            if(component==SeatComponent.SC_LEGREST){
                status = seatViewModel.setSecRightLegOutwardPos(percentPosition);
            }
            if(component==SeatComponent.SC_FOOTREST){
                status = seatViewModel.setSecRightFootPos(percentPosition);
            }
            if(component==SeatComponent.SC_LUMBAR && seatViewModel.getSecRightLumbar() && seatViewModel.getSecRightLumbarStatus()){
                if(direction== UpdateSeatPositionRequest.Direction.D_FORWARD) {
                    status = seatViewModel.setSecRightLmbrFwd(lumbarMov);
                }
                if(direction==UpdateSeatPositionRequest.Direction.D_BACKWARD){
                    status = seatViewModel.setSecRightLmbrBkwd(lumbarMov);
                }
                if(direction==UpdateSeatPositionRequest.Direction.D_UP){
                    status = seatViewModel.setSecRightLmbrUpwd(lumbarMov);
                }
                if(direction==UpdateSeatPositionRequest.Direction.D_DOWN){
                    status = seatViewModel.setSecRightLmbrDnwd(lumbarMov);
                }
            }

            return status ? StatusUtils.buildStatus(Code.OK, "success") : StatusUtils.buildStatus(Code.UNKNOWN, "fail to update field");
        }
        else if(seatName.equals("seat.row3_left")){
            seatViewModel.setThirdLeftRecallReq(true);
            // todo if the component is correct
            if(component==SeatComponent.SC_CUSHION){
                if(seatViewModel.getThdLeftCushionFoldConf()&&seatViewModel.getThdLeftCushionFoldStatus()) {
                    status = seatViewModel.setThirdLeftCushionFoldReq(percentPosition);
                }
            }
            if(component==SeatComponent.SC_CUSHION){
                if(seatViewModel.getThdLeftForwardConf()&&seatViewModel.getThdLeftForwardStatus()) {
                    status = seatViewModel.setThirdLeftForwardReq(percentPosition);
                }
            }
            if(component==SeatComponent.SC_BACK){
                if(seatViewModel.getThdLeftReclineUpwardConf()&&seatViewModel.getThdLeftReclineStatus()) {
                    status = seatViewModel.setThirdLeftReclineReq(percentPosition);
                }
            }
            return status ? StatusUtils.buildStatus(Code.OK, "success") : StatusUtils.buildStatus(Code.UNKNOWN, "fail to update field");
        }
        else if(seatName.equals("seat.row3_right")){
            seatViewModel.setThirdRightRecallReq(true);
            // todo if the component is correct
            if(component==SeatComponent.SC_CUSHION){
                if(seatViewModel.getThirdRightCushionFoldConf()&&seatViewModel.getThdRightCushionFoldStatus()) {
                    status = seatViewModel.setThirdRightCushionFoldReq(percentPosition);
                }
            }
            if(component==SeatComponent.SC_CUSHION){
                if(seatViewModel.getThirdRightForwardConf()&&seatViewModel.getThdRightForwardStatus()) {
                    status = seatViewModel.setThirdRightForwardReq(percentPosition);
                }
            }
            if(component==SeatComponent.SC_BACK){
                if(seatViewModel.getThirdRightReclineUpwardConf()&&seatViewModel.getThdRightReclineStatus()) {
                    status = seatViewModel.setThirdRightReclineReq(percentPosition);
                }
            }
            return status ? StatusUtils.buildStatus(Code.OK, "success") : StatusUtils.buildStatus(Code.UNKNOWN, "fail to update field");
        }
        else {
            return StatusUtils.buildStatus(Code.UNKNOWN, "fail to update field");
        }
    }
}
