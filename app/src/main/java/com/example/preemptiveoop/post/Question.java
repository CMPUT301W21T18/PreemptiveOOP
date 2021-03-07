package com.example.preemptiveoop.post;

import java.util.ArrayList;

public class Question extends Post {
    private ArrayList<Reply> replies;

    public Question() { }
    public Question(String targetExpId, String poster, String title, String body) {
        super(targetExpId, poster, title, body);
        replies = new ArrayList<>();
    }

    public ArrayList<Reply> getReplies() { return replies; }
}
