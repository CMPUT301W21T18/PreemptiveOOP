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
    private String username;    // databaseId
    private String password;
    private String contact;

    private ArrayList<String> ownedExpIdList;
    private ArrayList<String> partiExpIdList;

    public User() {}
    public User(String username, String password, String contact) {
        this.username = username;
        this.contact = contact;
        this.password = password;

        this.ownedExpIdList = new ArrayList<>();
        this.partiExpIdList = new ArrayList<>();
    }

    public void writeToDatabase() {
        FirebaseFirestore.getInstance().collection("Users")
                .document(username).set(this, SetOptions.merge());
    }

    public void addToOwnedExp(Experiment exp) {
        ownedExpIdList.add(exp.getDatabaseId());
    }
    public void participateExp(Experiment exp) {
        partiExpIdList.add(exp.getDatabaseId());
    }

    // getters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getContact() { return contact; }

    public ArrayList<String> getOwnedExpIdList() { return ownedExpIdList; }
    public ArrayList<String> getPartiExpIdList() { return partiExpIdList; }

    // setters
    public void setContact(String contact) { this.contact = contact; }
}
