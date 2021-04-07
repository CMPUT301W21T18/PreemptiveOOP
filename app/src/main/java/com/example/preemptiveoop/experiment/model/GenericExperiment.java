package com.example.preemptiveoop.experiment.model;

import com.example.preemptiveoop.trial.model.GenericTrial;

/**
 * The GenericExperiment class is a Firestore compatible class used as a intermedium for reading/writing
 * experiment data to Firestore. It contains conversion method for other specific experiment types.
 */
public class GenericExperiment extends Experiment<GenericTrial> {
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

        for (GenericTrial trial : super.getTrials())
            exp.addTrial(trial.toBinomialTrial());
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

        for (GenericTrial trial : super.getTrials())
            exp.addTrial(trial.toCountTrial());
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

        for (GenericTrial trial : super.getTrials())
            exp.addTrial(trial.toMeasurementTrial());
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

        for (GenericTrial trial : super.getTrials())
            exp.addTrial(trial.toNonNegativeTrial());
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
