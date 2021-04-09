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
import com.example.preemptiveoop.scan.CreateCodeActivity;
import com.example.preemptiveoop.trial.ExecuteTrial;
import com.example.preemptiveoop.trial.TrialList;
import com.example.preemptiveoop.uiwidget.TrialLocationsDisp;
import com.example.preemptiveoop.user.model.User;

/**
 * The ManageExperiment class is the fragment class that builds and manages the UI for the user to
 * manage an experiment.
 */
public class ManageExperiment extends DialogFragment {
    private Experiment experiment;
    private User user;

    private Button btTrials, btTrialLocations, btStats;
    private Button btParti, btDoTrial,btQRcode, btBarcode;
    private Button btEndExp, btUnpublish, btQuestion;

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

        btTrials         = view.findViewById(R.id.Button_trials);
        btTrialLocations = view.findViewById(R.id.Button_viewTrialLocations);
        btStats          = view.findViewById(R.id.Button_stats);

        btParti     = view.findViewById(R.id.Button_participate);
        btDoTrial   = view.findViewById(R.id.Button_doTrial);
        btQRcode    = view.findViewById(R.id.Button_qr_code);
        btBarcode   = view.findViewById(R.id.Button_barcode);

        btEndExp    = view.findViewById(R.id.Button_endExp);
        btUnpublish = view.findViewById(R.id.Button_unpublish);
        btQuestion  = view.findViewById(R.id.Button_view_question);

        btTrials.setOnClickListener(this::btTrialsOnClick);
        btTrialLocations.setOnClickListener(this::btTrialLocationsOnClick);
        btStats.setOnClickListener(this::btStatsOnClick);

        btParti.setOnClickListener(this::btPartiOnClick);
        btDoTrial.setOnClickListener(this::btDoTrialOnClick);
        btQRcode.setOnClickListener(this::btQrcodeOnClick);
        btBarcode.setOnClickListener(this::btBarcodeOnClick);

        btEndExp.setOnClickListener(this::btEndExperimentOnClick);
        btUnpublish.setOnClickListener(this::btUnPublishOnClick);
        btQuestion.setOnClickListener(this::btViewQuestionOnClick);

        setViewsAccordingly();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);

        return builder.create();
    }

    private void setViewsAccordingly() {
        if (!experiment.getOwner().equals(user.getUsername())) {
            btTrials.setVisibility(View.GONE);
            btEndExp.setVisibility(View.GONE);
            btUnpublish.setVisibility(View.GONE);
        }

        if (experiment.getExperimenters().contains(user.getUsername()))
            btParti.setVisibility(View.GONE);
        else
            btDoTrial.setVisibility(View.GONE);

        if (!experiment.isRequireLocation())
            btTrialLocations.setVisibility(View.GONE);
    }

    private void endThisFragment() {
        getFragmentManager().beginTransaction().remove(ManageExperiment.this).commit();
    }

    public void btTrialsOnClick(View v) {
        Intent i = new Intent(getActivity(), TrialList.class);
        i.putExtra(".experiment", experiment);
        startActivity(i);

        ((ExperimentList) getActivity()).updateExperimentList();
        endThisFragment();
    }

    public void btTrialLocationsOnClick(View v) {
        Intent i = new Intent(getActivity(), TrialLocationsDisp.class);
        i.putExtra(".trials", experiment.getTrials());
        startActivity(i);
    }

    public void btStatsOnClick(View v) {
        Intent i = new Intent(getActivity(),DisplayExpStats.class);
        i.putExtra(".experiment", experiment);
        startActivity(i);

        ((ExperimentList) getActivity()).updateExperimentList();
        endThisFragment();
    }

    public void btPartiOnClick(View v) {
        experiment.addExperimenter(user.getUsername());
        experiment.writeToDatabase();

        user.participateExp(experiment);
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

    public void btQrcodeOnClick(View v) {
        Intent intent = new Intent(getActivity(), CreateCodeActivity.class);
        intent.putExtra(".experiment", experiment);
        intent.putExtra(".user", user);
        intent.putExtra(".type", CreateCodeActivity.TYPE_CREATE_QRCODE);
        startActivity(intent);
        endThisFragment();
    }

    public void btBarcodeOnClick(View v) {
        Intent intent = new Intent(getActivity(), CreateCodeActivity.class);
        intent.putExtra(".experiment", experiment);
        intent.putExtra(".user", user);
        intent.putExtra(".type", CreateCodeActivity.TYPE_CREATE_BARCODE);
        startActivity(intent);
        endThisFragment();
    }
}
