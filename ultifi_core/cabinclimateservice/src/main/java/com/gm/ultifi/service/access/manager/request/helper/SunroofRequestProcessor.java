package com.gm.ultifi.service.access.manager.request.helper;

import static com.gm.ultifi.base.propertymanager.ProtobufMessageIds.POSITION;
import static com.gm.ultifi.base.utils.SeatAreaIdConst.GLOBAL_AREA_ID;

import android.util.Log;

import com.gm.ultifi.base.propertymanager.CarPropertyExtensionManager;
import com.gm.ultifi.base.request.BaseRequestProcessor;
import com.google.rpc.Code;
import com.google.rpc.Status;
import com.ultifi.core.common.util.StatusUtils;
import com.ultifi.vehicle.body.access.v1.Sunroof;
import com.ultifi.vehicle.body.access.v1.SunroofCommand;

/**
 * Zone related signal request processor
 */
public class SunroofRequestProcessor extends BaseRequestProcessor {

    private static final String TAG = SunroofRequestProcessor.class.getSimpleName();

    /**
     * should implement all process logic for Sunroof related signals
     */
    @Override
    public Status processRequest() {
        Log.i(TAG, "processRequest: process sunroof request");
        // transfer the mRequest.payload to RPC request message
        // SunroofCommand is the RPC request message to command the sunroof, see in dev portal
        // can find this object in com.ultifi.vehicle.body.access.v1, don't need to define by ourselves
        SunroofCommand req = this.mRequest.unpack(SunroofCommand.class).orElse(null);

        if (req == null) {
            return StatusUtils.buildStatus(Code.UNKNOWN);
        }

        // get the update field from field mask, such as "Sunroof.position", "position" is the target field name
        String path = req.getUpdateMask().getPaths(0);
        String fieldName = path.substring(path.indexOf(".") + 1);


        // get the protobuf message
        Sunroof sunroof = req.getSunroof();
        Log.i(TAG, "target field: " + fieldName + ", update value: " + sunroof.getPosition());
        // get the target field, we don't know its type now
        Object field = sunroof.getField(Sunroof.getDescriptor().findFieldByName(fieldName));

        // according to the protobuf message id to cast type
        if (POSITION.equals(fieldName)) {
            Integer newPositionStatus = (Integer) field;
            if (!checkPositionPreCondition(newPositionStatus)) {
                return StatusUtils.buildStatus(Code.UNKNOWN, "check precondition failed");
            }
            // update the new property value, property name is SUNROOF_PERCENTAGE_CONTROL_REQUEST
            this.setCarProperty(Integer.class, 557856807, GLOBAL_AREA_ID, newPositionStatus);
            Log.i(TAG, "processRequest: finish set SUNROOF_PERCENTAGE_CONTROL_REQUEST");
            return StatusUtils.buildStatus(Code.OK);
        }

        return StatusUtils.buildStatus(Code.UNKNOWN, "fail to update field");
    }

    protected Boolean checkPositionPreCondition(Integer newPosition) {
        CarPropertyExtensionManager carPropertyManager = getCarPropertyExtensionManager();

        Boolean infotainmentSunroofMotionControlAvailable
                = carPropertyManager.isPropertyAvailable(557847224, GLOBAL_AREA_ID);

        Log.i(TAG, "infotainmentSunroofMotionControlAvailable: " + infotainmentSunroofMotionControlAvailable);

        Boolean infotainmentSunroofMotionControlAllowed =
                carPropertyManager.getBooleanProperty(555750073, GLOBAL_AREA_ID);

        Log.i(TAG, "infotainmentSunroofMotionControlAllowed: " + infotainmentSunroofMotionControlAllowed);

        Integer sunroofPercentagePositionStatus
                = carPropertyManager.getIntegerProperty(557856808, GLOBAL_AREA_ID);

        Boolean isPositionChanged = !newPosition.equals(sunroofPercentagePositionStatus);

        Log.i(TAG, "sunroofPercentagePositionStatus: " + sunroofPercentagePositionStatus +
                ", new position: " + newPosition + ", isPositionChanged: " + isPositionChanged);

        Boolean isNewPositionLeagl = newPosition % 5 == 0;

        Log.i(TAG, "isNewPositionLeagl: " + isNewPositionLeagl);

        return infotainmentSunroofMotionControlAvailable && infotainmentSunroofMotionControlAllowed
                && isPositionChanged && isNewPositionLeagl;
    }
}