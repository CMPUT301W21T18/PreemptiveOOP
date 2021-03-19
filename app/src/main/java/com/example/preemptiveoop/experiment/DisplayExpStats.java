package com.example.preemptiveoop.experiment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.preemptiveoop.R;
import com.example.preemptiveoop.experiment.model.Experiment;
import com.example.preemptiveoop.trial.model.Trial;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Date;


/*
https://www.youtube.com/watch?v=vhKtbECeazQ&t=492s
 */


public class DisplayExpStats extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_exp_stats);


        Experiment exp =(Experiment) getIntent().getSerializableExtra("InputExp");


        ArrayList<Trial> trials = exp.getTrials();


        if(exp.getType().equals("Count")|| exp.getType().equals("NonNegative")||exp.getType().equals("Measurement")){

            BarChart barChart = findViewById(R.id.graphicv_exp_display_stats);

            ArrayList<BarEntry> barEntries = new ArrayList<>();
            int i = 0; // x axis -> could be refined next time
            for (Trial trial : trials) {
                Date date = trial.getCreationDate();;
                Float result = trial.getResult().floatValue();
                barEntries.add(new BarEntry(i, result));
                i++;
            }

            BarDataSet barDataSet = new BarDataSet(barEntries,"Exp Results");
            barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
            barDataSet.setValueTextColor(android.R.color.black);
            barDataSet.setValueTextSize(16f);

            BarData barData =  new BarData(barDataSet);
            barChart.setFitBars(true);

            barChart.setFitBars(true);
            barChart.setData(barData);
            barChart.getDescription().setText(exp.getDescription());
            barChart.animateY(1500);
            barChart.setDrawGridBackground(false);

        }


    }





}