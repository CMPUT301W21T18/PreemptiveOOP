package com.example.preemptiveoop.experiment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.preemptiveoop.R;
import com.example.preemptiveoop.experiment.model.Experiment;
import com.example.preemptiveoop.experiment.model.StatCalculator;
import com.example.preemptiveoop.trial.model.GenericTrial;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The DisplayExpHistogram class is the activity class for building and displaying a histogram for
 * experiment trial results to the user.
 */
public class DisplayExpHistogram extends AppCompatActivity {
    BarChart barChart;
    Experiment exp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_exp_histogram);

        barChart = findViewById(R.id.gv_histogram);
        exp = (Experiment) getIntent().getSerializableExtra(".experiment");
    }

    @Override
    protected void onResume() {
        super.onResume();

        ArrayList<GenericTrial> trials = StatCalculator.filterIgnoredTrials(exp.getTrials());

        HashMap<String,Integer> frequency = new HashMap<>();

        ArrayList<BarEntry> barEntries = new ArrayList<>();

        for (GenericTrial trial : trials) {
            if(!frequency.containsKey(trial.getResultStr()))
                frequency.put(trial.getResultStr(),1);
            else {
                int count = frequency.get(trial.getResultStr());
                frequency.put(trial.getResultStr(),count+1);
            }
        }

        for (Map.Entry<String, Integer> entry : frequency.entrySet()) {
            String key = entry.getKey();

            int occur = entry.getValue();
            barEntries.add(new BarEntry(Float.parseFloat(key), occur));
        }

        BarDataSet barDataSet = new BarDataSet(barEntries,"Exp Results");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(android.R.color.black);
        barDataSet.setValueTextSize(25f);

        BarData barData =  new BarData(barDataSet);
        barData.setBarWidth(1);
        barChart.setFitBars(true);

        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText(exp.getDescription());
        barChart.animateY(1500);
        barChart.setDrawGridBackground(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);

        YAxis lyAxis = barChart.getAxisLeft();
        lyAxis.setGranularity(1f);
        lyAxis.setDrawGridLines(false);

        barChart.getAxisRight().setEnabled(false);
    }
}
