package com.example.preemptiveoop.trial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.preemptiveoop.R;
import com.example.preemptiveoop.experiment.model.Experiment;
import com.example.preemptiveoop.trial.model.GenericTrial;
import com.example.preemptiveoop.user.model.User;

import java.util.ArrayList;

public class TrialList extends AppCompatActivity {
    private Experiment experiment;

    private ArrayList<GenericTrial> trials;
    private ArrayAdapter<GenericTrial> trialAdapter;

    private ListView trialListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trial_list);

        // get passed-in arguments
        Intent intent = getIntent();
        experiment = (Experiment) intent.getSerializableExtra(".experiment");

        
    }
}