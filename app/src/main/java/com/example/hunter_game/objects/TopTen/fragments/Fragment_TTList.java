package com.example.hunter_game.objects.TopTen.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.ListFragment;

import com.example.hunter_game.R;
import com.example.hunter_game.objects.User;

import java.util.ArrayList;

public class Fragment_TTList extends ListFragment implements OnItemClickListener {
    private AppCompatActivity activity;
    private ArrayAdapter<User> listUserAdapter;
    private ArrayList <User> listUser;

    public Fragment_TTList(){}

    public Fragment_TTList(AppCompatActivity activity, ArrayList <User> listUser) {
        this.activity = activity;
        this.listUser = listUser;
        Log.d("tag", "check name IN FRAGMENT: " + listUser.get(0).getName());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(view);
        listUserAdapter = new ArrayAdapter(activity, android.R.layout.simple_list_item_1, listUser);
        setListAdapter(listUserAdapter);
        return view;
    }





    private void findViews(View view) {
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //TODO: CALLBACK HERE TO activate the google map

    }
}
