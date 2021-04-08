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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * The ExecuteTrial class is the activity class that builds and manages the UI for the user to record
 * trials to an experiment.
 */
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
        Collections.sort(trials, new Comparator<GenericTrial>() {
            @Override
            public int compare(GenericTrial o1, GenericTrial o2) { return o2.getCreationDate().compareTo(o1.getCreationDate()); }
        });

        trialAdapter = new TrialArrayAdapter(this, trials);
        trialListView.setAdapter(trialAdapter);

        trialListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DocumentReference expDoc = FirebaseFirestore.getInstance()
                        .collection("Experiments").document(experiment.getDatabaseId());

                GenericTrial trial = trials.get(position);
                expDoc.update("trials", FieldValue.arrayRemove(trial));

                trial.setIgnored(!trial.isIgnored());
                expDoc.update("trials", FieldValue.arrayUnion(trial));

                trialAdapter.notifyDataSetChanged();
            }
        });
    }
}