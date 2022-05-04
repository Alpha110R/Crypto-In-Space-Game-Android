package com.example.hunter_game;

import android.app.Application;

import com.example.hunter_game.objects.Game.GameMoveSensor;
import com.example.hunter_game.utils.MySharedPreferences;
import com.example.hunter_game.utils.MySignal;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        MySharedPreferences.initHelper(this);
        MySignal.initHelper(this);
        GameMoveSensor.initHelper(this);
    }
}
