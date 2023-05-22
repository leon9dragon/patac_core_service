package com.gm.ultifi.service.access.manager;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.gm.ultifi.service.access.utils.TaskRunner;

import java.util.Arrays;

import gm.ultifi.canbridge.CanManager;
import gm.ultifi.canbridge.Signal;

/**
 * CAN bridge access helper
 * TODO: 2023/4/26 框架代码抽离
 */
public class CanManagerMonitor implements ConnectionManager {

    private static final String TAG = CanManagerMonitor.class.getSimpleName();

    private static final int MIN_UPDATE_TIME = 0;

    private final Context mContext;

    private CanManager mCanManager;
    private CanManager.CANBridgeListener mCANBridgeListener;

    private OnCanManagerConnectionListener mCanManagerConnListener;

    public CanManagerMonitor(Context context) {
        mContext = context;
    }

    @Override
    public void connect() {
        if (!mCanManager.isConnected()) {
            Log.d(TAG, "CAN manager is not connected and trying to connect");
            try {
                mCanManager.connect(mCANBridgeListener);
            } catch (IllegalStateException illegalStateException) {
                Log.e(TAG, "CanManager is already connected or connecting. " + illegalStateException.getMessage());
            } catch (Exception exception) {
                Log.e(TAG, "CanManager connection failed : " + exception.getMessage());
            }
        }
    }

    @Override
    public void init() {
        try {
            CanManager.ServiceLifecycleListener lifecycleListener = (canManager, ready) -> {
                Log.i(TAG, "Is CanManager connected: " + ready);
                if (mCanManagerConnListener == null) {
                    Log.e(TAG, "mCanManagerConnListener is null");
                } else {
                    mCanManagerConnListener.onCanManagerConnectionChanged(ready);
                }
            };
            Handler handler = new Handler(Looper.myLooper());

            mCanManager = CanManager.create(mContext, lifecycleListener, handler);
        } catch (NullPointerException nullPointerException) {
            Log.e(TAG, "Null Pointer Exception." + nullPointerException.getMessage());
        } catch (Exception exception) {
            Log.e(TAG, "Exception: " + exception.getMessage());
        }
    }

    @Override
    public void stop() {
        if (!isCanManagerReady()) {
            Log.e(TAG, "CanManager is not connected");
        } else {
            mCanManager.disconnect();
        }
    }

    public CanManager getCanManager() {
        if (isCanManagerReady()) {
            return mCanManager;
        }
        return null;
    }

    public boolean isCanManagerReady() {
        return mCanManager != null && mCanManager.isConnected();
    }

    public void setConnectionListener(OnCanManagerConnectionListener listener) {
        mCanManagerConnListener = listener;
    }

    public void setCANBridgeListener(CanManager.CANBridgeListener listener) {
        mCANBridgeListener = listener;
    }

    public void registerSignal(String[] signals) {
        try {
            if (isCanManagerReady()) {
                mCanManager.subscribeSignals(signals, MIN_UPDATE_TIME);
            }
        } catch (NullPointerException nullPointerException) {
            Log.e(TAG, "CANBridgeListener is null " + nullPointerException.getMessage());
        } catch (IllegalStateException illegalStateException) {
            Log.e(TAG, "CanManager is not connected " + illegalStateException.getMessage());
        } catch (Exception exception) {
            Log.e(TAG, "Exception occurs: " + exception.getMessage());
        }
    }

    public void unRegisterSignal(String[] signals) {
        try {
            if (isCanManagerReady()) {
                mCanManager.unsubscribeSignals(signals);
            }
        } catch (NullPointerException nullPointerException) {
            Log.e(TAG, "CANBridgeListener is null " + nullPointerException.getMessage());
        } catch (IllegalStateException illegalStateException) {
            Log.e(TAG, "CanManager is not connected " + illegalStateException.getMessage());
        } catch (Exception exception) {
            Log.e(TAG, "Exception occurs: " + exception.getMessage());
        }
    }

    public void sendSignalsToCanManager(Signal[] signals) {
        if (!isCanManagerReady()) {
            Log.i(TAG, "Publish failed. CanManager not connected (state):"
                    + mCanManager.isConnected() + " isReadyState:" + isCanManagerReady());
            return;
        }
        Log.i(TAG, "Send CAN signals..." + Arrays.toString(signals));
        TaskRunner.getInstance().execute(() -> mCanManager.sendSignals(signals));
    }

    public interface OnCanManagerConnectionListener {
        void onCanManagerConnectionChanged(boolean isConnected);
    }
}