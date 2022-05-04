package com.example.hunter_game.objects.TopTen.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.ListFragment;

import com.example.hunter_game.CallBacks.CallBack_ListUsers;
import com.example.hunter_game.R;
import com.example.hunter_game.objects.TopTen.User;

import java.util.ArrayList;

public class Fragment_TTList extends ListFragment {
    private AppCompatActivity topTenActivity;
    private ArrayAdapter<User> listUserAdapter;
    private ArrayList <User> listUser;
    private CallBack_ListUsers callBack_listUsers = null;

    public Fragment_TTList(){}

    public Fragment_TTList(AppCompatActivity topTenActivity, ArrayList <User> listUser) {
        this.topTenActivity = topTenActivity;
        this.listUser = listUser;
        Log.d("tag", "check name IN FRAGMENT: " + listUser.get(0).getName());

    }
    public void setCallBack_ListUsers(CallBack_ListUsers callBack_listUsers){
        this.callBack_listUsers = callBack_listUsers;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);
        listUserAdapter = new ArrayAdapter(topTenActivity, android.R.layout.simple_list_item_1, listUser);
        setListAdapter(listUserAdapter);

        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        callBack_listUsers.presentLocationInMap(position);

    }

}
