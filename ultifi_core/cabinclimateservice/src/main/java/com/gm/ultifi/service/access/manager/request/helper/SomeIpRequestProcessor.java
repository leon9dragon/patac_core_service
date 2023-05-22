package com.gm.ultifi.service.access.manager.request.helper;

import static com.gm.ultifi.service.access.manager.propertymanager.ProtobufMessageIds.POSITION;

import android.util.Log;

import com.google.rpc.Code;
import com.google.rpc.Status;
import com.ultifi.core.common.util.StatusUtils;
import com.ultifi.vehicle.body.access.v1.Sunroof;
import com.ultifi.vehicle.body.access.v1.SunroofCommand;

public class SomeIpRequestProcessor extends BaseRequestProcessor {

    private static final String TAG = SomeIpRequestProcessor.class.getSimpleName();

    @Override
    public Status processRequest() {
        // TODO: 2023/5/11 someip 客户端转接处理逻辑
        Log.i(TAG, "processRequest: process some/ip request");
        // this step is to get the request, same as car property processor
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

        if (POSITION.equals(fieldName)) {
            // TODO: 2023/5/15 将参数转换成SomeIpData格式, 然后通过client来调用server提供的方法

        }

        return StatusUtils.buildStatus(Code.UNKNOWN, "fail to update field");
    }
}
