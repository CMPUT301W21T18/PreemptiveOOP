package com.example.preemptiveoop.post.model;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Date;

/**
 * The Question class is used to store and manage the question posted to an experiment.
 * This class is Firestore compatible.
 */
public class Question extends Post {
    private ArrayList<Reply> replies;

    public Question() {}
    public Question(String databaseId, String targetExpId, String poster, String title, String body, Date creationDate) {
        super(databaseId, targetExpId, poster, title, body, creationDate);
        replies = new ArrayList<>();
    }

    public void addReply(Reply reply) { replies.add(reply); }
    public ArrayList<Reply> getReplies() { return replies; }

    public void writeToDatabase() {
        CollectionReference postCol = FirebaseFirestore.getInstance().collection("Posts");

        if (databaseId == null)
            databaseId = postCol.document().getId();
        postCol.document(databaseId).set(this, SetOptions.merge());
    }
}
