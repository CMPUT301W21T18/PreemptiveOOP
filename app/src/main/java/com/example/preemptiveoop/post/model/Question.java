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

    /**
     * Add a reply to this question.
     * @param reply The reply to add.
     */
    public void addReply(Reply reply) { replies.add(reply); }
    public ArrayList<Reply> getReplies() { return replies; }

    /**
     * Write all fields in this Question class to the Firestore database. Existing fields are
     * updated accordingly.
     */
    public void writeToDatabase() {
        CollectionReference postCol = FirebaseFirestore.getInstance().collection("Posts");

        if (databaseId == null)
            databaseId = postCol.document().getId();
        postCol.document(databaseId).set(this, SetOptions.merge());
    }
}
