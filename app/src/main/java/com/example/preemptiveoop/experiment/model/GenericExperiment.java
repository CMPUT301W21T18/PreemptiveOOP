package com.example.preemptiveoop.experiment.model;

import com.example.preemptiveoop.trial.model.Trial;

import java.util.ArrayList;

// firestore compatible class for reading from db
public class GenericExperiment extends Experiment<Trial<Number>> {
    public GenericExperiment() { super(); }
    public GenericExperiment(Experiment exp) {
        super(exp.getDatabaseId(), exp.getType(), exp.getOwner(), exp.getCreationDate(),
                exp.getDescription(),
                exp.getRegion(),
                exp.isRequireLocation(),
                exp.getMinNumOfTrials()
        );
    }

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
        exp.setStatus(super.getStatus());
        exp.getExperimenters().addAll(super.getExperimenters());

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
        exp.setStatus(super.getStatus());
        exp.getExperimenters().addAll(super.getExperimenters());

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
        exp.setStatus(super.getStatus());
        exp.getExperimenters().addAll(super.getExperimenters());

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
        exp.setStatus(super.getStatus());
        exp.getExperimenters().addAll(super.getExperimenters());

        for (Trial t : super.getTrials())
            exp.addTrial(new Trial<Integer>(t.getCreator(), t.getCreationDate(), t.getLocation(), t.getResult().intValue()));
        return exp;
    }

    public Experiment toCorrespondingExp() {
        if (super.getType().equals(Experiment.TYPE_BINOMIAL))
            return toBinomialExp();

        if (super.getType().equals(Experiment.TYPE_COUNT))
            return toCountExp();

        if (super.getType().equals(Experiment.TYPE_MEASUREMENT))
            return toMeasurementExp();

        if (super.getType().equals(Experiment.TYPE_NON_NEGATIVE))
            return toNonNegativeExp();
        return null;
    }
}