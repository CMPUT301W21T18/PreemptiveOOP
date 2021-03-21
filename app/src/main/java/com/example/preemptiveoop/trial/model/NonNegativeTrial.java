package com.example.preemptiveoop.trial.model;

import android.location.Location;

import java.util.Date;

/**
 * The NonNegativeTrial class is used to store and manage a non-negative trial result. The result is
 * stored as a Integer.
 */
public class NonNegativeTrial extends GenericTrial {
    Integer result;

    public NonNegativeTrial() {}
    public NonNegativeTrial(String creator, Date creationDate, Location location, Integer result) {
        super(creator, creationDate, location, result.toString());
        this.result = result;
    }

    public Integer getResult() { return result; }

    public void setResult(Integer result) {
        super.setResultStr(result.toString());
        this.result = result;
    }
}
