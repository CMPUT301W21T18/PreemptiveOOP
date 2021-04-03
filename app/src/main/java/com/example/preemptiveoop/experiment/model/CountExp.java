package com.example.preemptiveoop.experiment.model;

import com.example.preemptiveoop.trial.model.CountTrial;
import com.example.preemptiveoop.uiwidget.model.MyLocation;

import java.util.Date;

/**
 * The CountExp class is used to store and manage a count experiment result. The trial to be added to
 * this class is of type CountTrial. And the trial result must always be 1.
 */
public class CountExp extends Experiment <CountTrial> {
    public CountExp() {}
    public CountExp(String databaseId, String owner, Date creationDate, String description,
                    MyLocation region, boolean requireLocation, int requiredNumOfTrial) {
        super(databaseId, Experiment.TYPE_COUNT, owner, creationDate, description, region, requireLocation, requiredNumOfTrial);
    }

    @Override
    public void addTrial(CountTrial trial) {
        if (trial.getResult().intValue() != 1)
            throw new IllegalArgumentException("trial for CountExp must have an integer result of 1.");
        super.addTrial(trial);
    }
}
