package com.example.preemptiveoop.experiment.model;

import com.example.preemptiveoop.trial.model.Trial;

import java.util.ArrayList;

// firestore compatible class for reading from db
public class GenericExperiment extends Experiment<Trial<Number>> {
    public BinomialExp toBinomialExp() {
        BinomialExp exp =  new BinomialExp(
                super.getDatabaseId(),
                super.getOwner(),
                super.getCreationDate(),
                super.getDescription(),
                super.getRegion(),
                super.isRequireLocation(),
                super.getMinNumOfTrials()
        );

        for (Trial t : super.getTrials())
            exp.addTrial(new Trial<Integer>(t.getCreator(), t.getCreationDate(), t.getLocation(), t.getResult().intValue()));
        return exp;
    }

    public CountExp toCountExp() {
        CountExp exp =  new CountExp(
                super.getDatabaseId(),
                super.getOwner(),
                super.getCreationDate(),
                super.getDescription(),
                super.getRegion(),
                super.isRequireLocation(),
                super.getMinNumOfTrials()
        );

        for (Trial t : super.getTrials())
            exp.addTrial(new Trial<Integer>(t.getCreator(), t.getCreationDate(), t.getLocation(), t.getResult().intValue()));
            return exp;
    }

    public MeasurementExp toMeasurementExp() {
        MeasurementExp exp =  new MeasurementExp(
                super.getDatabaseId(),
                super.getOwner(),
                super.getCreationDate(),
                super.getDescription(),
                super.getRegion(),
                super.isRequireLocation(),
                super.getMinNumOfTrials()
        );

        for (Trial t : super.getTrials())
            exp.addTrial(new Trial<Double>(t.getCreator(), t.getCreationDate(), t.getLocation(), t.getResult().doubleValue()));
        return exp;
    }

    public NonNegativeExp toNonNegativeExp() {
        NonNegativeExp exp =  new NonNegativeExp(
                super.getDatabaseId(),
                super.getOwner(),
                super.getCreationDate(),
                super.getDescription(),
                super.getRegion(),
                super.isRequireLocation(),
                super.getMinNumOfTrials()
        );

        for (Trial t : super.getTrials())
            exp.addTrial(new Trial<Integer>(t.getCreator(), t.getCreationDate(), t.getLocation(), t.getResult().intValue()));
        return exp;
    }
}