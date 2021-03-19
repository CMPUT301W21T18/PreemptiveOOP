package com.example.preemptiveoop.experiment.model;

import android.location.Location;

import java.util.Date;

public class MeasurementExp extends Experiment <Trial<Double>> {
    public MeasurementExp() {}
    public MeasurementExp(String databaseId, String owner, Date creationDate, String description,
                    Location region, boolean requireLocation, int requiredNumOfTrial) {
        super(databaseId, Experiment.TYPE_MEASUREMENT, owner, creationDate, description, region, requireLocation, requiredNumOfTrial);
    }
}