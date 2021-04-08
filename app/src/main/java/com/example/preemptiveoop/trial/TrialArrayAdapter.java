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
import com.example.preemptiveoop.trial.model.GenericTrial;

import java.util.ArrayList;

/**
 * The TrialArrayAdapter class is an ArrayAdapter class to be used by TrialList for displaying a
 * single item in the list.
 */
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



        tvCreator.setText("\n\n    \uD83E\uDDD0Creator:          " + trial.getCreator()+'\n');
        tvCreationDate.setText("    \uD83D\uDD58Created on:   " + trial.getCreationDate().toString()+"\n");

        if (trial.getLocation() != null)
            tvLocation.setText("    \uD83D\uDEA9Location:       " + trial.getLocation().toString()+"\n");
        else
            tvLocation.setText("    \uD83D\uDEA9Location:       " + "none"+"\n");

        tvResult.setText("    \uD83D\uDCDDResult:           " + trial.getResultStr()+"\n");
        tvIsIgnored.setText("    ❎Ignored:         " + trial.isIgnored());
        return view;
    }
}