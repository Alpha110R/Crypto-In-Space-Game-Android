package com.example.hunter_game.objects.Game.ObjectInGame;

import com.example.hunter_game.objects.enums.Directions;

/**
 * COP
 * The Class name remains Hunter but its represent the cop
 */
public class Hunter extends ItemInGame {
    private Directions direction;
    private int cordinateX,
                cordinateY;

    public Hunter(){
        setDirection(Directions.DOWN);
    }

}
