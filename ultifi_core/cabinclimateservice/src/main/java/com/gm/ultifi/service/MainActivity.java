package com.gm.ultifi.service;

import android.os.Bundle;
import com.gm.ultifi.service.access.someip.AmbientLightViewModel;
import com.gm.ultifi.service.access.someip.SunroofViewModel;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.gm.ultifi.service.seating.someip.SeatViewModel;

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
    private int position = 1;

    public static MainActivity context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);
//        SunroofViewModel sunroofViewModel = new SunroofViewModel();
//        SeatViewModel seatViewModel = new SeatViewModel();
//        AmbientLightViewModel ambientLightViewModel = new AmbientLightViewModel();

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
//                    sunroofViewModel.setSunroofSunshadeControlRequest();
//                    sunroofViewModel.setWindowServiceControlRequest();
//                    sunroofViewModel.setSideSunblindControlRequest();
//                    sunroofViewModel.setWindowsPercentageControlRequest();

//                    seatViewModel.setDriverSeatRecallReq_M1();
//                    seatViewModel.setDriverSeatRecallReq_M2();
//                    seatViewModel.setDriverSeatRecallReq_M3();
//                    seatViewModel.setDriverSeatRecallReq_M4();
//                    seatViewModel.setSeatModeReq();
//
//                    ambientLightViewModel.setAmbientLight();
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
//                    sunroofViewModel.getSunroofStatus();
//                    sunroofViewModel.getHMI2LSunroofSystemControl();
//                    sunroofViewModel.getHMI2RSunroofSystemControl();
//                    sunroofViewModel.getFrontSunshadeStatus();
//                    sunroofViewModel.getRearSunshadeStatus();
//                    sunroofViewModel.getPercentageWindowControl2L();
//                    sunroofViewModel.getPercentageWindowControl2R();
//                    sunroofViewModel.getPercentageWindowControlRSTP();
//                    sunroofViewModel.getSunblindConfigurationAvailability();
//                    sunroofViewModel.getSunblindStatus();
//                    sunroofViewModel.getWindowControl2L();
//                    sunroofViewModel.getWindowControl2R();
//                    sunroofViewModel.getWindowControlRSTP();
//                    sunroofViewModel.getWindowStatus();
//                    sunroofViewModel.getWindowStatusInformation();

//                    seatViewModel.getDriverSeatConf();
//                    seatViewModel.getDriverSeatPosition_1();
//                    seatViewModel.getDriverSeatPosition_2();
//                    seatViewModel.getDriverSeatPosition_3();
//                    seatViewModel.getSeatMode();
//                    seatViewModel.getSeatMode2();
//                    seatViewModel.getDriverSeatRecoveryPosition();
//                    seatViewModel.getDriverSeatRecoveryPosition2();
//                    seatViewModel.getSeatPassenger_Com_Mode_Ser();
//                    seatViewModel.getSeatPassenger_Com_Mode_Ser_Resp();
//                    seatViewModel.getSeatPassenger_Com_Mode_Ser_Resp2();
//                    seatViewModel.getThirdLeftSeatConf();
//                    seatViewModel.getThirdLeftSeatPosition();
//                    seatViewModel.getThirdRightSeatConf();
//                    seatViewModel.getThirdRightSeatPosition();
//                    seatViewModel.getSec_RowLeft_Com_Mode_Req();
//                    seatViewModel.getSec_RowRight_Com_Mode_Req();
//                    seatViewModel.getSeatPassenger_Com_Mode_Ava_Notifi6();
//                    seatViewModel.getSecondLeftSeatPosition_1();
//                    seatViewModel.getSecondLeftSeatPosition_2();

//                    ambientLightViewModel.getAmbientLedControlAvailable();
//                    ambientLightViewModel.getLed1AndLed2Status();
//                    ambientLightViewModel.getLed3AndLed4Status();
//                    ambientLightViewModel.getLed5AndLed6Status();
//                    ambientLightViewModel.getLed7AndLed8Status();
//                    ambientLightViewModel.getLed9AndLed10Status();
//                    ambientLightViewModel.getLed11Status();
                }
            };
            if (s.equals("")) {
                timer.schedule(timerTask2, 1, 100);
            } else
                timer.schedule(timerTask2, 1, Long.valueOf(s));
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