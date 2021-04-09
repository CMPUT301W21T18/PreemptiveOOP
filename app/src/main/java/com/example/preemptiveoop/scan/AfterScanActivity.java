package com.example.preemptiveoop.scan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.preemptiveoop.R;
import com.example.preemptiveoop.experiment.model.BinomialExp;
import com.example.preemptiveoop.experiment.model.CountExp;
import com.example.preemptiveoop.experiment.model.Experiment;
import com.example.preemptiveoop.experiment.model.GenericExperiment;
import com.example.preemptiveoop.experiment.model.MeasurementExp;
import com.example.preemptiveoop.experiment.model.NonNegativeExp;
import com.example.preemptiveoop.trial.model.BinomialTrial;
import com.example.preemptiveoop.trial.model.CountTrial;
import com.example.preemptiveoop.trial.model.MeasurementTrial;
import com.example.preemptiveoop.trial.model.NonNegativeTrial;
import com.example.preemptiveoop.uiwidget.LocationPicker;
import com.example.preemptiveoop.uiwidget.MyDialog;
import com.example.preemptiveoop.uiwidget.model.MyLocation;
import com.example.preemptiveoop.user.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

/**
 * The AfterScanActivity class is the activity class that builds and manages the UI that is displayed
 * after the user scans a QR code or barcode.
 */
public class AfterScanActivity extends AppCompatActivity {
    private final int CHILD_LOCATION_PICKER = 1;

    private Button btLocation;
    private Button btConfirm;
    private TextView tvResult;

    private Experiment experiment;
    private MyLocation myLocation;
    private User user;

    private DocumentReference expDoc;

    private String experimentId;
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_scan);

        // get passed-in arguments
        Intent intent = getIntent();
        experimentId = intent.getStringExtra(".experimentId");
        result = intent.getStringExtra(".result");
        user = (User)intent.getSerializableExtra(".user");

        btLocation = findViewById(R.id.Button_scan_location);
        btConfirm= findViewById(R.id.Button_scan_confirm);
        tvResult = findViewById(R.id.TextView_scan_detail);

        btLocation.setOnClickListener(this::btLocationOnClick);
        btConfirm.setOnClickListener(this::btConfirmOnClick);

        getInfo();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHILD_LOCATION_PICKER:
                if (resultCode == Activity.RESULT_OK)
                    myLocation = (MyLocation) data.getSerializableExtra(".location");
                break;
        }
    }

    public void btLocationOnClick(View v) {
        Intent intent = new Intent(this, LocationPicker.class);
        startActivityForResult(intent, CHILD_LOCATION_PICKER);
    }

    public void btConfirmOnClick(View v) {
        updateInfo();
        finish();
    }

    private void getInfo(){
        expDoc = FirebaseFirestore.getInstance()
                .collection("Experiments").document(experimentId);

        expDoc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    experiment = documentSnapshot.toObject(GenericExperiment.class).toCorrespondingExp();
                    updateView();
                    Log.d("SCAN CONFIRM", "Found the experiment");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("SCAN CONFIRM", "No experiment found");
            }
        });
    }

    private void updateView() {
        if (experiment.isRequireLocation())
            btLocation.setVisibility(View.VISIBLE);

        String temp = String.format("The experiment detail:\n %s\nOwner: %s\nDate: %s\nStatus: %s\nTypes: %s\nResult: %s",
                experiment.getDescription(),
                experiment.getOwner(),
                experiment.getCreationDate(),
                experiment.getStatus(),
                experiment.getType(),
                result);
        tvResult.setText(temp);
    }

    private void updateInfo() {
        // participation the experiment
        if (!user.getPartiExpIdList().contains(experiment.getDatabaseId())) {
            experiment.addExperimenter(user.getUsername());
            experiment.writeToDatabase();
            user.participateExp(experiment);
            user.writeToDatabase();
        }

        // execute the trial in experiment
        Number data;
        try {
            if (experiment instanceof MeasurementExp) {
                data = Double.parseDouble(result);
            } else {
                data = Integer.parseInt(result);
            }
        } catch (NumberFormatException e){
            MyDialog.messageDialog(AfterScanActivity.this, "Invalid Input", "Please scan again.");
            return;
        }

        if (experiment instanceof BinomialExp) {
            expDoc.update("trials", FieldValue.arrayUnion(
                    new BinomialTrial(user.getUsername(), new Date(), myLocation, (Integer) data, false)
            ));
            setResult(Activity.RESULT_OK);
            finish();
        }

        if (experiment instanceof CountExp) {
            expDoc.update("trials", FieldValue.arrayUnion(
                    new CountTrial(user.getUsername(), new Date(), myLocation, (Integer) data, false)
            ));
            setResult(Activity.RESULT_OK);
            finish();
        }

        if (experiment instanceof MeasurementExp) {
            expDoc.update("trials", FieldValue.arrayUnion(
                    new MeasurementTrial(user.getUsername(), new Date(), myLocation, (Double)data, false)
            ));
            setResult(Activity.RESULT_OK);
            finish();
        }

        if (experiment instanceof NonNegativeExp) {
            expDoc.update("trials", FieldValue.arrayUnion(
                    new NonNegativeTrial(user.getUsername(), new Date(), myLocation, (Integer) data, false)
            ));
            setResult(Activity.RESULT_OK);
            finish();
        }
    }
}