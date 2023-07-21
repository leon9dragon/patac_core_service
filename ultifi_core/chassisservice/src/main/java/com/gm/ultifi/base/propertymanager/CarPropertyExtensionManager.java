package com.gm.ultifi.base.propertymanager;

import android.car.hardware.CarPropertyValue;
import android.car.hardware.property.CarPropertyManager;
import android.util.Log;

import androidx.collection.ArraySet;

import com.gm.ultifi.base.response.config.SignalInfo;
import com.gm.ultifi.service.access.response.config.enums.SunroofEnum;
import com.gm.ultifi.service.seating.response.config.enums.SeatTemperatureEnum;

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

    // TODO: 2023/7/1 这里还有一块业务代码没分离, 变成抽象方法
    @Deprecated
    public void registerCallback(CarPropertyExtensionCallback callback) {
        synchronized (mLock) {
            if (mExtCallbacks.isEmpty()) {
                mPropertyCallback = new CarPropertyEventListenerImpl(this);
            }
            // todo other service
            for (SunroofEnum sunroofEnum : SunroofEnum.values()) {
                mPropertyManager.registerCallback(mPropertyCallback,
                        sunroofEnum.getPropertyId(), sunroofEnum.getRate());
            }
            for (SeatTemperatureEnum seatTemperatureEnum : SeatTemperatureEnum.values()) {
                mPropertyManager.registerCallback(mPropertyCallback,
                        seatTemperatureEnum.getPropertyId(), seatTemperatureEnum.getRate());
            }

            mExtCallbacks.add(callback);
        }
    }

    // TODO: 2023/7/2 是不是可以这样改? 枚举的 values 方法在泛型好像不能调用?
    public <T extends Enum<T> & SignalInfo> void registerCallback(CarPropertyExtensionCallback callback, T[] enumList) {
        synchronized (mLock) {
            if (mExtCallbacks.isEmpty()) {
                mPropertyCallback = new CarPropertyEventListenerImpl(this);
            }
            // todo other service

            for (T en : enumList) {
                mPropertyManager.registerCallback(mPropertyCallback,
                        en.getPropertyId(), en.getRate());
            }

            mExtCallbacks.add(callback);
        }
    }

    // TODO: 2023/7/1 这里也是需要分离
    @Deprecated
    public void unregisterCallback(CarPropertyExtensionCallback callback) {
        synchronized (mLock) {
            mExtCallbacks.remove(callback);

            for (SunroofEnum sunroofEnum : SunroofEnum.values()) {
                mPropertyManager.unregisterCallback(mPropertyCallback, sunroofEnum.getPropertyId());
            }
            for (SeatTemperatureEnum seatTemperatureEnum : SeatTemperatureEnum.values()) {
                mPropertyManager.unregisterCallback(mPropertyCallback, seatTemperatureEnum.getPropertyId());
            }

            if (mExtCallbacks.isEmpty()) {
                mPropertyCallback = null;
            }
        }
    }

    public <T extends Enum<T> & SignalInfo> void unregisterCallback(CarPropertyExtensionCallback callback, T[] enumList) {
        synchronized (mLock) {
            mExtCallbacks.remove(callback);

            for (T en : enumList) {
                mPropertyManager.unregisterCallback(mPropertyCallback, en.getPropertyId());
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

    public Boolean isPropertyAvailable(int propertyId, int areaId) {
        try {
            return mPropertyManager.isPropertyAvailable(propertyId, areaId);
        } catch (RuntimeException runtimeException) {
            Log.e(TAG, "CarProperty not available: " + runtimeException.getMessage());
        } catch (Exception exception) {
            Log.e(TAG, "Exception : " + exception.getMessage());
        }
        return null;
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

    public Integer getIntegerProperty(int propertyId, int areaId) {
        try {
            return mPropertyManager.getProperty(Integer.class, propertyId, areaId).getValue();
        } catch (IllegalArgumentException illegalArgumentException) {
            Log.e(TAG, "Illegal Argument Exception " + illegalArgumentException.getMessage());
        } catch (IllegalStateException illegalStateException) {
            Log.e(TAG, "Illegal State Exception " + illegalStateException.getMessage());
        } catch (Exception exception) {
            Log.e(TAG, "Exception : " + exception.getMessage());
        }
        return null;
    }

    public Integer[] getListProperty(int propertyId, int areaId) {
        try {
            return (Integer[]) mPropertyManager.getProperty(propertyId, areaId).getValue();
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