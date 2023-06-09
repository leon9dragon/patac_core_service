package com.gm.ultifi.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.os.SystemProperties;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LifecycleService;

import com.gm.ultifi.service.cabinclimate.manager.CanManagerMonitor;
import com.gm.ultifi.service.cabinclimate.manager.CarPropertyManagerMonitor;
import com.gm.ultifi.service.cabinclimate.manager.ServiceLaunchManager;
import com.gm.ultifi.service.cabinclimate.manager.UltifiLinkMonitor;
import com.gm.ultifi.service.cabinclimate.utils.TaskRunner;

public class CabinClimateService extends LifecycleService {

    private static final String TAG = CabinClimateService.class.getSimpleName();

    private static final String SDV_ENABLE_PROP = "persist.sys.gm.sdv_enable";

    private static final String CHANNEL_ID = "com.gm.ultifi.service.cabinclimate";
    private static final String CHANNEL_NAME= "CabinClimate Service";

    private boolean isSDVEnabled = false;

    private ServiceLaunchManager mLaunchManager = null;

    public CabinClimateService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        isSDVEnabled = SystemProperties.getBoolean(SDV_ENABLE_PROP, true);
        Log.i(TAG, "isSDV enabled: " + isSDVEnabled);
        if (isSDVEnabled) {
            Log.i(TAG, "CabinClimate service started");
            init();
        }
        startForeground();
    }

    @Nullable
    @Override
    public IBinder onBind(@NonNull Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (isSDVEnabled) {
            mLaunchManager.stop();
            TaskRunner.getInstance().shutdownService();

            Log.i(TAG, "CabinClimate service is destroyed");
        }
    }

    private void init() {
        UltifiLinkMonitor ultifiLinkMonitor = new UltifiLinkMonitor(this);
        CanManagerMonitor canManagerMonitor = new CanManagerMonitor(this);
        CarPropertyManagerMonitor carPropertyManagerMonitor = new CarPropertyManagerMonitor(this);
        mLaunchManager = new ServiceLaunchManager(ultifiLinkMonitor, canManagerMonitor, carPropertyManagerMonitor);
        mLaunchManager.init();
    }

    private void startForeground() {
        // Make sure self being a foreground service
        NotificationChannel notificationChannel = new NotificationChannel(
                CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_NONE);

        notificationChannel.setLightColor(Color.RED);
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.createNotificationChannel(notificationChannel);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
            Notification notification = builder.setOngoing(true)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("CabinClimateService is running")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory("service")
                    .build();

            startForeground(2, notification);
        }
    }
}