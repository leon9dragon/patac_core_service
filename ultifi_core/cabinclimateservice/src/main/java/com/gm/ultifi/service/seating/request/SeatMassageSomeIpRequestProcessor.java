package com.gm.ultifi.service.seating.request;

import android.util.Log;

import com.gm.ultifi.base.request.BaseRequestProcessor;
import com.gm.ultifi.base.servicemanager.ServiceLaunchManager;
import com.gm.ultifi.service.seating.someip.SeatViewModel;
import com.google.rpc.Code;
import com.google.rpc.Status;
import com.ultifi.core.common.util.StatusUtils;
import com.ultifi.vehicle.body.seating.v1.UpdateSeatMassageRequest;

public class SeatMassageSomeIpRequestProcessor extends BaseRequestProcessor {

    private static final String TAG = SeatMassageSomeIpRequestProcessor.class.getSimpleName();

    private static SeatViewModel seatViewModel = ServiceLaunchManager.seatViewModel;

    @Override
    public Status processRequest() {

        Log.i(TAG, "processRequest: process seat massage request");

        UpdateSeatMassageRequest req = this.mRequest.unpack(UpdateSeatMassageRequest.class).orElse(null);

        if (req == null) {
            return StatusUtils.buildStatus(Code.UNKNOWN);
        }
        String name = req.getName();
        int mode = req.getTypeValue();
        int intensity = req.getIntensity();// if mode=1, intensity=0
        int modeVal = mode > 30? mode-30: mode;

        Log.i(TAG, "Seat massage name: " + name + ", update mode: " + mode + ", update intensity:"+ intensity);
        boolean status = false;
        //todo check if it is necessary to get the configuration
        if(name.equals("seat.row1_right") && seatViewModel.getPassSeatMassStatus()!=null &&checkSupportedMode(name, modeVal)){
            status = seatViewModel.setPassSeatMassageReq(modeVal, intensity);

        }
        else if(name.equals("seat.row2_left")&&seatViewModel.getSecLeftSeatMassStatus()!=null &&checkSupportedMode(name, modeVal)){
            status = seatViewModel.setSecLeftSeatMassageReq(modeVal, intensity);
        }
        else if(name.equals("seat.row2_right")&&seatViewModel.getSecRightSeatMassStatus()!=null &&checkSupportedMode(name, modeVal)){
            status = seatViewModel.setSecRightSeatMassageReq(modeVal, intensity);
        }
        else if(name.equals("seat.row3_left")&&seatViewModel.getThdLeftSeatMassStatus()!=null &&checkSupportedMode(name, modeVal)){
            status = seatViewModel.setThdLeftSeatMassageReq(modeVal, intensity);
        }
        else if(name.equals("seat.row3_right")&&seatViewModel.getThdRightSeatMassStatus()!=null &&checkSupportedMode(name, modeVal)){
            status = seatViewModel.setThdRightSeatMassageReq(modeVal, intensity);
        }

        return status ? StatusUtils.buildStatus(Code.OK, "success") : StatusUtils.buildStatus(Code.UNKNOWN, "fail to update field");
    }

    protected boolean checkSupportedMode(String seatName, int modeVal){
        boolean isSupported = false;
        switch (seatName){
            case "seat.row1_right":
                if(seatViewModel.getPassSeatMassConf() && modeVal==1){
                    isSupported = true;
                }
                break;
            case "seat.row2_left":
                if(seatViewModel.getSecLeftSeatMassConf()){
                    isSupported = true;
                }
                break;
            case "seat.row2_right":
                if(seatViewModel.getSecRightSeatMassConf()){
                    isSupported = true;
                }
                break;
            case "seat.row3_left":
                if(seatViewModel.getThdLeftSeatMassConf()){
                    isSupported = true;
                }
                break;
            case "seat.row3_right":
                if(seatViewModel.getThdRightSeatMassConf()){
                    isSupported = true;
                }
                break;
        }
        return isSupported;
    }

}
