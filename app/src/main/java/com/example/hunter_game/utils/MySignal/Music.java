package com.example.hunter_game.utils.MySignal;

import android.app.Application;
import android.content.Context;
import android.media.MediaPlayer;

import com.example.hunter_game.R;

public class Music {
    private Context appContext;
    private MediaPlayer mediaPlayer;
    private static Music me;
    public Music(){}
    Music(Context appContext){
        this.appContext = appContext;
    }
    public static void initMusic(Context appContext){
        if(me == null)
            me = new Music(appContext.getApplicationContext());
    }
    public static Music getMe(){return me;}

    public void activateMusicSpongebobWelcome(){
        mediaPlayer = MediaPlayer.create(appContext, R.raw.music_welcome);
        mediaPlayer.start();
    }
    public void pauseMusicSpongebobWelcome(){
        mediaPlayer.stop();
    }
    public void activateMusicAvengersTopTen(){
        mediaPlayer = MediaPlayer.create(appContext, R.raw.music_topten);
        mediaPlayer.start();
    }
    public void pauseMusicAvengersTopTen(){
        mediaPlayer.stop();
    }
    public void startCollisionMusic(){
        mediaPlayer = MediaPlayer.create(appContext, R.raw.music_spongedis);
        mediaPlayer.start();
    }
}
