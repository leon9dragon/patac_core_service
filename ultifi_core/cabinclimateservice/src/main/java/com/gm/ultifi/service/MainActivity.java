package com.gm.ultifi.service;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.gm.ultifi.service.access.someip.AmbientLightViewModel;
import com.gm.ultifi.service.access.someip.SeatingViewModel;
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
    TimerTask timerTask1;

    TimerTask timerTask2;
    TimerTask timerTask3;

    TimerTask timerTask4;

    TimerTask timerTask5;
    private int position = 1;

    public static MainActivity context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);
        SunroofViewModel sunroofViewModel = new SunroofViewModel();
        SeatingViewModel seatingViewModel = new SeatingViewModel();
        AmbientLightViewModel ambientLightViewModel = new AmbientLightViewModel();

        findViewById(R.id.btnSet).setOnClickListener(view -> {
//            if (position > 10) {
//                position = position % 10;
//            } else {
//                position++;
//            }
            initTimer();
            EditText editTextSet = (EditText) findViewById(R.id.editTextSet);
            String s = editTextSet.getText().toString();
            Log.d(TAG, "==================== set 时间：");
            Log.d(TAG, s);
            if (timerTask1 != null) {
                timerTask1.cancel();
            }
            timerTask1 = new TimerTask() {
                @Override
                public void run() {
//                    if (position < 10) {
//                        position++;
//                    } else {
//                        position = position % 10;
//                    }
//                    Log.d(TAG, "position = " + position);
                    sunroofViewModel.setSunroofSunshadeControlRequest();
                    sunroofViewModel.setWindowServiceControlRequest();
                    sunroofViewModel.setSideSunblindControlRequest();
                    sunroofViewModel.setWindowsPercentageControlRequest();

                    seatingViewModel.setDriverSeatRecallReq_M1();
                    seatingViewModel.setDriverSeatRecallReq_M2();
                    seatingViewModel.setDriverSeatRecallReq_M3();
                    seatingViewModel.setDriverSeatRecallReq_M4();
                    seatingViewModel.setSeatModeReq();

                    ambientLightViewModel.setAmbientLight();
                }
            };
            if (s.equals("")) {
                timer.schedule(timerTask1, 1, 100);
            } else
                timer.schedule(timerTask1, 1, Long.valueOf(s));
        });

        findViewById(R.id.btnGet).setOnClickListener(view -> {
            initTimer();
            EditText editTextGet = (EditText) findViewById(R.id.editTextGet);
            String s = editTextGet.getText().toString();
            Log.d(TAG, "==================== get 时间：");
            Log.d(TAG, s);

            if (timerTask2 != null) {
                timerTask2.cancel();
            }
            timerTask2 = new TimerTask() {
                @Override
                public void run() {
                    sunroofViewModel.getSunroofStatus();
                    sunroofViewModel.getHMI2LSunroofSystemControl();
                    sunroofViewModel.getHMI2RSunroofSystemControl();
                    sunroofViewModel.getFrontSunshadeStatus();
                    sunroofViewModel.getRearSunshadeStatus();
                    sunroofViewModel.getPercentageWindowControl2L();
                    sunroofViewModel.getPercentageWindowControl2R();
                    sunroofViewModel.getPercentageWindowControlRSTP();
                    sunroofViewModel.getSunblindConfigurationAvailability();
                    sunroofViewModel.getSunblindStatus();
                    sunroofViewModel.getWindowControl2L();
                    sunroofViewModel.getWindowControl2R();
                    sunroofViewModel.getWindowControlRSTP();
                    sunroofViewModel.getWindowStatus();
                    sunroofViewModel.getWindowStatusInformation();
                }
            };

            if (timerTask3 != null) {
                timerTask3.cancel();
            }
            timerTask3 = new TimerTask() {
                @Override
                public void run() {
                    seatingViewModel.getDriverSeatConf();
                    seatingViewModel.getDriverSeatPosition_1();
                    seatingViewModel.getDriverSeatPosition_2();
                    seatingViewModel.getDriverSeatPosition_3();
                    seatingViewModel.getSeatMode();
                    seatingViewModel.getSeatMode2();
                    seatingViewModel.getDriverSeatRecoveryPosition();
                    seatingViewModel.getDriverSeatRecoveryPosition2();
                    seatingViewModel.getSeatPassenger_Com_Mode_Ser();
                    seatingViewModel.getSeatPassenger_Com_Mode_Ser_Resp();
                    seatingViewModel.getSeatPassenger_Com_Mode_Ser_Resp2();
                }
            };

            if (timerTask4 != null) {
                timerTask4.cancel();
            }
            timerTask4 = new TimerTask() {
                @Override
                public void run() {
                    seatingViewModel.getThirdLeftSeatConf();
                    seatingViewModel.getThirdLeftSeatPosition();
                    seatingViewModel.getThirdRightSeatConf();
                    seatingViewModel.getThirdRightSeatPosition();
                    seatingViewModel.getSec_RowLeft_Com_Mode_Req();
                    seatingViewModel.getSec_RowRight_Com_Mode_Req();
                    seatingViewModel.getSeatPassenger_Com_Mode_Ava_Notifi6();
                    seatingViewModel.getSecondLeftSeatPosition_1();
                    seatingViewModel.getSecondLeftSeatPosition_2();
                }
            };

            if (timerTask5 != null) {
                timerTask5.cancel();
            }
            timerTask5 = new TimerTask() {
                @Override
                public void run() {
                    ambientLightViewModel.getAmbientLedControlAvailable();
                    ambientLightViewModel.getLed1AndLed2Status();
                    ambientLightViewModel.getLed3AndLed4Status();
                    ambientLightViewModel.getLed5AndLed6Status();
                    ambientLightViewModel.getLed7AndLed8Status();
                    ambientLightViewModel.getLed9AndLed10Status();
                    ambientLightViewModel.getLed11Status();
                }
            };

            if (s.equals("")) {
                timer.schedule(timerTask2, 1, 100);
                timer.schedule(timerTask3, 1, 100);
                timer.schedule(timerTask4, 1, 100);
                timer.schedule(timerTask5, 1, 100);
            } else {
                timer.schedule(timerTask2, 1, Long.parseLong(s));
                timer.schedule(timerTask3, 1, Long.parseLong(s));
                timer.schedule(timerTask4, 1, Long.parseLong(s));
                timer.schedule(timerTask5, 1, Long.parseLong(s));
            }
        });

        findViewById(R.id.btn3).setOnClickListener(view -> {
            if (timer != null) {
                timer.cancel();
            }
            if (timerTask1 != null) {
                timerTask1.cancel();
            }
            if (timerTask2 != null) {
                timerTask2.cancel();
            }
            timer = null;
            timerTask1 = null;
            timerTask2 = null;
        });

    }

    private void initTimer() {
        timer = new Timer();
    }

}