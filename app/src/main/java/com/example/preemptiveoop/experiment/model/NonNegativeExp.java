package com.example.preemptiveoop.experiment.model;

import android.location.Location;

import com.example.preemptiveoop.trial.model.NonNegativeTrial;

import java.util.Date;

/**
 * The NonNegativeExp class is used to store and manage a non-negative experiment result. The trial
 * to be added to this class is of type NonNegativeTrial. And the trial result must be >= 0.
 */
public class NonNegativeExp extends Experiment <NonNegativeTrial> {
    public NonNegativeExp() {}
    public NonNegativeExp(String databaseId, String owner, Date creationDate, String description,
                    Location region, boolean requireLocation, int requiredNumOfTrial) {
        super(databaseId, Experiment.TYPE_NON_NEGATIVE, owner, creationDate, description, region, requireLocation, requiredNumOfTrial);
    }

    @Override
    public void addTrial(NonNegativeTrial trial) {
        if (trial.getResult().intValue() < 0)
            throw new IllegalArgumentException("trial for NonNegativeExp must have an integer result >= 0.");
        super.addTrial(trial);
    }
}
