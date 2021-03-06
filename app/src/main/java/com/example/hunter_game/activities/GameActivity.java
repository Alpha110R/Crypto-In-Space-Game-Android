package com.example.hunter_game.activities;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.hunter_game.CallBacks.CallBack_MotionSensor;
import com.example.hunter_game.CallBacks.CallBack_Timer;
import com.example.hunter_game.R;
import com.example.hunter_game.objects.Game.GameManager;
import com.example.hunter_game.objects.Game.GameMoveSensor;
import com.example.hunter_game.objects.Game.GameTimer;
import com.example.hunter_game.objects.enums.Directions;
import com.example.hunter_game.objects.enums.KeysToSaveEnums;
import com.example.hunter_game.objects.enums.TimerStatus;
import com.example.hunter_game.utils.BackGround;
import com.example.hunter_game.utils.MySignal.MessagesToUser;
import com.example.hunter_game.utils.MySignal.Music;
import com.example.hunter_game.utils.MySignal.Vibrations;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;


public class GameActivity extends AppCompatActivity {

    private final int rows = 7;
    private final int columns = 5;
    private ImageView[] game_IMG_hearts;
    private ImageView game_IMG_backGround;
    private GameManager gameManager;
    private GameTimer gameTimer;
    private MaterialTextView game_LBL_score;
    private MaterialButton game_BTN_upArrow,
                           game_BTN_rightArrow,
                           game_BTN_downArrow,
                           game_BTN_leftArrow;
    private TimerStatus timerStatus = TimerStatus.OFF;
    private ImageView [][] matrix;
    private boolean newGameFlag = true;//Import flag to sign if this round of the timer is a new game or not.
                                        //If it is a new game, I don't want to move at the first second and wait with the score
    private Intent intent;
    private Bundle bundle;
    private String screenType;
    private GameMoveSensor gameMoveSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        findViews();
        BackGround.setBackGround(this, game_IMG_backGround);

        //Receive the data from main page and set the intent to the Top Ten page
        intent = getIntent();
        bundle = intent.getBundleExtra(KeysToSaveEnums.BUNDLE.toString());
        intent = new Intent(GameActivity.this, TopTenActivity.class);
        screenType = bundle.getString(KeysToSaveEnums.GAME_SCREEN.toString());

