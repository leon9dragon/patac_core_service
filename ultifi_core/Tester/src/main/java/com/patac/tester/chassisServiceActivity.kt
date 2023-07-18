package com.patac.tester

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UResource
import com.google.protobuf.Any
import com.google.rpc.Status
//import com.patac.tester.util.RPCUpdateTireRequest
import com.patac.tester.util.UriFactory
import com.patac.tester.util.createUpdateTractionandStabilitySystemRequest
import com.patac.tester.util.send
import com.ultifi.core.common.util.StatusUtils
import com.ultifi.vehicle.chassis.v1.*
import java.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.launch
import java.util.concurrent.CompletableFuture


class chassisServiceActivity : AppCompatActivity() {
    val TAG = "chassisServiceTest"

    private lateinit var timer: Timer
    private lateinit var timerTask: TimerTask

    private lateinit var sub_TCS_Button: Button
    private lateinit var unsub_TCS_Button: Button
    private lateinit var sub_ESC_Button: Button
    private lateinit var unsub_ESC_Button: Button
    private lateinit var TCS_is_engaged_Text: TextView
    private lateinit var TCS_is_enabled_Text: TextView
    private lateinit var TCS_is_faulted_Text: TextView
    private lateinit var ESC_is_engaged_Text: TextView
    private lateinit var ESC_is_enabled_Text: TextView
    private lateinit var ESC_is_faulted_Text: TextView
    private lateinit var uptdate_Tire_Button: Button
    private lateinit var tire_leak_notifier_enable_input : EditText
    private lateinit var tire_present_enable_input : EditText

