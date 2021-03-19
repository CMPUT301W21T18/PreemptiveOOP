package com.example.preemptiveoop.experiment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.preemptiveoop.R;

public class ExperimentTrialExecute extends AppCompatActivity {

    private Button btRecord;

    private EditText etCountInput;
    private Button btSuccess;
    private Button btFailure;
    private EditText etNonnegativeCountInput;
    private EditText etMeasurement;


    // State 1: Count; State 2: binomial trials;
    // State 3:non-negative integer counts; State 4: measurement
    private int state = 1;

    // 修改 statement需要在前面的activity加上
    // Intent intent = new Intent(activity, 前面的.class);
    // intent.putExtra("status",status);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment_execute);

        btRecord = findViewById(R.id.Button_Record);

        etCountInput =findViewById(R.id.EditText_Input_Counts);
        btSuccess = findViewById(R.id.Button_binomialTrial_Success);
        btFailure = findViewById(R.id.Button_binomialTrial_Failure);
        etNonnegativeCountInput = findViewById(R.id.EditText_Input_NonNegativeCounts);
        etMeasurement = findViewById(R.id.EditText_Input_Measurement);

        // check state for 4 different type of trials, counts , binomial trials, non-negative integer counts , measurement trials
        if(state == 1){
            etCountInput.setVisibility(View.VISIBLE);
            btSuccess.setVisibility(View.GONE);
            btFailure.setVisibility(View.GONE);
            etNonnegativeCountInput.setVisibility(View.GONE);
            etMeasurement.setVisibility(View.GONE);

            // int Count = Integer.parseInt(EditText_Count_Input.getText().toString());
            // 上面这行代码这里 String转换成int的时候报错 不知道为什么
            String Count = etCountInput.getText().toString();

        }
        else if(state == 2){
            etCountInput.setVisibility(View.GONE);
            btSuccess.setVisibility(View.VISIBLE);
            btFailure.setVisibility(View.VISIBLE);
            etNonnegativeCountInput.setVisibility(View.GONE);
            etMeasurement.setVisibility(View.GONE);
            btRecord.setVisibility(View.GONE);

            // Button do something.....
            // No record button

        }
        else if(state == 3){
            etCountInput.setVisibility(View.GONE);
            btSuccess.setVisibility(View.GONE);
            btFailure.setVisibility(View.GONE);
            etNonnegativeCountInput.setVisibility(View.VISIBLE);
            etMeasurement.setVisibility(View.GONE);

            // int Count = Integer.parseInt(EditText_NonNegativeCount_Input.getText().toString());

        }
        else if(state == 4){
            etCountInput.setVisibility(View.GONE);
            btSuccess.setVisibility(View.GONE);
            btFailure.setVisibility(View.GONE);
            etNonnegativeCountInput.setVisibility(View.GONE);
            etMeasurement.setVisibility(View.VISIBLE);

            // double Count = Double.parseDouble(EditText_Measurement_Input.getText().toString());
        }

    }
}
