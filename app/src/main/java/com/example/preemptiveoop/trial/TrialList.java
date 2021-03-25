package com.example.preemptiveoop.trial;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.preemptiveoop.R;
import com.example.preemptiveoop.experiment.model.Experiment;
import com.example.preemptiveoop.trial.model.GenericTrial;

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

        trialListView = findViewById(R.id.ListView_trials);

        trials = experiment.getTrials();
        trialAdapter = new TrialArrayAdapter(this, trials);
        trialListView.setAdapter(trialAdapter);

        trialListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GenericTrial trial = trials.get(position);
                trial.setIgnored(!trial.isIgnored());
                experiment.writeToDatabase();
            }
        });
    }
}