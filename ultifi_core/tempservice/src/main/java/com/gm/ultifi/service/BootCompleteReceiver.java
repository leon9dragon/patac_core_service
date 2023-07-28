package com.gm.ultifi.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import gm.ultifi.canbridge.BuildConfig;

public class BootCompleteReceiver extends BroadcastReceiver {

    private static final String TAG = BootCompleteReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            Log.i(TAG, "onReceive action: " + action);

            // Start foreground service when boot complete
            if (Intent.ACTION_BOOT_COMPLETED.equals(action)
                || (BuildConfig.DEBUG && "com.patac.intent.action.TEST".equals(action))) {
//                context.startForegroundService(new Intent(context, CabinClimateService.class));
                context.startForegroundService(new Intent(context, AccessService.class));
            }
        }
    }
}