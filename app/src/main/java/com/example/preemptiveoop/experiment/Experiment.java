package com.example.preemptiveoop.experiment;

import android.location.Location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Experiment <T extends Trial> {
    private String databaseId;

    private String owner;
    private Date creationDate;

    private String description;
    private ArrayList<String> keywords;

    private Location region;
    private boolean requireLocation;

    private ArrayList<String> experimenters;
    private ArrayList<T> trials;

    public Experiment() {}
    public Experiment(String databaseId, String owner, Date creationDate, String description, Location region, boolean requireLocation) {
        this.databaseId = databaseId;

        this.owner = owner;
        this.creationDate = creationDate;

        this.description = description;
        this.keywords = new ArrayList<>(Arrays.asList(description.toLowerCase().split(" ")));

        this.region = region;
        this.requireLocation = requireLocation;

        this.experimenters = new ArrayList<>();
        this.trials = new ArrayList<>();
    }

    public void addTrial(T trial) {
        trials.add(trial);
    }

    public String getDatabaseId() { return databaseId; }

    public String getOwner() { return owner; }
    public Date getCreationDate() { return creationDate; }

    public String getDescription() { return description; }
    public ArrayList<String> getKeywords() { return keywords; }

    public Location getRegion() { return region; }
    public boolean isRequireLocation() { return requireLocation; }

    public ArrayList<String> getExperimenters() { return experimenters; }
    public ArrayList<T> getTrials() { return trials; }
}
