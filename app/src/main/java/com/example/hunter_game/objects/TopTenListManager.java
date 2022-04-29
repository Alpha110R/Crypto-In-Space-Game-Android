package com.example.hunter_game.objects;

import android.os.Bundle;

import com.example.hunter_game.objects.enums.KeysToSaveEnums;
import com.example.hunter_game.utils.MySharedPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class TopTenListManager {
    private Bundle bundle;
    private User user;
    private int minScore;
    private ArrayList listUsers;
    //TODO: Take another look at the class and check it with test

    public TopTenListManager(){
        listUsers = new ArrayList<User>();
    }

    public TopTenListManager setBundle(Bundle bundle) {
        this.bundle = bundle;
        return this;
    }

    public void saveListUsers(){
        if(MySharedPreferences.getMe().contain(KeysToSaveEnums.LIST_USERS.toString()))
            MySharedPreferences.getMe().removeKey(KeysToSaveEnums.LIST_USERS.toString()); //Clean the memory before upload

        MySharedPreferences.getMe().putArray(KeysToSaveEnums.LIST_USERS.toString(), listUsers);

    }

    public ArrayList<User> getListUsers(){
        return this.listUsers;
    }

    public User createUser(){
        return new User().setName(bundle.getString(KeysToSaveEnums.NAME.toString(),""))
                         .setScore(bundle.getInt(KeysToSaveEnums.SCORE.toString(),0))
                         .setDate(getDateUser())
                         .setLocation(getLocationUser());
    }

    public void updateList(User user){
        if(listUsers.size() >= 10)
            exchangeLastToNewUserInList(user);
        else
            addUserToList(user);
    }

    public void addUserToList(User user){
        listUsers.add(user);
        Collections.sort(listUsers);
    }

    public boolean checkIfEnterTopTenList(User user){
        if(getListUsers().get(0).getScore() < user.getScore())
            return true;
        return false;
    }

    public void exchangeLastToNewUserInList(User user){
        listUsers.remove(0);
        listUsers.add(user);
        Collections.sort(listUsers);
    }

    public String getLocationUser(){
        return "";
    }

    public Date getDateUser(){
        return new Date();
    }
}
