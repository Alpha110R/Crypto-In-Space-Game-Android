package com.example.hunter_game.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.hunter_game.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

public class MainActivity extends AppCompatActivity {
    //private static final String LINK_BACKGROUND="https://images.pexels.com/photos/1723637/pexels-photo-1723637.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1";
    private static final String LINK_BACKGROUND="https://images.pexels.com/photos/10257142/pexels-photo-10257142.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1";

    private MaterialButton main_BTN_play,
                           main_BTN_topTen;
    private TextInputEditText main_EDT_playerName;
    ImageView main_IMG_backGround;
    static final String GAME_SCREEN = "GAME_SCREEN";
    static final String BUTTONS = "BUTTONS";
    static final String SENSORS = "SENSORS";
    static final String USER_NAME = "USER_NAME";
    static final String BUNDLE = "BUNDLE";
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
        intent = new Intent(MainActivity.this, GameActivity.class);
        bundle = new Bundle();
        main_BTN_play.setOnClickListener(view -> {
            playerName = main_EDT_playerName.getText().toString();
            Log.d("", "name: " + playerName);
            bundle.putString(USER_NAME, (String) playerName);
            setPopUp().show();
        });

    }
/*
INTENT:
      "BUNDLE":  BUNDLE:
                        "USER_NAME": playerName,
                        "GAME_SCREEN": SENSORS/BUTTONS
 */
    private void findViews() {
        main_BTN_play = findViewById(R.id.main_BTN_play);
        main_BTN_topTen = findViewById(R.id.main_BTN_topTen);
        main_EDT_playerName = findViewById(R.id.main_EDT_playerName);
        main_IMG_backGround = findViewById(R.id.main_IMG_backGround);
        Glide
                .with(this)
                .load(LINK_BACKGROUND)
                .into(main_IMG_backGround);
    }
    public MaterialAlertDialogBuilder setPopUp(){
        MaterialAlertDialogBuilder selectGameScreen = new MaterialAlertDialogBuilder(this)
                .setIcon(R.drawable.ic_play)
                .setTitle("Let's play")
                .setMessage("Choose how you want to play")
                .setPositiveButton("Sensors", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        bundle.putString(GAME_SCREEN, SENSORS);
                        intent.putExtra(BUNDLE, bundle);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Buttons", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        bundle.putString(GAME_SCREEN, BUTTONS);
                        intent.putExtra(BUNDLE, bundle);
                        startActivity(intent);
                    }
                });
        return selectGameScreen;
    }

}
