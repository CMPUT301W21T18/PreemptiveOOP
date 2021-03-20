package com.example.preemptiveoop.trial.model;

import android.location.Location;

import java.util.Date;

public class Trial <T> {
    private String creator;
    private Date creationDate;
    private Location location;

    private T result;
    private boolean isIgnored;

    public Trial() {}
    public Trial(String creator, Date creationDate, Location location, T result) {
        this.creator = creator;
        this.creationDate = creationDate;
        this.location = location;

        this.result = result;
        this.isIgnored = false;
    }

    // getters
    public String getCreator() { return creator; }
    public Date getCreationDate() { return creationDate; }
    public Location getLocation() { return location; }

    public T getResult() { return result; }
    public boolean isIgnored() { return isIgnored; }

    // setters
    public void setResult(T result) { this.result = result; }
}
