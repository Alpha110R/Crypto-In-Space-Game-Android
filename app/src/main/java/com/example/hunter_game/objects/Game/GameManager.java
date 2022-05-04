package com.example.hunter_game.objects.Game;
import android.content.Context;

import com.example.hunter_game.objects.Game.ObjectInGame.Coin;
import com.example.hunter_game.objects.Game.ObjectInGame.Deer;
import com.example.hunter_game.objects.Game.ObjectInGame.Hunter;
import com.example.hunter_game.objects.Game.ObjectInGame.ItemInGame;
import com.example.hunter_game.objects.enums.Directions;

import java.util.Locale;

public class GameManager {
    private ItemInGame hunter, deer, coin;
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
        this.deer = new Deer().setCordinateX(columns/2).setCordinateY(rows-1).setDirection(Directions.UP);//Start position
        this.hunter = new Hunter().setCordinateX(columns/2).setCordinateY(0);//Start position
        this.coin = new Coin();
        this.rows=rows;
        this.columns=columns;
        this.context = context;
        moveItems = new MoveItems().setRows(rows).setColumns(columns).setDeer(deer).setHunter(hunter).setCoin(coin);
        moveItems.moveCoin();//Start from random position in matrix
    }

    public GameManager (){}

    public MoveItems getMoveItems(){return moveItems;}

    public void setHighestScore(int highestScore){ this.highestScore = highestScore; }

    public int getHighestScore(){ return highestScore; }

    public void checkAndUpdateHighestScore(){
        if(score > highestScore)
            highestScore = score;
    }

    public int getScore() {
        return score;
    }

    public void restartScore(){ score =0; }

    public void addToScore(int number){
        score += number;
    }

    public ItemInGame getDeer() {
        return deer;
    }

    public ItemInGame getHunter() {
        return hunter;
    }

    public ItemInGame getCoin() {
        return coin;
    }

    public int getLives() {
        return lives;
    }

    public void reduceLives() { lives--; }

    public void changeDeerDirection(Directions direction){
        deer.setDirection(direction);
    }

    //Nice and easy way to get Resource of any image i want
    public int getResourceToImage(String nameOfImage, Directions direction){
        String imageName = "ic_" + nameOfImage + "_" + direction.name().toLowerCase(Locale.ROOT);
        return context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
    }

    public void move(){
        moveItems.moveDeer();
        moveItems.moveHunter();
    }

    public boolean checkCollisionHunterDeer(){
        return deer.getCordinateX() == hunter.getCordinateX() && deer.getCordinateY() == hunter.getCordinateY();
    }

    public boolean checkCollisionHunterCoin() {
        return coin.getCordinateX() == hunter.getCordinateX() && coin.getCordinateY() == hunter.getCordinateY();
    }

    public boolean checkCollisionDeerCoin() {
        return coin.getCordinateX() == deer.getCordinateX() && coin.getCordinateY() == deer.getCordinateY();
    }


        public void restartGamePositions(){
        deer.setCordinateX(columns/2).setCordinateY(rows-1).setDirection(Directions.UP);
        hunter.setCordinateX(columns/2).setCordinateY(0).setDirection(Directions.DOWN);
    }
}
