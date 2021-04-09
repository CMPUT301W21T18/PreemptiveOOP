package com.example.preemptiveoop.experiment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.preemptiveoop.R;
import com.example.preemptiveoop.experiment.model.BinomialExp;
import com.example.preemptiveoop.experiment.model.CountExp;
import com.example.preemptiveoop.experiment.model.Experiment;
import com.example.preemptiveoop.experiment.model.MeasurementExp;
import com.example.preemptiveoop.experiment.model.NonNegativeExp;
import com.example.preemptiveoop.uiwidget.LocationPicker;
import com.example.preemptiveoop.uiwidget.MyDialog;
import com.example.preemptiveoop.uiwidget.model.MyLocation;
import com.example.preemptiveoop.user.model.User;

import java.util.Date;

/**
 * The PublishExperiment class is the activity class that builds and manages the UI for the user to
 * publish a new experiment.
 */
public class PublishExperiment extends AppCompatActivity {
    private final int CHILD_LOCATION_PICKER = 1;

    private User owner;
    private MyLocation selectedLocation = null;

    private TextView tvUsername;
    private RadioGroup rgExpType;
    private EditText etDescription, etMinNumOfTrials;

    private Button btPickLocation;
    private CheckBox cbRequireLocation;
    private Button btPublish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_experiment);

        // get passed-in arguments: .user
        Intent intent = getIntent();
        owner = (User) intent.getSerializableExtra(".user");
        if (owner == null)
            throw new IllegalArgumentException("Expected '.user' passed-in through intent.");

        tvUsername          = findViewById(R.id.Button_search);
        rgExpType           = findViewById(R.id.RadioGroup_expType);
        etDescription       = findViewById(R.id.EditText_description);
        etMinNumOfTrials    = findViewById(R.id.EditText_minNumOfTrials);

        btPickLocation      = findViewById(R.id.Button_pickLocation);
        cbRequireLocation   = findViewById(R.id.CheckBox_requireLocation);
        btPublish           = findViewById(R.id.Button_publish);

        btPickLocation.setOnClickListener(this::btPickLocationOnClick);
        btPublish.setOnClickListener(this::btPublishOnClick);

        tvUsername.setText("New Experiment for: " + owner.getUsername());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHILD_LOCATION_PICKER:
                if (resultCode == Activity.RESULT_OK)
                    selectedLocation = (MyLocation) data.getSerializableExtra(".location");
                break;
        }
    }

    public void btPickLocationOnClick(View v) {
        Intent intent = new Intent(this, LocationPicker.class);
        startActivityForResult(intent, CHILD_LOCATION_PICKER);
    }

    public void btPublishOnClick(View v) {
        String desc = etDescription.getText().toString();
        String numb = etMinNumOfTrials.getText().toString();

        if (desc.isEmpty() || numb.isEmpty()) {
            MyDialog.messageDialog(this, "Empty Fields", "Please provide all the fields present in the form.");
            return;
        }

        if (selectedLocation == null) {
            MyDialog.messageDialog(this, "No Region Picked", "Please provide a region by picking one.");
            return;
        }

        boolean requireLocation = cbRequireLocation.isChecked();

        int minTrials = Integer.parseInt(numb);
        Experiment newExp = null;

        int rbid = rgExpType.getCheckedRadioButtonId();
        switch (rbid) {
            case R.id.RadioButton_binomial:
                newExp = new BinomialExp(null, owner.getUsername(), new Date(), desc, selectedLocation, requireLocation, minTrials);
                break;
            case R.id.RadioButton_count:
                newExp = new CountExp(null, owner.getUsername(), new Date(), desc, selectedLocation, requireLocation, minTrials);
                break;
            case R.id.RadioButton_measurement:
                newExp = new MeasurementExp(null, owner.getUsername(), new Date(), desc, selectedLocation, requireLocation, minTrials);
                break;
            case R.id.RadioButton_nonnegative:
                newExp = new NonNegativeExp(null, owner.getUsername(), new Date(), desc, selectedLocation, requireLocation, minTrials);
                break;
            default:
                MyDialog.messageDialog(this, "No Experiment Type", "Please select an experiment type to continue.");
                return;
        }
        newExp.writeToDatabase();

        owner.addToOwnedExp(newExp);
        owner.writeToDatabase();
        finish();
    }
}
