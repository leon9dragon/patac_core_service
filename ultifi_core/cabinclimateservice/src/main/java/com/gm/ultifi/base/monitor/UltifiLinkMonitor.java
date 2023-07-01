package com.gm.ultifi.base.monitor;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UAuthority;
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UEntity;
import com.gm.ultifi.sdk.uprotocol.uri.factory.UltifiUriFactory;
import com.gm.ultifi.base.response.mapper.BaseMapper;
import com.google.rpc.Status;
import com.ultifi.core.UltifiLink;
import com.ultifi.core.common.util.StatusUtils;

import io.cloudevents.CloudEvent;

/**
 * Ultifi(SOA) Link access helper
 * TODO: 2023/4/26 框架代码抽离
 */
public class UltifiLinkMonitor implements ConnectionManager {

    private static final String TAG = UltifiLinkMonitor.class.getSimpleName();

    private final Context mContext;

    private UltifiLink mUltifiLink;
    private UltifiLink.ServiceLifecycleListener mUltifiLinkLifeCycleListener;

    private UltifiLink.RequestListener mRPCRequestListener;
    /*
     * TODO: 2023/4/26 should switch to new listener because UltifiLink.RequestListener has been deprecated
     */
    private UltifiLink.RequestEventListener mRPCRequestEventListener;

    public UltifiLinkMonitor(Context paramContext) {
        mContext = paramContext;
    }

    @Override
    public void connect() {
        if (!mUltifiLink.isConnected()) {
            mUltifiLink.connect();
            Log.i(TAG, "UltifiLink connected");
        }
    }

    @Override
    public void init() {
        HandlerThread handlerThread = new HandlerThread("ClimateServiceConnectionThread");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());

        mUltifiLink = UltifiLink.Companion.create(mContext, handler, mUltifiLinkLifeCycleListener);
    }

    @Override
    public void stop() {
        if (mUltifiLink.isConnected()) {
            mUltifiLink.disconnect();
            Log.i(TAG, "UltifiLink disconnected");
        }
    }

    public void setConnectionListener(UltifiLink.ServiceLifecycleListener listener) {
        mUltifiLinkLifeCycleListener = listener;
    }

    public void setRPCRequestListener(UltifiLink.RequestListener listener) {
        mRPCRequestListener = listener;
    }

    public void setRPCRequestListener(UltifiLink.RequestEventListener listener) {
        mRPCRequestEventListener = listener;
    }

    public void registerRPCMethod(String[] methodNames) {
        for (String methodName : methodNames) {
            UAuthority uAuthority = UAuthority.local();
            // TODO: 2023/5/25 注意业务处理, 这边的服务前缀可能有很多
            UEntity uEntity = BaseMapper.SERVICE;
            String methodUri = UltifiUriFactory.buildMethodUri(uAuthority, uEntity, methodName);
            // todo methodUri 放什么位置，放topic中？
            Status status = mUltifiLink.registerRequestListener(methodUri, mRPCRequestListener);
            Log.i(TAG, "registerRPCMethod URI: " + methodUri + " Status: " + StatusUtils.toShortString(status));
        }
    }

    public boolean isUltifiLinkReady() {
        return mUltifiLink != null && mUltifiLink.isConnected();
    }


    public void publish(CloudEvent event) {
        if (!isUltifiLinkReady()) {
            Log.i(TAG, "Publish failed. UltifiLink not connected (state):" + mUltifiLink.isConnected() + " isReadyState: false");
            return;
        }

        Status status = mUltifiLink.publish(event);
        Log.i(TAG, "Published Cloud Event Status: " + StatusUtils.toShortString(status));
    }
}