package com.example.preemptiveoop.uiwidget.model;

import java.io.Serializable;

/**
 * The MyLocation class is a Firestore compatible class used to store a location.
 */
public class MyLocation implements Serializable {
    double latitude, longitude;

    public MyLocation() {}
    public MyLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return String.format("lat = %f, lon = %f", latitude, longitude);
    }

    // getters
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }

    // setters
    public void setLatitude(double latitude) { this.latitude = latitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
}
