package com.example.preemptiveoop.experiment.model;


import com.example.preemptiveoop.trial.model.GenericTrial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * The StatCalculator is used to calculate the statistics given an experiment.
 */
public class StatCalculator {
    
    public static ArrayList<GenericTrial> filterIgnoredTrials(ArrayList<GenericTrial> trials) {
        ArrayList<GenericTrial> filteredTrials = (ArrayList<GenericTrial>) trials.clone();

        for (int i = 0; i >= 0 && i < filteredTrials.size(); ++i) {
            if (filteredTrials.get(i).isIgnored())
                filteredTrials.remove(i--);
        }
        return filteredTrials;
    }

    public static ArrayList<GenericTrial> sortTrialsByResult(ArrayList<GenericTrial> trials) {
        ArrayList<GenericTrial> sortedTrials = (ArrayList<GenericTrial>) trials.clone();

        Collections.sort(sortedTrials, new Comparator<GenericTrial>() {
            @Override
            public int compare(GenericTrial o1, GenericTrial o2) {
                Double r1 = o1.getResultNum().doubleValue();
                Double r2 = o2.getResultNum().doubleValue();
                return r1.compareTo(r2);
            }
        });
        return sortedTrials;
    }

    private static double calcMedian(ArrayList<GenericTrial> sortedTrials) {
        int size = sortedTrials.size();
        if (size == 0)
            return Double.NaN;

        double m = sortedTrials.get(size / 2).getResultNum().doubleValue();

        if (size % 2 == 0)
            m = (m + sortedTrials.get(size / 2 - 1).getResultNum().doubleValue()) / 2;
        return m;
    }

    public static double calcQuartile(ArrayList<GenericTrial> trials, int quartNum) {
        ArrayList<GenericTrial> sortedTrials = sortTrialsByResult(trials);
        int size = sortedTrials.size();

        switch (quartNum) {
            case 0: return sortedTrials.get(0).getResultNum().doubleValue();
            case 1: return calcMedian(new ArrayList<>(sortedTrials.subList(0, size / 2)));
            case 2: return calcMedian(sortedTrials);
            case 3:
                if (size % 2 == 0)  return calcMedian(new ArrayList<>(sortedTrials.subList(size / 2, size)));
                else                return calcMedian(new ArrayList<>(sortedTrials.subList(size / 2 + 1, size)));
            case 4: return sortedTrials.get(size - 1).getResultNum().doubleValue();
            default:
                throw new IllegalArgumentException("quartNum can only take the integer values of 0-4.");
        }
    }

    public static double calcMean(ArrayList<GenericTrial> trials) {
        double sum = 0;
        for (GenericTrial t : trials)
            sum += t.getResultNum().doubleValue();
        return sum / trials.size();
    }

    public static double calcStdev(ArrayList<GenericTrial> trials) {
        double mean = calcMean(trials);
        double sum = 0;

        for (GenericTrial t : trials) {
            double buffer = t.getResultNum().doubleValue() - mean;
            sum += buffer * buffer;
        }
        sum /= trials.size();
        return Math.sqrt(sum);
    }
}
