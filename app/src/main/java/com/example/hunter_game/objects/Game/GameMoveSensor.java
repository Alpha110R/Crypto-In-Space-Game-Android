package com.example.hunter_game.objects.Game;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.example.hunter_game.CallBacks.CallBack_MotionSensor;

public class GameMoveSensor{
    private SensorManager sensorManager;
    private Sensor motionSensor;
    private Context gameContext;
    private CallBack_MotionSensor callBack_motionSensor = null;

    public GameMoveSensor(){}

    public GameMoveSensor(Context context) {
        this.gameContext = context;
        sensorManager = (SensorManager) gameContext.getSystemService(Context.SENSOR_SERVICE);
        motionSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(motionSensorEventListener, motionSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void setCallBack_motionSensor(CallBack_MotionSensor callBack_motionSensor){
        this.callBack_motionSensor = callBack_motionSensor;

    }

    private SensorEventListener motionSensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            int x = (int) sensorEvent.values[0];
            int z = (int) sensorEvent.values[2];
            if (callBack_motionSensor != null) {
                if (x > 1) {
                    callBack_motionSensor.left();
                } else {
                    if (x < -1) {
                        callBack_motionSensor.right();
                    } else {
                        if (z > 7) {
                            callBack_motionSensor.up();
                        }
                        if (z < 4) {
                            callBack_motionSensor.down();
                        }
                    }
                }

            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
            Log.d("", "onAccuracyChanged");
        }
    };

    public void onResumeSensorManager() {
        sensorManager.registerListener(motionSensorEventListener, motionSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onPauseSensorManager() {
        sensorManager.unregisterListener(motionSensorEventListener);
    }
}
