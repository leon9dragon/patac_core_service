package com.gm.ultifi.service.cabinclimate.manager;

import android.car.hardware.CarPropertyValue;
import android.util.Log;

import com.gm.ultifi.sdk.uprotocol.cloudevent.datamodel.UCloudEventAttributes;
import com.gm.ultifi.sdk.uprotocol.cloudevent.factory.CloudEventFactory;
import com.gm.ultifi.service.cabinclimate.manager.propertymanager.CarPropertyExtensionManager;
import com.gm.ultifi.service.cabinclimate.manager.request.listeners.UltifiLinkRequestListener;
import com.gm.ultifi.service.cabinclimate.response.config.PropertyConfig;
import com.gm.ultifi.service.cabinclimate.response.mapper.BaseMapper;
import com.gm.ultifi.service.cabinclimate.response.mapper.SystemSettings;
import com.gm.ultifi.service.cabinclimate.utils.TaskRunner;
import com.gm.ultifi.service.cabinclimate.utils.Utility;
import com.google.protobuf.Any;
import com.google.rpc.Status;
import com.ultifi.core.Topic;
import com.ultifi.core.UltifiLink;
import com.ultifi.core.common.util.StatusUtils;
import com.ultifi.core.usubscription.v1.CreateTopicRequest;
import com.ultifi.core.usubscription.v1.USubscription;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import gm.ultifi.canbridge.CanManager;
import gm.ultifi.canbridge.Signal;
import gm.ultifi.canbridge.SignalGroup;
import io.cloudevents.CloudEvent;

public class ServiceLaunchManager {

    private static final String TAG = ServiceLaunchManager.class.getSimpleName();

    private static final String ZONE_AREA_MAPPING_FILE = "zone_map.json";

    private final CanManagerMonitor mCanMgrMonitor;
    private final CanManagerMonitor.OnCanManagerConnectionListener mOnCanManagerConnectionListener;
    private final CanManager.CANBridgeListener mCanSignalListener;

    private final CarPropertyManagerMonitor mCarPropertyMgrMonitor;
    private final CarPropertyManagerMonitor.OnCarManagerConnectionListener mOnCarPropertyMgrConnListener;
    private final CarPropertyExtensionManager.CarPropertyExtensionCallback mPropertyExtMgrCallback;

    private final UltifiLinkMonitor mUltifiLinkMonitor;
    private final UltifiLink.ServiceLifecycleListener mOnUltifiLinkLifeCycleListener;
    private final UltifiLinkRequestListener mUltifiLinkRequestListener;

    private USubscription.BlockingStub mUSubscription;

    private boolean isCanSignalsRegistered = false;
    private boolean isCarPropertiesRegistered = false;
    private boolean isUltifiLinkConnected = false;

    public ServiceLaunchManager(UltifiLinkMonitor ultifiLinkMonitor,
                                CanManagerMonitor canManagerMonitor,
                                CarPropertyManagerMonitor carPropertyManagerMonitor) {
        mOnUltifiLinkLifeCycleListener = (ultifiLink, ready) -> {
            if (ready) {
                onUltifiLinkConnected(ultifiLink);
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
                    registerCabinClimateSignals();
                    isCanSignalsRegistered = true;
                }
            } else {
                unRegisterCabinClimateSignals();
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
            registerCabinClimateSignals();
            isCanSignalsRegistered = true;
        }
        // Make sure that register car property signals when isUltifiLinkConnected && mCarPropertyMgrMonitor.isCarPropertyManagerReady()
        if (mCarPropertyMgrMonitor.isCarPropertyManagerReady()) {
            registerCarPropertyCallback();
            isCarPropertiesRegistered = true;
        }

        mUSubscription = USubscription.newBlockingStub(ultifiLink);

        //Todo should update with actual RPC method names
        mUltifiLinkMonitor.registerRPCMethod(new String[] {
                BaseMapper.CLIMATE_ZONE,
                BaseMapper.SYSTEM_SETTINGS
        });

        // Create all topics to uBus
        for (String topic : Utility.buildTopicsList()) {
            createTopic(topic);
        }
    }

