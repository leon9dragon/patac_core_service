package com.gm.ultifi.service.cabinclimate.manager.propertymanager;

import android.car.hardware.CarPropertyValue;
import android.car.hardware.property.CarPropertyManager;
import android.util.Log;

import androidx.collection.ArraySet;

import com.gm.ultifi.service.cabinclimate.enums.SystemSettingsEnum;
import com.gm.ultifi.service.cabinclimate.enums.ZoneEnum;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * This class is a wrapper of CarPropertyManager, which implemented actual signal business
 */
public class CarPropertyExtensionManager {

    private static final String TAG = CarPropertyExtensionManager.class.getSimpleName();

    private final CarPropertyManager mPropertyManager;

    private final Object mLock = new Object();
    private final ArraySet<CarPropertyExtensionCallback> mExtCallbacks = new ArraySet<>();

    private CarPropertyManager.CarPropertyEventCallback mPropertyCallback;

    public CarPropertyExtensionManager(CarPropertyManager carPropertyManager) {
        mPropertyManager = carPropertyManager;
        mPropertyCallback = null;
    }

    public void registerCallback(CarPropertyExtensionCallback callback) {
        synchronized (mLock) {
            if (mExtCallbacks.isEmpty()) {
                mPropertyCallback = new CarPropertyEventListenerImpl(this);
            }
//            for (ZoneEnum zoneEnum : ZoneEnum.values()) {
//                mPropertyManager.registerCallback(mPropertyCallback,
//                        zoneEnum.getPropertyId(), zoneEnum.getRate());
//            }
//            for (SystemSettingsEnum systemSettingsEnum : SystemSettingsEnum.values()) {
//                mPropertyManager.registerCallback(mPropertyCallback,
//                        systemSettingsEnum.getPropertyId(), systemSettingsEnum.getRate());
//            }

            mExtCallbacks.add(callback);
        }
    }

    public void unregisterCallback(CarPropertyExtensionCallback callback) {
        synchronized (mLock) {
            mExtCallbacks.remove(callback);

            for (ZoneEnum zoneEnum : ZoneEnum.values()) {
                mPropertyManager.unregisterCallback(mPropertyCallback, zoneEnum.getPropertyId());
            }
            for (SystemSettingsEnum systemSettingsEnum : SystemSettingsEnum.values()) {
                mPropertyManager.unregisterCallback(mPropertyCallback, systemSettingsEnum.getPropertyId());
            }

            if (mExtCallbacks.isEmpty()) {
                mPropertyCallback = null;
            }
        }
    }

    public String setProperty(Class propertyClass, int propId, int areaId, Object value) {
        try {
            mPropertyManager.setProperty(propertyClass, propId, areaId, value);
            return null;
        } catch (IllegalStateException illegalStateException) {
            Log.e(TAG, "Illegal State Exception " + illegalStateException.getMessage());
            return "VHAL " + illegalStateException.getMessage();
        } catch (RuntimeException runtimeException) {
            Log.e(TAG, "CarProperty not available: " + runtimeException.getMessage());
            return "VHAL " + runtimeException.getMessage();
        } catch (Exception exception) {
            Log.e(TAG, "Exception : " + exception.getMessage());
            return "VHAL " + exception.getMessage();
        }
    }

    public Boolean getBooleanProperty(int propertyId, int areaId) {
        try {
            if (mPropertyManager.isPropertyAvailable(propertyId, areaId)) {
                return getPropertyValue(propertyId, areaId);
            }
        } catch (RuntimeException runtimeException) {
            Log.e(TAG, "CarProperty not available: " + runtimeException.getMessage());
        } catch (Exception exception) {
            Log.e(TAG, "Exception : " + exception.getMessage());
        }
        return null;
    }

    private Boolean getPropertyValue(int propertyId, int areaId) {
        try {
            return mPropertyManager.getProperty(Boolean.class, propertyId, areaId).getValue();
        } catch (IllegalArgumentException illegalArgumentException) {
            Log.e(TAG, "Illegal Argument Exception " + illegalArgumentException.getMessage());
        } catch (IllegalStateException illegalStateException) {
            Log.e(TAG, "Illegal State Exception " + illegalStateException.getMessage());
        } catch (Exception exception) {
            Log.e(TAG, "Exception : " + exception.getMessage());
        }
        return null;
    }

    private void handleOnChangeEvent(CarPropertyValue value) {
        synchronized (mLock) {
            ArrayList<CarPropertyExtensionCallback> callbacks = new ArrayList<>(mExtCallbacks);
            for (CarPropertyExtensionCallback callback : callbacks) {
                callback.onChangeEvent(value);
            }
        }
    }

    private void handleOnErrorEvent(int propertyId, int zone) {
        synchronized (mLock) {
            ArrayList<CarPropertyExtensionCallback> callbacks = new ArrayList<>(mExtCallbacks);
            for (CarPropertyExtensionCallback callback : callbacks) {
                callback.onErrorEvent(propertyId, zone);
            }
        }
    }

    private static class CarPropertyEventListenerImpl implements CarPropertyManager.CarPropertyEventCallback {

        private final WeakReference<CarPropertyExtensionManager> mManager;

        public CarPropertyEventListenerImpl(CarPropertyExtensionManager manager) {
            mManager = new WeakReference<>(manager);
        }

        @Override
        public void onChangeEvent(CarPropertyValue value) {
            CarPropertyExtensionManager manager = mManager.get();
            if (manager != null) {
                manager.handleOnChangeEvent(value);
            }
        }

        @Override
        public void onErrorEvent(int propertyId, int zone) {
            CarPropertyExtensionManager manager = mManager.get();
            if (manager != null) {
                manager.handleOnErrorEvent(propertyId, zone);
            }
        }
    }

    public interface CarPropertyExtensionCallback {
        void onChangeEvent(CarPropertyValue<?> value);

        void onErrorEvent(int propertyId, int zone);
    }
}