package com.example.preemptiveoop.experiment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.preemptiveoop.R;
import com.example.preemptiveoop.experiment.model.Experiment;
import com.example.preemptiveoop.user.model.User;

public class ManageExperiment extends DialogFragment {
    private Experiment experiment;
    private User user;

    private Button btStats, btParti, btDoTrial, btUnpublish;

    public ManageExperiment(Experiment experiment, User user) {
        super();
        this.experiment = experiment;
        this.user = user;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_manage_experiment, null);

        btStats     = view.findViewById(R.id.Button_stats);
        btParti     = view.findViewById(R.id.Button_participate);
        btDoTrial   = view.findViewById(R.id.Button_doTrial);
        btUnpublish = view.findViewById(R.id.Button_unpublish);



        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view).setTitle("Manage Experiment");

        return builder.create();
    }

    public void btStatsOnClick(View v) {

    }
    public void btPartiOnClick(View v) {

    }
    public void btDoTrialOnClick(View v) {
        
    }
    public void btUnPublish(View v) {

    }


}
