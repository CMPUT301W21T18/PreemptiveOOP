package com.example.preemptiveoop.post;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.preemptiveoop.R;
import com.example.preemptiveoop.experiment.model.Experiment;
import com.example.preemptiveoop.post.model.Question;
import com.example.preemptiveoop.user.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * The QuestionListActivity class is the activity class that builds and manages the UI for listing
 * questions for an experiment.
 */
public class QuestionListActivity extends AppCompatActivity {
    private User user;
    private Experiment experiment;

    private ArrayList<Question> questions;
    private ArrayAdapter<Question> postAdapter;

    private FloatingActionButton add_question;
    private ListView postListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        // get pass-in arguments
        Intent intent = getIntent();
        experiment = (Experiment) intent.getSerializableExtra(".experiment");
        user = (User) intent.getSerializableExtra(".user");

        // obtain the element of view
        add_question = findViewById(R.id.Button_post_question);
        postListView = findViewById(R.id.ListView_questions);

        // apply customize adapter
        questions = new ArrayList<>();
        postAdapter = new PostArrayAdapter(this, questions);
        postListView.setAdapter(postAdapter);

        add_question.setOnClickListener(this::btAddQuestionOnClick);
        postListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ManageQuestionFragment fragment = new ManageQuestionFragment(user, experiment, questions.get(position));
                fragment.show(getSupportFragmentManager(), "MANAGE QUESTION");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateList();
    }

    public void updateList() {
        CollectionReference postCol = FirebaseFirestore.getInstance().collection("Posts");

        postCol.whereEqualTo("targetExpId", experiment.getDatabaseId()).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // clear the old list and inject new item
                        questions.clear();
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            Question que = doc.toObject(Question.class);
                            questions.add(que);
                        }

                        Collections.sort(questions, new Comparator<Question>() {
                            @Override
                            public int compare(Question o1, Question o2) { return o2.getCreationDate().compareTo(o1.getCreationDate()); }
                        });
                        postAdapter.notifyDataSetChanged();
                    }
                });
    }

    public void btAddQuestionOnClick(View v) {
        PostQuestionReplyFragment fragment = new PostQuestionReplyFragment(experiment, user, null);
        fragment.show(getSupportFragmentManager(), "POST_QUESTION");
    }
}
