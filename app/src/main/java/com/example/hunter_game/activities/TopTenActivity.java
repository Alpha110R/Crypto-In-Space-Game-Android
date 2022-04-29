package com.example.hunter_game.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hunter_game.R;
import com.example.hunter_game.fragments.Fragment_TTList;
import com.example.hunter_game.fragments.Fragment_TTMap;

public class TopTenActivity extends AppCompatActivity {
    private Fragment_TTList fragmentList;
    private Fragment_TTMap fragmentMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topten);
        findViews();
    }

       /* fragmentList = new Fragment_TTList();
        getSupportFragmentManager().beginTransaction().add(R.id.topten_LAY_list, fragmentList).commit();


        fragmentMap = new Fragment_TTMap();
        fragmentMap.setCallBack_ActivityTitle(callBack_map);
        getSupportFragmentManager().beginTransaction().add(R.id.topten_LAY_map, fragmentMap).commit();

    }

    private CallBack_ListUsers callBack_listUsers = new CallBack_ListUsers() {
//If press on one of the users it open map in fragmentMap
    }*/




    private void findViews() {

    }


}