    private lateinit var TCS_Enable_ESC_Enable_Button: Button
    private lateinit var TCS_Disable_ESC_Disable_Button: Button
    private lateinit var TCS_Disable_ESC_Enable_Button: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chassis_service)
        init()

        //click subscribe TCS button
        sub_TCS_Button.setOnClickListener {
            //订阅TCS
            CoroutineScope(Dispatchers.IO).launch {
                UltifiLinkFlow(this@chassisServiceActivity).subscribe<TractionControlSystem>(toTractionControlSystemTopic())
                    .cancellable()
                    .collect {
                        val TCS = it.getOrNull()
                        Log.d("TAG", "TCS engaged: ${TCS?.isEngaged}")
                        Log.d("TAG", "TCS enabled: ${TCS?.isEnabled}")
                        Log.d("TAG", "TCS faulted: ${TCS?.isFaulted}")
                        TCS_is_enabled_Text.text = "TCS engaged: " + TCS?.isEnabled.toString()
                        TCS_is_engaged_Text.text = "TCS engaged: " + TCS?.isEngaged.toString()
                        TCS_is_faulted_Text.text = "TCS faulted: " + TCS?.isFaulted.toString()
                    }
            }
        }

        //click unsubscribe TCS button
        unsub_TCS_Button.setOnClickListener {
            //TODO: 取消订阅TCS

        }

        //click subscribe ESC button
        sub_ESC_Button.setOnClickListener {
            //订阅ESC
            CoroutineScope(Dispatchers.IO).launch {
                UltifiLinkFlow(this@chassisServiceActivity).subscribe<ElectronicStabilityControlSystem>(toElectronicStabilityControlSystemTopic())
                    .cancellable()
                    .collect {
                        val ESC = it.getOrNull()
                        Log.d("TAG", "ESC engaged: ${ESC?.isEngaged}")
                        Log.d("TAG", "ESC enabled: ${ESC?.isEnabled}")
                        Log.d("TAG", "ESC faulted: ${ESC?.isFaulted}")
                        ESC_is_enabled_Text.text = "ESC engaged: " + ESC?.isEnabled.toString()
                        ESC_is_engaged_Text.text = "ESC engaged: " + ESC?.isEngaged.toString()
                        ESC_is_faulted_Text.text = "ESC faulted: " + ESC?.isFaulted.toString()
                    }
            }
        }

        /**
         * Update TCS and ESC setting
         */
        TCS_Disable_ESC_Disable_Button.setOnClickListener {
            //TODO: 更新TCS和ESC设置
            Log.d(TAG, "TCS_Disable_ESC_Disable_Button clicked")
            CoroutineScope(Dispatchers.IO).launch {
                UltifiLinkFlow(this@chassisServiceActivity).invoke {
                    val req = createUpdateTractionandStabilitySystemRequest(UpdateTractionandStabilitySystemRequest.TractionandStabilitySystemRequest.Vehicle_stability_enhancement_disable_and_traction_control_system_disable)
                    Log.d(TAG, "TCS_Disable_ESC_Disable_Button: ${req.methodUri()}")
                    val status: CompletableFuture<Any> = it.invokeMethod(req)
                    Log.d(TAG, "TCS_Disable_ESC_Disable_Button: ${status.get()}")
                }
            }
        }

        TCS_Enable_ESC_Enable_Button.setOnClickListener {
            //TODO: 更新TCS和ESC设置
            Log.d(TAG, "TCS_Enable_ESC_Enable_Button clicked")
            CoroutineScope(Dispatchers.IO).launch {
                UltifiLinkFlow(this@chassisServiceActivity).invoke {
                    val req = createUpdateTractionandStabilitySystemRequest(UpdateTractionandStabilitySystemRequest.TractionandStabilitySystemRequest.Vehicle_stability_enhancement_enable_and_traction_control_system_enable)
                    Log.d(TAG, "TCS_Disable_ESC_Disable_Button: ${req.methodUri()}")
                    val status: CompletableFuture<Any> = it.invokeMethod(req)
                    Log.d(TAG, "TCS_Disable_ESC_Disable_Button: ${status.get()}")
                }
            }
        }

        TCS_Disable_ESC_Enable_Button.setOnClickListener {
            //TODO: 更新TCS和ESC设置
            Log.d(TAG, "TCS_Disable_ESC_Enable_Button clicked")
            CoroutineScope(Dispatchers.IO).launch {
                UltifiLinkFlow(this@chassisServiceActivity).invoke {
                    val req = createUpdateTractionandStabilitySystemRequest(UpdateTractionandStabilitySystemRequest.TractionandStabilitySystemRequest.Vehicle_stability_enhancement_enable_and_traction_control_system_disable)
                    Log.d(TAG, "TCS_Disable_ESC_Disable_Button: ${req.methodUri()}")
                    val status: CompletableFuture<Any> = it.invokeMethod(req)
                    Log.d(TAG, "TCS_Disable_ESC_Disable_Button: ${status.get()}")
                }
            }
        }


        //click update tire button
        uptdate_Tire_Button.setOnClickListener {
            //更新轮胎设置
//            CoroutineScope(Dispatchers.IO).launch {
//                UltifiLinkFlow(this@chassisServiceActivity).invoke {
//                    val request = RPCUpdateTireRequest(
//                        tire_leak_notifier_enable_input.text.toString().toBoolean(),
//                        tire_present_enable_input.text.toString().toBoolean()
//                    )
//
//                    Log.d(TAG, "tire leak notifier enable: ${request.methodUri()}")
//                    val status: Status = it.send(request)
//                    Log.d(TAG, "RPC完成： ${StatusUtils.toShortString(status)}")
//                }
//            }
            //TODO

        }

    }

    fun init(){
        sub_TCS_Button = findViewById(R.id.subscribe_TCS_button)
        unsub_TCS_Button = findViewById(R.id.unsubscribe_TCS_button)
        TCS_is_enabled_Text = findViewById(R.id.TCS_is_enabled_view)
        TCS_is_engaged_Text = findViewById(R.id.TCS_is_engaged_view)
        TCS_is_faulted_Text = findViewById(R.id.TCS_is_faulted_view)
        sub_ESC_Button = findViewById(R.id.subscribe_ESC_button)
        unsub_ESC_Button = findViewById(R.id.unsubscribe_ESC_button)
        ESC_is_enabled_Text = findViewById(R.id.ESC_is_enabled_view)
        ESC_is_engaged_Text = findViewById(R.id.ESC_is_engaged_view)
        ESC_is_faulted_Text = findViewById(R.id.ESC_is_faulted_view)
        uptdate_Tire_Button = findViewById(R.id.update_tire_button)
        tire_leak_notifier_enable_input = findViewById(R.id.leak_notifier_enable_input)
        tire_present_enable_input = findViewById(R.id.leak_notifier_enable_input)
        TCS_Disable_ESC_Enable_Button = findViewById(R.id.TCS_Disable_and_ESC_Enable_button)
        TCS_Enable_ESC_Enable_Button = findViewById(R.id.TCS_Enable_and_ESC_Enable_button)
        TCS_Disable_ESC_Disable_Button = findViewById(R.id.TCS_Disable_and_ESC_Disable_button)


    }

    fun toTractionControlSystemTopic(): String {
        val resource = UResource("toTractionControlSystemTopic",null,TractionControlSystem::class.java.simpleName)
        val uri = UriFactory.buildChassisTopicUri(resource)
        Log.d(TAG, "toTractionControlSystemTopic: $uri")
//        return "vehicle/chassis/v1/TractionControlSystem"
        return uri
    }

    fun toElectronicStabilityControlSystemTopic(): String {
        val resource = UResource(
            "toElectronicStabilityControlSystemTopic",
            null,
            ElectronicStabilityControlSystem::class.java.simpleName
        )
        val uri = UriFactory.buildChassisTopicUri(resource)
        Log.d(TAG, "toElectronicStabilityControlSystemTopic: $uri")
        return uri
    }

//    private fun initTimer() {
//        timer = Timer()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        if (timer != null) {
//            timer.cancel()
//        }
//        if (timerTask != null) {
//            timerTask.cancel()
//        }
//    }

}