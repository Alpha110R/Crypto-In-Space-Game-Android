package com.example.hunter_game.utils.MySignal;

import static android.content.Context.VIBRATOR_SERVICE;
import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

public class Vibrations {
    private static Vibrations me;
    private Context appContext;

    private Vibrations(Context appContext){
        this.appContext = appContext;
    }
    public static void initVibrations(Context appContext){
        if(me == null)
            me = new Vibrations(appContext.getApplicationContext());
    }
    public static Vibrations getMe(){return me;}
    public void vibrate() {
        if (Build.VERSION.SDK_INT >= 26) {
            ((Vibrator) appContext.getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(80, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            ((Vibrator) appContext.getSystemService(VIBRATOR_SERVICE)).vibrate(80);
        }
    }
}
