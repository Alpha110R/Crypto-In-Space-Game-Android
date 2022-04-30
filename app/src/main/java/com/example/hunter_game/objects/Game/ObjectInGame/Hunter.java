package com.example.hunter_game.objects.Game.ObjectInGame;

import com.example.hunter_game.objects.enums.Directions;

public class Hunter extends ItemInGame {
    private Directions direction;
    private int cordinateX,
                cordinateY;

    public Hunter(){
        setDirection(Directions.DOWN);
    }

}
