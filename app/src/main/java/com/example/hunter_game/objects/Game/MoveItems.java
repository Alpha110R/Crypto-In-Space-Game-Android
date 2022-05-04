package com.example.hunter_game.objects.Game;

import com.example.hunter_game.objects.Game.ObjectInGame.Deer;
import com.example.hunter_game.objects.Game.ObjectInGame.ItemInGame;
import com.example.hunter_game.objects.enums.Directions;

import java.util.Random;

public class MoveItems {
    private ItemInGame hunter, deer, coin;
    private int rows, columns;

    public MoveItems(){}

    public MoveItems setHunter(ItemInGame hunter) {
        this.hunter = hunter;
        return this;
    }

    public MoveItems setDeer(ItemInGame deer) {
        this.deer = deer;
        return this;
    }

    public MoveItems setCoin(ItemInGame coin) {
        this.coin = coin;
        return this;
    }

    public MoveItems setRows(int rows) {
        this.rows = rows;
        return this;
    }

    public MoveItems setColumns(int columns) {
        this.columns = columns;
        return this;
    }

    public void moveDeer(){
        if(checkBounderies(deer.getCordinateX(), deer.getCordinateY(),deer.getDirection())){
            moveIndexByDirection(deer, deer.getCordinateX(),deer.getCordinateY(),deer.getDirection());
        }
    }
    public void moveHunter(){
        int random = new Random().nextInt((4));//Random for the hunter movements
        hunter.setDirection(Directions.values()[random]);
        if(checkBounderies(hunter.getCordinateX(), hunter.getCordinateY(),hunter.getDirection())) {
            moveIndexByDirection(hunter, hunter.getCordinateX(), hunter.getCordinateY(), hunter.getDirection());
        }
        else{
            if(hunter.getCordinateY() == (rows-1) && hunter.getDirection() == Directions.DOWN) {
                hunter.setCordinateY(0);
                hunter.setCordinateX(columns / 2);
            }
        }
    }

    public void moveCoin(){
        int randomX;
        int randomY;
        do{
            randomX = new Random().nextInt((5));
            randomY = new Random().nextInt((7));
        }while (checkCollisionCoinBeforeMove(randomX, randomY));
        coin.setCordinateX(randomX);
        coin.setCordinateY(randomY);
    }

    public Boolean checkCollisionCoinBeforeMove(int x, int y){
        if((x == deer.getCordinateX() && y == deer.getCordinateY()) ||
                (x == hunter.getCordinateX() && y == hunter.getCordinateY()))
            return true;
        return false;
    }

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

    public void moveIndexByDirection(Object obj, int x, int y, Directions direction){
        if(obj instanceof Deer)
            moveIndexByDirectionDeer(x,y,direction);
        else
            moveIndexByDirectionHunter(x, y, direction);
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

    public void moveIndexByDirectionHunter(int x, int y, Directions direction){
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
}
