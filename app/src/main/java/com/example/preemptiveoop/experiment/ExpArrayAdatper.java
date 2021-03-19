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
import com.example.preemptiveoop.user.model.User;

import java.util.ArrayList;

public class ExpArrayAdatper extends ArrayAdapter<Experiment> {
    private Context context;
    private ArrayList<Experiment> experiments;
    private User user;

    public ExpArrayAdatper(Context context, ArrayList<Experiment> experiments, User user) {
        super(context, 0, experiments);
        this.context = context;
        this.experiments = experiments;
        this.user = user;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // return super.getView(position, convertView, parent);
        View view = convertView;
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.activity_experiment_list_content, parent, false);

        Experiment exp = experiments.get(position);

        TextView tvOwner        = view.findViewById(R.id.TextView_owner);
        TextView tvCreationDate = view.findViewById(R.id.TextView_creationDate);
        TextView tvDescr        = view.findViewById(R.id.TextView_description);

        TextView tvStatus       = view.findViewById(R.id.TextView_status);
        TextView tvIsParti      = view.findViewById(R.id.TextView_isParticipated);

        tvOwner.setText("Owner: " + exp.getOwner());
        tvCreationDate.setText("Created on: " + exp.getCreationDate().toString());
        tvDescr.setText("Description: " + exp.getDescription());

        tvStatus.setText("Status: " + exp.getStatus());
        tvIsParti.setText("Participated: " + new Boolean(exp.getExperimenters().contains(user.getUsername())));
        return view;
    }
}