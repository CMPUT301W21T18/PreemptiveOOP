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
import com.example.preemptiveoop.post.model.Post;
import com.example.preemptiveoop.post.model.Question;
import com.example.preemptiveoop.post.model.Reply;
import com.example.preemptiveoop.user.model.User;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class PostQuestionReplyFragment extends DialogFragment {

    private User user;
    private Experiment experiment;
    private Question question;
    private int type;

    private EditText etQuestionTitle;
    private EditText etQuestionBody;

    PostQuestionReplyFragment(User user, Experiment experiment, Question question, int type) {
        super();
        this.user = user;
        this.experiment = experiment;
        this.question = question;
        this.type = type; // 0 for question and 1 for reply
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_post_question, null);

        etQuestionTitle = view.findViewById(R.id.EditText_question_title);
        etQuestionBody = view.findViewById(R.id.EditText_question_body);

        CollectionReference postCol = FirebaseFirestore.getInstance().collection("Posts");

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        if (type == 0)
            builder.setView(view).setTitle("Post Question");
        else
            builder.setView(view).setTitle("Reply the Question");
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = etQuestionTitle.getText().toString();
                String body = etQuestionBody.getText().toString();
                if (type == 0) {
                    Question new_que = new Question(experiment.getDatabaseId(), user.getUsername(), title, body);
                    new_que.writeToDatabase();
                } else {
                    Reply new_reply = new Reply(experiment.getDatabaseId(), user.getUsername(), question.getDbID(), title, body);
                    question.addReply(new_reply);
                    question.writeToDatabase();
                }
                ((QuestionListActivity)getActivity()).updateList();
            }
        });


        return builder.create();
    }
}
