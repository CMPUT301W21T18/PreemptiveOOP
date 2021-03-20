package com.example.preemptiveoop.trial.model;

import android.location.Location;

import java.util.Date;

public class GenericTrial {
    private String creator;
    private Date creationDate;
    private Location location;

    private String resultStr;
    private boolean isIgnored;

    public GenericTrial() {}
    public GenericTrial(String creator, Date creationDate, Location location, String resultStr) {
        this.creator = creator;
        this.creationDate = creationDate;
        this.location = location;

        this.resultStr = resultStr;
        this.isIgnored = false;
    }

    public BinomialTrial toBinomialTrial() {
        return new BinomialTrial(creator, creationDate, location, Integer.parseInt(resultStr));
    }
    public CountTrial toCountTrial() {
        return new CountTrial(creator, creationDate, location, Integer.parseInt(resultStr));
    }
    public MeasurementTrial toMeasurementTrial() {
        return new MeasurementTrial(creator, creationDate, location, Double.parseDouble(resultStr));
    }
    public NonNegativeTrial toNonNegativeTrial() {
        return new NonNegativeTrial(creator, creationDate, location, Integer.parseInt(resultStr));
    }

    // getters
    public String getCreator() { return creator; }
    public Date getCreationDate() { return creationDate; }
    public Location getLocation() { return location; }

    public String getResultStr() { return resultStr; }
    public boolean isIgnored() { return isIgnored; }

    // setters
    public void setResultStr(String resultStr) { this.resultStr = resultStr; }
}
