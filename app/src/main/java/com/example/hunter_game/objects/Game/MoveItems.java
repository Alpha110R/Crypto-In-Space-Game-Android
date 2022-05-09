package com.example.hunter_game.objects.Game;

import com.example.hunter_game.objects.enums.Directions;

import java.util.Random;

public class MoveItems {
    private ItemInGame miner, cop;
    private int rows, columns;

    public MoveItems() {
    }

    public MoveItems setMiner(ItemInGame miner) {
        this.miner = miner;
        return this;
    }

    public MoveItems setCop(ItemInGame cop) {
        this.cop = cop;
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

    public void moveDeer(ItemInGame item) {
        if (checkBoundaries(item.getCoordinateX(), item.getCoordinateY(), item.getDirection())) {
            moveIndexByDirection(item, item.getCoordinateX(), item.getCoordinateY(), item.getDirection());
        }
    }

    public void moveHunter(ItemInGame item) {
        int random = new Random().nextInt((4));//Random for the hunter movements
        item.setDirection(Directions.values()[random]);
        if (checkBoundaries(item.getCoordinateX(), item.getCoordinateY(), item.getDirection())) {
            moveIndexByDirection(item, item.getCoordinateX(), item.getCoordinateY(), item.getDirection());
        } else {
            if (item.getCoordinateY() == (rows - 1) && item.getDirection() == Directions.DOWN) {
                item.setCoordinateY(0);
                item.setCoordinateX(columns / 2);
            }
        }
    }

    public void moveCoin(ItemInGame item) {
        int randomX;
        int randomY;
        do {
            randomX = new Random().nextInt((5));
            randomY = new Random().nextInt((7));
        } while (checkCollisionCoinBeforeMove(randomX, randomY));
        item.setCoordinateX(randomX);
        item.setCoordinateY(randomY);
    }

    public Boolean checkCollisionCoinBeforeMove(int x, int y) {
        if ((x == miner.getCoordinateX() && y == miner.getCoordinateY()) ||
                (x == cop.getCoordinateX() && y == cop.getCoordinateY()))
            return true;
        return false;
    }

    public boolean checkBoundaries(int x, int y, Directions direction) {
        switch (direction) {
            case UP:
                if (y == 0)
                    return false;
                break;
            case RIGHT:
                if (x == (columns - 1))
                    return false;
                break;
            case DOWN:
                if (y == (rows - 1))
                    return false;
                break;
            case LEFT:
                if (x == 0)
                    return false;
                break;
        }
        return true;
    }

    public void moveIndexByDirection(ItemInGame obj, int x, int y, Directions direction) {
        switch (direction) {
            case UP:
                obj.setCoordinateY(--y);
                break;
            case RIGHT:
                obj.setCoordinateX(++x);
                break;
            case DOWN:
                obj.setCoordinateY(++y);
                break;
            case LEFT:
                obj.setCoordinateX(--x);
                break;
        }

    }
}

