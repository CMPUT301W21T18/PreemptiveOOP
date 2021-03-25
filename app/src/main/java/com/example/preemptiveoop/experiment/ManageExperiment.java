package com.example.preemptiveoop.experiment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.preemptiveoop.R;
import com.example.preemptiveoop.experiment.model.Experiment;
import com.example.preemptiveoop.post.QuestionListActivity;
import com.example.preemptiveoop.trial.ExecuteTrial;
import com.example.preemptiveoop.uiwidget.MyDialog;
import com.example.preemptiveoop.user.model.User;

public class ManageExperiment extends DialogFragment {
    private Experiment experiment;
    private User user;

    private Button btStats, btParti, btDoTrial;
    private Button btEndExp, btUnpublish, btViewQuestion;

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

        btEndExp = view.findViewById(R.id.Button_endExp);
        btUnpublish = view.findViewById(R.id.Button_unpublish);
        btViewQuestion = view.findViewById(R.id.Button_view_question);

        btStats.setOnClickListener(this::btStatsOnClick);
        btParti.setOnClickListener(this::btPartiOnClick);
        btDoTrial.setOnClickListener(this::btDoTrialOnClick);

        btEndExp.setOnClickListener(this::btEndExperimentOnClick);
        btUnpublish.setOnClickListener(this::btUnPublishOnClick);
        btViewQuestion.setOnClickListener(this::btViewQuestionOnClick);

        if (!experiment.getOwner().equals(user.getUsername())) {
            btEndExp.setVisibility(View.GONE);
            btUnpublish.setVisibility(View.GONE);
        }

        if (experiment.getExperimenters().contains(user.getUsername()))
            btParti.setVisibility(View.GONE);
        else
            btDoTrial.setVisibility(View.GONE);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view).setTitle("Manage Experiment");

        return builder.create();
    }

    private void endThisFragment() {
        getFragmentManager().beginTransaction().remove(ManageExperiment.this).commit();
    }

    public void btStatsOnClick(View v) {
        Intent i = new Intent(getActivity(),DisplayExpStats.class);
        i.putExtra(".experiment",experiment);
        startActivity(i);

    }

    public void btPartiOnClick(View v) {
        experiment.addExperimenter(user.getUsername());
        experiment.writeToDatabase();

        user.addToOwnedExp(experiment);
        user.writeToDatabase();

        ((ExperimentList) getActivity()).updateExperimentList();
        endThisFragment();
    }

    public void btDoTrialOnClick(View v) {
        Intent intent = new Intent(getActivity(), ExecuteTrial.class);
        intent.putExtra(".experiment", experiment);
        intent.putExtra(".user", user);
        startActivity(intent);

        ((ExperimentList) getActivity()).updateExperimentList();
        endThisFragment();
    }

    public void btEndExperimentOnClick(View v) {
        experiment.setStatus(Experiment.STATUS_ENDED);
        experiment.writeToDatabase();

        ((ExperimentList) getActivity()).updateExperimentList();
        endThisFragment();
    }

    public void btUnPublishOnClick(View v) {
        experiment.setStatus(Experiment.STATUS_UNPUBLISHED);
        experiment.writeToDatabase();

        ((ExperimentList) getActivity()).updateExperimentList();
        endThisFragment();
    }

    public void btViewQuestionOnClick(View v) {
        Intent intent = new Intent(getActivity(), QuestionListActivity.class);
        intent.putExtra(".experiment", experiment);
        intent.putExtra(".user", user);
        startActivity(intent);

        ((ExperimentList) getActivity()).updateExperimentList();
        endThisFragment();
    }



}