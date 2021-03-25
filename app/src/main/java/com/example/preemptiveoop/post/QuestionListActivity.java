package com.example.preemptiveoop.post;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.preemptiveoop.R;
import com.example.preemptiveoop.experiment.model.Experiment;
import com.example.preemptiveoop.user.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class QuestionListActivity extends AppCompatActivity {

    private FloatingActionButton add_question;

    private Intent intent;

    private User user;
    private Experiment experiment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        intent = getIntent();
        experiment = (Experiment) intent.getSerializableExtra(".experiment");
        user = (User) intent.getSerializableExtra(".user");

        add_question = findViewById(R.id.Button_post_question);

        add_question.setOnClickListener(this::btAddQuestionOnClick);
    }

    public void btAddQuestionOnClick(View v) {
        PostQuestionFragment fragment = new PostQuestionFragment(user, experiment.getDatabaseId());
        fragment.show(getSupportFragmentManager(), "POST QUESTION");
    }
}