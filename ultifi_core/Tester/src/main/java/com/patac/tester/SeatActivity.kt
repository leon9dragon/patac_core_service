package com.patac.tester

//import android.support.v7.app.AppCompatActivity
//import android.support.v4.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.gm.ultifi.vehicle.body.seating.v1.SeatMode
import com.google.protobuf.Any
import com.patac.tester.util.createUpdateSeatModeRequest
import com.patac.tester.util.createUpdateTractionandStabilitySystemRequest
import com.ultifi.vehicle.body.seating.v1.UpdateSeatModeRequest
import com.ultifi.vehicle.chassis.v1.UpdateTractionandStabilitySystemRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.CompletableFuture

class SeatActivity : AppCompatActivity() {
    val TAG = "SeatActivityTest"

    private lateinit var timer: Timer
    private lateinit var timerTask: TimerTask

    private lateinit var update_seat_mode_Button: Button
    private lateinit var update_seat_mode_input: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seat)

        init()

        update_seat_mode_Button.setOnClickListener {
            //TODO: update seat mode
            Log.d(TAG, "update seat mode button clicked")
            val mode_value = update_seat_mode_input.text.toString().toInt()

            try {
                //todo: check input value
                if (mode_value < 30 || mode_value > 50)
                    throw Exception("input value out of range")
                else
                    Log.d(TAG, "input valid value: $mode_value")
                CoroutineScope(Dispatchers.IO).launch {
                    UltifiLinkFlow(this@SeatActivity).invoke {
                        val req = createUpdateSeatModeRequest(
                            update_seat_mode_input.text.toString().toInt()
                        )
                        Log.d(TAG, "update seat mode button: ${req.methodUri()}")
                        val status: CompletableFuture<Any> = it.invokeMethod(req)
                        Log.d(TAG, "update seat mode button: ${status.get()}")
                    }
                }
            } catch (e: Exception) {
                Log.d(TAG, "input invalid value: ${e.message}")

//            CoroutineScope(Dispatchers.IO).launch {
//                UltifiLinkFlow(this@SeatActivity).invoke {
//                    // UpdateTractionandStabilitySystemRequest 里传入的值只有一个，TractionandStabilitySystemRequest类型的枚举值Vehicle_stability_enhancement_enable_and_traction_control_system_disable
////                    val req = createUpdateTractionandStabilitySystemRequest(
////                        UpdateTractionandStabilitySystemRequest.TractionandStabilitySystemRequest.Vehicle_stability_enhancement_enable_and_traction_control_system_disable)
//                    val req = createUpdateSeatModeRequest(update_seat_mode_input.text.toString().toInt())
//                    Log.d(TAG, "update seat mode button: ${req.methodUri()}")
//                    val status: CompletableFuture<Any> = it.invokeMethod(req)
//                    Log.d(TAG, "update seat mode button: ${status.get()}")
//                }
            }
            }
        }

        fun init() {
            update_seat_mode_Button = findViewById(R.id.seat_mode_button)
            update_seat_mode_input = findViewById(R.id.seat_mode_input)

        }
    }
