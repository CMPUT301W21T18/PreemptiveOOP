package com.example.preemptiveoop.trial;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

/**
 * The ExecuteTrial class is the activity class that builds and manages the UI for the user to record
 * trials to an experiment.
 */
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

    private ProgressBar pbStatus;

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
        pbStatus = findViewById(R.id.ProgressBar_status);

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
        pbStatus.setVisibility(View.GONE);

        if (experiment.isRequireLocation())
            MyDialog.messageDialog(ExecuteTrial.this,
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

    private final OnSuccessListener<Void> recordOnSuccessListener = new OnSuccessListener<Void>() {
        @Override
        public void onSuccess(Void aVoid) {
            pbStatus.setVisibility(View.GONE);

            AlertDialog dialog = MyDialog.messageDialog(ExecuteTrial.this, "Good Record", "The trial has been added to DB.");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() { if (dialog.isShowing()) dialog.dismiss(); }
            }, 2000);
        }
    };

    private void binomialRecord(View v) {               // to be used by btOnClickForMultiple
        pbStatus.setVisibility(View.VISIBLE);

        if (v.getId() != R.id.Button_success && v.getId() != R.id.Button_failure)
            throw new IllegalStateException("binomialRecord() in wrong state.");

        Task<Void> task = null;
        if      (v.getId() == R.id.Button_success) {
            task = expDoc.update("trials", FieldValue.arrayUnion(
                    new BinomialTrial(user.getUsername(), new Date(), selectedLocation, 1, false)
            ));
        }
        else if (v.getId() == R.id.Button_failure) {
            task = expDoc.update("trials", FieldValue.arrayUnion(
                    new BinomialTrial(user.getUsername(), new Date(), selectedLocation, 0, false)
            ));
        }

        task.addOnSuccessListener(recordOnSuccessListener);
    }

    private void countRecord(View v) {                  // to be used by btOnClickForMultiple
        pbStatus.setVisibility(View.VISIBLE);

        if (v.getId() != R.id.Button_record)
            throw new IllegalStateException("countRecord() in wrong state.");

        expDoc.update("trials", FieldValue.arrayUnion(
                new CountTrial(user.getUsername(), new Date(), selectedLocation, 1, false)
        )).addOnSuccessListener(recordOnSuccessListener);
    }

    private void measurementRecord(View v) {            // to be used by btOnClickForMultiple
        pbStatus.setVisibility(View.VISIBLE);

        if (v.getId() != R.id.Button_record)
            throw new IllegalStateException("measurementRecord() in wrong state.");

        String resultStr = etResult.getText().toString();
        double result;

        try { result = Double.parseDouble(resultStr); }
        catch (NumberFormatException e) {
            MyDialog.messageDialog(ExecuteTrial.this, "Invalid Result", "Please enter a decimal number.");
            return;
        }

        expDoc.update("trials", FieldValue.arrayUnion(
                new MeasurementTrial(user.getUsername(), new Date(), selectedLocation, result, false)
        )).addOnSuccessListener(recordOnSuccessListener);
    }

    private void nonNegativeRecord(View v) {            // to be used by btOnClickForMultiple
        pbStatus.setVisibility(View.VISIBLE);

        if (v.getId() != R.id.Button_record)
            throw new IllegalStateException("nonNegativeRecord() in wrong state.");

        String resultStr = etResult.getText().toString();
        int result;

        try { result = Integer.parseInt(resultStr); }
        catch (NumberFormatException e) {
            MyDialog.messageDialog(ExecuteTrial.this, "Invalid Result", "Please enter an integer number.");
            return;
        }

        if (result < 0) {
            MyDialog.messageDialog(ExecuteTrial.this, "Invalid Result", "Result of non-negative trial must be >= 0.");
            return;
        }

        expDoc.update("trials", FieldValue.arrayUnion(
                new NonNegativeTrial(user.getUsername(), new Date(), selectedLocation, result, false)
        )).addOnSuccessListener(recordOnSuccessListener);
    }

    public void btOnClickForMultiple(View v) {
        if (experiment.isRequireLocation() && selectedLocation == null) {
            MyDialog.messageDialog(ExecuteTrial.this, "Require Location", "Please pick a location.");
            return;
        }

        if      (experiment instanceof BinomialExp)
            binomialRecord(v);
        else if (experiment instanceof CountExp)
            countRecord(v);
        else if (experiment instanceof MeasurementExp)
            measurementRecord(v);
        else if (experiment instanceof NonNegativeExp)
            nonNegativeRecord(v);
        else
            throw new IllegalStateException("experiment is not of any concrete experiment class.");
    }

    public void btPickLocationOnClick(View v) {
        Intent intent = new Intent(this, LocationPicker.class);
        startActivityForResult(intent, CHILD_LOCATION_PICKER);
    }
}
