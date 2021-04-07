package com.example.preemptiveoop.post;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;;

import com.example.preemptiveoop.R;
import com.example.preemptiveoop.experiment.model.Experiment;
import com.example.preemptiveoop.post.model.Question;
import com.example.preemptiveoop.user.model.User;

/**
 * The ManageQuestionFragment class is the fragment class that builds and manages the UI for the user
 * to manage a question.
 */
public class ManageQuestionFragment extends DialogFragment {
    private User user;
    private Experiment experiment;
    private Question question;

    private Button btViewReply;
    private Button btReply;

    ManageQuestionFragment(User user, Experiment experiment, Question question) {
        super();
        this.user = user;
        this.experiment = experiment;
        this.question = question;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_manage_question, null);

        btViewReply = view.findViewById(R.id.Button_view_reply);
        btReply = view.findViewById(R.id.Button_add_reply);

        btViewReply.setOnClickListener(this::btViewReplyOnClick);
        btReply.setOnClickListener(this::btReplyOnClick);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);

        return builder.create();
    }

    public void btViewReplyOnClick(View v) {
        ReplyListFragment fragment = new ReplyListFragment(experiment, user, question);
        fragment.show(getActivity().getSupportFragmentManager(), "VIEW REPLY");
        dismiss();
    }

    public void btReplyOnClick(View v) {
        PostQuestionReplyFragment fragment = new PostQuestionReplyFragment(experiment, user, question);
        fragment.show(getActivity().getSupportFragmentManager(), "REPLY QUESTION");
        dismiss();
    }
}
