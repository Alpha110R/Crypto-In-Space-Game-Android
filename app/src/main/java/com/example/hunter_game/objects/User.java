package com.example.hunter_game.objects;

import java.util.Date;

public class User {
    private String name,
                   location;
    private Date date;
    private int score;

    public User (){}

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public User setLocation(String location) {
        this.location = location;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public User setDate(Date date) {
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
}
