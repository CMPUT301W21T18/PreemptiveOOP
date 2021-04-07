package com.example.preemptiveoop.experiment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.preemptiveoop.R;
import com.example.preemptiveoop.experiment.model.Experiment;
import com.example.preemptiveoop.experiment.model.StatCalculator;
import com.example.preemptiveoop.trial.model.GenericTrial;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * The DisplayExpTimePlot class is the activity class for building and displaying a time-plot for
 * experiment trial results to the user.
 */
public class DisplayExpTimePlot extends AppCompatActivity {
    Experiment exp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_exp_time_plot);

        exp =(Experiment) getIntent().getSerializableExtra(".experiment");
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        GraphView plot = (GraphView)findViewById(R.id.gv_plot);

        ArrayList<GenericTrial> trials = StatCalculator.filterIgnoredTrials(exp.getTrials());

        HashMap<Date,ArrayList<Double>> indexTable = new HashMap<>();

        for (GenericTrial trial : trials) {
            Date dateWoTime = removeTime(trial.getCreationDate());

            if(!indexTable.containsKey(dateWoTime)) {
                //if during that day, there exist a trail, put it in hashmap.
                ArrayList<Double> resultsOfTheDay = new ArrayList<>();
                resultsOfTheDay.add(trial.getResult().doubleValue());
                indexTable.put(dateWoTime, resultsOfTheDay);
            }
            else {
                //during that day, there are many trails, then find average of the trail result.
                ArrayList<Double> results = indexTable.get(dateWoTime);
                results.add(trial.getResult().doubleValue());
                indexTable.put(dateWoTime,results);
            }
        }

        //calculate average value of trails during that day
        TreeMap<Date,Double> plotValue = new TreeMap<>();
        for (Map.Entry<Date, ArrayList<Double>> entry : indexTable.entrySet()) {
            Date key = entry.getKey();
            ArrayList<Double> values = entry.getValue();
            double sum = 0;

            for (Double value : values) {
                sum = sum + value;
            }

            double avg = sum/values.size();
            plotValue.put(key,avg);
        }

        //plotValue.put(new Date(2021,4,3),40.3);

        int size = plotValue.size();
        DataPoint[] arrayDataPoints = new DataPoint[size];

        Set<Map.Entry<Date, Double>> entries = plotValue.entrySet();
        Iterator<Map.Entry<Date, Double>> iterator = entries.iterator();

        for(int i = 0; i<size;i++){
            Map.Entry<Date, Double> next = iterator.next();
            arrayDataPoints[i] = new DataPoint(next.getKey(),next.getValue());
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(arrayDataPoints);
        series.setDrawDataPoints(true);

        plot.addSeries(series);

        plot.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
        plot.getGridLabelRenderer().setNumHorizontalLabels(plotValue.size()-1);

        plot.getViewport().setXAxisBoundsManual(true);
        plot.getGridLabelRenderer().setHumanRounding(false);

        GridLabelRenderer glr = plot.getGridLabelRenderer();
        glr.setPadding(52);
    }

    /*
    remove time part from date
     */
    public Date removeTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
