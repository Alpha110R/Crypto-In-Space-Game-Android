package com.example.hunter_game.objects.TopTen;

import android.os.Bundle;

import com.example.hunter_game.objects.TopTen.ComparatorListUsers;
import com.example.hunter_game.objects.User;
import com.example.hunter_game.objects.enums.KeysToSaveEnums;
import com.example.hunter_game.utils.MySharedPreferences;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class TopTenListManager {
    private Bundle bundle;
    private User user;
    private int minScore;
    private ArrayList<User> listUsers;

    public TopTenListManager(){}
    public TopTenListManager(Bundle bundle){
        setBundle(bundle);
        setTopTenListUsersFromSp();
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

    public void updateList(){
        if(listUsers.size() >= 10)
            exchangeLastToNewUserInList();
        else
            addUserToList();
        Collections.sort(listUsers, new ComparatorListUsers());
    }

    public boolean checkIfEnterTopTenList(){
        if(getListUsers().size() <10 || getListUsers().get(0).getScore() < user.getScore())
            return true;
        return false;
    }

    public ArrayList<User> getListUsers(){
        return this.listUsers;
    }

    public User getUser(){
        return user;
    }

    private void setTopTenListUsersFromSp(){
        if(MySharedPreferences.getMe().contain(KeysToSaveEnums.LIST_USERS.toString())) {
            TypeToken token = new TypeToken<ArrayList<User>>() {};
            this.listUsers = MySharedPreferences.getMe().getArray(KeysToSaveEnums.LIST_USERS.toString(), token);
        }
        else
            this.listUsers = new ArrayList<>();
    }



    private User createUser(){
        return new User().setName(bundle.getString(KeysToSaveEnums.NAME.toString(),""))
                         .setScore(bundle.getInt(KeysToSaveEnums.SCORE.toString(),0))
                         .setDate(getDateUser())
                         .setLocation(getLocationUser());
    }

    private void addUserToList(){//TODO: private + delete user parameter
        listUsers.add(user);
        Collections.sort(listUsers, new ComparatorListUsers());
    }

    private void exchangeLastToNewUserInList(){
        listUsers.remove(0);
        listUsers.add(user);
    }

    private String getLocationUser(){
        return "";
    }

    private Date getDateUser(){
        return new Date();
    }
}
