package com.gm.ultifi.service.cabinclimate.manager;

import android.car.Car;
import android.car.hardware.property.CarPropertyManager;
import android.content.Context;
import android.util.Log;

import com.gm.ultifi.service.cabinclimate.manager.propertymanager.CarPropertyExtensionManager;

/**
 * Car Property Manager access helper
 */
public class CarPropertyManagerMonitor implements ConnectionManager {

    private static final String TAG = CarPropertyManagerMonitor.class.getSimpleName();

    private final Context mContext;

    private Car mCar;
    private final Car.CarServiceLifecycleListener mCarServiceLifecycleListener;

    private CarPropertyManager mCarPropertyManager;

    private OnCarManagerConnectionListener mOnCarManagerConnectionListener;

    private CarPropertyExtensionManager mPropertyExtensionManager;

    public CarPropertyManagerMonitor(Context context) {
        mContext = context;
        mCarServiceLifecycleListener = (car, ready) -> {
            Log.d(TAG, "onLifecycleChanged ready: " + ready);
            if (ready) {
                mCar = car;
                onCarServiceConnected();
            } else {
                onCarServiceDisconnected();
            }
        };
    }

    @Override
    public void connect() {
    }

    @Override
    public void init() {
        Log.d(TAG, "init called and starting car service");

        startCarService();
    }

    @Override
    public void stop() {
        if (mCar != null) {
            mCar.disconnect();
            mCar = null;

            Log.d(TAG, "stop called and car api is disconnected and set to null");
        }
    }

    public boolean isCarPropertyManagerReady() {
        return mCar.isConnected() && mCarPropertyManager != null;
    }

    public CarPropertyExtensionManager getCarPropertyExtensionManager() {
        if (!isCarPropertyManagerReady()) {
            return null;
        } else {
            return mPropertyExtensionManager;
        }
    }

    public void setConnectionListener(OnCarManagerConnectionListener listener) {
        mOnCarManagerConnectionListener = listener;
    }

    public void registerCallback(CarPropertyExtensionManager.CarPropertyExtensionCallback callback) {
        if (mPropertyExtensionManager == null) {
            Log.e(TAG, "mPropertyExtensionManager is null");
        } else if (!isCarPropertyManagerReady()) {
            Log.e(TAG, "mCarPropertyManager is null");
        } else {
            mPropertyExtensionManager.registerCallback(callback);
        }
    }

    public void unRegisterCallback(CarPropertyExtensionManager.CarPropertyExtensionCallback callback) {
        if (mPropertyExtensionManager == null) {
            Log.e(TAG, "mPropertyExtensionManager is null");
        } else if (!isCarPropertyManagerReady()) {
            Log.e(TAG, "mCarPropertyManager is null");
        } else {
            mPropertyExtensionManager.unregisterCallback(callback);
        }
    }

    private void onCarServiceConnected() {
        Log.d(TAG, "onCarServiceConnected");

        mCarPropertyManager = (CarPropertyManager) mCar.getCarManager(Car.PROPERTY_SERVICE);
        if (mCarPropertyManager != null) {
            mPropertyExtensionManager = new CarPropertyExtensionManager(mCarPropertyManager);
            mOnCarManagerConnectionListener.onCarManagerConnectionChanged(true);
        } else {
            Log.e(TAG, "onCarServiceConnected while carPropertyManager is null");
        }
    }

    private void onCarServiceDisconnected() {
        Log.d(TAG, "onCarServiceDisconnected");

        mOnCarManagerConnectionListener.onCarManagerConnectionChanged(false);
        if (isCarPropertyManagerReady()) {
            mCarPropertyManager = null;
        }
    }

    private void startCarService() {
        Log.d(TAG, "startCarService");
        if (mCar == null) {
            mCar = Car.createCar(mContext, null, 0L, mCarServiceLifecycleListener);
        }
    }

    public interface OnCarManagerConnectionListener {
        void onCarManagerConnectionChanged(boolean isConnected);
    }
}