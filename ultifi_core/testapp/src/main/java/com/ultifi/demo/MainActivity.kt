package com.ultifi.demo

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.gm.ultifi.sdk.uprotocol.uri.datamodel.UResource
import com.google.rpc.Status
import com.ultifi.demo.ultifi.UriFactory
import com.ultifi.demo.ultifi.createAcUpdateRequest
import com.ultifi.demo.ultifi.send
import com.ultifi.vehicle.body.cabin_climate.v1.SystemSettings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.connect).setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                UltifiLinkFlow(this@MainActivity).subscribe<SystemSettings>(toSystemSettingsTopic())
                    .collect {
                        println(it.toString())
                    }
            }

        }
        findViewById<Button>(R.id.set).setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                UltifiLinkFlow(this@MainActivity).invoke {
                    val request = createAcUpdateRequest()
                    Log.d("TAG", "start send: ${request.methodUri()}")
                    val status: Status = it.send(request)
                    Log.d("TAG", "SystemSettings status: $status")

                }

            }

        }

    }


    fun toSystemSettingsTopic(): String {
        val resource = UResource("system_settings", null, SystemSettings::class.java.simpleName)
        val uri = UriFactory.buildCabinClimateUri(resource)
        Log.d("TAG", "SystemSettings SpecUri: $uri")
        return uri
    }

}