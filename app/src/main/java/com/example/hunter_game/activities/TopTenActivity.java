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
import com.example.hunter_game.objects.TopTenListManager;
import com.example.hunter_game.objects.enums.KeysToSaveEnums;
import com.google.android.material.button.MaterialButton;

public class TopTenActivity extends AppCompatActivity {
    private Fragment_TTList fragmentList;
    private Fragment_TTMap fragmentMap;
    private ImageView topten_IMG_backGround;
    private Intent intent;
    private Bundle bundle;
    private MaterialButton topten_BTN_playAgain;
    private TopTenListManager topTenListManager;

/*
INTENT:
      "BUNDLE":  BUNDLE:
                        "USER_NAME": playerName (String),
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

        topTenListManager = new TopTenListManager().setBundle(bundle);

        topten_BTN_playAgain.setOnClickListener(view -> {Log.d("tag","Name user after save: " );});

    }

    private void findViews() {
        topten_BTN_playAgain = findViewById(R.id.topten_BTN_playAgain);
        topten_IMG_backGround = findViewById(R.id.topten_IMG_backGround);
    }

    public void setBackGround(){
        Glide
                .with(this)
                .load(MainActivity.LINK_BACKGROUND)
                .into(topten_IMG_backGround);
    }


}
