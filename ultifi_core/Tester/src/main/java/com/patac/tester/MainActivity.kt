package com.patac.tester

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UResource
import com.google.protobuf.Any
import com.google.rpc.Status
import com.patac.tester.util.UriFactory
import com.patac.tester.util.createAcUpdateRequest
import com.patac.tester.util.createSunroofUpdateRequest
import com.ultifi.core.common.util.StatusUtils
import com.ultifi.vehicle.body.cabin_climate.v1.SystemSettings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.launch
import com.patac.tester.util.send
import com.patac.tester.R
import com.ultifi.vehicle.body.access.v1.Sunroof
import java.util.*
import java.util.concurrent.CompletableFuture

class MainActivity : AppCompatActivity() {

    val TAG = "TestMainActivity"

    private lateinit var timer: Timer
    private lateinit var timerTask: TimerTask

    private lateinit var seatbutton: Button
    private lateinit var chassisbutton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seatbutton = findViewById(R.id.seating)
        chassisbutton = findViewById(R.id.chassis)

        findViewById<Button>(R.id.connect).setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                UltifiLinkFlow(this@MainActivity).subscribe<Sunroof>(toSunroofTopic())
                    .cancellable()
                    .collect {
                        val sunroof = it.getOrNull()
                        Log.d("TAG", "当前天窗位置: ${sunroof?.position}")
                    }
            }
        }

        seatbutton.setOnClickListener {
            //TODO: 切换到座椅控制页面
        }

        chassisbutton.setOnClickListener {
            //TODO: 切换到底盘控制页面
            val chassis_intent = Intent(this, chassisServiceActivity::class.java)
            startActivity(chassis_intent)
        }

//        findViewById<Button>(R.id.set).setOnClickListener {
//            CoroutineScope(Dispatchers.IO).launch {
//                UltifiLinkFlow(this@MainActivity).invoke {
//                    val request = createAcUpdateRequest()
//                    Log.d("TAG", "start send: ${request.methodUri()}")
//                    val status: Status = it.send(request)
//                    Log.d("TAG", "RPC 完成: ${StatusUtils.toShortString(status)}")
//                }
//            }
//        }

        val seekBar = findViewById<SeekBar>(R.id.seekBar)

        val textView = findViewById<TextView>(R.id.textView)
        seekBar?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {
                // write custom code for progress is changed
                textView.setText("当前进度" + progress + "/100")
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                CoroutineScope(Dispatchers.IO).launch {
                    UltifiLinkFlow(this@MainActivity).invoke {
                        val request = createSunroofUpdateRequest(seek.progress)
                        Log.d("TAG", "start send: ${request.methodUri()}")
                        val status: Status = it.send(request)
                        Log.d("TAG", "RPC 完成: ${StatusUtils.toShortString(status)}")
                    }
                }
                // write custom code for progress is stopped
//                Toast.makeText(this@MainActivity,
//                    "Progress is: " + seek.progress + "%",
//                    Toast.LENGTH_SHORT).show()
            }
        })

        findViewById<Button>(R.id.set).setOnClickListener {
            val editTextSet = findViewById<View>(R.id.editTextSet) as EditText
            val s = editTextSet.text.toString()
            Log.d(TAG, "==================== set 时间：")
//            Log.d(TAG, s)
            initTimer()
//            if (timerTask != null) {
//                timerTask.cancel()
//            }
            timerTask = object : TimerTask() {
                override fun run() {
                    setMethod()
                }
            }

            if (s == "") {
                timer.schedule(timerTask, 1, 100)
            } else timer.schedule(timerTask, 1, java.lang.Long.valueOf(s))
        }

        findViewById<View>(R.id.btn3).setOnClickListener { view: View? ->
            if (timer != null) {
                timer.cancel()
            }
            if (timerTask != null) {
                timerTask.cancel()
            }
        }
    }

    private fun initTimer() {
        timer = Timer()
    }

    fun setMethod() {
        CoroutineScope(Dispatchers.IO).launch {
            UltifiLinkFlow(this@MainActivity).invoke {
                val req = createAcUpdateRequest()
                Log.d("TAG", "request uri: ${req.methodUri()}")
                val status: CompletableFuture<Any> = it.invokeMethod(req)
                Log.d("TAG", "rpc 完成")
            }
        }
    }

    fun toSystemSettingsTopic(): String {
        val resource = UResource("system_settings", null, SystemSettings::class.java.simpleName)
        val uri = UriFactory.buildCabinClimateUri(resource)
        Log.d("TAG", "SystemSettings SpecUri: $uri")
        return uri
    }


    fun toSunroofTopic(): String {
        val resource = UResource("sunroof.front", null, Sunroof::class.java.simpleName)
        val uri = UriFactory.buildSunroofTopicUri(resource)
        Log.d("TAG", "Sunroof uri: $uri")
        return uri
    }

    override fun onDestroy() {
        super.onDestroy()
        if (timer != null) {
            timer.cancel()
        }
        if (timerTask != null) {
            timerTask.cancel()
        }
    }
}