package com.example.preemptiveoop.experiment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.preemptiveoop.R;
import com.example.preemptiveoop.experiment.model.Experiment;
import com.example.preemptiveoop.experiment.model.StatCalculator;

public class DisplayExpStats extends AppCompatActivity {
    Experiment experiment;
    TextView tvQuart, tvMedian, tvMean, tvStdev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_exp_stats);

        // get passed-in arguments
        Intent intent = getIntent();
        experiment = (Experiment) intent.getSerializableExtra(".experiment");

        tvQuart  = findViewById(R.id.tv_result_quartiles);
        tvMedian = findViewById(R.id.tv_result_median);
        tvMean  = findViewById(R.id.tv_result_mean);
        tvStdev = findViewById(R.id.tv_result_stdev);

        updateStats();
    }

    public void updateStats() {
        Double q1 = StatCalculator.calcQuartile(experiment.getTrials(), 1);
        Double q2 = StatCalculator.calcQuartile(experiment.getTrials(), 2);
        Double q3 = StatCalculator.calcQuartile(experiment.getTrials(), 3);

        Double mean  = StatCalculator.calcMean(experiment.getTrials());
        Double stdev = StatCalculator.calcStdev(experiment.getTrials());

        String quartStr  = q1.isNaN() ? "Q1 = N/A, " : String.format("Q1 = %.2f, ", q1);
               quartStr += q3.isNaN() ? "Q3 = N/A  " : String.format("Q3 = %.2f", q3);
        String medianStr = q2.isNaN() ? "N/A" : String.format("%.2f", q2);

        String meanStr  = mean.isNaN() ? "N/A" : String.format("%.2f", mean);
        String stdevStr = stdev.isNaN() ? "N/A" : String.format("%.2f", stdev);

        tvQuart.setText(quartStr);
        tvMedian.setText(medianStr);
        tvMean.setText(meanStr);
        tvStdev.setText(stdevStr);
    }
}
