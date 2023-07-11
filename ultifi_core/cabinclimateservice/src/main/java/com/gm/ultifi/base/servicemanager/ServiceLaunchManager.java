package com.gm.ultifi.base.servicemanager;

import android.car.hardware.CarPropertyValue;
import android.util.Log;

import com.gm.ultifi.base.monitor.CanManagerMonitor;
import com.gm.ultifi.base.monitor.CarPropertyManagerMonitor;
import com.gm.ultifi.base.monitor.UltifiLinkMonitor;
import com.gm.ultifi.base.propertymanager.CarPropertyExtensionManager;
import com.gm.ultifi.base.request.listeners.UltifiLinkRequestListener;
import com.gm.ultifi.base.response.config.PropertyConfig;
import com.gm.ultifi.base.response.mapper.BaseTopic;
import com.gm.ultifi.base.response.mapper.TopicMappingFactory;
import com.gm.ultifi.sdk.uprotocol.cloudevent.datamodel.UCloudEventAttributes;
import com.gm.ultifi.sdk.uprotocol.cloudevent.factory.CloudEventFactory;
import com.gm.ultifi.service.access.someip.SunroofViewModel;
import com.gm.ultifi.service.seating.someip.SeatViewModel;
import com.google.protobuf.Any;
import com.google.rpc.Status;
import com.ultifi.core.Topic;
import com.ultifi.core.UltifiLink;
import com.ultifi.core.common.util.StatusUtils;
import com.ultifi.core.usubscription.v1.CreateTopicRequest;
import com.ultifi.core.usubscription.v1.USubscription;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import gm.ultifi.canbridge.CanManager;
import gm.ultifi.canbridge.Signal;
import gm.ultifi.canbridge.SignalGroup;
import io.cloudevents.CloudEvent;

public abstract class ServiceLaunchManager {

    private static final String TAG = ServiceLaunchManager.class.getSimpleName();

    private final CanManagerMonitor mCanMgrMonitor;
    private final CanManagerMonitor.OnCanManagerConnectionListener mOnCanManagerConnectionListener;
    private final CanManager.CANBridgeListener mCanSignalListener;

    private final CarPropertyManagerMonitor mCarPropertyMgrMonitor;
    private final CarPropertyManagerMonitor.OnCarManagerConnectionListener mOnCarPropertyMgrConnListener;
    private final CarPropertyExtensionManager.CarPropertyExtensionCallback mPropertyExtMgrCallback;

    protected final UltifiLinkMonitor mUltifiLinkMonitor;
    private final UltifiLink.ServiceLifecycleListener mOnUltifiLinkLifeCycleListener;
    private final UltifiLinkRequestListener mUltifiLinkRequestListener;

    private USubscription.BlockingStub mUSubscription;

    private boolean isCanSignalsRegistered = false;
    private boolean isCarPropertiesRegistered = false;
    private boolean isUltifiLinkConnected = false;

    public static SunroofViewModel sunroofViewModel;
    public static SeatViewModel seatViewModel;

