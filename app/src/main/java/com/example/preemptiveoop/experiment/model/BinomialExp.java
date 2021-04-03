package com.example.preemptiveoop.experiment.model;

import com.example.preemptiveoop.trial.model.BinomialTrial;
import com.example.preemptiveoop.uiwidget.model.MyLocation;

import java.util.Date;

/**
 * The BinomialExp class is used to store and manage a binomial experiment result. The trial to be
 * added to this class is of type BinomialTrial. And the trial result must be either 1 or 0.
 */
public class BinomialExp extends Experiment <BinomialTrial> {
    public BinomialExp() {}
    public BinomialExp(String databaseId, String owner, Date creationDate, String description,
                       MyLocation region, boolean requireLocation, int requiredNumOfTrial) {
        super(databaseId, Experiment.TYPE_BINOMIAL, owner, creationDate, description, region, requireLocation, requiredNumOfTrial);
    }

    @Override
    public void addTrial(BinomialTrial trial) {
        if (trial.getResult().intValue() != 0 && trial.getResult().intValue() != 1)
            throw new IllegalArgumentException("trial for BinomialExp must have an integer result of 1 or 0.");
        super.addTrial(trial);
    }
}
