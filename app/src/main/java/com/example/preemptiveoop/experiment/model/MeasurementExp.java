package com.example.preemptiveoop.experiment.model;

import com.example.preemptiveoop.trial.model.MeasurementTrial;
import com.example.preemptiveoop.uiwidget.model.MyLocation;

import java.util.Date;

/**
 * The MeasurementExp class is used to store and manage a measurement experiment result. The trial
 * to be added to this class is of type MeasurementTrial.
 */
public class MeasurementExp extends Experiment <MeasurementTrial> {
    public MeasurementExp() {}
    public MeasurementExp(String databaseId, String owner, Date creationDate, String description,
                          MyLocation region, boolean requireLocation, int requiredNumOfTrial) {
        super(databaseId, Experiment.TYPE_MEASUREMENT, owner, creationDate, description, region, requireLocation, requiredNumOfTrial);
    }
}
