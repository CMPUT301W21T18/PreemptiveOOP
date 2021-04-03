package com.example.preemptiveoop.trial.model;

import com.example.preemptiveoop.uiwidget.model.MyLocation;

import java.util.Date;

/**
 * The MeasurementTrial class is used to store and manage a measurement trial result. The result is
 * stored as a Double.
 */
public class MeasurementTrial extends GenericTrial {
    Double result;

    public MeasurementTrial() {}
    public MeasurementTrial(String creator, Date creationDate, MyLocation location, Double resultNum, boolean isIgnored) {
        super(creator, creationDate, location, resultNum.toString(), isIgnored);
        this.result = resultNum;
    }

    @Override
    public Number getResult() { return result; }

    @Override
    public void setResult_(Number result) {
        if (!(result instanceof Double))
            throw new IllegalArgumentException("resultNum for MeasurementTrial must be Double.");

        super.setResultStr(result.toString());
        this.result = result.doubleValue();
    }
}
