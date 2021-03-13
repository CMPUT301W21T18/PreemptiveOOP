package com.example.preemptiveoop.user;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.preemptiveoop.R;
import com.example.preemptiveoop.experiment.Experiment;
import com.example.preemptiveoop.user.activity.UserLogin;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String username;
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

    public void addToOwnedExp(Experiment exp) {
        ownedExpIdList.add(exp.getDatabaseId());
    }
    public void participateExp(Experiment exp) {
        partiExpIdList.add(exp.getDatabaseId());
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getContact() { return contact; }

    public ArrayList<String> getOwnedExpIdList() { return ownedExpIdList; }
    public ArrayList<String> getPartiExpIdList() { return partiExpIdList; }



}