    public ServiceLaunchManager(UltifiLinkMonitor ultifiLinkMonitor,
                                CanManagerMonitor canManagerMonitor,
                                CarPropertyManagerMonitor carPropertyManagerMonitor) {
        mOnUltifiLinkLifeCycleListener = (ultifiLink, ready) -> {
            if (ready) {
                onUltifiLinkConnected(ultifiLink);          // register methods and topic
            } else {
                onUltifiLinkDisconnected();
            }
            Log.d(TAG, "UltifiLink status, isConnected=" + ready
                    + "->isUltifiLinkConnected=" + isUltifiLinkConnected
                    + "->isCanSignalsRegistered=" + isCanSignalsRegistered
                    + "->isCarPropertiesRegistered=" + isCarPropertiesRegistered);
        };
        mUltifiLinkRequestListener = new UltifiLinkRequestListener(carPropertyManagerMonitor);

        mOnCanManagerConnectionListener = isConnected -> {
            if (isConnected) {
                // Make sure that register can bridge signals when isUltifiLinkConnected && isConnected
                if (isUltifiLinkConnected && !isCanSignalsRegistered) {
                    registerServiceSignals();
                    isCanSignalsRegistered = true;
                }
            } else {
                unRegisterServiceSignals();
                isCanSignalsRegistered = false;
            }
            Log.d(TAG, "CanManager status, isConnected=" + isConnected
                    + "->isUltifiLinkConnected=" + isUltifiLinkConnected
                    + "->isCanSignalsRegistered=" + isCanSignalsRegistered);
        };
        mOnCarPropertyMgrConnListener = isConnected -> {
            if (isConnected) {
                // Make sure that register car property signals when isUltifiLinkConnected && isConnected
                if (isUltifiLinkConnected && !isCarPropertiesRegistered) {
                    registerCarPropertyCallback();
                    isCarPropertiesRegistered = true;
                }
            } else {
                unRegisterCarPropertyCallback();
                isCarPropertiesRegistered = false;
            }
            Log.d(TAG, "CarPropertyManager status, isConnected=" + isConnected
                    + "->isUltifiLinkConnected=" + isUltifiLinkConnected
                    + "->isCarPropertiesRegistered=" + isCarPropertiesRegistered);
        };

        mCanSignalListener = new CanManager.CANBridgeListener() {
            @Override
            public void onSignals(Signal[] signals) {
                Log.i(TAG, "Received Signals: " + Arrays.toString(signals));
                for (Signal signal : signals) {
                    List<CloudEvent> list = buildSignalCloudEvents(signal);
                    if (list == null || list.size() == 0) {
                        Log.i(TAG, "Cloud Event is null");
                    } else {
                        Log.i(TAG, "Publishing the cloud events to Bus");
                        for (CloudEvent event : list) {
                            mUltifiLinkMonitor.publish(event);
                        }
                    }
                }
            }

            @Override
            public void onGroups(SignalGroup[] signalGroups) {
                Log.i(TAG, "onGroups");
            }
        };
        // supply the callback function, notifying topic subscriber the changing of property
        mPropertyExtMgrCallback = new CarPropertyExtensionManager.CarPropertyExtensionCallback() {
            @Override
            public void onChangeEvent(CarPropertyValue<?> value) {
                Log.d(TAG, "onChangeEvent: CarPropValue:" + value.toString());
                Log.i(TAG, "Publishing the cloud events to Bus");

                List<CloudEvent> list = buildCarPropertyCloudEvents(value);
                if (list == null || list.size() == 0) {
                    Log.i(TAG, "Cloud Event is null");
                } else {
                    Log.i(TAG, "Publishing the cloud events to Bus");
                    for (CloudEvent cloudEvent : list) {
                        mUltifiLinkMonitor.publish(cloudEvent);
                    }
                }
            }

            @Override
            public void onErrorEvent(int propertyId, int zone) {
                Log.e(TAG, "onErrorEvent: " + propertyId + ":" + zone);
            }
        };

        mUltifiLinkMonitor = ultifiLinkMonitor;
        mCanMgrMonitor = canManagerMonitor;
        mCarPropertyMgrMonitor = carPropertyManagerMonitor;
    }

    private void onUltifiLinkConnected(UltifiLink ultifiLink) {
        isUltifiLinkConnected = true;
        // Make sure that register can bridge signals when isUltifiLinkConnected && mCanMgrMonitor.isCanManagerReady()
        if (mCanMgrMonitor.isCanManagerReady()) {
            Log.i(TAG, "Start Registering in UltifiLink");
            registerServiceSignals();
            isCanSignalsRegistered = true;
        }
        // Make sure that register car property signals when isUltifiLinkConnected && mCarPropertyMgrMonitor.isCarPropertyManagerReady()
        if (mCarPropertyMgrMonitor.isCarPropertyManagerReady()) {
            registerCarPropertyCallback();
            isCarPropertiesRegistered = true;
        }

        mUSubscription = USubscription.newBlockingStub(ultifiLink);

        // before this step, add signingConfigs in build.gradle and add platform.keystore to config folder
        // update with actual RPC method names
        registerTopicMethod();
    }

    private void onUltifiLinkDisconnected() {
        isUltifiLinkConnected = false;
        Log.d(TAG, "UltifiLink disconnected");
        if (mCanMgrMonitor.isCanManagerReady()) {
            Log.i(TAG, "Unregistering in UltifiLink");
            unRegisterServiceSignals();
            isCanSignalsRegistered = false;
        }
        if (mCarPropertyMgrMonitor.isCarPropertyManagerReady()) {
            unRegisterCarPropertyCallback();
            isCarPropertiesRegistered = false;
        }
    }

    public void init() {
        Log.d(TAG, "initializing all the monitors");
        mUltifiLinkMonitor.setConnectionListener(mOnUltifiLinkLifeCycleListener);
        mUltifiLinkMonitor.setRPCRequestListener(mUltifiLinkRequestListener);
        mUltifiLinkMonitor.init();
        mUltifiLinkMonitor.connect();

        mCanMgrMonitor.setConnectionListener(mOnCanManagerConnectionListener);
        mCanMgrMonitor.setCANBridgeListener(mCanSignalListener);
        mCanMgrMonitor.init();
        mCanMgrMonitor.connect();

        mCarPropertyMgrMonitor.setConnectionListener(mOnCarPropertyMgrConnListener);
        mCarPropertyMgrMonitor.init();
        mCarPropertyMgrMonitor.connect();

        // TODO: 2023/7/1 或许之后改成统一的 Manager 来处理 some/ip client 的 get
        sunroofViewModel = new SunroofViewModel();

//        new Timer().schedule(new TimerTask() {
//            int position = 0;
//            @Override
//            public void run() {
//                Log.i(TAG, "run: set new position " + position
//                        + ", time: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.PRC).format(new Date()));
//                sunroofViewModel.setSunroofPosition(position);
//                position++;
//            }
//        }, 50000, 1000);
    }

