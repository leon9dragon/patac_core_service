package com.gm.ultifi.service.cabinclimate.manager.request.helper;

import com.google.rpc.Code;
import com.google.rpc.Status;
import com.ultifi.core.common.util.StatusUtils;

/**
 * Zone related signal request processor
 */
public class ZoneRequestProcessor extends BaseRequestProcessor {

    private static final String TAG = ZoneRequestProcessor.class.getSimpleName();

    @Override
    public Status processRequest() {
        //Todo should implement all process logic for Zone related signals
        return StatusUtils.buildStatus(Code.OK);
    }
}