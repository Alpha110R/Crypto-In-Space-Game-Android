package com.example.hunter_game.objects.Game;
import android.content.Context;

import com.example.hunter_game.objects.enums.Directions;

import java.util.Locale;

public class GameManager {
    private ItemInGame cop, miner, coin;
    private MoveItems moveItems;
    private int score =0,
                lives =3,
                highestScore=0,
                rows,
                columns;
    private Context context;
    //private boolean newGameFlag = true;//Import flag to sign if this round of the timer is a new game or not.
    //If it is a new game, I don't want to move at the first second and wait with the score

    public GameManager(int rows, int columns, Context context){
        this.miner = new ItemInGame().setCoordinateX(columns/2).setCoordinateY(rows-1).setDirection(Directions.UP);//Start position
        this.cop = new ItemInGame().setCoordinateX(columns/2).setCoordinateY(0).setDirection(Directions.DOWN);//Start position
        this.coin = new ItemInGame().setDirection(Directions.UP);
        this.rows = rows;
        this.columns = columns;
        this.context = context;
        moveItems = new MoveItems().setRows(rows).setColumns(columns).setCop(cop).setMiner(miner);
        moveItems.moveCoin(coin);//Start from random position in matrix
    }

    public GameManager (){}

    public MoveItems getMoveItems(){return moveItems;}

    public int getHighestScore(){ return highestScore; }

    public void checkAndUpdateHighestScore(){
        if(score > highestScore)
            highestScore = score;
    }

    public int getScore() {
        return score;
    }

    public void restartScore(){ score = 0; }

    public void addToScore(int number){
        score += number;
    }

    public ItemInGame getMiner() {
        return miner;
    }

    public ItemInGame getCop() {
        return cop;
    }

    public ItemInGame getCoin() {
        return coin;
    }

    public int getLives() {
        return lives;
    }

    public void reduceLives() { lives--; }

    public void changeDeerDirection(Directions direction){
        miner.setDirection(direction);
    }

    //Nice and easy way to get Resource of any image i want
    public int getResourceToImage(String nameOfImage, Directions direction){
        String imageName = "ic_" + nameOfImage + "_" + direction.name().toLowerCase(Locale.ROOT);
        return context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
    }

    public void move(){
        moveItems.moveDeer(miner);
        moveItems.moveHunter(cop);
    }

    public boolean checkCollision(ItemInGame item1, ItemInGame item2){
        return item1.getCoordinateX() == item2.getCoordinateX() && item1.getCoordinateY() == item2.getCoordinateY();
    }

        public void restartGamePositions(){
        miner.setCoordinateX(columns/2).setCoordinateY(rows-1).setDirection(Directions.UP);
        cop.setCoordinateX(columns/2).setCoordinateY(0).setDirection(Directions.DOWN);
    }
}
