package com.example.preemptiveoop.post;

public abstract class Post {
    private String targetExpId;
    private String poster;

    private String title;
    private String body;

    public Post() {}
    public Post(String targetExpId, String poster, String title, String body) {
        this.targetExpId = targetExpId;
        this.poster = poster;
        this.title = title;
        this.body = body;
    }

    public String getTargetExpId() { return targetExpId; }
    public String getPoster() { return poster; }
    public String getTitle() { return title; }
    public String getBody() { return body; }
}
