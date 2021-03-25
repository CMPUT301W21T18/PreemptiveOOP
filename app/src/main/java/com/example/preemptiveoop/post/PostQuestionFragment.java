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

    private EditText edit_title;
    private EditText edit_body;

    PostQuestionFragment(User user, String expId) {
        super();
        this.user = user;
        this.expId = expId;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_post_question, null);

        edit_title = view.findViewById(R.id.editText_question_title);
        edit_body = view.findViewById(R.id.editText_question_body);

        CollectionReference postCol = FirebaseFirestore.getInstance().collection("Posts");

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view).setTitle("Post Question");
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = edit_title.getText().toString();
                String body = edit_body.getText().toString();

                Post new_que = new Question(expId, user.getUsername(), title, body);

                String id = postCol.document().getId();
                postCol.document(id).set(new_que);

            }
        });
        return builder.create();
    }
}
