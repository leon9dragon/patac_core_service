package com.patac.tester

import android.content.Intent
//import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class InitActivity : AppCompatActivity() {
    val TAG = "TestMainActivity"

    private lateinit var timer: Timer
    private lateinit var timerTask: TimerTask

    private lateinit var seatbutton: Button
    private lateinit var chassisbutton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_init)

        seatbutton = findViewById(R.id.seating)
        chassisbutton = findViewById(R.id.chassis)

        seatbutton.setOnClickListener {
            //TODO: 切换到座椅控制页面
            val seat_intent = Intent(this, SeatActivity::class.java)
            startActivity(seat_intent)
        }

        chassisbutton.setOnClickListener {
            //TODO: 切换到底盘控制页面
            val chassis_intent = Intent(this, chassisServiceActivity::class.java)
            startActivity(chassis_intent)
        }

    }
}