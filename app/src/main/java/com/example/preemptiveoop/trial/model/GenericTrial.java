package com.example.preemptiveoop.trial.model;

import android.location.Location;

import com.example.preemptiveoop.uiwidget.model.MyLocation;

import java.io.Serializable;
import java.util.Date;

/**
 * The GenericTrial class is a Firestore compatible class used as a intermedium for reading/writing
 * trial data to Firestore. It contains conversion methods for other specific trial types.
 */
public class GenericTrial implements Serializable {
    private String creator;
    private Date creationDate;
    private MyLocation location;

    private String resultStr;
    private boolean isIgnored;

    public GenericTrial() {}
    public GenericTrial(String creator, Date creationDate, MyLocation location, String resultStr, boolean isIgnored) {
        this.creator = creator;
        this.creationDate = creationDate;
        this.location = location;

        this.resultStr = resultStr;
        this.isIgnored = isIgnored;
    }

    public BinomialTrial toBinomialTrial() {
        return new BinomialTrial(creator, creationDate, location, Integer.parseInt(resultStr), isIgnored);
    }
    public CountTrial toCountTrial() {
        return new CountTrial(creator, creationDate, location, Integer.parseInt(resultStr), isIgnored);
    }
    public MeasurementTrial toMeasurementTrial() {
        return new MeasurementTrial(creator, creationDate, location, Double.parseDouble(resultStr), isIgnored);
    }
    public NonNegativeTrial toNonNegativeTrial() {
        return new NonNegativeTrial(creator, creationDate, location, Integer.parseInt(resultStr), isIgnored);
    }

    /**
     * Get the result contained by this trial. This method is meant to be overridden in subclasses.
     * @return The result.
     */
    public Number getResult() {
        throw new IllegalStateException("GenericTrial getResultNum() is not meant to be called.");
    }
    /**
     * Set the result contained by this trial. This method is meant to be overridden in subclasses.
     * @param result The result.
     */
    public void setResult_(Number result) {     // extra "_" at the end to avoid Firestore messing with this method
        throw new IllegalStateException("GenericTrial setResultNum() is not meant to be called.");
    }

    // getters
    public String getCreator() { return creator; }
    public Date getCreationDate() { return creationDate; }
    public MyLocation getLocation() { return location; }

    public String getResultStr() { return resultStr; }
    public boolean isIgnored() { return isIgnored; }

    // setters
    public void setResultStr(String resultStr) { this.resultStr = resultStr; }
    public void setIgnored(boolean ignored) { isIgnored = ignored; }
}
