package com.example.preemptiveoop.trial.model;

import android.location.Location;

import java.util.Date;

/**
 * The BinomialTrial class is used to store and manage a binomial trial result. The result is stored
 * as a Integer.
 */
public class BinomialTrial extends GenericTrial {
    Integer result;

    public BinomialTrial() {}
    public BinomialTrial(String creator, Date creationDate, Location location, Integer result, boolean isIgnored) {
        super(creator, creationDate, location, result.toString(), isIgnored);
        this.result = result;
    }
    
    @Override
    public Number getResultNum() { return result; }

    public void setResult(Integer result) {
        super.setResultStr(result.toString());
        this.result = result;
    }
}
