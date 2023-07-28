package com.gm.ultifi.service.cabinclimate.manager.request.helper;

import com.google.rpc.Code;
import com.google.rpc.Status;
import com.ultifi.core.common.util.StatusUtils;

/**
 * SystemSettings related signal request processor
 */
public class SystemSettingsRequestProcessor extends BaseRequestProcessor {

    private static final String TAG = SystemSettingsRequestProcessor.class.getSimpleName();

    @Override
    public Status processRequest() {
        //Todo should implement all process logic for SystemSettings related signals
        return StatusUtils.buildStatus(Code.OK);
    }
}