package com.example.preemptiveoop.trial.model;

public class GenericTrial extends Trial<Number> {
    public GenericTrial() { super(); }
    public GenericTrial(Trial t) {
        super(t.getCreator(), t.getCreationDate(), t.getLocation(), t.getResult());
    }

    public CountTrial toCountTrial() {
        return new CountTrial(creator, creationDate, location, Integer.parseInt(resultStr));
    }
    public MeasurementTrial toMeasurementTrial() {
        return new MeasurementTrial(creator, creationDate, location, Double.parseDouble(resultStr));
    }
    public NonNegativeTrial toNonNegativeTrial() {
        return new NonNegativeTrial(creator, creationDate, location, Integer.parseInt(resultStr));
    }

    // getters
    public String getCreator() { return creator; }
    public Date getCreationDate() { return creationDate; }
    public Location getLocation() { return location; }

    public String getResultStr() { return resultStr; }
    public boolean isIgnored() { return isIgnored; }

    // setters
    public void setResultStr(String resultStr) { this.resultStr = resultStr; }
    public void setCreationDate(Date d){this.creationDate = d;};

}
