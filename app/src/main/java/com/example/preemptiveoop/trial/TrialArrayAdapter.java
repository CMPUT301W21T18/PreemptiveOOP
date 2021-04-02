package com.example.preemptiveoop.trial;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.preemptiveoop.R;
import com.example.preemptiveoop.experiment.model.Experiment;
import com.example.preemptiveoop.trial.model.GenericTrial;
import com.example.preemptiveoop.user.model.User;

import java.util.ArrayList;

public class TrialArrayAdapter extends ArrayAdapter<GenericTrial> {
    private Context context;
    private ArrayList<GenericTrial> trials;

    public TrialArrayAdapter(Context context, ArrayList<GenericTrial> trials) {
        super(context, 0, trials);
        this.context = context;
        this.trials = trials;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // return super.getView(position, convertView, parent);
        View view = convertView;
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.activity_trial_list_content, parent, false);

        GenericTrial trial = trials.get(position);

        TextView tvCreator      = view.findViewById(R.id.TextView_creator);
        TextView tvCreationDate = view.findViewById(R.id.TextView_creationDate);
        TextView tvLocation     = view.findViewById(R.id.TextView_location);

        TextView tvResult       = view.findViewById(R.id.TextView_result);
        TextView tvIsIgnored    = view.findViewById(R.id.TextView_isIgnored);

        tvCreator.setText("Creator: " + trial.getCreator());
        tvCreationDate.setText("Created on: " + trial.getCreationDate().toString());

        if (trial.getLocation() != null)
            tvLocation.setText("Location: " + trial.getLocation().toString());
        else
            tvLocation.setText("Location: " + "none");

        tvResult.setText("Result: " + trial.getResultStr());
        tvIsIgnored.setText("Ignored: " + trial.isIgnored());
        return view;
    }
}