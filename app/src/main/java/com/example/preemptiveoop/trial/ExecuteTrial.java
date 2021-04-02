package com.example.preemptiveoop.trial;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
import com.example.preemptiveoop.uiwidget.MyDialog;
import com.example.preemptiveoop.user.model.User;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class ExecuteTrial extends AppCompatActivity {
    private Experiment experiment;
    private User user;

    private DocumentReference expDoc;

    private Button btSuccess, btFailure;
    private Button btRecord;

    private TextView tvHint;
    private EditText etResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execute_trial);

        Intent intent = getIntent();
        experiment = (Experiment) intent.getSerializableExtra(".experiment");
        user = (User) intent.getSerializableExtra(".user");

        expDoc = FirebaseFirestore.getInstance()
                .collection("Experiments").document(experiment.getDatabaseId());

        btSuccess = findViewById(R.id.Button_success);
        btFailure = findViewById(R.id.Button_failure);

        tvHint = findViewById(R.id.TextView_hint);
        etResult = findViewById(R.id.EditText_result);
        btRecord = findViewById(R.id.Button_record);

        btSuccess.setOnClickListener(this::btSuccessOnClick);
        btFailure.setOnClickListener(this::btFailureOnClick);
        btRecord.setOnClickListener(this::btRecordOnClick);

        setViewsPerExpType();
    }

    private void setViewsPerExpType() {
        if (experiment.getType().equals(Experiment.TYPE_BINOMIAL)) {
            tvHint.setVisibility(View.GONE);
            etResult.setVisibility(View.GONE);
            btRecord.setVisibility(View.GONE);
            return;
        }

        btSuccess.setVisibility(View.GONE);
        btFailure.setVisibility(View.GONE);

        if (experiment.getType().equals(Experiment.TYPE_COUNT)) {
            tvHint.setVisibility(View.GONE);
            etResult.setVisibility(View.GONE);
        }
    }

    public void btSuccessOnClick(View v) {
        expDoc.update("trials", FieldValue.arrayUnion(
                new BinomialTrial(user.getUsername(), new Date(), null, 1, false)
        ));
    }
    public void btFailureOnClick(View v) {
        expDoc.update("trials", FieldValue.arrayUnion(
                new BinomialTrial(user.getUsername(), new Date(), null, 0, false)
        ));
    }

    public void btRecordOnClick(View v) {
        if (experiment.getType().equals(Experiment.TYPE_COUNT)) {
            expDoc.update("trials", FieldValue.arrayUnion(
                    new CountTrial(user.getUsername(), new Date(), null, 1, false)
            ));
            return;
        }

        String resultStr = etResult.getText().toString();

        if (experiment.getType().equals(Experiment.TYPE_MEASUREMENT)) {
            double result;
            try { result = Double.parseDouble(resultStr); }
            catch (NumberFormatException e) {
                MyDialog.errorDialog(ExecuteTrial.this, "Invalid Result", "Please enter a decimal number.");
                return;
            }

            expDoc.update("trials", FieldValue.arrayUnion(
                    new MeasurementTrial(user.getUsername(), new Date(), null, result, false)
            ));
            return;
        }

        if (experiment.getType().equals(Experiment.TYPE_NON_NEGATIVE)) {
            int result;
            try { result = Integer.parseInt(resultStr); }
            catch (NumberFormatException e) {
                MyDialog.errorDialog(ExecuteTrial.this, "Invalid Result", "Please enter an integer number.");
                return;
            }

            expDoc.update("trials", FieldValue.arrayUnion(
                    new NonNegativeTrial(user.getUsername(), new Date(), null, result, false)
            ));
            return;
        }
    }
}