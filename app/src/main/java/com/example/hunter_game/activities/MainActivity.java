package com.example.hunter_game.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hunter_game.R;
import com.example.hunter_game.objects.User;
import com.example.hunter_game.objects.enums.KeysToSaveEnums;
import com.example.hunter_game.utils.BackGround;
import com.example.hunter_game.utils.MySharedPreferences;
import com.example.hunter_game.utils.MySignal;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
INTENT:
      "BUNDLE":  BUNDLE:
                        "NAME": playerName (String),
                        "GAME_SCREEN": SENSORS/BUTTONS (String)
 */
public class MainActivity extends AppCompatActivity {
    static final String BUTTONS = "BUTTONS";
    static final String SENSORS = "SENSORS";

    private MaterialButton main_BTN_play,
                           main_BTN_topTen;
    private TextInputEditText main_EDT_playerName;
    private ImageView main_IMG_backGround;
    private String playerName;
    private Intent intent;
    private Bundle bundle;
    //TODO: Make icon and put it above the text input
    //TODO: Text style for all the app
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        new BackGround(this, main_IMG_backGround).setBackGround();

        initializeIntentBundleAndPlayerName();

        /**
         * PLAY & TOP TEN BUTTONS
         */
        main_BTN_play.setOnClickListener(view -> {
            if(!validatePlayerName(main_EDT_playerName.getText().toString())){//Checks the user name before starting the game
                MySignal.getMe().makeToastMessage("Not Valid Name. Use: A-Z / a-z / -");
            }
            else {
                moveToPageWithBundle(GameActivity.class);
                setPopUp().show();
            }
        });
        main_BTN_topTen.setOnClickListener(view -> { moveToTopTenPage(); });
    }

    private void findViews() {
        main_BTN_play = findViewById(R.id.main_BTN_play);
        main_BTN_topTen = findViewById(R.id.main_BTN_topTen);
        main_EDT_playerName = findViewById(R.id.main_EDT_playerName);
        main_IMG_backGround = findViewById(R.id.main_IMG_backGround);
    }

    public void putInBundlePlayerName(){
        playerName = main_EDT_playerName.getText().toString();
        bundle.putString(KeysToSaveEnums.NAME.toString(), (String) playerName);
    }

    public void moveToPageWithBundle(Class activity){
        intent = new Intent(MainActivity.this, activity);
        putInBundlePlayerName();
    }

    /**
     * Checks if there is any player that get in the Top Ten list
     * If there isn't -> there is nothing to see
     */
    public void moveToTopTenPage(){
        TypeToken token = new TypeToken<ArrayList<User>>() {};
        if(MySharedPreferences.getMe().getArray(KeysToSaveEnums.LIST_USERS.toString(), token).size() == 0)
            MySignal.getMe().makeToastMessage("You are the first player, lets set the bar!");
        else{
            moveToPageWithBundle(TopTenActivity.class);
            bundle.putString(KeysToSaveEnums.PAGE.toString(), KeysToSaveEnums.MAIN_PAGE.toString());
            intent.putExtra(KeysToSaveEnums.BUNDLE.toString(), bundle);
            startActivity(intent);
        }
    }

    /**
     * Presenting the name of the player if its not the first time in the main page
     * So that the player does not have to rewrite the name
     */
    public void initializeIntentBundleAndPlayerName(){
        intent = getIntent();
        if (intent.getBundleExtra(KeysToSaveEnums.BUNDLE.toString()) != null) {
            bundle = intent.getBundleExtra(KeysToSaveEnums.BUNDLE.toString());
            main_EDT_playerName.setText(bundle.getString(KeysToSaveEnums.NAME.toString()));
        } else {
            bundle = new Bundle();
        }
    }

    //POP-UP FOR BUTTON / SENSORS
    public MaterialAlertDialogBuilder setPopUp(){
        MaterialAlertDialogBuilder selectGameScreen = new MaterialAlertDialogBuilder(this)
                .setIcon(R.drawable.ic_play)
                .setTitle("Let's play")
                .setMessage("Choose how you want to play")
                .setPositiveButton("Sensors", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        bundle.putString(KeysToSaveEnums.GAME_SCREEN.toString(), SENSORS);
                        intent.putExtra(KeysToSaveEnums.BUNDLE.toString(), bundle);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Buttons", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        bundle.putString(KeysToSaveEnums.GAME_SCREEN.toString(), BUTTONS);
                        intent.putExtra(KeysToSaveEnums.BUNDLE.toString(), bundle);
                        startActivity(intent);
                    }
                });
        return selectGameScreen;
    }

    /**
     * [A-Za-z]+([ '-][a-zA-Z])
     * @param name String
     * @return Boolean
     */
    public Boolean validatePlayerName(String name){
        return name.matches( "[A-Za-z]+([ '-][a-zA-Z]+)*" );
    }

}
