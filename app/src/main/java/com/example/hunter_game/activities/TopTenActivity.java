package com.example.hunter_game.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hunter_game.CallBacks.CallBack_ListUsers;
import com.example.hunter_game.objects.TopTen.fragments.FragmentMap;
import com.example.hunter_game.R;
import com.example.hunter_game.objects.TopTen.fragments.Fragment_TTList;
import com.example.hunter_game.objects.TopTen.TopTenListManager;
import com.example.hunter_game.objects.enums.KeysToSaveEnums;
import com.example.hunter_game.utils.BackGround;
import com.example.hunter_game.utils.MySignal.MessagesToUser;
import com.example.hunter_game.utils.MySignal.Music;
import com.google.android.material.button.MaterialButton;

public class TopTenActivity extends AppCompatActivity{
    private Fragment_TTList fragmentList;
    private FragmentMap fragmentMap;
    private ImageView topTen_IMG_backGround;
    private Intent intent;
    private Bundle bundle;
    private MaterialButton topTen_BTN_playAgain,
                           topTen_BTN_backToMenu;
    private TopTenListManager topTenListManager;
/**
INTENT:
      "BUNDLE":  BUNDLE:
                        "NAME": playerName (String),
                        "GAME_SCREEN": SENSORS/BUTTONS (String),
                        "SCORE": score (int)
                        "LATITUDE": latitude (Double)
                        "LONGITUDE": longitude (DOUBLE)
 */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topten);

        setIntentAndBundle();
        findViews();
        BackGround.setBackGround(this, topTen_IMG_backGround);
        Music.getMe().activateMusicAvengersTopTen();

        topTenListManager = new TopTenListManager(bundle);

        /**
         * If the player entered here from the main page he cant go and play
         */
        if(bundle.getString(KeysToSaveEnums.PAGE.toString()).equals(KeysToSaveEnums.MAIN_PAGE.toString()))
            topTen_BTN_playAgain.setVisibility(View.INVISIBLE);

        checkAndUpdateUserToEnterTopTenList();
        fragmentList = new Fragment_TTList(this, topTenListManager.getListUsers());
        fragmentMap = new FragmentMap();
        fragmentList.setCallBack_ListUsers(callBack_listUsers);
        getSupportFragmentManager()
                                    .beginTransaction()
                                    .add(R.id.topTen_LAY_list, fragmentList)
                                    .commit();
        getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.topTen_LAY_map, fragmentMap)
                                    .commit();


        /**
         * Move to the page that the player chose
         */
        topTen_BTN_playAgain.setOnClickListener(view -> {
            moveToPageWithBundle(GameActivity.class);
        });
        topTen_BTN_backToMenu.setOnClickListener(view -> {
           moveToPageWithBundle(MainActivity.class);
        });
    }
    private CallBack_ListUsers callBack_listUsers = new CallBack_ListUsers() {
        @Override
        public void presentLocationInMap(int position) {
            TopTenActivity.this.fragmentMap.updateMap(topTenListManager.getListUsers().get(position).getLocation());
        }
    };

    /**
     * Set the intent to the activity I want to go to
     * Includes finish() to remove the activity from the stack
     * @param activity Class
     */
    public void moveToPageWithBundle(Class activity){
        intent = new Intent(TopTenActivity.this, activity);
        exitPage();
        startActivity(intent);
        finish();
        return ;
    }

    /**
     * Nullify player's score and push the bundle to intent.
     * Also change the flag to true in order to do not get into the page opening loop from the intent
     */
    public void exitPage(){
        bundle.putInt(KeysToSaveEnums.SCORE.toString(), 0);
        intent.putExtra(KeysToSaveEnums.BUNDLE.toString(), bundle);
    }

    /**
     * Receive the data from game page. Set the intent to the game page to save the state of the player.
     */
    public void setIntentAndBundle(){
        intent = getIntent();
        bundle = intent.getBundleExtra(KeysToSaveEnums.BUNDLE.toString());
    }

    /**
     * Checks with the manager if the user arrived from menu +
     *                         if the user has the score to enter to the TOP TEN list +
     *                         if the user has the score the manager will update the list with the user score.
     * Update only if the player arrived from game
     */
    public void checkAndUpdateUserToEnterTopTenList(){
        if(topTenListManager.getUser().getScore() > 0) {//If the player arrived from the menu to see the list he has 0 score
            if (topTenListManager.checkIfEnterTopTenList()){
                topTenListManager.updateList();//If the player succeeded to enter to the TOP TEN list
                MessagesToUser.getMe().makeToastMessage("You entered to Top Ten List!");
            }
            else
                MessagesToUser.getMe().makeToastMessage("You didn't made it to TOP TEN");
        }
    }

    private void findViews() {
        topTen_BTN_playAgain = findViewById(R.id.topTen_BTN_playAgain);
        topTen_IMG_backGround = findViewById(R.id.topTen_IMG_backGround);
        topTen_BTN_backToMenu = findViewById(R.id.topTen_BTN_backToMenu);
    }

    @Override
    protected void onPause() {
        super.onPause();
        topTenListManager.saveTopTenListUsersToSP();
        Music.getMe().pauseMusicAvengersTopTen();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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