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
import com.example.preemptiveoop.post.model.Post;
import com.example.preemptiveoop.post.model.Question;
import com.example.preemptiveoop.user.model.User;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class PostQuestionFragment extends DialogFragment {

    private User user;
    private String expId;

    private EditText etQuestionTitle;
    private EditText etQuestionBody;

    PostQuestionFragment(User user, String expId) {
        super();
        this.user = user;
        this.expId = expId;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_post_question, null);

        etQuestionTitle = view.findViewById(R.id.EditText_question_title);
        etQuestionBody = view.findViewById(R.id.EditText_question_body);

        CollectionReference postCol = FirebaseFirestore.getInstance().collection("Posts");

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view).setTitle("Post Question");
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = etQuestionTitle.getText().toString();
                String body = etQuestionBody.getText().toString();

                Post new_que = new Question(expId, user.getUsername(), title, body);

                String id = postCol.document().getId();
                postCol.document(id).set(new_que);

            }
        });
        return builder.create();
    }
}
