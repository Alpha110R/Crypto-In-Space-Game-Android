package com.example.hunter_game;

public class Deer {
    private Directions direction;
    private int cordinateX,
                cordinateY;

    public Deer(){
        setDirection(Directions.UP);
    };

    public int getCordinateX() {
        return cordinateX;
    }

    public Deer setCordinateX(int cordinateX) {
        this.cordinateX = cordinateX;
        return this;
    }

    public int getCordinateY() {
        return cordinateY;
    }

    public Deer setCordinateY(int cordinateY) {
        this.cordinateY = cordinateY;
        return this;
    }

    public Directions getDirection() {
        return direction;
    }

    public Deer setDirection(Directions direction) {
        this.direction = direction;
        return this;
    }

}
