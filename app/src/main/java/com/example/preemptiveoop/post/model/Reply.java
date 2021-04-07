package com.example.preemptiveoop.post.model;

import java.util.Date;

/**
 * The Reply class is used to store and manage the reply posted to an question.
 * This class is Firestore compatible.
 */
public class Reply extends Post {
    Reply() {}
    public Reply(String targetExpId, String poster, String title, String body, Date creationDate) {
        super(null, targetExpId, poster, title, body, creationDate);
    }
}
