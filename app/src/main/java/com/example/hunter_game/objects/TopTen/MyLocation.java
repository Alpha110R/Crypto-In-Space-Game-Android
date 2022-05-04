package com.example.hunter_game.objects.TopTen;

public class MyLocation {
    private double latitude;
    private double longitude;

    public MyLocation() {}

    public MyLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public MyLocation setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public double getLongitude() {
        return longitude;
    }

    public MyLocation setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }
}
