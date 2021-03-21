package com.example.preemptiveoop.trial.model;


import java.util.Comparator;

public class TrialComparator implements Comparator<GenericTrial> {

    @Override
    public int compare(GenericTrial o1, GenericTrial o2) {

        if(o1.getCreationDate().compareTo(o2.getCreationDate()) <0){
            return -1;
        }else if(o1.getCreationDate().compareTo(o2.getCreationDate())>0){
            return 1;
        }else{
            return 0;
        }

    }
}