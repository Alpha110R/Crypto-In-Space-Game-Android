package com.example.hunter_game;

import android.app.Application;

import com.example.hunter_game.utils.MySharedPreferences;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        MySharedPreferences.initHelper(this);

    }
}
