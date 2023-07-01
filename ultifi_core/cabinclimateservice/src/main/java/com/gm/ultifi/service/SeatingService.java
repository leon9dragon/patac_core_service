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

import com.gm.ultifi.base.monitor.CanManagerMonitor;
import com.gm.ultifi.base.monitor.CarPropertyManagerMonitor;
import com.gm.ultifi.base.monitor.UltifiLinkMonitor;
import com.gm.ultifi.base.servicemanager.ServiceLaunchManager;
import com.gm.ultifi.base.utils.TaskRunner;
import com.gm.ultifi.service.access.AccessLaunchManger;

public class SeatingService extends LifecycleService {

    private static final String TAG = SeatingService.class.getSimpleName();

    private static final String SDV_ENABLE_PROP = "persist.sys.gm.sdv_enable";

    private static final String CHANNEL_ID = "com.gm.ultifi.service.seating";
    private static final String CHANNEL_NAME = "Seating Service";

    public static SeatingService context = null;

    private boolean isSDVEnabled = false;

    public static ServiceLaunchManager mLaunchManager = null;

    public SeatingService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        isSDVEnabled = SystemProperties.getBoolean(SDV_ENABLE_PROP, true);
        Log.i(TAG, "isSDV enabled: " + isSDVEnabled);
        if (isSDVEnabled) {
            Log.i(TAG, "Access service started");
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

            Log.i(TAG, "Access service is destroyed");
        }
    }

    private void init() {
        UltifiLinkMonitor ultifiLinkMonitor = new UltifiLinkMonitor(this);
        CanManagerMonitor canManagerMonitor = new CanManagerMonitor(this);
        CarPropertyManagerMonitor carPropertyManagerMonitor = new CarPropertyManagerMonitor(this);
        mLaunchManager = new AccessLaunchManger(ultifiLinkMonitor, canManagerMonitor, carPropertyManagerMonitor);
        mLaunchManager.init();
    }

    private void startForeground() {
        // Make sure self being a foreground service
        NotificationChannel notificationChannel =
                new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_NONE);

        notificationChannel.setLightColor(Color.RED);
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.createNotificationChannel(notificationChannel);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
            Notification notification = builder.setOngoing(true)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("SeatingService is running")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory("service")
                    .build();

            startForeground(3, notification);
        }
    }


}
