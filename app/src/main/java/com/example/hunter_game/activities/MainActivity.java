package com.example.hunter_game.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hunter_game.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textview.MaterialTextView;

public class MainActivity extends AppCompatActivity {
    private MaterialButton main_BTN_play,
                           main_BTN_topTen;
    private EditText main_EDT_playerName;
    static final String GAME_SCREEN = "GAME_SCREEN";
    static final String BUTTONS = "BUTTONS";
    static final String SENSORS = "SENSORS";
    static final String USER_NAME = "USER_NAME";
    static final String BUNDLE = "BUNDLE";
    private String playerName;
    private Intent intent;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        intent = new Intent(MainActivity.this, GameActivity.class);
        bundle = new Bundle();
        main_BTN_play.setOnClickListener(view -> {
            playerName = main_EDT_playerName.getText().toString();
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
