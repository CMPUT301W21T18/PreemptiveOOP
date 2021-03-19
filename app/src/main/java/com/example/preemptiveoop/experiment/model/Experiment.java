package com.example.preemptiveoop.experiment.model;

import android.location.Location;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Experiment <T extends Trial> {
    public static final String TYPE_BINOMIAL = "Binomial";
    public static final String TYPE_NON_NEGATIVE = "NonNegative";
    public static final String TYPE_COUNT = "Count";
    public static final String TYPE_MEASUREMENT = "Measurement";

    private String databaseId;
    private String type;

    private String owner;
    private Date creationDate;

    private String description;
    private ArrayList<String> keywords;

    private Location region;
    private boolean requireLocation;

    private ArrayList<String> experimenters;
    private ArrayList<T> trials;
    private int minNumOfTrials;

    public Experiment() {}
    public Experiment(String databaseId, String type, String owner, Date creationDate, String description,
                      Location region, boolean requireLocation, int requiredNumOfTrial) {
        this.databaseId = databaseId;
        this.type = type;

        this.owner = owner;
        this.creationDate = creationDate;

        this.description = description;
        this.keywords = new ArrayList<>(Arrays.asList(description.toLowerCase().split(" ")));

        this.region = region;
        this.requireLocation = requireLocation;

        this.experimenters = new ArrayList<>();
        this.trials = new ArrayList<>();

        this.minNumOfTrials = requiredNumOfTrial;
    }

    public void writeToDatabase() {
        CollectionReference expCol = FirebaseFirestore.getInstance().collection("Experiments");

        if (databaseId == null)     // if there is no databaseId, create one
            databaseId = expCol.document().getId();
        expCol.document(databaseId).set(this);
    }

    public void addTrial(T trial) {
        trials.add(trial);
    }

    public String getDatabaseId() { return databaseId; }
    public String getType() { return type; }

    public String getOwner() { return owner; }
    public Date getCreationDate() { return creationDate; }

    public String getDescription() { return description; }
    public ArrayList<String> getKeywords() { return keywords; }

    public Location getRegion() { return region; }
    public boolean isRequireLocation() { return requireLocation; }

    public ArrayList<String> getExperimenters() { return experimenters; }
    public ArrayList<T> getTrials() { return trials; }

    public int getMinNumOfTrials() { return minNumOfTrials; }
}