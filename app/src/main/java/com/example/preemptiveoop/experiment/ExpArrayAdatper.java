package com.example.preemptiveoop.experiment;

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

import java.util.ArrayList;

public class ExpArrayAdatper extends ArrayAdapter<Experiment> {
    private Context context;
    private ArrayList<Experiment> experiments;

    public ExpArrayAdatper(Context context, ArrayList<Experiment> experiments) {
        super(context, 0, experiments);
        this.context = context;
        this.experiments = experiments;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // return super.getView(position, convertView, parent);
        View view = convertView;
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.activity_experiment_list_content, parent, false);

        Experiment exp = experiments.get(position);

        TextView tvOwner = view.findViewById(R.id.TextView_owner);
        TextView tvDescr = view.findViewById(R.id.TextView_description);
        TextView tvRegion = view.findViewById(R.id.TextView_region);
        TextView tvMinTrial = view.findViewById(R.id.TextView_numOfTrials);

        tvOwner.setText(exp.getOwner());
        tvDescr.setText(exp.getDescription());
        tvRegion.setText(exp.getRegion().toString());
        tvMinTrial.setText(exp.getTrials().size());
        return view;
    }
}
