package com.example.preemptiveoop.user.model;

import com.example.preemptiveoop.experiment.model.Experiment;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The GenericTrial class is a Firestore compatible class used directly to store and manage a user's
 * profile.
 */
public class User implements Serializable {
    private String bondDeviceId;

    private String username;    // databaseId
    private String contact;

    private ArrayList<String> ownedExpIdList;
    private ArrayList<String> partiExpIdList;

    public User() {}
    public User(String bondDeviceId, String username, String contact) {
        this.bondDeviceId = bondDeviceId;

        this.username = username;
        this.contact = contact;

        this.ownedExpIdList = new ArrayList<>();
        this.partiExpIdList = new ArrayList<>();
    }

    /**
     * Write all fields in this User class to the Firestore database. Existing fields are
     * updated accordingly.
     */
    public void writeToDatabase() {
        FirebaseFirestore.getInstance().collection("Users")
                .document(username).set(this, SetOptions.merge());
    }

    /**
     * Add an experiment to this user's owned-experiment list.
     * @param exp The experiment to add.
     */
    public void addToOwnedExp(Experiment exp) {
        ownedExpIdList.add(exp.getDatabaseId());
    }
    /**
     * Add an experiment to this user's participated-experiment list.
     * @param exp The experiment to add.
     */
    public void participateExp(Experiment exp) {
        partiExpIdList.add(exp.getDatabaseId());
    }

    // getters
    public String getBondDeviceId() { return bondDeviceId; }

    public String getUsername() { return username; }
    public String getContact() { return contact; }

    public ArrayList<String> getOwnedExpIdList() { return ownedExpIdList; }
    public ArrayList<String> getPartiExpIdList() { return partiExpIdList; }

    // setters
    public void setContact(String contact) { this.contact = contact; }
}
