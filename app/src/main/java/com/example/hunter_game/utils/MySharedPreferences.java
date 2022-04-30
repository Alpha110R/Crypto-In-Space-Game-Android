package com.example.hunter_game.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.internal.Primitives;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;


public class MySharedPreferences {

    private final String SP_FILE_NAME = "MY_SP_FILE";
    private SharedPreferences prefs = null;

    private static MySharedPreferences me;


    private MySharedPreferences(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
    }

    public static void initHelper(Context context) {
        if (me == null) {
            me = new MySharedPreferences(context);
        }
    }

    public static MySharedPreferences getMe() {
        return me;
    }


    public void removeKey(String key){
        prefs.edit().remove(key).apply();
    }

    public boolean contain(String key) { return prefs.contains(key);}

    public void clear(){ prefs.edit().clear().commit();}


    //INT
    public void putInt(String key, int value) {
        prefs.edit().putInt(key, value).apply();
    }

    public int getInt(String key, int defValue) {
        return prefs.getInt(key, defValue);
    }

    //DOUBLE
    public void putDouble(String key, double value){
        prefs.edit().putString(key, String.valueOf(value)).apply();
    }

    public Double getDouble(String key, boolean defValue){
        return Double.parseDouble(prefs.getString(key, String.valueOf(defValue)));
    }

    //BOOLEAN
    public  void putBoolean(String key, boolean value){
        prefs.edit().putBoolean(key, value).apply();
    }

    public boolean getBoolean(String key, boolean defValue){
        return prefs.getBoolean(key, defValue);
    }

    //STRING
    public void putString(String key, String value) {
        prefs.edit().putString(key, value).apply();
    }

    public String getString(String key, String defValue) {
        return prefs.getString(key, defValue);
    }

    //ARRAY
    public <T> void putArray(String KEY, ArrayList<T> array) {
        String json = new Gson().toJson(array);
        prefs.edit().putString(KEY, json).apply();
    }

    public <T> ArrayList<T> getArray(String KEY, TypeToken typeToken) {
        // type token == new TypeToken<ArrayList<YOUR_CLASS>>() {}
        try {
            ArrayList<T> arr = new Gson().fromJson(prefs.getString(KEY, ""), typeToken.getType());
            if (arr == null) {
                return new ArrayList<>();
            }
            return arr;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ArrayList<>();
    }

    //MAP
    public <S, T> void putMap(String KEY, HashMap<S, T> map) {
        String json = new Gson().toJson(map);
        prefs.edit().putString(KEY, json).apply();
    }

    public <S, T> HashMap<S, T> getMap(String KEY, TypeToken typeToken) {
        // getMap(MySharedPreferencesV4.KEYS.SP_PLAYLISTS, new TypeToken<HashMap<String, Playlist>>() {});
        // TypeToken token = new TypeToken<ArrayList<YOUR_CLASS>>() {}
        try {
            HashMap<S, T> map = new Gson().fromJson(prefs.getString(KEY, ""), typeToken.getType());
            if (map == null) {
                return new HashMap<>();
            }
            return map;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new HashMap<>();
    }

    //OBJECT
    public void putObject(String key, Object value){
        prefs.edit().putString(key, new Gson().toJson(value)).apply();
    }

    public <T> T getObject(String key, Class<T> defClass){
        Object object = null;
        try{
            object = new Gson().fromJson(prefs.getString(key, ""), defClass);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Primitives.wrap(defClass).cast(object);

    }
}
