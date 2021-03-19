package com.example.preemptiveoop.experiment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.preemptiveoop.R;
import com.example.preemptiveoop.experiment.model.Experiment;
import com.example.preemptiveoop.experiment.model.GenericExperiment;
import com.example.preemptiveoop.user.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ExperimentList extends AppCompatActivity {
    private User user;

    private ArrayList<Experiment> experiments;
    private ArrayAdapter<Experiment> expAdapter;
    private ListView expListView;

    private FloatingActionButton btAddExp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment_list);

        // get passed-in expIdList
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("MainActivity.user");

        expListView = findViewById(R.id.ListView_experiments);
        btAddExp = findViewById(R.id.Button_addExp);

        experiments = new ArrayList<>();
        expAdapter = new ExpArrayAdatper(this, experiments);
        expListView.setAdapter(expAdapter);

        btAddExp.setOnClickListener(this::btAddExpOnClick);

        readExpFromDatabase(user.getOwnedExpIdList());
    }

    public void readExpFromDatabase(ArrayList<String> expIdList) {
        for (String expId : expIdList) {
            FirebaseFirestore.getInstance().collection("Experiments")
                    .document(expId)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (!documentSnapshot.exists()) {                   // query returned no document!
                                Log.d("ExperimentList.DB", "Failed to get expId in expIdList.");
                                return;
                            }

                            Experiment exp = documentSnapshot.toObject(GenericExperiment.class);
                            experiments.add(exp);
                            expAdapter.notifyDataSetChanged();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {           // query failed!
                        @Override
                        public void onFailure(@NonNull Exception e) { Log.d("ExperimentList.DB", "Failed to perform query.", e); }
                    });
        }
    }

    public void btAddExpOnClick(View v) {
        PublishExperiment fragment = new PublishExperiment(user);
        fragment.show(getSupportFragmentManager(), "PUBLISH_EXPERIMENT");
    }
}