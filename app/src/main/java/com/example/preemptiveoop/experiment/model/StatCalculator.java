package com.example.preemptiveoop.experiment.model;


import com.example.preemptiveoop.trial.model.GenericTrial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * The StatCalculator is used to calculate the statistics given an experiment.
 */
public class StatCalculator {
    /**
     * Filter the given GenericTrial array to remove ignored trials from it.
     * @param trials The trial array to filter.
     * @return The filtered array.
     */
    public static ArrayList<GenericTrial> filterIgnoredTrials(ArrayList<GenericTrial> trials) {
        ArrayList<GenericTrial> filteredTrials = (ArrayList<GenericTrial>) trials.clone();

        for (int i = 0; i >= 0 && i < filteredTrials.size(); ++i) {
            if (filteredTrials.get(i).isIgnored())
                filteredTrials.remove(i--);
        }
        return filteredTrials;
    }

    /**
     * Sort the given GenericTrial array based on each GenericTrial's trial result.
     * @param trials The trial array to sort.
     * @return The sorted trial array.
     */
    public static ArrayList<GenericTrial> sortTrialsByResult(ArrayList<GenericTrial> trials) {
        ArrayList<GenericTrial> sortedTrials = (ArrayList<GenericTrial>) trials.clone();

        Collections.sort(sortedTrials, new Comparator<GenericTrial>() {
            @Override
            public int compare(GenericTrial o1, GenericTrial o2) {
                Double r1 = o1.getResult().doubleValue();
                Double r2 = o2.getResult().doubleValue();
                return r1.compareTo(r2);
            }
        });
        return sortedTrials;
    }

    private static double calcMedian(ArrayList<GenericTrial> sortedTrials) {
        int size = sortedTrials.size();
        if (size == 0)
            return Double.NaN;

        double m = sortedTrials.get(size / 2).getResult().doubleValue();

        if (size % 2 == 0)
            m = (m + sortedTrials.get(size / 2 - 1).getResult().doubleValue()) / 2;
        return m;
    }

    /**
     * Calculate the quartile given a GenericTrial array and a quartile number.
     * @param trials The GenericTrial array.
     * @param quartNum The quartile number.
     * @return The calculated quartile.
     */
    public static double calcQuartile(ArrayList<GenericTrial> trials, int quartNum) {
        ArrayList<GenericTrial> sortedTrials = sortTrialsByResult(trials);
        int size = sortedTrials.size();

        switch (quartNum) {
            case 0: return sortedTrials.get(0).getResult().doubleValue();
            case 1: return calcMedian(new ArrayList<>(sortedTrials.subList(0, size / 2)));
            case 2: return calcMedian(sortedTrials);
            case 3:
                if (size % 2 == 0)  return calcMedian(new ArrayList<>(sortedTrials.subList(size / 2, size)));
                else                return calcMedian(new ArrayList<>(sortedTrials.subList(size / 2 + 1, size)));
            case 4: return sortedTrials.get(size - 1).getResult().doubleValue();
            default:
                throw new IllegalArgumentException("quartNum can only take the integer values of 0-4.");
        }
    }

    /**
     * Calculate the mean given a GenericTrial array.
     * @param trials The GenericTrial array.
     * @return The calculated mean.
     */
    public static double calcMean(ArrayList<GenericTrial> trials) {
        double sum = 0;
        for (GenericTrial t : trials)
            sum += t.getResult().doubleValue();
        return sum / trials.size();
    }

    /**
     * Calculate the standard deviation given a GenericTrial array.
     * @param trials The GenericTrial array.
     * @return The calculated standard deviation.
     */
    public static double calcStdev(ArrayList<GenericTrial> trials) {
        double mean = calcMean(trials);
        double sum = 0;

        for (GenericTrial t : trials) {
            double buffer = t.getResult().doubleValue() - mean;
            sum += buffer * buffer;
        }
        sum /= trials.size();
        return Math.sqrt(sum);
    }
}