    private void onUltifiLinkDisconnected() {
        isUltifiLinkConnected = false;
        Log.d(TAG, "UltifiLink disconnected");
        if (mCanMgrMonitor.isCanManagerReady()) {
            Log.i(TAG, "Unregistering in UltifiLink");
            unRegisterCabinClimateSignals();
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

        //Todo Climate has zone related signals, so should parse zone_map.json to get a map of zone properties
        ClassLoader classLoader = Objects.requireNonNull(getClass().getClassLoader());
        InputStream inputStream = classLoader.getResourceAsStream(ZONE_AREA_MAPPING_FILE);
        TaskRunner.getInstance().execute(() -> {
            //Todo parse zone_map.json with AreaPropertyMapper
        });
    }

    public void stop() {
        mUltifiLinkMonitor.stop();
        mCarPropertyMgrMonitor.stop();
        mCanMgrMonitor.stop();
    }

    public List<CloudEvent> buildCarPropertyCloudEvents(CarPropertyValue<?> value) {
        ArrayList<CloudEvent> events = new ArrayList<>();
        BaseMapper baseMapper = BaseMapper.getMapper(value.getPropertyId());
        baseMapper.setAreaId(value.getAreaId());
        baseMapper.setPropertyStatus(value.getStatus());
        PropertyConfig propertyConfig = baseMapper.getConfig(value.getPropertyId());
        Log.i(TAG, propertyConfig.toString());

        Map<String, Any> topicWithMsgs = baseMapper.generateProtobufMessage(
                mCarPropertyMgrMonitor.getCarPropertyExtensionManager(),
                value.getValue(),
                propertyConfig);

        Log.d(TAG, "isRepeatedSignal " + baseMapper.isRepeatedSignal());
        // Do not re-publish events
        if (!baseMapper.isRepeatedSignal()) {
            for (String topic : topicWithMsgs.keySet()) {
                Log.i(TAG, "uri: " + topic);

                events.add(CloudEventFactory.publish(topic,
                        Objects.requireNonNull(topicWithMsgs.get(topic)),
                        UCloudEventAttributes.empty()));
            }
        }
        return events;
    }

    public List<CloudEvent> buildSignalCloudEvents(Signal signal) {
        ArrayList<CloudEvent> events = new ArrayList<>();
        BaseMapper baseMapper = BaseMapper.getMapper(signal.name);
        Log.d(TAG, baseMapper.toString());
        PropertyConfig propertyConfig = baseMapper.getConfig(signal.name);
        Log.i(TAG, propertyConfig.toString());

        Map<String, Any> payloads = baseMapper.generateProtobufMessage(
                mCarPropertyMgrMonitor.getCarPropertyExtensionManager(),
                signal,
                propertyConfig);

        Log.d(TAG, "isRepeatedSignal " + baseMapper.isRepeatedSignal());
        // Do not re-publish events
        if (!baseMapper.isRepeatedSignal()) {
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

    // Create topic to Bus
    private void createTopic(String topic) {
        CreateTopicRequest createTopicRequest = CreateTopicRequest.newBuilder()
                .setTopic(Topic.newBuilder().setUri(topic).build())
                .build();
        Status status = mUSubscription.createTopic(createTopicRequest);
        Log.i(TAG, "createTopic : " + topic + " status: " + StatusUtils.toShortString(status));
    }

    private void registerCabinClimateSignals() {
        registerCanSignals(SystemSettings.getSignals());
        //Todo if Zone also had CAN bridge related signals, add registration codes here
    }

    private void unRegisterCabinClimateSignals() {
        unRegisterCanSignals(SystemSettings.getSignals());
    }

    private void registerCanSignals(List<String> signals) {
        mCanMgrMonitor.registerSignal(signals.toArray(new String[0]));
    }

    private void unRegisterCanSignals(List<String> signals) {
        mCanMgrMonitor.unRegisterSignal(signals.toArray(new String[0]));
    }

    private void registerCarPropertyCallback() {
        mCarPropertyMgrMonitor.registerCallback(mPropertyExtMgrCallback);
    }

    private void unRegisterCarPropertyCallback() {
        mCarPropertyMgrMonitor.unRegisterCallback(mPropertyExtMgrCallback);
    }
}