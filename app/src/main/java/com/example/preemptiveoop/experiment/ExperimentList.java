package com.example.preemptiveoop.experiment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.preemptiveoop.R;
import com.example.preemptiveoop.experiment.model.Experiment;
import com.example.preemptiveoop.experiment.model.GenericExperiment;
import com.example.preemptiveoop.user.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * The ExperimentList class is the activity class for displaying an experiment list to the user for
 * further operations. This activity can operate in "searchMode" to display search results.
 */
public class ExperimentList extends AppCompatActivity {
    private final int CHILD_PUBLISH_EXPERIMENT = 1;

    private User user;
    private boolean searchMode;

    private ArrayList<Experiment> experiments;
    private ArrayAdapter<Experiment> expAdapter;

    private EditText etKeywords;
    private Button btSearch;

    private RadioGroup rgExpType;
    private ListView expListView;
    private FloatingActionButton btAddExp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment_list);

        // get passed-in arguments: .user, .searchMode
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra(".user");
        searchMode = intent.getBooleanExtra(".searchMode", false);

        if (user == null)
            throw new IllegalArgumentException("Expected '.user' passed-in through intent.");

        etKeywords  = findViewById(R.id.EditText_keywords);
        btSearch    = findViewById(R.id.Button_search);

        rgExpType   = findViewById(R.id.RadioButton_expType);
        expListView = findViewById(R.id.ListView_experiments);
        btAddExp    = findViewById(R.id.Button_addExp);

        experiments = new ArrayList<>();
        expAdapter = new ExpArrayAdatper(this, experiments, user);
        expListView.setAdapter(expAdapter);

        expListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ManageExperiment fragment = new ManageExperiment(experiments.get(position), user);
                fragment.show(getSupportFragmentManager(), "MANAGE_EXPERIMENT");
            }
        });

        btSearch.setOnClickListener(this::btSearchOnClick);

        rgExpType.setOnCheckedChangeListener(this::rgExpTypeOnCheckedChanged);
        btAddExp.setOnClickListener(this::btAddExpOnClick);

        if (searchMode) {
            rgExpType.setVisibility(View.GONE);
            return;
        }

        etKeywords.setVisibility(View.GONE);
        btSearch.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateExperimentList();
    }

    private void updateExpListFromQuerySnapshot(QuerySnapshot queryDocumentSnapshots) {
        experiments.clear();
        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
            GenericExperiment exp = document.toObject(GenericExperiment.class);
            experiments.add(exp.toCorrespondingExp());
        }

        Collections.sort(experiments, new Comparator<Experiment>() {
            @Override
            public int compare(Experiment o1, Experiment o2) { return o2.getCreationDate().compareTo(o1.getCreationDate()); }
        });
        expAdapter.notifyDataSetChanged();
    }

    private void displayOwnedExpList() {
        CollectionReference expCol = FirebaseFirestore.getInstance().collection("Experiments");
        // perform query
        expCol.whereEqualTo("owner", user.getUsername()).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) { updateExpListFromQuerySnapshot(queryDocumentSnapshots); }
                });
    }

    private void displayPartiExpList() {
        CollectionReference expCol = FirebaseFirestore.getInstance().collection("Experiments");
        // perform query
        expCol.whereArrayContains("experimenters", user.getUsername()).whereEqualTo("status", Experiment.STATUS_PUBLISHED).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) { updateExpListFromQuerySnapshot(queryDocumentSnapshots); }
                });
    }

    private void displaySearchedExpList(String keyword) {
        CollectionReference expCol = FirebaseFirestore.getInstance().collection("Experiments");
        // perform query
        expCol.whereArrayContains("keywords", keyword).whereEqualTo("status", Experiment.STATUS_PUBLISHED).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) { updateExpListFromQuerySnapshot(queryDocumentSnapshots); }
                });
    }

    public void updateExperimentList() {
        if (searchMode) {
            displaySearchedExpList(etKeywords.getText().toString());
            return;
        }

        int checkId = rgExpType.getCheckedRadioButtonId();
        rgExpTypeOnCheckedChanged(rgExpType, checkId);
    }

    public void btSearchOnClick(View v) {
        String keyword = etKeywords.getText().toString().toLowerCase();
        if (keyword.isEmpty())
            return;
        displaySearchedExpList(keyword);
    }

    public void rgExpTypeOnCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.RadioButton_ownedExp:
                btAddExp.setVisibility(View.VISIBLE);
                displayOwnedExpList();
                break;
            case R.id.RadioButton_partiExp:
                btAddExp.setVisibility(View.GONE);
                displayPartiExpList();
                break;
        }
    }

    public void btAddExpOnClick(View v) {
        Intent intent = new Intent(this, PublishExperiment.class);
        intent.putExtra(".user", user);
        startActivity(intent);
    }
}