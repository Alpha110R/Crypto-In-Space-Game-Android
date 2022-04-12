package com.example.hunter_game;

public class Hunter {
    private Directions direction;
    private int cordinateX,
                cordinateY;
    public Hunter(){
        setDirection(Directions.DOWN);
    }

    public int getCordinateX() {
        return cordinateX;
    }

    public Hunter setCordinateX(int cordinateX) {
        this.cordinateX = cordinateX;
        return this;
    }

    public int getCordinateY() {
        return cordinateY;
    }

    public Hunter setCordinateY(int cordinateY) {
        this.cordinateY = cordinateY;
        return this;
    }
    public Directions getDirection() {
        return direction;
    }

    public Hunter setDirection(Directions direction) {
        this.direction = direction;
        return this;
    }
}
