package com.example.preemptiveoop.experiment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.preemptiveoop.R;
import com.example.preemptiveoop.experiment.model.Experiment;
import com.example.preemptiveoop.trial.model.GenericTrial;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class DisplayExpPlots extends AppCompatActivity {

    Experiment exp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_exp_plot);


        exp =(Experiment) getIntent().getSerializableExtra(".experiment");


        /*
        exp = new MeasurementExp(null, "xiaolei", new Date(), "hello", null, false, 3);
        MeasurementTrial t1 = new MeasurementTrial();
        MeasurementTrial t2 = new MeasurementTrial();
        MeasurementTrial t3 = new MeasurementTrial();
        t1.setResult(3.0);
        t1.setCreationDate(new Date());
        sleep(1000);
        t3.setResult(3.7);
        t2.setCreationDate(new Date());
        t2.setResult(2.2);
        sleep(1000);
        t3.setCreationDate(new Date());
        exp.addTrial(t1);
        exp.addTrial(t2);
        exp.addTrial(t3);
         */

    }

    @Override
    protected void onResume() {
        super.onResume();
        GraphView plot = (GraphView)findViewById(R.id.gv_plot);

        ArrayList<GenericTrial> trials = exp.getTrials();
        Collections.sort(trials, new Comparator<GenericTrial>() {
            @Override
            public int compare(GenericTrial o1, GenericTrial o2) { return o1.getCreationDate().compareTo(o2.getCreationDate()); }
        });

        int size = trials.size();
        DataPoint[] arrayDataPoints = new DataPoint[size];

        for(int i = 0; i<trials.size();i++){
            arrayDataPoints[i] = new DataPoint(trials.get(i).getCreationDate(),Double.parseDouble(trials.get(i).getResultStr()));
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(arrayDataPoints);
        series.setDrawDataPoints(true);

        plot.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(DisplayExpPlots.this));
        GridLabelRenderer glr = plot.getGridLabelRenderer();
        glr.setPadding(52);
        plot.addSeries(series);
        plot.getViewport().setXAxisBoundsManual(true);
        plot.getGridLabelRenderer().setHumanRounding(false);

    }
}