package com.gm.ultifi.service.seating.request;

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
import java.util.Map;

public class SeatTemperatureRequestProcessor extends BaseRequestProcessor {
    private static final String TAG = SeatTemperatureRequestProcessor.class.getSimpleName();

    private static Map availableMap;
    static{
        availableMap = new HashMap<>();
        availableMap.put("HEATED_SEAT", 622874671);
        availableMap.put("HEATED_LEG", 622874717);
        availableMap.put("HEATED_NECK", 622874721);
        availableMap.put("VENT_SEAT", 622874673);
        availableMap.put("VENT_LEG", 622874719);
        availableMap.put("VENT_NECK", 622874723);
    }

    private static Map configurationMap; //supported
    static{
        configurationMap = new HashMap<>();
        configurationMap.put("VENT_SEAT", 622874672);
        configurationMap.put("VENT_LEG", 622874718);
        configurationMap.put("VENT_NECK", 622874722);
//        SC_BACK and SC_CUSHION
        configurationMap.put("HEATED_SEAT", 622874670);
//        LEGREST(need to add)
        configurationMap.put("HEATED_LEG", 622874716);
//       SC_NECK_SCARF
        configurationMap.put("HEATED_NECK", 622874720);
    }

    private static Map setPropertyMap;
    static{
        setPropertyMap = new HashMap<>();
        availableMap.put("HEATED_SEAT", 356517131);
        availableMap.put("HEATED_LEG", 624971864);
        availableMap.put("HEATED_NECK", 624971865);
        availableMap.put("VENT_SEAT", 356517139);
        availableMap.put("VENT_LEG", 624971866);
        availableMap.put("VENT_NECK", 624971867);
    }

    private static Map areaMap;
    static {
        areaMap = new HashMap<>();
        areaMap.put("seat.row1_left", 1);
//        areaMap.put("seat.row1_center", ?);
        areaMap.put("seat.row1_right", 4);
        areaMap.put("seat.row2_left", 16);
//        areaMap.put("seat.row2_center", ?);
        areaMap.put("seat.row2_right", 64);
        areaMap.put("seat.row3_left", 256);
//        areaMap.put("seat.row3_center", ?);
        areaMap.put("seat.row3_right", 1024);

    }

    // 判断是不是support available，然后再取对应component

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

        int areaId = (int) areaMap.get(name);


        String key = combineKey(component, mode);
        if(key == null){
            return StatusUtils.buildStatus(Code.UNAVAILABLE, "fail to update field");
        }
        if(checkCondition(areaId, key)){
            int properId = (int)setPropertyMap.get(key);
            this.setCarProperty(Integer.class, properId, areaId, temperLevel);
            return StatusUtils.buildStatus(Code.OK);
        }

        return StatusUtils.buildStatus(Code.UNKNOWN, "fail to update field");
    }

    protected Boolean checkCondition(int areaId, String key){
        return isAvailable(areaId, key) && isSupported(areaId, key);
    }

    protected Boolean isAvailable(int areaId, String key){
        CarPropertyExtensionManager carPropertyManager = getCarPropertyExtensionManager();
        if(key == null){
            return false;
        }
        Integer propId = (Integer) availableMap.get(key);
        return carPropertyManager.isPropertyAvailable(propId, areaId);
    }

    protected Boolean isSupported(Integer areaId, String key){
        CarPropertyExtensionManager carPropertyManager = getCarPropertyExtensionManager();
        if(key == null){
            return false;
        }
        Integer propId = (Integer) configurationMap.get(key);
        return carPropertyManager.isPropertyAvailable(propId, areaId);
    }

    public String combineKey(SeatComponent component, SeatTemperature.Mode mode){
        String keyMode = convertMode(mode);
        String keyCom = convertCom(component);
        if(keyMode!=null && keyCom!=null){
            return keyMode + "_" + keyCom;
        }
        else {
            return null;
        }
    }

    public String convertCom(SeatComponent component){
        if(component == SeatComponent.SC_BACK||component==SeatComponent.SC_CUSHION){
            return "SEAT";
        }
        else if(component == SeatComponent.SC_NECK_SCARF){
            return "NECK";
        }
//        else if(component == SeatComponent.SC_LEGREST){
//            return "LEG";
//        }
        else {
            return null;
        }
    }

    public String convertMode(SeatTemperature.Mode mode){
        if(mode == SeatTemperature.Mode.M_HEAT){
            return "HEATED";
        }
        else if(mode == SeatTemperature.Mode.M_VENT){
            return "VENT";
        }
        else {
            return null;
        }
    }

}
