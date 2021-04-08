package com.example.preemptiveoop.post;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.preemptiveoop.R;
import com.example.preemptiveoop.experiment.model.Experiment;
import com.example.preemptiveoop.post.model.Question;
import com.example.preemptiveoop.post.model.Reply;
import com.example.preemptiveoop.user.model.User;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

/**
 * The PostQuestionReplyFragment class is the fragment class that builds and manages the UI for the user
 * to post a question or a reply.
 */
public class PostQuestionReplyFragment extends DialogFragment {
    private Experiment experiment;
    private User user;
    private Question question;

    private EditText etQuestionTitle;
    private EditText etQuestionBody;

    PostQuestionReplyFragment(Experiment experiment, User user, Question question) {    // question = null for posting new question
        super();
        this.experiment = experiment;
        this.user = user;
        this.question = question;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_post_question, null);

        etQuestionTitle = view.findViewById(R.id.EditText_question_title);
        etQuestionBody = view.findViewById(R.id.EditText_question_body);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        if (question == null)
            builder.setView(view).setTitle("Post Question");
        else
            builder.setView(view).setTitle("Reply the Question");

        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = etQuestionTitle.getText().toString();
                String body = etQuestionBody.getText().toString();

                if (question != null) {
                    Reply newReply = new Reply(experiment.getDatabaseId(), user.getUsername(), title, body, new Date());
                    question.addReply(newReply);

                    // update database
                    FirebaseFirestore.getInstance().collection("Posts").document(question.getDatabaseId())
                            .update("replies", FieldValue.arrayUnion(newReply));
                }
                else {
                    Question newQuest = new Question(null, experiment.getDatabaseId(), user.getUsername(), title, body, new Date());
                    newQuest.writeToDatabase();
                }
                ((QuestionListActivity) getActivity()).updateList();
            }
        });
        builder.setNegativeButton("Cancel", null);

        return builder.create();
    }
}
