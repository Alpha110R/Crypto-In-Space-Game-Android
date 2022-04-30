package com.example.hunter_game.objects.Game.ObjectInGame;

import com.example.hunter_game.objects.enums.Directions;

public abstract class ItemInGame {
    private Directions direction;
    private int cordinateX,
                cordinateY;
    public int getCordinateX() {
        return cordinateX;
    }

    public int getCordinateY() {
        return cordinateY;
    }

    public Directions getDirection() {
        return direction;
    }

    public ItemInGame setCordinateX(int cordinateX) {
        this.cordinateX = cordinateX;
        return this;
    }

    public ItemInGame setCordinateY(int cordinateY) {
        this.cordinateY = cordinateY;
        return this;
    }

    public ItemInGame setDirection(Directions direction) {
        this.direction = direction;
        return this;
    }


}
