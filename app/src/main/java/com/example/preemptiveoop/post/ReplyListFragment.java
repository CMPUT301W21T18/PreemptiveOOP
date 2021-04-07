package com.example.preemptiveoop.post;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.preemptiveoop.R;
import com.example.preemptiveoop.experiment.model.Experiment;
import com.example.preemptiveoop.post.model.Question;
import com.example.preemptiveoop.post.model.Reply;
import com.example.preemptiveoop.user.model.User;

import java.util.ArrayList;

/**
 * The ReplyListFragment class is the fragment class that builds and manages the UI for listing
 * replies to a question.
 */
public class ReplyListFragment extends DialogFragment {
    private Experiment experiment;
    private User user;
    private Question question;

    private ArrayList<Reply> replies;
    private ArrayAdapter<Reply> postAdapter;

    private ListView postListView;

    public ReplyListFragment(Experiment experiment, User user, Question question) {
        super();
        this.experiment = experiment;
        this.user = user;
        this.question = question;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_reply_list, null);

        postListView = view.findViewById(R.id.ListView_reply);

        replies = question.getReplies();
        postAdapter = new PostArrayAdapter(getContext(), replies);
        postListView.setAdapter(postAdapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view).setTitle("Reply List");

        return builder.create();
    }
}
