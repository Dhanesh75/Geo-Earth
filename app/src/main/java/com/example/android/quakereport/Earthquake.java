package com.example.android.quakereport;

import android.support.annotation.NonNull;

public class Earthquake{

    @Override
    public String toString() {
        return "[ Lat=" + latitude+" Long= "+longitude +"]";
    }
    private double Magnitude;
    private String Location;
    private long TimeInMilliseconds;
    private String Url;
    private double latitude;
    private double longitude;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Earthquake(double magnitude, String location, long timeInMilliseconds, String url,double lat,double lon) {
        Magnitude = magnitude;
        Location = location;
        TimeInMilliseconds = timeInMilliseconds;
        Url = url;
        latitude = lat;
        longitude = lon;
    }
    public Earthquake(double magnitude, String location, long timeInMilliseconds, String url) {
        Magnitude = magnitude;
        Location = location;
        TimeInMilliseconds = timeInMilliseconds;
        Url = url;
    }
    public double getMagnitude() {
        return Magnitude;
    }

    public String getLocation() {
        return Location;
    }
    public long getTimeInMilliseconds() {
        return TimeInMilliseconds;
    }
    public String getUrl() {
        return Url;
    }


}
