package com.example.hunter_game.objects.Game;
import android.os.Handler;

import com.example.hunter_game.CallBacks.CallBack_Timer;


public class GameTimer {
    private final int DELAY = 1000;
    private Handler handler;
    private int timer;

    public GameTimer (){}

    private CallBack_Timer callBack_timer = null;

    public GameTimer (CallBack_Timer callBack_timer) {
        this.callBack_timer = callBack_timer;
    }

    public void start() {
        handler = new Handler();

        handler.postDelayed(new Runnable() {
            public void run() {
                timer++;
                if (timer %10 == 0) {
                    if (callBack_timer != null) {
                        callBack_timer.coinJump();
                    }
                }
                if (callBack_timer != null) {
                    callBack_timer.gameTimer();
                }
                handler.postDelayed(this, DELAY);
            }

        }, DELAY);
    }

    public void restartTimer(){
        timer = 0;
    }

    /**
     * Stop the timer
     */
    public void stopTimer(){
        handler.removeCallbacksAndMessages(null);
    }
}
