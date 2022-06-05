package com.example.hunter_game.utils.MySignal;
import android.app.Application;
import android.content.Context;
import android.widget.Toast;

public class MessagesToUser {
    private Context appContext;
    private static MessagesToUser me;
    public MessagesToUser(){}
    MessagesToUser(Context appContext){
        this.appContext = appContext;
    }
    public static void initMessagesToUser(Context appContext){
        if(me == null)
            me = new MessagesToUser(appContext.getApplicationContext());
    }
    public static MessagesToUser getMe(){return me;}

    public void makeToastMessage(String message) {
        Toast.makeText(appContext, message, Toast.LENGTH_LONG).show();
    }
}
