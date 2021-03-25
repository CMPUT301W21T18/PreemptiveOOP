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

import java.util.Date;

public class ExecuteTrial extends AppCompatActivity {
    private Experiment experiment;
    private User user;

    private Button btSuccess, btFailure;
    private Button btRecord;

    private TextView tvHint;
    private EditText etResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execute_trial);

        btSuccess = findViewById(R.id.Button_success);
        btFailure = findViewById(R.id.Button_failure);

        tvHint = findViewById(R.id.TextView_hint);
        etResult = findViewById(R.id.EditText_result);
        btRecord = findViewById(R.id.Button_record);

        Intent intent = getIntent();
        experiment = (Experiment) intent.getSerializableExtra(".experiment");
        user = (User) intent.getSerializableExtra(".user");

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
        ((BinomialExp) experiment).addTrial(
                new BinomialTrial(user.getUsername(), new Date(), null, 1, false)
        );
        experiment.writeToDatabase();
    }
    public void btFailureOnClick(View v) {
        ((BinomialExp) experiment).addTrial(
                new BinomialTrial(user.getUsername(), new Date(), null, 0, false)
        );
        experiment.writeToDatabase();
    }

    public void btRecordOnClick(View v) {

        if (experiment.getType().equals(Experiment.TYPE_COUNT)) {
            ((CountExp) experiment).addTrial(
                    new CountTrial(user.getUsername(), new Date(), null, 1, false)
            );
            experiment.writeToDatabase();
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

            ((MeasurementExp) experiment).addTrial(
                    new MeasurementTrial(user.getUsername(), new Date(), null, result, false)
            );
            experiment.writeToDatabase();
            return;
        }

        if (experiment.getType().equals(Experiment.TYPE_NON_NEGATIVE)) {
            int result;
            try { result = Integer.parseInt(resultStr); }
            catch (NumberFormatException e) {
                MyDialog.errorDialog(ExecuteTrial.this, "Invalid Result", "Please enter an integer number.");
                return;
            }

            ((NonNegativeExp) experiment).addTrial(
                    new NonNegativeTrial(user.getUsername(), new Date(), null, result, false)
            );
            experiment.writeToDatabase();
            return;
        }
    }
}