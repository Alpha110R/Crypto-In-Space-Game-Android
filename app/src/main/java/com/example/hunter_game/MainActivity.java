package com.example.hunter_game;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private final int rows = 5;
    private final int columns = 3;
    private LinearLayout grid;
    private LinearLayout [] rowsInGrid;
    private ImageView[] game_IMG_hearts;
    private Timer timer;
    private final int DELAY = 1000;
    private GameManager gameManager;
    private MaterialTextView main_LBL_score;
    private MaterialButton main_BTN_upArrow,
                           main_BTN_rightArrow,
                           main_BTN_downArrow,
                           main_BTN_leftArrow;
    private TimerStatus timerStatus = TimerStatus.OFF;
    private ImageView [][] matrix;
    private boolean newGameFlag = true;//Import flag to sign if this round of the timer it is a new game or not.
                                        //If it is a new game, I don't want to move at the first second and wait with the score

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        //initMatrix(matrix);
        main_BTN_upArrow.setOnClickListener(view -> gameManager.changeDeerDirection(Directions.UP));
        main_BTN_rightArrow.setOnClickListener(view -> gameManager.changeDeerDirection(Directions.RIGHT));
        main_BTN_downArrow.setOnClickListener(view -> gameManager.changeDeerDirection(Directions.DOWN));
        main_BTN_leftArrow.setOnClickListener(view -> gameManager.changeDeerDirection(Directions.LEFT));

    }

    private void findViews() {
        matrix = new ImageView[][]{{
                findViewById(R.id.main_IMG_row0col0), findViewById(R.id.main_IMG_row0col1), findViewById(R.id.main_IMG_row0col2)},
                {findViewById(R.id.main_IMG_row1col0), findViewById(R.id.main_IMG_row1col1), findViewById(R.id.main_IMG_row1col2)},
                {findViewById(R.id.main_IMG_row2col0), findViewById(R.id.main_IMG_row2col1), findViewById(R.id.main_IMG_row2col2)},
                {findViewById(R.id.main_IMG_row3col0), findViewById(R.id.main_IMG_row3col1), findViewById(R.id.main_IMG_row3col2)},
                {findViewById(R.id.main_IMG_row4col0), findViewById(R.id.main_IMG_row4col1), findViewById(R.id.main_IMG_row4col2)}};
        game_IMG_hearts = new ImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)
        };
        main_LBL_score = findViewById(R.id.main_LBL_score);
        grid = (LinearLayout) findViewById(R.id.main_LNL_matrix);
        gameManager = new GameManager(rows, columns);
        main_BTN_upArrow = findViewById(R.id.main_BTN_upArrow);
        main_BTN_rightArrow = findViewById(R.id.main_BTN_rightArrow);
        main_BTN_downArrow = findViewById(R.id.main_BTN_downArrow);
        main_BTN_leftArrow = findViewById(R.id.main_BTN_leftArrow);
        //rowsInGrid = new LinearLayout[rows];
    }

    //Nice and easy way to get Resource of any image i want
    public int getResourceToImage(String nameOfImage, Directions direction){
        String imageName = "ic_" + nameOfImage + "_" + direction.name().toLowerCase(Locale.ROOT);
        return this.getResources().getIdentifier(imageName, "drawable", this.getPackageName());
    }

    public void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        manageMove();
                        setScore();
                    }
                });
            }
        }, 0, DELAY);
    }

    public void setScore() {
        if(!newGameFlag){
            gameManager.addToScore();
            main_LBL_score.setText("" + gameManager.getScore());
        }
        newGameFlag = false;
    }

    public void manageMove(){
        if(gameManager.checkCollision()) {
            if(gameManager.getLives() == 1){
                Toast.makeText(this, "LOSER", Toast.LENGTH_LONG).show();
                finish();
            }else {
                newGameFlag = true;//There was collision
                clearIndexInMatrix();//Needs to clean before I set the positions
                gameManager.restartGamePositions();
                gameManager.reduceLives();
                gameManager.restartScore();
                main_LBL_score.setText("" + gameManager.getScore());
                updateUINewGame();
                Toast.makeText(this, "You have only " + gameManager.getLives() + " times to play", Toast.LENGTH_LONG).show();
            }
        }else{
            clearIndexInMatrix();
            updateUIMatrix();
        }
    }

    public void updateUINewGame(){
        updateUIHeart();
        updateUIMatrix();
    }

    public void clearIndexInMatrix(){
        matrix[gameManager.getDeer().getCordinateY()][gameManager.getDeer().getCordinateX()].setVisibility(View.INVISIBLE);
        matrix[gameManager.getHunter().getCordinateY()][gameManager.getHunter().getCordinateX()].setVisibility(View.INVISIBLE);
    }

    public void updateUIMatrix(){
        if(!newGameFlag)
            gameManager.move();
        matrix[gameManager.getDeer().getCordinateY()][gameManager.getDeer().getCordinateX()].setImageResource(getResourceToImage("deer",gameManager.getDeer().getDirection()));
        matrix[gameManager.getHunter().getCordinateY()][gameManager.getHunter().getCordinateX()].setImageResource(getResourceToImage("hunter",gameManager.getHunter().getDirection()));
        matrix[gameManager.getDeer().getCordinateY()][gameManager.getDeer().getCordinateX()].setVisibility(View.VISIBLE);
        matrix[gameManager.getHunter().getCordinateY()][gameManager.getHunter().getCordinateX()].setVisibility(View.VISIBLE);
    }

    private void updateUIHeart() {
        for (int i = 0; i < game_IMG_hearts.length; i++) {
            game_IMG_hearts[i].setVisibility(gameManager.getLives() > i ? View.VISIBLE : View.INVISIBLE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (timerStatus == TimerStatus.RUNNING) {
            stopTimer();
            timerStatus = TimerStatus.PAUSE;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (timerStatus == TimerStatus.OFF || timerStatus == TimerStatus.PAUSE) {
            startTimer();
            timerStatus = TimerStatus.RUNNING;
        }
    }
    private void stopTimer() {
        timer.cancel();
    }
/*
    public void initMatrix(Cell [][] matrix){
        LinearLayout row2 = new LinearLayout(this);
        for(int i=0 ; i<rows ; i++){
            rowsInGrid[i] = new LinearLayout(this);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            layoutParams.setMargins(5, 5, 5, 5);
            layoutParams.weight = 1;
            rowsInGrid[i].setOrientation(LinearLayout.HORIZONTAL);
            rowsInGrid[i].setGravity(1);
            row2.setOrientation(LinearLayout.HORIZONTAL);

            for(int j=0 ; j<columns ; j++){
                matrix[i][j] = new Cell(this);
                matrix[i][j].setCordinateX(j).
                        setCordinateY(i).
                        setCellRole(CellRole.NONE).
                        setImage(getResourceToImage("deer", gameManager.getDeer().getDirection()));
                TextView tt = new TextView(this);
                ImageView im = new ImageView(this);
                im.setImageResource(getResourceToImage("hunter", Directions.UP));
                im.setMaxHeight(20);
                im.setMaxWidth(20);
                tt.setText("AAA    ");
                tt.setTextSize(50);
                //rowsInGrid[i].addView(tt);
                rowsInGrid[i].addView(matrix[i][j].getImage(),layoutParams);
                //row2.addView(im, layoutParams);

                Log.d("","io" + i + " j" + j);
            }
            //grid.addView(rowsInGrid[i]);
            grid.setGravity(11);

        }

        for(int i=0; i<rows; i++) {
            grid.addView(rowsInGrid[i]);
        }
        //grid.addView(row2);
    }*/
}