package com.example.preemptiveoop.post.model;

import java.util.Date;

public class Reply extends Post {
    Reply() {}
    public Reply(String targetExpId, String poster, String title, String body, Date creationDate) {
        super(null, targetExpId, poster, title, body, creationDate);
    }
}
