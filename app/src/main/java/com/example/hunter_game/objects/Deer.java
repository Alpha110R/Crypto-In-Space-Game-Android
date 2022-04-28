package com.example.hunter_game.objects;

import com.example.hunter_game.objects.enums.Directions;

public class Deer extends ItemInGame{
    private Directions direction;
    private int cordinateX,
                cordinateY;

    public Deer(){
        setDirection(Directions.UP);
    };


}
