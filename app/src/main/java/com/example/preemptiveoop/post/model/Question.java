package com.example.preemptiveoop.post.model;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;

public class Question extends Post {
    private ArrayList<Reply> replies;

    public Question() { }
    public Question(String targetExpId, String poster, String title, String body) {
        super(targetExpId, poster, title, body);
        replies = new ArrayList<>();
    }

    public ArrayList<Reply> getReplies() { return replies; }

    public void writeToDatabase() {
        CollectionReference postCol = FirebaseFirestore.getInstance().collection("Posts");

        if (dbID == null)     // if yes, assign a new id
            dbID = postCol.document().getId();
        postCol.document(dbID).set(this, SetOptions.merge());
    }

    public void addReply(Reply reply) {
        replies.add(reply);
    }
}
