package com.example.hunter_game.objects.TopTen;

import com.example.hunter_game.utils.MySignal;
import com.google.android.gms.maps.model.LatLng;

public class User {
    private String name,
                   date;
    private int score;
    private MyLocation location;

    public User (){}

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public MyLocation getLocation() {
        return location;
    }

    public User setLocation(MyLocation location) {
        this.location = location;
        return this;
    }

    public String getDate() {
        return date;
    }

    public User setDate(String date) {
        this.date = date;
        return this;
    }

    public int getScore() {
        return score;
    }

    public User setScore(int score) {
        this.score = score;
        return this;
    }

    @Override
    public String toString() {
        return
                "Player name: " + name +
                "      SCORE: " + score +
                "\nDate: " + date;
    }
}
