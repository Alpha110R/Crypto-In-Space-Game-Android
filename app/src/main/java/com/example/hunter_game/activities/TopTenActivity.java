package com.example.hunter_game.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.hunter_game.R;
import com.example.hunter_game.objects.TopTen.fragments.Fragment_TTList;
import com.example.hunter_game.objects.TopTen.fragments.Fragment_TTMap;
import com.example.hunter_game.objects.TopTen.TopTenListManager;
import com.example.hunter_game.objects.User;
import com.example.hunter_game.objects.enums.KeysToSaveEnums;
import com.google.android.material.button.MaterialButton;

public class TopTenActivity extends AppCompatActivity {
    private Fragment_TTList fragmentList;
    private Fragment_TTMap fragmentMap;
    private ImageView topTen_IMG_backGround;
    private Intent intent;
    private Bundle bundle;
    private MaterialButton topTen_BTN_playAgain,
                           topTen_BTN_backToMenu;
    private TopTenListManager topTenListManager;
    private boolean flagNextPage = false;

/**
INTENT:
      "BUNDLE":  BUNDLE:
                        "NAME": playerName (String),
                        "GAME_SCREEN": SENSORS/BUTTONS (String),
                        "SCORE": score (int)
 */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topten);
        /**
         * Receive the data from game page. Set the intent to the game page to save the state of the player.
         */
        intent = getIntent();
        bundle = intent.getBundleExtra(KeysToSaveEnums.BUNDLE.toString());
        intent = new Intent();
        findViews();
        setBackGround();

        /**
         * If the player entered here from the main page he cant go and play
         */
        if(bundle.getString(KeysToSaveEnums.PAGE.toString()).equals(KeysToSaveEnums.MAIN_PAGE.toString()))
            topTen_BTN_playAgain.setVisibility(View.INVISIBLE);


        topTenListManager = new TopTenListManager(bundle);
        checkAndUpdateUserToEnterTopTenList();
        fragmentList = new Fragment_TTList(this, topTenListManager.getListUsers());
        //fragmentList.setCallBack_listToActivity(callBack_activityTitle);
        //fragmentList.setCallBack_listAnimalClicked(callBack_listAnimalClicked);
        getSupportFragmentManager().beginTransaction().add(R.id.topTen_LAY_list, fragmentList).commit();



        /**
         * Return to the game page and kill this page
         */
        topTen_BTN_playAgain.setOnClickListener(view -> {
            if(!flagNextPage){
                intent = new Intent(TopTenActivity.this, GameActivity.class);
                exitPage();
                startActivity(intent);
            }
            finish();
        });
        topTen_BTN_backToMenu.setOnClickListener(view -> {
            if(!flagNextPage){
                intent = new Intent(TopTenActivity.this, MainActivity.class);
                exitPage();
                startActivity(intent);
            }
            finish();
        });

    }

    /**
     * Nullify player's score and push the bundle to intent.
     * Also change the flag to true in order to do not get into the page opening loop from the intent
     */
    public void exitPage(){
        bundle.putInt(KeysToSaveEnums.SCORE.toString(), 0);
        intent.putExtra(KeysToSaveEnums.BUNDLE.toString(), bundle);
        flagNextPage = true;
    }

    public void checkAndUpdateUserToEnterTopTenList(){
        if(topTenListManager.checkIfEnterTopTenList())
            topTenListManager.updateList();
        else{

        }
            //TODO: SIGNAL -> TOAST
    }

    private void findViews() {
        topTen_BTN_playAgain = findViewById(R.id.topTen_BTN_playAgain);
        topTen_IMG_backGround = findViewById(R.id.topTen_IMG_backGround);
        topTen_BTN_backToMenu = findViewById(R.id.topTen_BTN_backToMenu);
    }

    public void setBackGround(){
        Glide
                .with(this)
                .load(MainActivity.LINK_BACKGROUND)
                .into(topTen_IMG_backGround);
    }

    @Override
    protected void onPause() {
        super.onPause();
        topTenListManager.saveTopTenListUsersToSP();
    }
}
/*
TEST:
MySharedPreferences.getMe().clear();
        User user1 = new User().setDate(new Date()).setLocation("LOCATION USER1").setName("User1").setScore(10);
        User user2 = new User().setDate(new Date()).setLocation("LOCATION USER2").setName("User2").setScore(20);
        User user3 = new User().setDate(new Date()).setLocation("LOCATION USER3").setName("User3").setScore(15);
        User user4 = new User().setDate(new Date()).setLocation("LOCATION USER4").setName("User4").setScore(5);
        topTenListManager.addUserToList(user1);
        topTenListManager.addUserToList(user2);
        topTenListManager.addUserToList(user3);
        topTenListManager.addUserToList(user4);
        Log.d("tag","user name " + bundle.getString(KeysToSaveEnums.NAME.toString(),"") + " BEFORE save. SCORE: " + bundle.getInt(KeysToSaveEnums.SCORE.toString(),0)  + " Date: "+ bundle.getString(KeysToSaveEnums.DATE.toString(),"") );
        for (User user:
                topTenListManager.getListUsers()) {
            Log.d("tag","user name " + user.getName() + " BEFORE save SCORE: " + user.getScore() + " Date: "+ user.getDate());
        }
        topTenListManager.saveTopTenListUsersToSP();
        topTenListManager.setTopTenListUsersFromSp();
        for (User user:
             topTenListManager.getListUsers()) {
            Log.d("tag","user name " + user.getName() + " AFTER save SCORE: " + user.getScore() + " Date: "+ user.getDate());
        }
 */