    public void stop() {
        mUltifiLinkMonitor.stop();
        mCarPropertyMgrMonitor.stop();
        mCanMgrMonitor.stop();
    }

    public List<CloudEvent> buildCarPropertyCloudEvents(CarPropertyValue<?> value) {
        ArrayList<CloudEvent> events = new ArrayList<>();
        BaseTopic baseTopic = TopicMappingFactory.getInstance().getMapper(value.getPropertyId());
        baseTopic.setAreaId(value.getAreaId());
        baseTopic.setPropertyStatus(value.getStatus());
        baseTopic.setPropertyId(value.getPropertyId());
        PropertyConfig propertyConfig = baseTopic.getConfig(value.getPropertyId());
        Log.i(TAG, propertyConfig.toString());

        Map<String, Any> topicWithMsgs = baseTopic.generateProtobufMessage(
                mCarPropertyMgrMonitor.getCarPropertyExtensionManager() ,
                value.getValue(),
                propertyConfig);

        Log.d(TAG, "isRepeatedSignal " + baseTopic.isRepeatedSignal());
        // Do not re-publish events
        if (!baseTopic.isRepeatedSignal()) {
            for (String topic : topicWithMsgs.keySet()) {
                Log.i(TAG, "publish cloud event, topic uri: " + topic);

                events.add(CloudEventFactory.publish(topic,
                        Objects.requireNonNull(topicWithMsgs.get(topic)),
                        UCloudEventAttributes.empty()));
            }
        }
        return events;
    }

    // Create topic to Bus
    protected void createTopic(String topic) {
        CreateTopicRequest createTopicRequest = CreateTopicRequest.newBuilder()
                .setTopic(Topic.newBuilder().setUri(topic).build())
                .build();
        Status status = mUSubscription.createTopic(createTopicRequest);
        Log.i(TAG, "createTopic : " + topic + " status: " + StatusUtils.toShortString(status));
    }

    public CarPropertyManagerMonitor getmCarPropertyMgrMonitor() {
        return mCarPropertyMgrMonitor;
    }

    public CarPropertyExtensionManager.CarPropertyExtensionCallback getmPropertyExtMgrCallback() {
        return mPropertyExtMgrCallback;
    }

    public abstract void registerCarPropertyCallback();

    private void unRegisterCarPropertyCallback() {
        mCarPropertyMgrMonitor.unRegisterCallback(mPropertyExtMgrCallback);
    }

    public UltifiLinkMonitor getmUltifiLinkMonitor() {
        return mUltifiLinkMonitor;
    }

    public abstract void registerTopicMethod();

    //region Ignore, Can signal never uses.
    @Deprecated
    public List<CloudEvent> buildSignalCloudEvents(Signal signal) {
        ArrayList<CloudEvent> events = new ArrayList<>();
        BaseTopic baseTopic = TopicMappingFactory.getInstance().getMapper(signal.name);
        Log.d(TAG, baseTopic.toString());
        PropertyConfig propertyConfig = baseTopic.getConfig(signal.name);
        Log.i(TAG, propertyConfig.toString());

        Map<String, Any> payloads = baseTopic.generateProtobufMessage(
                mCarPropertyMgrMonitor.getCarPropertyExtensionManager(),
                signal,
                propertyConfig);

        Log.d(TAG, "isRepeatedSignal " + baseTopic.isRepeatedSignal());
        // Do not re-publish events
        if (!baseTopic.isRepeatedSignal()) {
            for (String key : payloads.keySet()) {
                Log.i(TAG, "uri: " + key);
                events.add(CloudEventFactory.publish(key,
                        Objects.requireNonNull(payloads.get(key)),
                        UCloudEventAttributes.empty())
                );
            }
        }
        return events;
    }

    @Deprecated
    private void registerServiceSignals() {
        // TODO: 2023/7/2 CAN 信号不用, 暂且先保留入口
        // if Sunroof also had CAN bridge related signals, add registration codes here
        // registerCanSignals(Sunroof.getSignals());
    }

    @Deprecated
    private void unRegisterServiceSignals() {
        // unRegisterCanSignals(Sunroof.getSignals());
    }

    @Deprecated
    private void registerCanSignals(List<String> signals) {
        mCanMgrMonitor.registerSignal(signals.toArray(new String[0]));
    }

    @Deprecated
    private void unRegisterCanSignals(List<String> signals) {
        mCanMgrMonitor.unRegisterSignal(signals.toArray(new String[0]));
    }
    //endregion
}