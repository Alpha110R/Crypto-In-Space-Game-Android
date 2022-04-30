package com.example.hunter_game.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.hunter_game.R;
import com.example.hunter_game.fragments.Fragment_TTList;
import com.example.hunter_game.fragments.Fragment_TTMap;
import com.example.hunter_game.objects.TopTen.TopTenListManager;
import com.example.hunter_game.objects.User;
import com.example.hunter_game.objects.enums.KeysToSaveEnums;
import com.example.hunter_game.utils.MySharedPreferences;
import com.google.android.material.button.MaterialButton;

import java.util.Date;

public class TopTenActivity extends AppCompatActivity {
    private Fragment_TTList fragmentList;
    private Fragment_TTMap fragmentMap;
    private ImageView topTen_IMG_backGround;
    private Intent intent;
    private Bundle bundle;
    private MaterialButton topten_BTN_playAgain;
    private TopTenListManager topTenListManager;

/*
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

        //Receive the data from game page and set the intent to the game page to save the state of the player.
        intent = getIntent();
        bundle = intent.getBundleExtra(KeysToSaveEnums.BUNDLE.toString());
        intent = new Intent(TopTenActivity.this, GameActivity.class);

        findViews();
        setBackGround();
        topTenListManager = new TopTenListManager(bundle);

        checkAndUpdateUserToEnterTopTenList();
        topten_BTN_playAgain.setOnClickListener(view -> {

        });

    }

    public void checkAndUpdateUserToEnterTopTenList(){
        if(topTenListManager.checkIfEnterTopTenList())
            topTenListManager.updateList();
        else{

        }
            //TODO: SIGNAL -> TOAST
    }

    private void findViews() {
        topten_BTN_playAgain = findViewById(R.id.topten_BTN_playAgain);
        topTen_IMG_backGround = findViewById(R.id.topTen_IMG_backGround);
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