package com.example.hunter_game.activities;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hunter_game.CallBack_Timer;
import com.example.hunter_game.R;
import com.example.hunter_game.objects.GameManager;
import com.example.hunter_game.objects.GameTimer;
import com.example.hunter_game.objects.GameUiUpdate;
import com.example.hunter_game.objects.enums.Directions;
import com.example.hunter_game.objects.enums.TimerStatus;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class GameButtonsActivity extends AppCompatActivity {
    private final int rows = 7;
    private final int columns = 5;
    private ImageView[] game_IMG_hearts;
    private GameManager gameManager;
    private GameUiUpdate gameUiUpdate;
    private GameTimer gameTimer;
    private MaterialTextView main_LBL_score;
    private MaterialButton main_BTN_upArrow,
                           main_BTN_rightArrow,
                           main_BTN_downArrow,
                           main_BTN_leftArrow;
    private TimerStatus timerStatus = TimerStatus.OFF;
    private ImageView [][] matrix;
    private boolean newGameFlag = true;//Import flag to sign if this round of the timer is a new game or not.
    //If it is a new game, I don't want to move at the first second and wait with the score


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_gamebuttons);
        setContentView(R.layout.activity_gamesensors);
        findViews();
        /*main_BTN_upArrow.setOnClickListener(view -> gameManager.changeDeerDirection(Directions.UP));
        main_BTN_rightArrow.setOnClickListener(view -> gameManager.changeDeerDirection(Directions.RIGHT));
        main_BTN_downArrow.setOnClickListener(view -> gameManager.changeDeerDirection(Directions.DOWN));
        main_BTN_leftArrow.setOnClickListener(view -> gameManager.changeDeerDirection(Directions.LEFT));
        */gameTimer = new GameTimer(callBack_Timer);
        gameTimer.start();

        gameUiUpdate = new GameUiUpdate(this, matrix, main_LBL_score, game_IMG_hearts);
        gameManager = new GameManager(rows, columns, this);
        //main_BTN_leftArrow.setVisibility(View.INVISIBLE);
    }
    private CallBack_Timer callBack_Timer = new CallBack_Timer() {
        @Override
        public void gameTimer() {
            manageMove();
            updateScore();
        }
        @Override
        public void coinJump() {
            moveCoinGameManager();
        }
    };

    private void findViews() {
        matrix = new ImageView[][]{{
                findViewById(R.id.main_IMG_row0col0), findViewById(R.id.main_IMG_row0col1), findViewById(R.id.main_IMG_row0col2), findViewById(R.id.main_IMG_row0col3), findViewById(R.id.main_IMG_row0col4)},
                {findViewById(R.id.main_IMG_row1col0), findViewById(R.id.main_IMG_row1col1), findViewById(R.id.main_IMG_row1col2), findViewById(R.id.main_IMG_row1col3), findViewById(R.id.main_IMG_row1col4)},
                {findViewById(R.id.main_IMG_row2col0), findViewById(R.id.main_IMG_row2col1), findViewById(R.id.main_IMG_row2col2), findViewById(R.id.main_IMG_row2col3), findViewById(R.id.main_IMG_row2col4)},
                {findViewById(R.id.main_IMG_row3col0), findViewById(R.id.main_IMG_row3col1), findViewById(R.id.main_IMG_row3col2), findViewById(R.id.main_IMG_row3col3), findViewById(R.id.main_IMG_row3col4)},
                {findViewById(R.id.main_IMG_row4col0), findViewById(R.id.main_IMG_row4col1), findViewById(R.id.main_IMG_row4col2), findViewById(R.id.main_IMG_row4col3), findViewById(R.id.main_IMG_row4col4)},
                {findViewById(R.id.main_IMG_row5col0), findViewById(R.id.main_IMG_row5col1), findViewById(R.id.main_IMG_row5col2), findViewById(R.id.main_IMG_row5col3), findViewById(R.id.main_IMG_row5col4)},
                {findViewById(R.id.main_IMG_row6col0), findViewById(R.id.main_IMG_row6col1), findViewById(R.id.main_IMG_row6col2), findViewById(R.id.main_IMG_row6col3), findViewById(R.id.main_IMG_row6col4)}};
        game_IMG_hearts = new ImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)
        };
        main_LBL_score = findViewById(R.id.main_LBL_score);
        main_BTN_upArrow = findViewById(R.id.main_BTN_upArrow);
        main_BTN_rightArrow = findViewById(R.id.main_BTN_rightArrow);
        main_BTN_downArrow = findViewById(R.id.main_BTN_downArrow);
        main_BTN_leftArrow = findViewById(R.id.main_BTN_leftArrow);
    }

    public void setScoreText(int score){
        main_LBL_score.setText("" + score);
    }

    public void updateUINewGame(){
        updateUIHeart();
        updateUIMatrix();
    }

    public void clearIndexInMatrix(){
        matrix[gameManager.getDeer().getCordinateY()][gameManager.getDeer().getCordinateX()].setVisibility(View.INVISIBLE);
        matrix[gameManager.getHunter().getCordinateY()][gameManager.getHunter().getCordinateX()].setVisibility(View.INVISIBLE);
    }

    public void clearIndexCoinInMatrix(){
        matrix[gameManager.getCoin().getCordinateY()][gameManager.getCoin().getCordinateX()].setVisibility(View.INVISIBLE);
    }

    public void updateUIMatrix(){
        if(!newGameFlag)
            gameManager.move();
        matrix[gameManager.getDeer().getCordinateY()][gameManager.getDeer().getCordinateX()].setImageResource(gameManager.getResourceToImage("deer",gameManager.getDeer().getDirection()));
        matrix[gameManager.getHunter().getCordinateY()][gameManager.getHunter().getCordinateX()].setImageResource(gameManager.getResourceToImage("hunter",gameManager.getHunter().getDirection()));
        matrix[gameManager.getCoin().getCordinateY()][gameManager.getCoin().getCordinateX()].setImageResource(gameManager.getResourceToImage("coin",gameManager.getCoin().getDirection()));
        matrix[gameManager.getDeer().getCordinateY()][gameManager.getDeer().getCordinateX()].setVisibility(View.VISIBLE);
        matrix[gameManager.getHunter().getCordinateY()][gameManager.getHunter().getCordinateX()].setVisibility(View.VISIBLE);
        matrix[gameManager.getCoin().getCordinateY()][gameManager.getCoin().getCordinateX()].setVisibility(View.VISIBLE);
    }

    private void updateUIHeart() {
        for (int i = 0; i < game_IMG_hearts.length; i++) {
            game_IMG_hearts[i].setVisibility(gameManager.getLives() > i ? View.VISIBLE : View.INVISIBLE);
        }
    }

    public void manageMove(){
        if (gameManager.checkCollisionHunterDeer()) {
            if (gameManager.getLives() == 1) {
                Toast.makeText(this, "LOSER", Toast.LENGTH_LONG).show();
                finish();
            } else {
                newGameFlag = true;//There was collision
                clearIndexInMatrix();//Needs to clean before I set the positions
                gameManager.restartGamePositions();
                gameManager.reduceLives();
                gameManager.restartScore();
                updateUINewGame();
                Toast.makeText(this, "You only have " + gameManager.getLives() + " more times to play", Toast.LENGTH_LONG).show();
            }
        } else {
            if (gameManager.checkCollisionHunterCoin() || gameManager.checkCollisionDeerCoin()) {
                if (gameManager.checkCollisionDeerCoin()) {
                    gameManager.addToScoreTen();
                    setScoreText(gameManager.getScore());
                }
                moveCoinGameManager();
            }
            clearIndexInMatrix();
            updateUIMatrix();
        }
    }
    public void moveCoinGameManager(){
        clearIndexCoinInMatrix();
        gameManager.getMoveItems().moveCoin();
    }

    public void updateScore() {
        if(!newGameFlag)
            gameManager.addToScoreOne();
        setScoreText(gameManager.getScore());
        newGameFlag = false;
    }

}