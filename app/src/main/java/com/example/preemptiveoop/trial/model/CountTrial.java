package com.example.preemptiveoop.trial.model;

import android.location.Location;

import java.util.Date;

/**
 * The CountTrial class is used to store and manage a count trial result. The result is stored
 * as a Integer.
 */
public class CountTrial extends GenericTrial {
    Integer result;

    public CountTrial() {}
    public CountTrial(String creator, Date creationDate, Location location, Integer result, boolean isIgnored) {
        super(creator, creationDate, location, result.toString(), isIgnored);
        this.result = result;
    }

    public Integer getResult() { return result; }

    public void setResult(Integer result) {
        super.setResultStr(result.toString());
        this.result = result;
    }
}
