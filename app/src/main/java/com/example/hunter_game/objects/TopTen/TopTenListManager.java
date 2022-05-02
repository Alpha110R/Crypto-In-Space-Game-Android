package com.example.hunter_game.objects.TopTen;

import android.os.Bundle;
import android.util.Log;

import com.example.hunter_game.objects.enums.KeysToSaveEnums;
import com.example.hunter_game.utils.MySharedPreferences;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

/**
INTENT:
        "BUNDLE":  BUNDLE:
                            "NAME": playerName (String),
                            "GAME_SCREEN": SENSORS/BUTTONS (String),
                            "SCORE": score (int)
                            "LATITUDE": latitude (Double)
                            "LONGITUDE": longitude (DOUBLE)
                            "DATE": date (String)
 */

public class TopTenListManager {

    private Bundle bundle;
    private User user;
    private ArrayList<User> listUsers;
    private int lastIndex;

    public TopTenListManager(){}
    public TopTenListManager(Bundle bundle){
        setBundle(bundle);

        setTopTenListUsersFromSp();

        //Creates the current user that played the game
        this.user = createUser();

    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public void saveTopTenListUsersToSP(){
        if(MySharedPreferences.getMe().contain(KeysToSaveEnums.LIST_USERS.toString()))
            MySharedPreferences.getMe().clear(); //Clean the memory before upload

        MySharedPreferences.getMe().putArray(KeysToSaveEnums.LIST_USERS.toString(), listUsers);

    }

    /**
     Add user to the list after I checked in the activity that the user needs to enter the list.
     After the add function I am sorting the list with comparator
     @return VOID
     */
    public void updateList(){
        if(listUsers.size() >= 10)
            exchangeLastToNewUserInList();
        else
            addUserToList();
        Collections.sort(listUsers, new ComparatorListUsers());
    }

    /**
     User will enter the list if the size of the list is less of 10
     or his score is bigger from the last one in the list
     @return VOID
     */
    public boolean checkIfEnterTopTenList(){
        if(getListUsers().size() <10 || getListUsers().get(lastIndex).getScore() < user.getScore())
            return true;
        return false;
    }

    public ArrayList<User> getListUsers(){
        return this.listUsers;
    }

    public User getUser(){
        return user;
    }

    /**
     Initialize the user list by pulling the array from the memory.
     If there is no array in the memory it will create a new one
     @return VOID
     */
    private void setTopTenListUsersFromSp(){
        if(MySharedPreferences.getMe().contain(KeysToSaveEnums.LIST_USERS.toString())) {
            TypeToken token = new TypeToken<ArrayList<User>>() {};
            this.listUsers = MySharedPreferences.getMe().getArray(KeysToSaveEnums.LIST_USERS.toString(), token);
        }
        else
            this.listUsers = new ArrayList<>();
        this.lastIndex = this.listUsers.size()-1;
    }

    private User createUser(){
        return new User().setName(bundle.getString(KeysToSaveEnums.NAME.toString(),""))
                         .setScore(bundle.getInt(KeysToSaveEnums.SCORE.toString(),0))
                         .setDate(getDateUser())
                         .setLocation(makeLocationUser());
    }

    private void addUserToList(){
        listUsers.add(user);
    }

    private void exchangeLastToNewUserInList(){
        listUsers.remove(lastIndex);
        addUserToList();
    }

    private MyLocation makeLocationUser(){
        Log.d("tagg", "LAT bundle: " + bundle.getDouble(KeysToSaveEnums.LATITUDE.toString(), 0));
        return new MyLocation().setLatitude(bundle.getDouble(KeysToSaveEnums.LATITUDE.toString(), 0))
                                .setLongitude(bundle.getDouble(KeysToSaveEnums.LONGITUDE.toString(), 0));
    }

    private String getDateUser(){
        return new SimpleDateFormat("dd-MM-yy HH:mm", Locale.US).format(System.currentTimeMillis());
    }
}
