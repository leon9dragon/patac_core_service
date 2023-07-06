package com.gm.ultifi.service.seating.request;

import static com.gm.ultifi.base.propertymanager.ProtobufMessageIds.POSITION;
import static com.gm.ultifi.base.utils.SeatAreaIdConst.GLOBAL_AREA_ID;

import android.util.Log;

import com.gm.ultifi.base.propertymanager.CarPropertyExtensionManager;
import com.gm.ultifi.base.request.BaseRequestProcessor;
import com.gm.ultifi.vehicle.body.seating.v1.SeatComponent;
import com.gm.ultifi.vehicle.body.seating.v1.SeatTemperature;
import com.google.rpc.Code;
import com.google.rpc.Status;
import com.ultifi.core.common.util.StatusUtils;
import com.ultifi.vehicle.body.seating.v1.UpdateSeatTemperatureRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeatTemperatureRequestProcessor extends BaseRequestProcessor {
    private static final String TAG = SeatTemperatureRequestProcessor.class.getSimpleName();

    private static Map heatPropertyMap;
    static{
        heatPropertyMap = new HashMap();
        heatPropertyMap.put("HEATED_SEAT_CONFIGURATION", 622874670);
        heatPropertyMap.put("HEATED_LEG_CONFIGURATION", 622874716);
        heatPropertyMap.put("HEATED_NICK_CONFIGURATION", 622874720);
    }

    private static Map ventPropertyMap;
    static{
        ventPropertyMap = new HashMap();
        ventPropertyMap.put("VENT_SEAT_CONFIGURATION", 622874672);
        ventPropertyMap.put("VENT_NECK_CONFIGURATION", 622874722);
        ventPropertyMap.put("VENT_LEG_CONFIGURATION", 622874718);
    }

    @Override
    public Status processRequest() {
        Log.i(TAG, "processRequest: process sunroof request");
        // transfer the mRequest.payload to RPC request message
        // SunroofCommand is the RPC request message to command the sunroof, see in dev portal
        // can find this object in com.ultifi.vehicle.body.access.v1, don't need to define by ourselves
        UpdateSeatTemperatureRequest req = this.mRequest.unpack(UpdateSeatTemperatureRequest.class).orElse(null);

        if (req == null) {
            return StatusUtils.buildStatus(Code.UNKNOWN);
        }

        String name = req.getName();
        SeatTemperature.Mode mode = req.getMode();
        SeatComponent component = req.getComponent();
        SeatTemperature.TemperatureLevel temperLevel= req.getTemperatureLevel();


        // need to check if it needs to add in each condition
        this.setCarProperty(Integer.class, SeatModePropertyId, GLOBAL_AREA_ID, mode);

        if(mode.equals(SeatTemperature.Mode.M_HEAT)){
            if(isSupportedHeat(component)){

                // set that component heat propertyId
                this.setCarProperty(Integer.class, SeatHeatPropertyId, GLOBAL_AREA_ID, mode);
                this.setCarProperty(Integer.class, LevelPropertyId, GLOBAL_AREA_ID, temperLevel);
            }
        }
        else if(mode.equals(SeatTemperature.Mode.M_VENT)){
            if(isSupportedVent(component)){
                // set that component vent propertyId
                this.setCarProperty(Integer.class, SeatVentPropertyId, GLOBAL_AREA_ID, mode);
                this.setCarProperty(Integer.class, LevelPropertyId, GLOBAL_AREA_ID, temperLevel);
            }
        }
        else{
            this.setCarProperty(Integer.class, SeatVentPropertyId, GLOBAL_AREA_ID, mode);
        }

        return StatusUtils.buildStatus(Code.OK);



        return StatusUtils.buildStatus(Code.UNKNOWN, "fail to update field");
    }

    protected Boolean checkTemperatureEnable(){
        return false;
    }

    protected Boolean isSupportedHeat(SeatComponent componentName){
        CarPropertyExtensionManager carPropertyManager = getCarPropertyExtensionManager();
        Integer propertyId = (Integer) heatPropertyMap.get(componentName);
        Integer isSupport = carPropertyManager.getIntegerProperty(propertyId, GLOBAL_AREA_ID);
        return isSupport == 1;

    }

    protected Boolean isSupportedVent(SeatComponent componentName){
        CarPropertyExtensionManager carPropertyManager = getCarPropertyExtensionManager();
        Integer propertyId = (Integer) ventPropertyMap.get(componentName);
        Integer isSupport = carPropertyManager.getIntegerProperty(propertyId, GLOBAL_AREA_ID);
        return isSupport == 1;
    }

}
