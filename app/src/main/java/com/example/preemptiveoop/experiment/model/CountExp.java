package com.example.preemptiveoop.experiment.model;

import android.location.Location;

import java.util.Date;

public class CountExp extends Experiment <Trial<Integer>> {
    public CountExp() {}
    public CountExp(String databaseId, String owner, Date creationDate, String description,
                       Location region, boolean requireLocation, int requiredNumOfTrial) {
        super(databaseId, Experiment.TYPE_COUNT, owner, creationDate, description, region, requireLocation, requiredNumOfTrial);
    }

    @Override
    public void addTrial(Trial<Integer> trial) {
        if (trial.getResult() != 1)
            throw new IllegalArgumentException("Trial for CountExp can only have a result of 1.");
        super.addTrial(trial);
    }
}