package com.example.preemptiveoop.experiment.model;

import android.location.Location;

import com.example.preemptiveoop.trial.model.Trial;

import java.util.Date;

public class BinomialExp extends Experiment <Trial<Integer>> {
    public BinomialExp() {}
    public BinomialExp(String databaseId, String owner, Date creationDate, String description,
                       Location region, boolean requireLocation, int requiredNumOfTrial) {
        super(databaseId, Experiment.TYPE_BINOMIAL, owner, creationDate, description, region, requireLocation, requiredNumOfTrial);
    }

    @Override
    public void addTrial(Trial<Integer> trial) {
        if (trial.getResult() != 0 && trial.getResult() != 1)
            throw new IllegalArgumentException("Trial for BinomialExp can only have a result of 1 or 0.");
        super.addTrial(trial);
    }
}