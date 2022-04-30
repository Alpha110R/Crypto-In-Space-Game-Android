package com.example.hunter_game.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.hunter_game.R;
import com.example.hunter_game.objects.enums.KeysToSaveEnums;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    //private static final String LINK_BACKGROUND="https://images.pexels.com/photos/1723637/pexels-photo-1723637.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1";
    static final String LINK_BACKGROUND="https://images.pexels.com/photos/10257142/pexels-photo-10257142.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1";
    static final String BUTTONS = "BUTTONS";
    static final String SENSORS = "SENSORS";


    private MaterialButton main_BTN_play,
                           main_BTN_topTen;
    private TextInputEditText main_EDT_playerName;
    ImageView main_IMG_backGround;
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
        setBackGround();

        intent = new Intent(MainActivity.this, GameActivity.class);
        bundle = new Bundle();
        main_BTN_play.setOnClickListener(view -> {
            playerName = main_EDT_playerName.getText().toString();
            Log.d("tag", "name: " + playerName);
            bundle.putString(KeysToSaveEnums.NAME.toString(), (String) playerName);
            setPopUp().show();
        });

    }
/*
INTENT:
      "BUNDLE":  BUNDLE:
                        "NAME": playerName (String),
                        "GAME_SCREEN": SENSORS/BUTTONS (String)
 */
    private void findViews() {
        main_BTN_play = findViewById(R.id.main_BTN_play);
        main_BTN_topTen = findViewById(R.id.main_BTN_topTen);
        main_EDT_playerName = findViewById(R.id.main_EDT_playerName);
        main_IMG_backGround = findViewById(R.id.main_IMG_backGround);
    }

    public void setBackGround(){
        Glide
                .with(this)
                .load(LINK_BACKGROUND)
                .into(main_IMG_backGround);
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

}
