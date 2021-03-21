package com.example.preemptiveoop.experiment.model;

import android.location.Location;

import com.example.preemptiveoop.trial.model.MeasurementTrial;

import java.util.Date;

public class MeasurementExp extends Experiment <MeasurementTrial> {
    public MeasurementExp() {}
    public MeasurementExp(String databaseId, String owner, Date creationDate, String description,
                    Location region, boolean requireLocation, int requiredNumOfTrial) {
        super(databaseId, Experiment.TYPE_MEASUREMENT, owner, creationDate, description, region, requireLocation, requiredNumOfTrial);
    }
}
