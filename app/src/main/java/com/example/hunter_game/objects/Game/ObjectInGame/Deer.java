package com.example.hunter_game.objects.Game.ObjectInGame;

import com.example.hunter_game.objects.enums.Directions;


/**
 * MINER
 * The name remains deer but its represent MINER
 */
public class Deer extends ItemInGame {
    private Directions direction;
    private int cordinateX,
                cordinateY;

    public Deer(){
        setDirection(Directions.UP);
    };


}
