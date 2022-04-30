package com.example.hunter_game.objects.Game;

import android.content.Context;
import android.widget.ImageView;

import com.example.hunter_game.objects.Game.GameManager;
import com.google.android.material.textview.MaterialTextView;

public class GameUiUpdate {
    private GameManager gameManager;
    private Context context;
    private ImageView[][] matrix;
    private MaterialTextView main_LBL_score;
    private ImageView[] game_IMG_hearts;


    public GameUiUpdate (){}
    public GameUiUpdate (Context context, ImageView [][] matrix, MaterialTextView main_LBL_score, ImageView[] game_IMG_hearts){
        this.context = context;
        this.matrix = matrix;
        this.main_LBL_score = main_LBL_score;
        this.game_IMG_hearts = game_IMG_hearts;
    }
/*
    public void setScoreText(int score){
            ((GameActivity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    main_LBL_score.setText(""+score);
                }
            });
    }

    //GAMEUIMANAGER
    public void updateUINewGame(boolean newGameFlag){
        updateUIHeart();
        updateUIMatrix(newGameFlag);
    }

    public void clearIndexInMatrix(){
        ((GameActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                matrix[gameManager.getDeer().getCordinateY()][gameManager.getDeer().getCordinateX()].setVisibility(View.INVISIBLE);
                matrix[gameManager.getHunter().getCordinateY()][gameManager.getHunter().getCordinateX()].setVisibility(View.INVISIBLE);
            }
        });

    }

    public void updateUIMatrix(boolean newGameFlag){
        if(!newGameFlag)
            gameManager.move();
        ((GameActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                matrix[gameManager.getDeer().getCordinateY()][gameManager.getDeer().getCordinateX()].setImageResource(gameManager.getResourceToImage("deer",gameManager.getDeer().getDirection()));
                matrix[gameManager.getHunter().getCordinateY()][gameManager.getHunter().getCordinateX()].setImageResource(gameManager.getResourceToImage("hunter",gameManager.getHunter().getDirection()));
                matrix[gameManager.getDeer().getCordinateY()][gameManager.getDeer().getCordinateX()].setVisibility(View.VISIBLE);
                matrix[gameManager.getHunter().getCordinateY()][gameManager.getHunter().getCordinateX()].setVisibility(View.VISIBLE);
            }
        });
    }

    private void updateUIHeart() {
        ((GameActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < game_IMG_hearts.length; i++) {
                    game_IMG_hearts[i].setVisibility(gameManager.getLives() > i ? View.VISIBLE : View.INVISIBLE);
                }
            }
        });
    }*/
}
