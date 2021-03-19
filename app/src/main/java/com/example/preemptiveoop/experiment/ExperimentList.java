package com.example.preemptiveoop.experiment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.preemptiveoop.R;
import com.example.preemptiveoop.experiment.model.Experiment;
import com.example.preemptiveoop.experiment.model.GenericExperiment;
import com.example.preemptiveoop.user.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ExperimentList extends AppCompatActivity {
    private User user;

    private ArrayList<Experiment> experiments;
    private ArrayAdapter<Experiment> expAdapter;

    private RadioGroup rgExpType;
    private ListView expListView;
    private FloatingActionButton btAddExp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment_list);

        // get passed-in expIdList
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("MainActivity.user");

        rgExpType   = findViewById(R.id.RadioButton_expType);
        expListView = findViewById(R.id.ListView_experiments);
        btAddExp    = findViewById(R.id.Button_addExp);

        experiments = new ArrayList<>();
        expAdapter = new ExpArrayAdatper(this, experiments);
        expListView.setAdapter(expAdapter);

        rgExpType.setOnCheckedChangeListener(this::rgExpTypeOnCheckedChanged);
        btAddExp.setOnClickListener(this::btAddExpOnClick);

        displayOwnedExpList();
    }

    public void displayOwnedExpList() {
        CollectionReference expCol = FirebaseFirestore.getInstance().collection("Experiments");
        experiments.clear();

        // perform query
        expCol.whereEqualTo("owner", user.getUsername())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.d("ExperimentList.DB", "Failed to get owned experiments.", task.getException());
                            return;
                        }
                        for (QueryDocumentSnapshot document : task.getResult())
                            experiments.add(document.toObject(GenericExperiment.class));
                        expAdapter.notifyDataSetChanged();
                    }
                });
    }

    public void displayPartiExpList() {
        CollectionReference expCol = FirebaseFirestore.getInstance().collection("Experiments");
        experiments.clear();

        // perform query
        expCol.whereArrayContains("experimenters", user.getUsername())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.d("ExperimentList.DB", "Failed to get participated experiments.", task.getException());
                            return;
                        }
                        for (QueryDocumentSnapshot document : task.getResult())
                            experiments.add(document.toObject(GenericExperiment.class));
                        expAdapter.notifyDataSetChanged();
                    }
                });
    }

    public void rgExpTypeOnCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.RadioButton_ownedExp:
                displayOwnedExpList();
                break;
            case R.id.RadioButton_partiExp:
                displayPartiExpList();
                break;
        }
    }

    public void btAddExpOnClick(View v) {
        PublishExperiment fragment = new PublishExperiment(user);
        fragment.show(getSupportFragmentManager(), "PUBLISH_EXPERIMENT");
    }
}