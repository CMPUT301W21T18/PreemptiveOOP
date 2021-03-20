package com.example.preemptiveoop.experiment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.preemptiveoop.R;
import com.example.preemptiveoop.experiment.model.Experiment;
import com.example.preemptiveoop.experiment.model.MeasurementExp;
import com.example.preemptiveoop.trial.model.Trial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

public class DisplayExpStats extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_experiment_results);

    }


    @Override
    protected void onResume() {
        super.onResume();

        Experiment exp =(Experiment) getIntent().getSerializableExtra(".experiment");

        ArrayList<Trial> trials = exp.getTrials();

        /*
        Calculate mean
         */
        double sum = 0;
        for (Trial trial : trials) {
            sum += trial.getResult().doubleValue();
        }
        double mean = sum/trials.size();


        /*
        Calculate median
         */
        Collections.sort(trials);
        double median;
        if(trials.size()%2 == 1){
            median = trials.get(trials.size()/2).getResult().doubleValue();
        }else{
            int med1 = (trials.size()-1)/2;
            int med2 = (trials.size()+1)/2;
            median =( trials.get(med1).getResult().doubleValue() + trials.get(med2).getResult().doubleValue())/2;
        }



        /*
        calculate standard deviation
         */
        double stdv = 0.0;
        for (Trial trial : trials) {
            stdv += Math.pow(trial.getResult().doubleValue()-mean,2);
        }
        stdv = Math.sqrt(stdv/trials.size());




        String strMean = String.format("%.3f",mean);
        String strMedian = String.valueOf(median);
        String strStdv = String.format("%.3f",stdv);


        TextView tv_Median = findViewById(R.id.tv_result_median);
        tv_Median.setText(strMedian);

        TextView tv_Mean = findViewById(R.id.tv_result_mean);
        tv_Mean.setText(strMean);


        TextView tv_stdv = findViewById(R.id.tv_result_stdev);
        tv_stdv.setText(strStdv);


        //TextView tv_quartiles = findViewById(R.id.tv_result_quartiles);


    }
}