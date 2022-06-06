package com.example.hunter_game.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.hunter_game.objects.TopTen.User;
import com.example.hunter_game.objects.enums.KeysToSaveEnums;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class DataManager extends MySharedPreferences{
    private static DataManager me;

    private DataManager (Context context){
        super(context);
    }

    public static void initHelper(Context context) {
        if (me == null) {
            me = new DataManager(context);
        }
    }

    public static DataManager getMe() {
        return me;
    }

    public boolean checksIfThereIsListOfUsers(){
        return contain(KeysToSaveEnums.LIST_USERS.toString());
    }

    public ArrayList<User> getListOfUsers(){
        TypeToken <ArrayList<User>> token = new TypeToken<ArrayList<User>>() {};
        return getArray(KeysToSaveEnums.LIST_USERS.toString(), token);
    }

    public void saveListOfUsers(ArrayList<User> listUsers){
        putArray(KeysToSaveEnums.LIST_USERS.toString(), listUsers);
    }


}
