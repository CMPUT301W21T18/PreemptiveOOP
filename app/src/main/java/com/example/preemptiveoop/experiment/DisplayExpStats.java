package com.example.preemptiveoop.experiment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.preemptiveoop.R;
import com.example.preemptiveoop.experiment.model.Experiment;
import com.example.preemptiveoop.experiment.model.StatCalculator;
import com.example.preemptiveoop.trial.model.GenericTrial;

import java.util.ArrayList;

/**
 * The DisplayExpStats class is the activity class for calculating and displaying the experiment
 * trial results statistics to the user.
 */
public class DisplayExpStats extends AppCompatActivity {
    Experiment experiment;

    TextView tvQuart, tvMedian, tvMean, tvStdev;
    Button btHistogram, btTimePlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_exp_stats);

        // get passed-in arguments
        Intent intent = getIntent();
        experiment = (Experiment) intent.getSerializableExtra(".experiment");

        tvQuart  = findViewById(R.id.tv_quartiles);
        tvMedian = findViewById(R.id.tv_median);
        tvMean  = findViewById(R.id.tv_mean);
        tvStdev = findViewById(R.id.tv_stdev);

        btHistogram = findViewById(R.id.Button_histogram);
        btTimePlot  = findViewById(R.id.Button_timePlot);

        btHistogram.setOnClickListener(this::btHistogramExpOnClick);
        btTimePlot.setOnClickListener(this::btTimePlotOnClick);

        updateStats();
    }

    public void updateStats() {
        ArrayList<GenericTrial> trials = StatCalculator.filterIgnoredTrials(experiment.getTrials());
        trials = StatCalculator.sortTrialsByResult(trials);

        Double q1 = StatCalculator.calcQuartile(trials, 1);
        Double q2 = StatCalculator.calcQuartile(trials, 2);
        Double q3 = StatCalculator.calcQuartile(trials, 3);

        Double mean  = StatCalculator.calcMean(trials);
        Double stdev = StatCalculator.calcStdev(trials);

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

    public void btHistogramExpOnClick(View v) {
        Intent intent = new Intent(this, DisplayExpHistogram.class);
        intent.putExtra(".experiment", experiment);
        startActivity(intent);
    }
    public void btTimePlotOnClick(View v) {
        Intent intent = new Intent(this, DisplayExpTimePlot.class);
        intent.putExtra(".experiment", experiment);
        startActivity(intent);
    }
}
