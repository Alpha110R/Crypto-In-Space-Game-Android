package com.example.hunter_game.utils;
import static android.content.Context.VIBRATOR_SERVICE;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

public class MySignal {
    private static MySignal me;
    private Context appContext;

    private MySignal(Context context) {
        this.appContext = context;
    }

    public static void initHelper(Context context) {
        if (me == null) {
            me = new MySignal(context.getApplicationContext());
        }
    }

    public static MySignal getMe() { return me; }


    public void makeToastMessage(String message) {
        Toast.makeText(appContext, message, Toast.LENGTH_LONG).show();

    }

    public void vibrate() {
        if (Build.VERSION.SDK_INT >= 26) {
            ((Vibrator) appContext.getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(80, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            ((Vibrator) appContext.getSystemService(VIBRATOR_SERVICE)).vibrate(80);
        }
    }
}
