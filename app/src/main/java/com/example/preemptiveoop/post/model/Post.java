package com.example.preemptiveoop.post.model;

import java.util.Date;

/**
 * The Post class is a abstract class to be extended by other specific post types
 * (Question and Reply).
 */
public abstract class Post {
    protected String databaseId;

    private String targetExpId;
    private String poster;

    private String title;
    private String body;
    private Date creationDate;

    public Post() {}
    public Post(String databaseId, String targetExpId, String poster, String title, String body, Date creationDate) {
        this.databaseId = databaseId;

        this.targetExpId = targetExpId;
        this.poster = poster;

        this.title = title;
        this.body = body;
        this.creationDate = creationDate;
    }

    // getters
    public String getDatabaseId() { return databaseId; }

    public String getTargetExpId() { return targetExpId; }
    public String getPoster() { return poster; }

    public String getTitle() { return title; }
    public String getBody() { return body; }
    public Date getCreationDate() { return creationDate; }
}
