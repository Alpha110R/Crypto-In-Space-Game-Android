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
    private static GameMoveSensor me;
    private CallBack_MotionSensor callBack_motionSensor =null;

    public GameMoveSensor(){}

    private GameMoveSensor(Context context, CallBack_MotionSensor callBack_motionSensor) {
        this.gameContext = context;
        this.callBack_motionSensor = callBack_motionSensor;
        sensorManager = (SensorManager) gameContext.getSystemService(gameContext.SENSOR_SERVICE);
        motionSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(motionSensorEventListener, motionSensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    public static void initHelper(Context context, CallBack_MotionSensor callBack_motionSensor) {
        if (me == null) {
            me = new GameMoveSensor(context.getApplicationContext(), callBack_motionSensor);
        }
    }

    public static GameMoveSensor getMe() {
        return me;
    }
    public SensorManager getSensorManager(){
        return sensorManager;
    }


    private SensorEventListener motionSensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            int x = (int) sensorEvent.values[0];
            int y = (int) sensorEvent.values[1];
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
    //TODO: try to activate and deactivate the sensor listener
    public void onResumeSensorManager() {
        sensorManager.registerListener(motionSensorEventListener, motionSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onPauseSensorManager() {
        sensorManager.unregisterListener(motionSensorEventListener);
    }
}
