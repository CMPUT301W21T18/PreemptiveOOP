package com.example.preemptiveoop.trial.model;

public class GenericTrial extends Trial<Number> {
    public GenericTrial() { super(); }
    public GenericTrial(Trial t) {
        super(t.getCreator(), t.getCreationDate(), t.getLocation(), t.getResult());
    }
}
