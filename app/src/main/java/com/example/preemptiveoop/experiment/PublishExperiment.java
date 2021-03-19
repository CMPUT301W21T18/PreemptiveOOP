package com.example.preemptiveoop.experiment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.preemptiveoop.R;
import com.example.preemptiveoop.experiment.model.BinomialExp;
import com.example.preemptiveoop.experiment.model.Experiment;
import com.example.preemptiveoop.uiwidget.MyDialog;
import com.example.preemptiveoop.user.model.User;

import java.util.Date;

public class PublishExperiment extends DialogFragment {
    private User owner;

    private TextView tvUsername;
    private EditText etDescription;
    private EditText etMinNumOfTrials;
    private Button btPickLocation;

    public PublishExperiment(User owner) {
        super();
        this.owner = owner;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // return super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_publish_experiment, null);

        tvUsername          = view.findViewById(R.id.TextView_username);
        etDescription       = view.findViewById(R.id.EditText_description);
        etMinNumOfTrials    = view.findViewById(R.id.EditText_minNumOfTrials);
        btPickLocation      = view.findViewById(R.id.Button_pickLocation);

        tvUsername.setText("New Experiment for: " + owner.getUsername());

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view).setTitle("Publish New Experiment");
        builder.setNegativeButton("Cancel", null);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String desc = etDescription.getText().toString();
                String numb = etMinNumOfTrials.getText().toString();

                if (desc.equals("") || numb.equals("")) {
                    MyDialog.errorDialog(getActivity(),
                            "Empty Fields",
                            "Please provide all the fields present in the form."
                    );
                    return;
                }

                int minTrials = Integer.parseInt(numb);

                Experiment newExp = new BinomialExp(null, owner.getUsername(), new Date(), desc, null, false, minTrials);
                newExp.writeToDatabase();

                owner.addToOwnedExp(newExp);
                owner.writeToDatabase();

                ((ExperimentList) getActivity()).readExpFromDatabase(owner.getOwnedExpIdList());
            }
        });
        return builder.create();
    }
}