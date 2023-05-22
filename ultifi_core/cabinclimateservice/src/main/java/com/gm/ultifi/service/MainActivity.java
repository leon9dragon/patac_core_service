package com.gm.ultifi.service;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * simple page to launch service
 * TODO delete this activity before build apk
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn9).setOnClickListener(view -> {
//            startForegroundService(new Intent(MainActivity.this, CabinClimateService.class));
            startForegroundService(new Intent(MainActivity.this, AccessService.class));
        });
    }
}