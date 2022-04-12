package com.example.hunter_game;

import android.util.Log;
import java.util.Random;

public class GameManager {
    private Deer deer;
    private Hunter hunter;
    private int score =0;
    private int lives =3,
                rows,
                columns;

    public GameManager(){}

    public GameManager (int rows, int columns){
        this.deer = new Deer().setCordinateX(columns/2).setCordinateY(rows-1).setDirection(Directions.UP);
        this.hunter = new Hunter().setCordinateX(columns/2).setCordinateY(0);
        this.rows=rows;
        this.columns=columns;
    };

    public int getScore() {
        return score;
    }

    public void addToScore() {
        score += 1;
    }

    public void restartScore(){ score =0; }

    public Deer getDeer() {
        return deer;
    }

    public Hunter getHunter() {
        return hunter;
    }

    public int getLives() {
        return lives;
    }

    public void reduceLives() { lives--; }

    public boolean checkBounderies(int x, int y, Directions direction){
        switch (direction){
            case UP:
                if(y==0)
                    return false;
                break;
            case RIGHT:
                if(x == (columns-1))
                    return false;
                break;
            case DOWN:
                if (y == (rows-1))
                    return false;
                break;
            case LEFT:
                if (x == 0)
                    return false;
                break;
        }
        return true;
    }

    public void changeDeerDirection(Directions direction){
        deer.setDirection(direction);
    }

    public void moveIndexByDirectionDeer(int x, int y, Directions direction){
        switch (direction){
            case UP:
                    deer.setCordinateY(--y);
                    break;
            case RIGHT:
                    deer.setCordinateX(++x);
                    break;
            case DOWN:
                    deer.setCordinateY(++y);
                    break;
            case LEFT:
                    deer.setCordinateX(--x);
                    break;
        }
    }

    public void moveIndexByDirectionHunter(int x, int y, Directions direction, Object obj){
        switch (direction){
            case UP:
                hunter.setCordinateY(--y);
                break;
            case RIGHT:
                hunter.setCordinateX(++x);
                break;
            case DOWN:
                hunter.setCordinateY(++y);
                break;
            case LEFT:
                hunter.setCordinateX(--x);
                break;
        }
    }

    public void move(){
        if(checkBounderies(deer.getCordinateX(), deer.getCordinateY(),deer.getDirection())){
            moveIndexByDirectionDeer(deer.getCordinateX(),deer.getCordinateY(),deer.getDirection());
        }
        int random = new Random().nextInt((4));
        hunter.setDirection(Directions.values()[random]);
        if(checkBounderies(hunter.getCordinateX(), hunter.getCordinateY(),hunter.getDirection())) {
            moveIndexByDirectionHunter(hunter.getCordinateX(), hunter.getCordinateY(), hunter.getDirection(), hunter);
        }
        else{
            if(hunter.getCordinateY() == (rows-1) && hunter.getDirection() == Directions.DOWN) {
            hunter.setCordinateY(0);
            hunter.setCordinateX(columns / 2);
            }
        }
    }

    public boolean checkCollision(){
        return deer.getCordinateX() == hunter.getCordinateX() && deer.getCordinateY() == hunter.getCordinateY();
    }

    public void restartGamePositions(){
        deer.setCordinateX(columns/2).setCordinateY(rows-1).setDirection(Directions.UP);
        hunter.setCordinateX(columns/2).setCordinateY(0).setDirection(Directions.DOWN);
    }

    public String stringAdapter(Directions direction){
        switch (direction){
            case UP: return "up";
            case RIGHT: return "right";
            case DOWN: return "down";
            case LEFT: return "left";
        }
        return "";
    }
}
