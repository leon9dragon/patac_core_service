package com.gm.ultifi.service;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.gm.ultifi.service.access.someip.SunroofViewModel;

import java.util.Timer;
import java.util.TimerTask;

/**
 * simple page to launch service
 * TODO delete this activity before build apk
 */
public class MainActivity extends AppCompatActivity {
    public static final String TAG = "Cabin_MainActivity";

    private Timer timer;
    TimerTask timerTask;
    private int position = 1;

    public static MainActivity context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);
        SunroofViewModel sunroofViewModel = new SunroofViewModel();
        findViewById(R.id.btnSet).setOnClickListener(view -> {
            if (position > 10) {
                position = position % 10;
            } else {
                position++;
            }
            initTimer();
            EditText editTextSet = (EditText) findViewById(R.id.editTextSet);
            String s = editTextSet.getText().toString();
            Log.d(TAG, "==================== set 时间：");
            Log.d(TAG, s);
            if (timerTask != null) {
                timerTask.cancel();
            }
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    if (position < 10) {
                        position++;
                    } else {
                        position = position % 10;
                    }
                    Log.d(TAG, "position = " + position);
                    sunroofViewModel.setSunroofPosition(10 * position);
                }
            };
            if (s.equals("")) {
                timer.schedule(timerTask, 1, 100);
            } else
                timer.schedule(timerTask, 1, Long.valueOf(s));
        });
        findViewById(R.id.btnGet).setOnClickListener(view -> {
            initTimer();
            EditText editTextGet = (EditText) findViewById(R.id.editTextGet);
            String s = editTextGet.getText().toString();
            Log.d(TAG, "==================== get 时间：");
            Log.d(TAG, s);
            if (timerTask != null) {
                timerTask.cancel();
            }
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    sunroofViewModel.getSunroofStatus();
                }
            };
            if (s.equals("")) {
                timer.schedule(timerTask, 1, 100);
            } else
                timer.schedule(timerTask, 1, Long.valueOf(s));
        });

        findViewById(R.id.btn3).setOnClickListener(view -> {
            if (timer != null) {
                timer.cancel();
            }
            if (timerTask != null) {
                timerTask.cancel();
            }
            timer = null;
            timerTask = null;
        });

    }

    private void initTimer() {
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
    }

}