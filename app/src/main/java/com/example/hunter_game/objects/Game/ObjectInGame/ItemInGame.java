package com.example.hunter_game.objects.Game.ObjectInGame;

import com.example.hunter_game.objects.enums.Directions;

public abstract class ItemInGame {
    private Directions direction;
    private int coordinateX,
                coordinateY;
    public int getCoordinateX() {
        return coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }

    public Directions getDirection() {
        return direction;
    }

    public ItemInGame setCoordinateX(int coordinateX) {
        this.coordinateX = coordinateX;
        return this;
    }

    public ItemInGame setCoordinateY(int coordinateY) {
        this.coordinateY = coordinateY;
        return this;
    }

    public ItemInGame setDirection(Directions direction) {
        this.direction = direction;
        return this;
    }


}
