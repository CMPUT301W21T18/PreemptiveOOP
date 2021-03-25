package com.example.preemptiveoop.post.model;

import java.util.Date;

public abstract class Post {
    private String targetExpId;
    private String poster;

    private String title;
    private String body;
    private Date creationDate;

    public Post() {}
    public Post(String targetExpId, String poster, String title, String body) {
        this.targetExpId = targetExpId;
        this.poster = poster;
        this.title = title;
        this.body = body;
        this.creationDate = new Date();
    }

    public String getTargetExpId() { return targetExpId; }
    public String getPoster() { return poster; }
    public String getTitle() { return title; }
    public String getBody() { return body; }
    public Date getCreationDate() { return creationDate; }
}
