package com.example.preemptiveoop.trial;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.preemptiveoop.R;
import com.example.preemptiveoop.experiment.model.BinomialExp;
import com.example.preemptiveoop.experiment.model.CountExp;
import com.example.preemptiveoop.experiment.model.Experiment;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class ExecuteTrial extends AppCompatActivity {
    private final int CHILD_LOCATION_PICKER = 1;

    private Experiment experiment;
    private User user;

    private DocumentReference expDoc;
    private MyLocation selectedLocation;

    private Button btSuccess, btFailure;
    private Button btRecord, btPickLocation;

    private TextView tvHint;
    private EditText etResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execute_trial);

        Intent intent = getIntent();
        experiment = (Experiment) intent.getSerializableExtra(".experiment");
        user = (User) intent.getSerializableExtra(".user");

        if (experiment == null || user == null)
            throw new IllegalArgumentException("Expected '.experiment' and '.user' passed-in through intent.");

        expDoc = FirebaseFirestore.getInstance()
                .collection("Experiments").document(experiment.getDatabaseId());

        selectedLocation = null;

        btSuccess = findViewById(R.id.Button_success);
        btFailure = findViewById(R.id.Button_failure);

        tvHint = findViewById(R.id.TextView_hint);
        etResult = findViewById(R.id.EditText_result);

        btRecord = findViewById(R.id.Button_record);
        btPickLocation = findViewById(R.id.Button_pickLocation);

        btSuccess.setOnClickListener(this::btOnClickForMultiple);
        btFailure.setOnClickListener(this::btOnClickForMultiple);

        btRecord.setOnClickListener(this::btOnClickForMultiple);
        btPickLocation.setOnClickListener(this::btPickLocationOnClick);

        setViewsPerExpType();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHILD_LOCATION_PICKER:
                if (resultCode == Activity.RESULT_OK)
                    selectedLocation = (MyLocation) data.getSerializableExtra(".location");
                break;
        }
    }

    private void setViewsPerExpType() {
        if (experiment.isRequireLocation())
            MyDialog.errorDialog(ExecuteTrial.this,
                    "Privacy Warning: Require Location",
                    "A location must be provided to all trials for this experiment.");
        else
            btPickLocation.setVisibility(View.GONE);

        if (experiment instanceof BinomialExp) {
            tvHint.setVisibility(View.GONE); etResult.setVisibility(View.GONE);
            btRecord.setVisibility(View.GONE);
            return;
        }

        btSuccess.setVisibility(View.GONE); btFailure.setVisibility(View.GONE);

        if (experiment instanceof CountExp) {
            tvHint.setVisibility(View.GONE); etResult.setVisibility(View.GONE);
        }
    }

    public void btOnClickForMultiple(View v) {
        if (experiment.isRequireLocation() && selectedLocation == null) {
            MyDialog.errorDialog(ExecuteTrial.this, "Require Location", "Please pick a location.");
            return;
        }

        // for binomial experiment
        if      (experiment instanceof BinomialExp && v.getId() == R.id.Button_success) {
            expDoc.update("trials", FieldValue.arrayUnion(
                    new BinomialTrial(user.getUsername(), new Date(), selectedLocation, 1, false)
            ));
            return;
        }
        else if (experiment instanceof BinomialExp && v.getId() == R.id.Button_failure) {
            expDoc.update("trials", FieldValue.arrayUnion(
                    new BinomialTrial(user.getUsername(), new Date(), selectedLocation, 0, false)
            ));
            return;
        }

        // for count experiment
        if (experiment instanceof CountExp) {
            expDoc.update("trials", FieldValue.arrayUnion(
                    new CountTrial(user.getUsername(), new Date(), selectedLocation, 1, false)
            ));
            return;
        }

        String resultStr = etResult.getText().toString();

        // for measurement experiment
        if (experiment instanceof MeasurementExp) {
            double result;
            try { result = Double.parseDouble(resultStr); }
            catch (NumberFormatException e) {
                MyDialog.errorDialog(ExecuteTrial.this, "Invalid Result", "Please enter a decimal number.");
                return;
            }

            expDoc.update("trials", FieldValue.arrayUnion(
                    new MeasurementTrial(user.getUsername(), new Date(), selectedLocation, result, false)
            ));
            return;
        }

        // for non-negative experiment
        if (experiment instanceof NonNegativeExp) {
            int result;
            try { result = Integer.parseInt(resultStr); }
            catch (NumberFormatException e) {
                MyDialog.errorDialog(ExecuteTrial.this, "Invalid Result", "Please enter an integer number.");
                return;
            }

            expDoc.update("trials", FieldValue.arrayUnion(
                    new NonNegativeTrial(user.getUsername(), new Date(), selectedLocation, result, false)
            ));
            return;
        }
    }

    public void btPickLocationOnClick(View v) {
        Intent intent = new Intent(this, LocationPicker.class);
        startActivityForResult(intent, CHILD_LOCATION_PICKER);
    }
}
