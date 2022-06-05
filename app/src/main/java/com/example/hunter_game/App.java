package com.example.hunter_game;

import android.app.Application;

import com.example.hunter_game.objects.Game.GameMoveSensor;
import com.example.hunter_game.utils.MySignal.MessagesToUser;
import com.example.hunter_game.utils.MySignal.Music;
import com.example.hunter_game.utils.MySharedPreferences;
import com.example.hunter_game.utils.MySignal.Vibrations;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MySharedPreferences.initHelper(this);
        Music.initMusic(this);
        MessagesToUser.initMessagesToUser(this);
        Vibrations.initVibrations(this);
    }
}