        //Check which mode to activate in the game -> SENSORS / BUTTONS
        if(screenType.equals(MainActivity.SENSORS)){
            gameMoveSensor = new GameMoveSensor(this);
            makeButtonsInvisible();
            gameMoveSensor.setCallBack_motionSensor(callBack_motionSensor);
        }
        else
            activateButtonsListeners();
        gameManager = new GameManager(rows, columns, this);
    }

    private void activateButtonsListeners(){
        game_BTN_upArrow.setOnClickListener(view -> gameManager.changeDeerDirection(Directions.UP));
        game_BTN_rightArrow.setOnClickListener(view -> gameManager.changeDeerDirection(Directions.RIGHT));
        game_BTN_downArrow.setOnClickListener(view -> gameManager.changeDeerDirection(Directions.DOWN));
        game_BTN_leftArrow.setOnClickListener(view -> gameManager.changeDeerDirection(Directions.LEFT));
    }

    private void makeButtonsInvisible(){
        game_BTN_upArrow.setVisibility(View.INVISIBLE);
        game_BTN_rightArrow.setVisibility(View.INVISIBLE);
        game_BTN_downArrow.setVisibility(View.INVISIBLE);
        game_BTN_leftArrow.setVisibility(View.INVISIBLE);
    }

    ////CALLBACK TIMER
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

    //////CALLBACK MOTION SENSOR
    private CallBack_MotionSensor callBack_motionSensor = new CallBack_MotionSensor() {
        @Override
        public void right() {
            gameManager.changeDeerDirection(Directions.RIGHT);
        }

        @Override
        public void left() {
            gameManager.changeDeerDirection(Directions.LEFT);
        }

        @Override
        public void up() {
            gameManager.changeDeerDirection(Directions.UP);
        }

        @Override
        public void down() {
            gameManager.changeDeerDirection(Directions.DOWN);
        }
    };

    //Set views
    private void findViews() {
        matrix = new ImageView[][]{{
                findViewById(R.id.game_IMG_row0col0), findViewById(R.id.game_IMG_row0col1), findViewById(R.id.game_IMG_row0col2), findViewById(R.id.game_IMG_row0col3), findViewById(R.id.game_IMG_row0col4)},
                {findViewById(R.id.game_IMG_row1col0), findViewById(R.id.game_IMG_row1col1), findViewById(R.id.game_IMG_row1col2), findViewById(R.id.game_IMG_row1col3), findViewById(R.id.game_IMG_row1col4)},
                {findViewById(R.id.game_IMG_row2col0), findViewById(R.id.game_IMG_row2col1), findViewById(R.id.game_IMG_row2col2), findViewById(R.id.game_IMG_row2col3), findViewById(R.id.game_IMG_row2col4)},
                {findViewById(R.id.game_IMG_row3col0), findViewById(R.id.game_IMG_row3col1), findViewById(R.id.game_IMG_row3col2), findViewById(R.id.game_IMG_row3col3), findViewById(R.id.game_IMG_row3col4)},
                {findViewById(R.id.game_IMG_row4col0), findViewById(R.id.game_IMG_row4col1), findViewById(R.id.game_IMG_row4col2), findViewById(R.id.game_IMG_row4col3), findViewById(R.id.game_IMG_row4col4)},
                {findViewById(R.id.game_IMG_row5col0), findViewById(R.id.game_IMG_row5col1), findViewById(R.id.game_IMG_row5col2), findViewById(R.id.game_IMG_row5col3), findViewById(R.id.game_IMG_row5col4)},
                {findViewById(R.id.game_IMG_row6col0), findViewById(R.id.game_IMG_row6col1), findViewById(R.id.game_IMG_row6col2), findViewById(R.id.game_IMG_row6col3), findViewById(R.id.game_IMG_row6col4)}};
        game_IMG_hearts = new ImageView[]{
                findViewById(R.id.game_IMG_heart1),
                findViewById(R.id.game_IMG_heart2),
                findViewById(R.id.game_IMG_heart3)
        };
        game_LBL_score = findViewById(R.id.game_LBL_score);
        game_BTN_upArrow = findViewById(R.id.game_BTN_upArrow);
        game_BTN_rightArrow = findViewById(R.id.game_BTN_rightArrow);
        game_BTN_downArrow = findViewById(R.id.game_BTN_downArrow);
        game_BTN_leftArrow = findViewById(R.id.game_BTN_leftArrow);
        game_IMG_backGround = findViewById(R.id.game_IMG_backGround);
    }

    public void manageMove(){
        if (gameManager.checkCollision(gameManager.getMiner(), gameManager.getCop())) {
            gameManager.checkAndUpdateHighestScore();
            if (gameManager.getLives() == 3) {
                gameTimer.stopTimer();
                Vibrations.getMe().vibrate();
                bundle.putInt(KeysToSaveEnums.SCORE.toString(), gameManager.getHighestScore());
                bundle.putString(KeysToSaveEnums.PAGE.toString(), KeysToSaveEnums.GAME_PAGE.toString());
                intent.putExtra(KeysToSaveEnums.BUNDLE.toString(), bundle);
                startActivity(intent);
                finish();
                return;

            } else {
                Vibrations.getMe().vibrate();
                Music.getMe().startCollisionMusic();
                newGameFlag = true;//There was collision
                clearIndexInMatrix();//Needs to clean before I set the positions
                gameManager.restartGamePositions();
                gameManager.reduceLives();
                gameManager.restartScore();
                updateUINewGame();
                MessagesToUser.getMe().makeToastMessage("You only have " + gameManager.getLives() + " more times to play");
            }
        } else {
            /**
             * Collision with coin
             */
            if (gameManager.checkCollision(gameManager.getCop(), gameManager.getCoin())
                    || gameManager.checkCollision(gameManager.getMiner(), gameManager.getCoin())) {
                if (gameManager.checkCollision(gameManager.getMiner(), gameManager.getCoin())) {
                    Vibrations.getMe().vibrate();
                    gameManager.addToScore(10);
                    setScoreText(gameManager.getScore());
                }
                gameTimer.restartTimer();
                moveCoinGameManager();
            }
            clearIndexInMatrix();
            updateUIMatrix();
        }
    }

    public void setScoreText(int score){
        game_LBL_score.setText("" + score);
    }

    public void updateUINewGame(){
        updateUIHeart();
        updateUIMatrix();
    }

    public void clearIndexInMatrix(){
        matrix[gameManager.getMiner().getCoordinateY()][gameManager.getMiner().getCoordinateX()].setVisibility(View.INVISIBLE);
        matrix[gameManager.getCop().getCoordinateY()][gameManager.getCop().getCoordinateX()].setVisibility(View.INVISIBLE);
    }

    public void clearIndexCoinInMatrix(){
        matrix[gameManager.getCoin().getCoordinateY()][gameManager.getCoin().getCoordinateX()].setVisibility(View.INVISIBLE);
    }

    public void updateUIMatrix(){
        if(!newGameFlag)
            gameManager.move();
        matrix[gameManager.getMiner().getCoordinateY()][gameManager.getMiner().getCoordinateX()].setImageResource(gameManager.getResourceToImage("miner",gameManager.getMiner().getDirection()));
        matrix[gameManager.getCop().getCoordinateY()][gameManager.getCop().getCoordinateX()].setImageResource(gameManager.getResourceToImage("cop",gameManager.getCop().getDirection()));
        matrix[gameManager.getCoin().getCoordinateY()][gameManager.getCoin().getCoordinateX()].setImageResource(gameManager.getResourceToImage("coin",gameManager.getCoin().getDirection()));
        matrix[gameManager.getMiner().getCoordinateY()][gameManager.getMiner().getCoordinateX()].setVisibility(View.VISIBLE);
        matrix[gameManager.getCop().getCoordinateY()][gameManager.getCop().getCoordinateX()].setVisibility(View.VISIBLE);
        matrix[gameManager.getCoin().getCoordinateY()][gameManager.getCoin().getCoordinateX()].setVisibility(View.VISIBLE);
    }

    private void updateUIHeart() {
        for (int i = 0; i < game_IMG_hearts.length; i++) {
            game_IMG_hearts[i].setVisibility(gameManager.getLives() > i ? View.VISIBLE : View.INVISIBLE);
        }
    }

    public void moveCoinGameManager(){
        clearIndexCoinInMatrix();
        gameManager.getMoveItems().moveCoin(gameManager.getCoin());
    }

    public void updateScore() {
        if(!newGameFlag)
            gameManager.addToScore(1);
        setScoreText(gameManager.getScore());
        newGameFlag = false;
    }


    @Override
    protected void onStart() {
        super.onStart();
        //Set the timer for the game and send him the callback
        gameTimer = new GameTimer(callBack_Timer);
        gameTimer.start();
        if(gameMoveSensor != null)
            gameMoveSensor.onResumeSensorManager();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    /**
     * Stop the Sensors + Timer
     */
    @Override
    protected void onStop() {
        super.onStop();
        if(gameMoveSensor != null)
            gameMoveSensor.onPauseSensorManager();
        gameTimer.stopTimer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}